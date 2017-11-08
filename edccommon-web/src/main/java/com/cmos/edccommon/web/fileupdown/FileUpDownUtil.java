package com.cmos.edccommon.web.fileupdown;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
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
 * @ClassName:  FileUpDownUtil
 * @Description: 文件上传下载工具类
 * @author: 王海洋
 * @date:   2017年11月1日 下午5:47:25
 */
@Component
@SuppressWarnings("rawtypes")
public class FileUpDownUtil {

    @Autowired
    private Environment env;

    private static final Logger logger = LoggerFactory.getActionLog(FileUpDownUtil.class);

    @Autowired
    private CacheFatctoryUtil cacheUtil;

    private Integer maxEnabled = 0;

    private Integer pos = 0;

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
        in = new ByteArrayInputStream(inputByte);
        try {
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
        int len = 0;
        while ((len = result.read(data)) != -1) {
            baos.write(data, 0, len);
        }
        resultByte =baos.toByteArray();
        return resultByte;
    }


    // --------------------------------------------------业务图片上传下载start----------------------------------------------------------------------

    /**
     *  字符串 文件上传方法
     * @param fileType P图片,W无纸化,V视频
     * @param relativePath  文件相对目录 例:10085file/371/
     * @param fileName   上传文件名 例：1008520171031144100098765_Z.jpg
     * @param content  文件ISO8859-1字符串
     * @return 文件记录url 例：aFtp/10085file/371/1008520171031144100098765_Z.jpg
     *
     */
    public String uploadBusiFileStr(String fileType, String relativePath, String fileName, String content)
            throws GeneralException {
        String onestFlag = cacheUtil.getJVMString(CacheConsts.UPDOWN_JVM.ONEST_UPDOWN_FILE_FALG);
        if ("true".equals(onestFlag)) {
            // 拼装全路径

            if (!relativePath.startsWith("/")) {
                relativePath = "/" + relativePath;
            }
            if (!relativePath.endsWith("/")) {
                relativePath = relativePath + "/";
            }

            String path = relativePath + fileName;
            path.replaceAll("//", "/");

            try {
                return uploadBusiByOnest(env.getProperty("onest.gztfile.bucketname"), path, content.getBytes(FileUpDownConstants.FILE_CHAR_SET));
            } catch (Exception e) {
                logger.error("onest upload getBytes error:", e);
            }

        } else {

            return uploadBusiStr(fileType, relativePath, fileName, content, null);
        }

        return null;
    }

    /**
     * byte 数组 文件上传方法
     * @param fileType P图片,W无纸化,V视频
     * @param relativePath 文件相对目录 例:10085file/371/
     * @param fileName 上传文件名 例：1008520171031144100098765_Z.jpg
     * @param inputByte 文件字节数组
     * @return 文件记录url 例：aFtp/10085file/371/1008520171031144100098765_Z.jpg
     * @throws GeneralException
     */
    public String uploadBusiFileByte(String fileType, String relativePath, String fileName, byte[] inputByte)
            throws GeneralException {

        String onestFlag = cacheUtil.getJVMString(CacheConsts.UPDOWN_JVM.ONEST_UPDOWN_FILE_FALG);
        if ("true".equals(onestFlag)) {
            // 拼装全路径
            if (!relativePath.startsWith("/")) {
                relativePath = "/" + relativePath;
            }
            if (!relativePath.endsWith("/")) {
                relativePath = relativePath + "/";
            }

            String path = relativePath + fileName;
            path.replaceAll("//", "/");

            try {
                return uploadBusiByOnest(env.getProperty("onest.busifile.bucketname"), path, inputByte);
            } catch (Exception e) {
                logger.error("onest upload getBytes error:", e);
            }

        } else {

            return uploadBusiByte(fileType, relativePath, fileName, inputByte, null);
        }

        return null;

    }

    /**
     * 根据文件存储路径下载文件字符串
     * @param remotePathAndName
     * @return
     * @throws BusiException
     */
    public String downloadBusiFileStr(String remotePathAndName) throws GeneralException {

        // 判断是否使用onest下载
        if (remotePathAndName.startsWith(FileUpDownConstants.ONEST_URL_PREFIX)) {
            byte[] file = downloadBusiByOnest(env.getProperty("onest.busifile.bucketname"),
                remotePathAndName.replaceAll(FileUpDownConstants.ONEST_URL_PREFIX, "").trim());
            try {
                return new String(file, FileUpDownConstants.FILE_CHAR_SET);
            } catch (Exception e) {
                throw new GeneralException("9999", e);
            }
        } else {

            return downloadBusiStr(remotePathAndName, null);
        }

    }

    /**
     * 根据文件路径下载 文件字节数组
     * @param remotePathAndName
     * 文件全路径
     * @return
     * @throws BusiException
     */
    public byte[] downloadBusiFileByte(String remotePathAndName) throws GeneralException {
        // 判断是否使用onest下载
        if (remotePathAndName.startsWith(FileUpDownConstants.ONEST_URL_PREFIX)) {
            try {
                return downloadBusiByOnest(env.getProperty("onest.busifile.bucketname"),
                    remotePathAndName.replaceAll(FileUpDownConstants.ONEST_URL_PREFIX, "").trim());
            } catch (Exception e) {
                throw new GeneralException("9999", e);
            }
        } else {

            return downloadBusiByte(remotePathAndName, null);
        }
    }

    /**
     * 有超时时间参数的文件上传方法
     * @param fileType P图片,W无纸化,V视频
     * @param filePath
     * @param fileName
     * @param inputStr 文件ISO8859-1字符串
     * @param timeOut 超时时间
     * @return 文件全路径
     * @throws BusiException
     */
    public String uploadBusiStr(String fileType, String relativePath, String fileName, String inputStr,
        String timeOut) throws GeneralException {
        if (StringUtil.isNotBlank(inputStr)) {
            try {
                return uploadBusiByte(fileType, relativePath, fileName,
                    inputStr.getBytes(FileUpDownConstants.FILE_CHAR_SET), timeOut);
            } catch (Exception e) {
                logger.error("upload getBytes error:", e);
            }
            return null;
        } else {
            throw new GeneralException("2999", "上传文件不能为空！" + "relativePath=" + relativePath);
        }
    }

    /**
     * 有超时时间参数的文件上传方法（采用轮询方式）
     * @param fileType P图片,W无纸化,V视频
     * @param filePath
     * @param fileName
     * @param inputByte文件字节数组
     * @param timeOut 超时时间
     * @return 文件全路径
     */
    public String uploadBusiByte(String fileType, String filePath, String fileName, byte[] inputByte,
        String timeOut) {
        String remotePath = filePath;
        long begin = System.currentTimeMillis();
        try {
            if (StringUtil.isBlank(fileType)) {
                throw new GeneralException("2999", "文件上传，必须传入文件类型（P,W,V） filePath=" + filePath);
            }
            if (StringUtil.isBlank(remotePath)) {
                throw new GeneralException("2999", "文件上传路径不能为空！ filePath=" + filePath);
            }
            if (StringUtil.isBlank(fileName)) {
                throw new GeneralException("2999", "文件名不能为空！ filePath=" + filePath);
            }
            if (inputByte == null) {
                throw new GeneralException("2999", "上传文件不能为空！ filePath=" + filePath);
            }
            // 替换反斜线
            remotePath = remotePath.replaceAll("\\\\", "/");
            if (!remotePath.startsWith("/")) {
                remotePath = "/" + remotePath;
            }
            if (!remotePath.endsWith("/")) {
                remotePath = remotePath + "/";
            }
            // 获取轮询ip服务器
            Map<String, String> uploadServer = roundRobin(fileType);
            if (uploadServer == null) {
                throw new GeneralException("2999", "没有可用的上传服务器！ filePath=" + filePath);
            }
            String pathHead = uploadServer.get("rootPath");
            if (StringUtil.isBlank(pathHead)) {
                throw new GeneralException("2999", "上传时未找到对应的远程目录配置，pathHead=" + pathHead);
            }
            if (!pathHead.startsWith("/")) {
                pathHead = "/" + pathHead;
            }
            if (!pathHead.endsWith("/")) {
                pathHead = pathHead + "/";
            }
            String remotePathAndName = pathHead + remotePath + fileName;
            remotePathAndName = remotePathAndName.replaceAll("//", "/");// 将两个斜杠替换为一个
            logger.error("upload remotePathAndName=" + remotePathAndName);
            // 拼接最终返回给外层调用方法的路径
            String ftpRemotePathAndName = uploadServer.get("ftpAls") + remotePath + fileName;
            ftpRemotePathAndName = ftpRemotePathAndName.replaceAll("//", "/");// 将两个斜杠替换为一个
            logger.error("ftpRemotePathAndName= " + ftpRemotePathAndName);
            // 如果上传方式为rnfs则执行
            if (FileUpDownConstants.UPDOWN_RNFS.equalsIgnoreCase(uploadServer.get("uploadDwnldModeCd"))) {
                String ipPort = uploadServer.get("rnfsAddrPrtnum");
                // 上传方法
                String res = uploadBusiByRnfs(ipPort, inputByte, timeOut, remotePathAndName);
                if ("0000".equals(res)) {
                    long end = System.currentTimeMillis();
                    logger.error(String.format("UPLOAD_RNFS remotePathAndName=%s time=%s", ftpRemotePathAndName,
                        String.valueOf(end - begin)));
                    return ftpRemotePathAndName;
                }
            } else if (FileUpDownConstants.UPDOWN_FTP.equalsIgnoreCase(uploadServer.get("uploadDwnldModeCd"))) {
                String ftpPathAndName = remotePath + fileName;
                ftpPathAndName = ftpPathAndName.replaceAll("//", "/");// 将两个斜杠替换为一个
                boolean res = uploadBusiByFtp(uploadServer, ftpPathAndName, inputByte);
                if (res) {
                    long end = System.currentTimeMillis();
                    logger.error(String.format("UPLOAD_FTP remotePathAndName=%s time=%s", ftpRemotePathAndName,
                        String.valueOf(end - begin)));
                    return ftpRemotePathAndName;
                }
            }
        } catch (Exception e) {
            long end = System.currentTimeMillis();
            logger.error(e.getMessage() + "filePath=" + filePath + " time=" + String.valueOf(end - begin), e);
        }
        return null;
    }

    /**
     * 有超时时间参数的文件下载方法
     * @param remotePathAndName
     * 文件全路径
     * @param timeOut
     * 超时时间
     * @return
     * @throws BusiException
     */
    public String downloadBusiStr(String remotePathAndName, String timeOut) throws GeneralException {
        byte[] file = downloadBusiByte(remotePathAndName, timeOut);
        if (file != null) {
            try {
                return new String(file, FileUpDownConstants.FILE_CHAR_SET);
            } catch (Exception e) {
                throw new GeneralException("9999", e);
            }
        } else {
            return null;
        }
    }

    /**
     * 有超时时间参数的文件下载方法
     * @param remotePathAndName
     * 文件全路径
     * @param timeOut
     * 超时时间
     * @return
     * @throws BusiException
     */
    public byte[] downloadBusiByte(String remotePathAndName, String timeOut) throws GeneralException {
        long begin = System.currentTimeMillis();
        byte[] ret = null;
        // 如果存在ftp路径则替换ftp路径
        if (StringUtil.isBlank(remotePathAndName)) {
            logger.info("downloadHttp: url is not null |remotePathAndName:" + remotePathAndName);
            throw new NullPointerException("urlStr is null remotePathAndName:" + remotePathAndName);
        }
        String newFileFullPath = remotePathAndName;
        logger.info("http_download_path:" + newFileFullPath);
        Map<String, String> ftpMap = getDownLoadCfg(newFileFullPath);
        if (ftpMap == null) {
            throw new GeneralException("2999", "根据下载路径未找到可用的下载服务，remotePathAndName=" + newFileFullPath);
        }
        if (FileUpDownConstants.UPDOWN_RNFS.equalsIgnoreCase(ftpMap.get("uploadDwnldModeCd"))) {
            // 如果路径中包含FTP则替换路径
            if (newFileFullPath.startsWith(ftpMap.get("ftpAls"))) {
                String rootPath = ftpMap.get("rootPath");
                if (!rootPath.endsWith("/")) {
                    rootPath = rootPath + "/";
                }
                newFileFullPath = newFileFullPath.replaceAll(ftpMap.get("ftpAls"), rootPath);
                newFileFullPath = newFileFullPath.replaceAll("//", "/");// 将两个斜杠替换为一个
            }
            String ipPort = ftpMap.get("rnfsAddrPrtnum");
            ret = downBusiByRnfs(ipPort, timeOut, newFileFullPath);
            long end = System.currentTimeMillis();
            logger.error(String.format("DOWNLOAD_RNFS PATH=%s time=%s", newFileFullPath, String.valueOf(end - begin)));
        } else if (FileUpDownConstants.UPDOWN_FTP.equalsIgnoreCase(ftpMap.get("uploadDwnldModeCd"))) {
            ret = downBusiByFtp(ftpMap, newFileFullPath);
            long end = System.currentTimeMillis();
            logger.error(String.format("DOWNLOAD_FTP PATH=%s time=%s", newFileFullPath, String.valueOf(end - begin)));
        }
        return ret;

    }

    private String uploadBusiByOnest(String bucketName, String path, byte[] inputByte) {
        String uploadUrl;
        InputStream in = null;
        in = new ByteArrayInputStream(inputByte);
        try {
            ONestUtil.uploadAndGetPrivateUrl(bucketName, path, in);
            uploadUrl = FileUpDownConstants.ONEST_URL_PREFIX + path;
            return uploadUrl;
        } catch (Exception e) {
            logger.error("ONEST上传异常 onest error:", e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                logger.error("close inputstream error:", e2);
            }
        }
        return null;
    }

    private byte[] downloadBusiByOnest(String bucketName, String path) {
        InputStream result;
        byte[] resultByte = null;
        try {
            result = ONestUtil.download(bucketName, path);
            if (result != null) {
                resultByte = toByteArray(result);
            }
        } catch (Exception e) {
            logger.error("downloadByOnest error:", e);
        }
        return resultByte;
    }

    private byte[] downBusiByFtp(Map<String, String> ftpMap, String remotePathAndName) throws GeneralException {
        FTPClient client = null;
        InputStream input = null;
        try {
            String ftpPathAndName = remotePathAndName;
            if (remotePathAndName.startsWith(ftpMap.get("ftpAls"))) {
                ftpPathAndName = ftpPathAndName.replaceAll(ftpMap.get("ftpAls"), "");
            } else {
                ftpPathAndName = ftpPathAndName.replaceAll(ftpMap.get("rootPath"), "");
            }
            if (!ftpPathAndName.startsWith("/")) {
                ftpPathAndName = "/" + ftpPathAndName;
            }
            // 获取ftp客户连接工具
            client = getFtpClient(ftpMap);
            input = client.retrieveFileStream(ftpPathAndName);
            if (input != null) {
                return IOUtils.toByteArray(input);
            } else {
                logger.error("FTP file name=" + remotePathAndName + " is not exist");
                return null;
            }
        } catch (Exception e) {
            throw new GeneralException("9999", e.getMessage() + " remotePathAndName=" + remotePathAndName, e);
        } finally {
            try {
                if (input != null) {
                    input.close();
                    if (client != null) {
                        client.completePendingCommand();
                    }
                }
            } catch (Exception e2) {
                logger.error(e2.getMessage(), e2);
            }
            closeFtp(client);
        }
    }

    private byte[] downBusiByRnfs(String ipPort, String timeOut, String newFileFullPath)
            throws GeneralException {
        if (StringUtil.isBlank(ipPort)) {
            throw new GeneralException("2999", "根据下载路径未找到相应下载节点ip，remotePathAndName=" + newFileFullPath);
        }
        try {
            // String isNginx = BaseDataCodeActionNew.getCodeValue("RNFS_DOWN_LOAD", "NGINX");
            // String urlStr;
            // if ("true".equalsIgnoreCase(isNginx)) {
            // urlStr = "http://" + ipPort + newFileFullPath;
            // logger.info(String.format("nginx下载url=%s,filePath=%s", urlStr, newFileFullPath));
            // return HttpMultiPartUtil.upDownPost(urlStr, null, null);
            // }
            // String urlParam = BaseDataCodeActionNew.getCodeValue("RNFS_URL_PARAM", "RNFS_URL_PARAM");
            String urlStr;
            String urlParam = cacheUtil.getJVMString(CacheConsts.UPDOWN_JVM.RNFS_USERNAME_PWD);
            if (StringUtil.isBlank(urlParam)) {
                throw new GeneralException("2999", "RNFS_URL_PARAM配置数据为空！");
            }
            Map<String, String> textMap = new HashMap<String, String>();
            textMap.put("filePath", newFileFullPath);
            urlStr = String.format(FileUpDownConstants.RNFS_URL, ipPort, FileUpDownConstants.DOWNLOAD, urlParam);
            if (timeOut != null && timeOut.trim().length() > 0) {
                HttpMultiPartUtil.setConnection_timeout(timeOut);
                HttpMultiPartUtil.setSo_timeout(timeOut);
                logger.info("download timeOut=" + timeOut);
            } else {
                // String _timeOut = BaseDataCodeActionNew.getCodeValue("RNFS_TIMEOUT", "HTTP_TIMEOUT");
                String _timeOut = cacheUtil.getJVMString(CacheConsts.UPDOWN_JVM.RNFS_TIME_OUT);
                if (_timeOut != null && _timeOut.trim().length() > 0) {
                    HttpMultiPartUtil.setConnection_timeout(_timeOut);
                    HttpMultiPartUtil.setSo_timeout(_timeOut);
                    logger.info("download timeOut=" + _timeOut);
                }
            }
            logger.info(String.format("下载url=%s,filePath=%s", urlStr, newFileFullPath));
            return HttpMultiPartUtil.upDownPost(urlStr, textMap, null);
        } catch (GeneralException e) {
            throw new GeneralException("2999", e.getMessage() + "downByRnfs error，remotePathAndName=" + newFileFullPath,
                e);
        } catch (Exception e) {
            throw new GeneralException("9999", e.getMessage() + "downByRnfs error，remotePathAndName=" + newFileFullPath,
                e);
        }
    }

    /**
     * 根据下载文件路径获取文件服务配置
     * @param downLoadPath
     * @return
     * @throws GeneralException
     */
    private Map<String, String> getDownLoadCfg(String downLoadPath) throws GeneralException {
        try {
            if (downLoadPath != null && downLoadPath.trim().length() > 0) {

                Map<String, String> ftpMap = cacheUtil
                        .getJVMMap(
                            CacheConsts.UPDOWN_JVM.FTP_CFG_PREFIX + downLoadPath.substring(0, downLoadPath.indexOf("/")));
                return ftpMap;
            }
        } catch (Exception e) {
            throw new GeneralException("9999", "获取下载节点ip端口号异常 downLoadPath=" + downLoadPath, e);

        }
        return null;
    }

    /**
     * 轮询获取服务列表
     * @return
     */
    private synchronized Map<String, String> roundRobin(String fileType) throws GeneralException {
        Map<String, String> uploadMap;
        if (StringUtil.isBlank(fileType)) {
            throw new GeneralException("2999", "文件上传，必须传入文件类型（P,W,V）");
        }

        // 获取分组cfg配置，用于轮询
        List<Map<String, String>> uploadList = cacheUtil
                .getJVMList(CacheConsts.UPDOWN_JVM.GROUP_RNFS_CFG_PREFIX + fileType);

        if (uploadList == null) {
            throw new GeneralException("2999", "没有可用的服务组列表，fileType=" + fileType);
        }
        if (maxEnabled >= uploadList.size()) {// 避免 如果不小心配置成全不可用，则程序一直循环
            throw new GeneralException("2999", maxEnabled + "请检查配置，现在配置服务全不可用");
        }

        if (pos >= uploadList.size()) {
            pos = 0;
        }
        uploadMap = uploadList.get(pos);
        pos++;
        // 如果上传方式为rnfs 或者ftp直接返回
        if (FileUpDownConstants.UPDOWN_RNFS.equalsIgnoreCase(uploadMap.get("uploadDwnldModeCd"))
                || FileUpDownConstants.UPDOWN_FTP.equalsIgnoreCase(uploadMap.get("uploadDwnldModeCd"))) {
            maxEnabled = 0;
            return uploadMap;
        } else {
            maxEnabled++;
            return roundRobin(fileType);
        }
    }

    /**
     * http Rnfs 上传文件方法
     * @param ipPort
     * @param inputByte 文件ISO8859-1字符串
     * @param timeOut 超时时间
     * @param remotePathAndName 远程主机存储路径（包含文件名）
     * @return
     * @throws BusiException
     */
    private String uploadBusiByRnfs(String ipPort, byte[] inputByte, String timeOut, String remotePathAndName)
            throws GeneralException {
        Map<String, byte[]> fileMap = new HashMap<String, byte[]>();
        fileMap.put(remotePathAndName, inputByte);
        // BaseDataCodeActionNew.getCodeValue("RNFS_URL_PARAM", "RNFS_URL_PARAM");
        String urlParam = cacheUtil.getJVMString(CacheConsts.UPDOWN_JVM.RNFS_USERNAME_PWD);
        if (StringUtil.isBlank(urlParam)) {
            throw new GeneralException("2999", "RNFS_URL_PARAM配置数据为空！");
        }
        if (StringUtil.isBlank(ipPort)) {
            throw new GeneralException("2999", "上传时未找到可用的服务，remotePathAndName=" + remotePathAndName);
        }
        String urlStr = String.format(FileUpDownConstants.RNFS_URL, ipPort, FileUpDownConstants.UPLOAD, urlParam);
        logger.info("upload urlStr=" + urlStr + "  remotePathAndName=" + remotePathAndName);
        if (timeOut != null && timeOut.trim().length() > 0) {
            HttpMultiPartUtil.setConnection_timeout(timeOut);
            HttpMultiPartUtil.setSo_timeout(timeOut);
            logger.info("upload timeOut=" + timeOut);
        } else {
            // BaseDataCodeActionNew.getCodeValue("RNFS_TIMEOUT", "HTTP_TIMEOUT");
            String _timeOut = cacheUtil.getJVMString(CacheConsts.UPDOWN_JVM.RNFS_TIME_OUT);
            if (_timeOut != null && _timeOut.trim().length() > 0) {
                HttpMultiPartUtil.setConnection_timeout(_timeOut);
                HttpMultiPartUtil.setSo_timeout(_timeOut);
                logger.info("upload timeOut=" + _timeOut);
            }
        }
        byte[] res = HttpMultiPartUtil.upDownPost(urlStr, null, fileMap);
        if (res != null) {
            String resStr = new String(res);
            logger.error("upload res:" + resStr);
            return resStr;
        } else {
            logger.error("upload res:is null");
        }
        return "";
    }

    /**
     * ftp文件上传
     * @param uploadServerBean
     * @param ftpPathAndName
     * @param inputByte
     * @return
     */
    private boolean uploadBusiByFtp(Map<String, String> uploadServerMap, String ftpPathAndName, byte[] inputByte)
            throws GeneralException {
        FTPClient client = null;
        InputStream input = null;
        try {
            if (inputByte == null) {
                throw new GeneralException("2999", "上传文件内容为空，ftpPathAndName=" + ftpPathAndName);
            }
            // 获取ftp客户连接工具
            client = getFtpClient(uploadServerMap);
            String[] pathNames = getPathAndFileName(ftpPathAndName);
            if (pathNames != null && pathNames.length == 2) {
                // 切换工作目录
                changeDir(client, pathNames[0]);
                input = new ByteArrayInputStream(inputByte);
                // 上传文件
                return client.storeFile(pathNames[1], input);
            } else {
                throw new GeneralException("2999", " 文件上传路径格式错误，ftpPathAndName=" + ftpPathAndName);
            }
        } catch (Exception e) {
            throw new GeneralException("9999", e.getMessage(), e);
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (Exception e2) {
                logger.error(e2.getMessage(), e2);
            }
            closeFtp(client);

        }
    }

    private String[] getPathAndFileName(String remotePathAndName) throws GeneralException {
        try {
            if (StringUtil.isNotBlank(remotePathAndName)) {
                String pathAndName = remotePathAndName;
                pathAndName = pathAndName.replaceAll("\\\\", "/");
                String[] rtn = new String[2];
                String filePath = pathAndName.substring(0, pathAndName.lastIndexOf("/"));
                String fileName = pathAndName.substring(pathAndName.lastIndexOf("/") + 1, pathAndName.length());
                rtn[0] = filePath;
                rtn[1] = fileName;
                return rtn;
            }
        } catch (Exception e) {
            throw new GeneralException("9999", e.getMessage() + "remotePathAndName=" + remotePathAndName, e);
        }
        return null;
    }

    /**
     * 改变工作目录
     * @param client
     * @param path 需要切换的目录
     * @throws BusiException
     */
    private void changeDir(FTPClient client, String path) throws GeneralException {
        try {
            if (client != null) {
                logger.info("changeDir: " + path);
                if (StringUtil.isBlank(path)) {
                    throw new GeneralException("FTP的要转换的工作目录为NULL");
                }
                String[] paths = path.split("/");
                List<String> pathList = new ArrayList<String>();
                StringBuilder sb = new StringBuilder();
                if (paths != null && paths.length > 0) {
                    for (String path2 : paths) {
                        if (StringUtil.isNotBlank(path2)) {
                            sb.append("/" + path2);
                            pathList.add(sb.toString());
                        }
                    }
                }
                // 降序排序
                Collections.sort(pathList, Collections.reverseOrder());
                int pathTag = pathList.size();
                for (int i = 0; i < pathList.size(); i++) {
                    if (client.changeWorkingDirectory(pathList.get(i))) {
                        pathTag = i;
                        break;
                    }
                }
                if (pathTag <= pathList.size()) {
                    for (int i = pathTag - 1; i >= 0; i--) {
                        String lastPath = pathList.get(i).substring(pathList.get(i).lastIndexOf("/") + 1);
                        client.makeDirectory(new String(lastPath.getBytes("UTF-8"), "iso-8859-1"));// 创建指定目录
                        logger.info("crateDir: " + lastPath);
                        client.changeWorkingDirectory(pathList.get(i));// 切换到指定目录
                        logger.info("new changeDir=" + pathList.get(i));
                    }
                }
            }
        } catch (Exception e) {
            throw new GeneralException("9999", e.getMessage(), e);
        }
    }

    private void closeFtp(FTPClient client) {
        try {
            if (client != null && client.isConnected()) {
                client.disconnect();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 根据rnfsCfg获取ftp
     * @param serverMap
     * @return
     * @throws BusiException
     */
    private FTPClient getFtpClient(Map<String, String> serverMap) throws GeneralException {
        FTPClient client;
        try {
            if (serverMap != null) {
                client = new FTPClient();
                // ftpBean.getFtpIpPort()
                String ipPort = serverMap.get("ftpPrtnum");
                if (StringUtil.isBlank(ipPort)) {
                    throw new GeneralException("2999", serverMap.get("rootPath") + "未配置对应的ftpip与端口");
                }
                String[] ipPorts = ipPort.split(":");
                if (ipPorts.length == 2) {
                    client.connect(ipPorts[0], Integer.parseInt(ipPorts[1]));
                } else {
                    client.connect(ipPorts[0], 21);
                }
                client.login(serverMap.get("ftpUserNm"), serverMap.get("ftpUserPw"));
                client.setFileType(FTP.BINARY_FILE_TYPE);
                return client;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
    // --------------------------------------------------业务图片上传下载end----------------------------------------------------------------------

}
