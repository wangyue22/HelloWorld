package com.cmos.edccommon.web.fileupdown;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.cmos.common.exception.GeneralException;
import com.cmos.common.remote.Response;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.edccommon.beans.common.GetGztPhotoDTO;
import com.cmos.edccommon.utils.Base64;
import com.cmos.edccommon.utils.HttpMultiPartUtil;
import com.cmos.edccommon.utils.StringUtil;
import com.cmos.edccommon.utils.consts.CacheConsts;
import com.cmos.edccommon.utils.consts.FileUpDownConstants;
import com.cmos.edccommon.web.cache.CacheFatctoryUtil;
import com.cmos.edccommon.web.remote.IRemoteGztFileSV;
import com.cmos.onest.ONestUtil;

/**
*
* @ClassName:  GztFileUpDownUtil
* @Description: 国政通文件上传下载工具类
* @author: 王海洋
* @date:   2017年11月16日 下午5:47:25
*/
@Component
public class GztFileDownloadUtil {
    @Autowired
    private Environment env;
    
    @Autowired
    private IRemoteGztFileSV remoteSV;

    private static final Logger logger = LoggerFactory.getActionLog(GztFileDownloadUtil.class);

    @Autowired
    private CacheFatctoryUtil cacheUtil;


    /**
     * 根据路径下载国政通图片，并转换为base64字符串返回
     * @param inPath
     * @return
     * @throws GeneralException
     */
    public String downloadGztPicBase64Str(String inPath, String provCode, String sourceCode, String sourceSys,
        String swftNo) throws GeneralException {
        String base64Str = null;
        byte[] bt = dowdloadGztPic(inPath, provCode, sourceCode, sourceSys, swftNo);
        if (bt != null) {
            base64Str = Base64.encode(bt);
        }
        return base64Str;
    }

    /**
     * 下载国政通头像
     * @param inPath 国政通头像路径
     * @param provCode 省编码
     * @param requestSource 请求源
     * @param sourceSys 来源系统名称
     * @param swftNo 流水号
     * @return
     * @throws GeneralException
     */
    @SuppressWarnings("rawtypes")
    public byte[] dowdloadGztPic(String inPath, String provCode, String sourceCode, String sourceSys, String swftNo)
        throws GeneralException {
        String path = inPath;
        if (StringUtil.isBlank(path)) {
            throw new GeneralException("2999", "下载路径不能为空！");
        } else {
            path = path.substring(path.indexOf("cert"));
        }

        // 获取onest开关，onest是否开放优先使用
        String onestSwitch = cacheUtil.getJVMString(CacheConsts.UPDOWN_JVM.GZT_FILE_ONEST_SWITCH);
        // 根据路径下载图片
        byte[] inputByte = null;
        if ("true".equals(onestSwitch)) {
            inputByte = downloadGztFileByOnest(env.getProperty("onest.gztfile.bucketname"), path);
        }

        try {
            if ("false".equals(onestSwitch) || inputByte == null) {
                // 如果onest优先未开启，或者如果下载失败，使用rnfs下载
                // 如果是rnfs，则根据路径获取文件服务器地址并上传
                Map<String, String> map = getGztRnfsServerNameByPath(path);
                String rnfsServerName = map.get("rnfsServerName");
                String lastServerName = map.get("lastServerName");
                inputByte = downGztFileByRnfs(path, rnfsServerName);
                if (inputByte == null && StringUtil.isNotBlank(lastServerName)) {
                    inputByte = downGztFileByRnfs(path, lastServerName);
                }
            }
        } catch (Exception e) {
            logger.error("*******downGztFileByRnfs error******");
        }
        
        if (inputByte == null) {
            GetGztPhotoDTO paramDto = new GetGztPhotoDTO();
            paramDto.setGztUrl(path);
            paramDto.setProvCode(provCode);
            paramDto.setReqstSrcCode(sourceCode);
            paramDto.setSourceSystem(sourceSys);
            paramDto.setSwftno(swftNo);
            Response response = remoteSV.getGztPhotoByPath(paramDto);
            String gztBase64Str = (String) response.getResult().getBean().get("gztBase64Str");
            if(StringUtil.isNotBlank(gztBase64Str)){
                inputByte = Base64.decode(gztBase64Str);
            }
        }
        return inputByte;
    }


    /**
     * 根据国政通头像路径获取rnfs服务器别名
     * @param path
     * @return
     * @throws GeneralException
     */
    private Map<String,String> getGztRnfsServerNameByPath(String path) throws GeneralException {
        Map<String,String> serverMap = new HashMap<String,String>();
        String lastServerName = "";
        // 分省获取文件服务器规则：
        // 根据路径解析，由于路径中有身份证前6位分成的3级目录，先截取出这3级目录
        // 然后由3级到1级逐级匹配
        // 先截取路径中的第9到16位，得到3级目录如： 41/10/81
        String switchStr = path.substring(path.indexOf("/")+1, path.lastIndexOf("/"));
        // 将斜杠替换为下划线
        switchStr = switchStr.replace("/", "_");// 41_10_81
        // 先全匹配获取rnfs主机别名
        String rnfsServerName = cacheUtil.getJVMString(CacheConsts.UPDOWN_JVM.GZT_FILE_SERVER_ + switchStr);
        // 如果获取到的地址为空，并且switchStr仍然包含下划线，（包含下划线意味着仍然有下一层级）
        while (StringUtil.isBlank(rnfsServerName) && switchStr.contains("_")) {
            switchStr = switchStr.substring(0, switchStr.lastIndexOf("_"));
            rnfsServerName = cacheUtil.getJVMString(CacheConsts.UPDOWN_JVM.GZT_FILE_SERVER_ + switchStr);
        }
        if(StringUtil.isNotBlank(rnfsServerName)){
            // 如果匹配到的rnfs主机名不为空，则还要取上一次配置的主机名
            lastServerName = cacheUtil.getJVMString(CacheConsts.UPDOWN_JVM.GZT_FILE_LAST_SERVER_ + switchStr);
        }else{
            rnfsServerName = cacheUtil.getJVMString(CacheConsts.UPDOWN_JVM.GZT_FILE_SERVER_DEFAULT);
        }
        serverMap.put("rnfsServerName", rnfsServerName);
        serverMap.put("lastServerName", lastServerName);
        return serverMap;
    }

    /**
     * 使用onest方式下载文件
     * @param bucketName
     * @param path
     * @return
     */
    private byte[] downloadGztFileByOnest(String bucketName, String path){
        InputStream result;
        byte[] resultByte = null;
        try {
            result = ONestUtil.download(bucketName, path);
            if(result!=null){
                resultByte = toByteArray(result);
            }
        } catch (Exception e) {
            logger.error("downloadByOnest error:",e);
        }
        return resultByte;
    }

    /**
     * 使用rnfs方式下载文件
     * @param path
     * @param serverName
     * @return
     * @throws GeneralException
     */
    @SuppressWarnings("rawtypes")
    private byte[] downGztFileByRnfs(String path , String serverName) throws GeneralException {
        // 根据serverName 找到缓存中的rnfs文件服务器配置信息 ,key 为 rnfs文件服务器别名
        Map serverInfoMap = cacheUtil.getJVMMap(CacheConsts.UPDOWN_JVM.FTP_CFG_PREFIX+serverName);

        String ipPort = (String)serverInfoMap.get("rnfsAddrPrtnum");
        String rootPath = (String)serverInfoMap.get("rootPath");
        String remotePath = rootPath + "/" + path;
        if (!remotePath.startsWith("/")) {
            remotePath = "/" + remotePath;
        }
        remotePath = remotePath.replaceAll("//", "/");// 将两个斜杠替换为一个
        if (StringUtil.isBlank(ipPort)) {
            throw new GeneralException("2999", "根据下载路径未找到相应下载节点ip，remotePathAndName=" + remotePath);
        }
        try {
            //            String isNginx = cacheUtil.getJVMString("ACMS_SWITCH:GZT_FILE_RNFS_TIME_OUT");
            String urlStr;
            //            if ("true".equalsIgnoreCase(isNginx)) {
            //                urlStr = "http://" + ipPort + newFileFullPath;
            // logger.info(String.format("nginx下载url=%s,filePath=%s", urlStr, newFileFullPath));
            //                return HttpMultiPartUtil.upDownPost(urlStr, null, null);
            //            }
            Map<String, String> textMap = new HashMap<String, String>();
            textMap.put("filePath", remotePath);
            urlStr = String.format(FileUpDownConstants.RNFS_URL, ipPort, FileUpDownConstants.DOWNLOAD, FileUpDownConstants.RNFS_URL_PARAM);
            String timeOut = cacheUtil.getJVMString(CacheConsts.UPDOWN_JVM.GZT_FILE_RNFS_TIME_OUT);
            if (timeOut != null && timeOut.trim().length() > 0) {
                HttpMultiPartUtil.setConnection_timeout(timeOut);
                HttpMultiPartUtil.setSo_timeout(timeOut);
                logger.info("download timeOut=" + timeOut);
            } else {
                HttpMultiPartUtil.setConnection_timeout(FileUpDownConstants.RNFS_TIME_OUT);
                HttpMultiPartUtil.setSo_timeout(FileUpDownConstants.RNFS_TIME_OUT);
                logger.info("download timeOut=" + FileUpDownConstants.RNFS_TIME_OUT);
            }
            logger.info(String.format("下载url=%s,filePath=%s", urlStr, remotePath));
            return HttpMultiPartUtil.upDownPost(urlStr, textMap, null);
        } catch (Exception e) {
            throw new GeneralException("2999", e.getMessage() + "downByRnfs error，remotePathAndName=" + remotePath, e);
        }
    }

    /**
     * 将输入流转换为byte数组
     * @param result
     * @return
     * @throws IOException
     */
    private byte[] toByteArray(InputStream result) throws IOException {
        byte[] resultByte;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] data = new byte[4096];
        int len;
        while ((len = result.read(data)) != -1) {
            baos.write(data, 0, len);
        }
        resultByte =baos.toByteArray();
        return resultByte;
    }
}
