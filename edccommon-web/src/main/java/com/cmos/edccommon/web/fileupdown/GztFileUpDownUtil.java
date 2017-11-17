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
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.edccommon.utils.Base64;
import com.cmos.edccommon.utils.HttpMultiPartUtil;
import com.cmos.edccommon.utils.StringUtil;
import com.cmos.edccommon.utils.consts.CacheConsts;
import com.cmos.edccommon.utils.consts.FileUpDownConstants;
import com.cmos.edccommon.web.cache.CacheFatctoryUtil;
import com.cmos.onest.ONestUtil;

/**
*
* @ClassName:  GztFileUpDownUtil
* @Description: 国政通文件上传下载工具类
* @author: 王海洋
* @date:   2017年11月16日 下午5:47:25
*/
@Component
public class GztFileUpDownUtil {
    @Autowired
    private Environment env;

    private static final Logger logger = LoggerFactory.getActionLog(GztFileUpDownUtil.class);

    @Autowired
    private CacheFatctoryUtil cacheUtil;

    /**
     * 上传国政通头像
     * @param path
     * @param base64Str
     * @param doubleWriteFlag
     * @return
     * @throws GeneralException
     */
    public Map<String,String> uploadGztPic(String path, String base64Str) throws GeneralException {
        Map<String,String> resMap = new HashMap<String,String>();
        // 获取onest开关，onest是否开放优先使用
        String onestSwitch = cacheUtil.getJVMString(CacheConsts.UPDOWN_JVM.GZT_FILE_ONEST_SWITCH);
        String onestUploadResult = FileUpDownConstants.GztFile.ONEST_NOT_UPLOAD;
        byte[] inputByte = Base64.decode(base64Str);
        if ("true".equals(onestSwitch)) {
            onestUploadResult = uploadGztFileByOnest(env.getProperty("onest.gztfile.bucketname"), path, inputByte);
            resMap.put("uploadType", FileUpDownConstants.GztFile.ONEST);
        }
        if ("false".equals(onestSwitch) || FileUpDownConstants.GztFile.ONEST_UPLOAD_FAILED.equals(onestUploadResult)) {
            String serverName = uploadGztFileByRnfs(path, inputByte);
            if(StringUtil.isNotBlank(serverName)){
                resMap.put("uploadType", FileUpDownConstants.GztFile.RNFS);
                resMap.put("rnfsServerName", serverName);
            }else{
                resMap.put("uploadType", FileUpDownConstants.GztFile.UPLOAD_FAILED);
            }
        }
        resMap.put("onestUploadResult", onestUploadResult);
        return resMap;
    }

    /**
     * 根据路径下载国政通图片，并转换为base64字符串返回
     * @param inPath
     * @return
     * @throws GeneralException
     */
    public String downloadGztPicBase64Str(String inPath) throws GeneralException{
        String base64Str = null;
        byte[] bt = dowdloadGztPic(inPath);
        if(bt!=null){
            base64Str = Base64.encode(bt);
        }
        return base64Str;
    }

    /**
     * 下载国政通头像
     * @param path
     * @return
     * @throws GeneralException
     */
    public byte[] dowdloadGztPic(String inPath) throws GeneralException {
        String path = inPath;
        if(StringUtil.isBlank(path)){
            throw new GeneralException("2999","下载路径不能为空！");
        }else{
            path = path.substring(path.indexOf("cert"));
        }

        // 获取onest开关，onest是否开放优先使用
        String onestSwitch = cacheUtil.getJVMString(CacheConsts.UPDOWN_JVM.GZT_FILE_ONEST_SWITCH);
        // 根据路径下载图片
        byte[] inputByte = null;
        if ("true".equals(onestSwitch)) {
            inputByte = downloadGztFileByOnest(env.getProperty("onest.gztfile.bucketname"), path);
        }
        if ("false".equals(onestSwitch) || inputByte==null) {
            // 如果onest优先未开启，或者如果下载失败，使用rnfs下载
            // 如果是rnfs，则根据路径获取文件服务器地址并上传
            Map<String, String> map = getGztRnfsServerNameByPath(path);
            String rnfsServerName = map.get("rnfsServerName");
            String lastServerName = map.get("lastServerName");
            inputByte = downGztFileByRnfs(path, rnfsServerName);
            if(inputByte==null && StringUtil.isNotBlank(lastServerName)){
                inputByte = downGztFileByRnfs(path, lastServerName);
            }
            if(inputByte == null){
                // 如果仍然下载失败，使用default节点下载
                String defaultRnfsServerName = cacheUtil.getJVMString(CacheConsts.UPDOWN_JVM.GZT_FILE_SERVER_DEFAULT);
                inputByte = downGztFileByRnfs(path, defaultRnfsServerName);
            }
        }
        if("false".equals(onestSwitch) && inputByte==null){
            //如果onest优先未开启，然后使用rnfs下载失败，则使用onset下载
            inputByte = downloadGztFileByOnest(env.getProperty("onest.gztfile.bucketname"), path);
        }
        return inputByte;
    }

    /**
     * 使用rnfs上传国政通头像
     * @param path
     * @param inputByte
     * @return
     * @throws GeneralException
     */
    @SuppressWarnings("rawtypes")
    public String uploadGztFileByRnfs(String path, byte[] inputByte) throws GeneralException {
        long begin = System.currentTimeMillis();
        // 如果是rnfs，则根据路径获取文件服务器地址并上传
        Map<String, String> map = getGztRnfsServerNameByPath(path);
        String rnfsServerName = map.get("rnfsServerName");
        // 根据serverName 找到缓存中的rnfs文件服务器配置信息 ,key 为 rnfs文件服务器别名
        Map serverInfoMap = cacheUtil.getJVMMap(CacheConsts.UPDOWN_JVM.FTP_CFG_PREFIX+rnfsServerName);
        String ipPort = (String)serverInfoMap.get("rnfsAddrPrtnum");
        String rootPath = (String)serverInfoMap.get("rootPath");
        String remotePath = rootPath + "/" + path;
        if (!remotePath.startsWith("/")) {
            remotePath = "/" + remotePath;
        }
        remotePath = remotePath.replaceAll("//", "/");// 将两个斜杠替换为一个
        String resStr = uploadByRnfs(inputByte, ipPort, remotePath);

        if ("0000".equals(resStr)) {
            long end = System.currentTimeMillis();
            logger.error(String.format("GZTFILE_UPLOAD_RNFS serverName=%s path=%s time=%s",rnfsServerName, path, String.valueOf(end - begin)));
            return rnfsServerName;
        }else{
            return null;
        }
    }

    /**
     * 使用onest方式上传文件
     * @param bucketName
     * @param path
     * @param inputByte
     * @return
     */
    public String uploadGztFileByOnest(String bucketName, String path, byte[] inputByte) {
        String onestUploadResult;
        // 如果是onest，上传后返回上传主机，onest方式上传返回onest，rnfs上传返回主机别名
        InputStream in = null;
        try {
            in = new ByteArrayInputStream(inputByte);
            ONestUtil.uploadAndGetPrivateUrl(bucketName, path, in);
            onestUploadResult = FileUpDownConstants.GztFile.ONEST_UPLOAD_SUC;
        } catch (Exception e) {
            logger.error("ONEST上传异常 onest error:", e);
            onestUploadResult = FileUpDownConstants.GztFile.ONEST_UPLOAD_FAILED;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                logger.error("close inputstream error:", e2);
            }
        }
        return onestUploadResult;
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
     * 使用rnfs方式上传文件
     * @param inputByte
     * @param ipPort
     * @param remotePath
     * @return
     * @throws GeneralException
     */
    private String uploadByRnfs(byte[] inputByte, String ipPort, String remotePath) throws GeneralException {
        Map<String, byte[]> fileMap = new HashMap<String, byte[]>();
        fileMap.put(remotePath, inputByte);

        if (StringUtil.isBlank(ipPort)) {
            throw new GeneralException("2999", "上传时未找到可用的服务，remotePathAndName=" + remotePath);
        }
        String urlStr = String.format(FileUpDownConstants.RNFS_URL, ipPort, FileUpDownConstants.UPLOAD, FileUpDownConstants.RNFS_URL_PARAM);
        logger.info("upload urlStr=" + urlStr + "  remotePathAndName=" + remotePath);
        String timeOut = cacheUtil.getJVMString(CacheConsts.UPDOWN_JVM.GZT_FILE_RNFS_TIME_OUT);
        if (timeOut != null && timeOut.trim().length() > 0) {
            HttpMultiPartUtil.setConnection_timeout(timeOut);
            HttpMultiPartUtil.setSo_timeout(timeOut);
            logger.info("upload timeOut=" + timeOut);
        } else {
            HttpMultiPartUtil.setConnection_timeout(FileUpDownConstants.RNFS_TIME_OUT);
            HttpMultiPartUtil.setSo_timeout(FileUpDownConstants.RNFS_TIME_OUT);
            logger.info("upload timeOut=" + FileUpDownConstants.RNFS_TIME_OUT);
        }
        String resStr = null;
        byte[] res = HttpMultiPartUtil.upDownPost(urlStr, null, fileMap);
        if (res != null) {
            resStr = new String(res);
            logger.error("upload res:" + resStr);
        } else {
            logger.error("upload res:is null; remotePath="+remotePath);
        }
        return resStr;
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
