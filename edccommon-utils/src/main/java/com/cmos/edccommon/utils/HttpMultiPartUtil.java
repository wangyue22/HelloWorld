package com.cmos.edccommon.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.cmos.common.exception.GeneralException;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;



public class HttpMultiPartUtil {
    public final static Logger logger = LoggerFactory.getActionLog(HttpMultiPartUtil.class);

    // HTTP连接超时的时间 秒
    private static String connection_timeout = "30";
    // 从响应中读取数据超时的时间
    private static String so_timeout = "30";
    private static String charset = "UTF-8";
    private static String method = "POST";
    private static boolean ismultipart = false;

    private static ThreadLocal<String> conTimeOut = new ThreadLocal<String>();// 连接超时时间
    private static ThreadLocal<String> soTimeOut = new ThreadLocal<String>();// 读取超时时间
    private static ThreadLocal<String> session = new ThreadLocal<String>();// 保存session

    /**
     * post请求
     *
     * @param urlStr
     * @param textMap
     * @param fileMap
     * @return
     */
    public static String post(String urlStr, Map<String, String> textMap,
        Map<String, String> fileMap)throws GeneralException {
        OutputStream out = null;
        InputStream input = null;
        ByteArrayOutputStream baos = null;
        String res = "";
        HttpURLConnection conn = null;
        String BOUNDARY = "WebKitFormBoundSmrz"; // boundary就是request头和上传文件内容的分隔符
        if (fileMap != null && fileMap.size() > 0) {
            ismultipart = true;
        }else{
            ismultipart=false;
        }
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(Integer.valueOf(getConnection_timeout()) * 1000);
            conn.setReadTimeout(Integer.valueOf(getSo_timeout()) * 1000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod(method);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Accept-Charset", charset);
            conn.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            if (ismultipart) {
                conn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + BOUNDARY);
            } else {
                conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded; charset=" + charset);
            }

            out = new DataOutputStream(conn.getOutputStream());
            // text
            if (textMap != null) {
                StringBuffer strBuf = new StringBuffer();
                for (String key : textMap.keySet()) {
                    String inputName = key;
                    String inputValue = textMap.get(key);
                    if (inputValue == null) {
                        continue;
                    }
                    if (ismultipart) {
                        strBuf.append("\r\n").append("--").append(BOUNDARY)
                        .append("\r\n");
                        strBuf.append("Content-Disposition: form-data; name=\""
                                + inputName + "\"\r\n\r\n");
                        strBuf.append(inputValue);
                    } else {
                        strBuf.append(key).append("=").append(inputValue)
                        .append("&");
                    }

                }
                String param=strBuf.toString();
                if(param.endsWith("&")){
                    param=param.substring(0, strBuf.length()-1);
                }
                out.write(param.getBytes(charset));
            }
            // file
            if (fileMap != null) {
                for (String key : fileMap.keySet()) {
                    String inputName = key;
                    String inputValue = fileMap.get(key);
                    if (inputValue == null) {
                        continue;
                    }
                    String filename = inputName;
                    String contentType = "application/octet-stream";
                    StringBuffer strBuf = new StringBuffer();
                    strBuf.append("\r\n").append("--").append(BOUNDARY)
                    .append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\""
                            + inputName + "\"; filename=\"" + filename
                            + "\"\r\n");
                    strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
                    out.write(strBuf.toString().getBytes(charset));
                    out.write(inputValue.getBytes("ISO8859-1"));
                }
            }
            if (ismultipart) {
                String endData = "\r\n--" + BOUNDARY + "--\r\n";
                out.write(endData.getBytes());
                out.flush();
                out.close();
            }
            input = conn.getInputStream();
            baos = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int len = 0;
            while ((len = input.read(data)) != -1) {
                baos.write(data, 0, len);
            }
            res = new String(baos.toByteArray(), "ISO8859-1");
            //log.info("res:" + res);
        } catch (Exception e) {
            try {
                if (ismultipart&&out!=null) {
                    String endData = "\r\n--" + BOUNDARY + "--\r\n";
                    out.write(endData.getBytes());
                    out.flush();
                    out.close();
                }
            } catch (Exception e2) {
                logger.debug(e2.getMessage(),e2);
            }
            logger.error("发送<" + urlStr + ">请求出错。" + e);
            throw new GeneralException("", e.getMessage(), e);
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (input != null) {
                    input.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }

            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
        return res;
    }

    /**
     * 文件采用byte数组上传下载请求
     *
     * @param urlStr
     * @param textMap
     * @param fileMap
     * @return byte[]
     */
    public static byte[] upDownPost(String urlStr, Map<String, String> textMap,
        Map<String, byte[]> fileMap)throws GeneralException {
        OutputStream out = null;
        InputStream input = null;
        ByteArrayOutputStream baos = null;
        byte[] res =null;
        HttpURLConnection conn = null;
        String BOUNDARY = "WebKitFormBoundSmrz"; // boundary就是request头和上传文件内容的分隔符
        if (fileMap != null && fileMap.size() > 0) {
            ismultipart = true;
        }else{
            ismultipart=false;
        }
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(Integer.valueOf(getConnection_timeout()) * 1000);
            conn.setReadTimeout(Integer.valueOf(getSo_timeout()) * 1000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod(method);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Accept-Charset", charset);
            conn.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            if (ismultipart) {
                conn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + BOUNDARY);
            } else {
                conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded; charset=" + charset);
            }

            out = new DataOutputStream(conn.getOutputStream());
            // text
            if (textMap != null) {
                StringBuffer strBuf = new StringBuffer();
                for (String key : textMap.keySet()) {
                    String inputName = key;
                    String inputValue = textMap.get(key);
                    if (inputValue == null) {
                        continue;
                    }
                    if (ismultipart) {
                        strBuf.append("\r\n").append("--").append(BOUNDARY)
                        .append("\r\n");
                        strBuf.append("Content-Disposition: form-data; name=\""
                                + inputName + "\"\r\n\r\n");
                        strBuf.append(inputValue);
                    } else {
                        strBuf.append(key).append("=").append(inputValue)
                        .append("&");
                    }

                }
                String param=strBuf.toString();
                if(param.endsWith("&")){
                    param=param.substring(0, strBuf.length()-1);
                }
                out.write(param.getBytes(charset));
            }
            // file
            if (fileMap != null) {
                for (String key : fileMap.keySet()) {
                    String inputName = key;
                    byte[] inputValue = fileMap.get(key);
                    if (inputValue == null) {
                        continue;
                    }
                    String filename = inputName;
                    String contentType = "application/octet-stream";
                    StringBuffer strBuf = new StringBuffer();
                    strBuf.append("\r\n").append("--").append(BOUNDARY)
                    .append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\""
                            + inputName + "\"; filename=\"" + filename
                            + "\"\r\n");
                    strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
                    out.write(strBuf.toString().getBytes(charset));
                    out.write(inputValue);
                }
            }
            if (ismultipart) {
                String endData = "\r\n--" + BOUNDARY + "--\r\n";
                out.write(endData.getBytes());
                out.flush();
                out.close();
            }
            input = conn.getInputStream();
            baos = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int len = 0;
            while ((len = input.read(data)) != -1) {
                baos.write(data, 0, len);
            }
            res =baos.toByteArray();
            //log.info("res:" + res);
        } catch (Exception e) {
            try {
                if (ismultipart&&out!=null) {
                    String endData = "\r\n--" + BOUNDARY + "--\r\n";
                    out.write(endData.getBytes());
                    out.flush();
                    out.close();
                }
            } catch (Exception e2) {
                logger.debug(e2.getMessage(),e2);
            }
            logger.error("发送<" + urlStr + ">请求出错。" + e);
            throw new GeneralException("", e.getMessage(), e);
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (input != null) {
                    input.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }

            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
        return res;
    }
    /**
     * @return the connection_timeout
     */
    public static String getConnection_timeout() {
        String strConTimeOut=conTimeOut.get();
        if(strConTimeOut==null){
            strConTimeOut=connection_timeout;
        }
        return strConTimeOut;
    }

    /**
     * @param aConnection_timeout
     *            the connection_timeout to set
     */
    public static void setConnection_timeout(String aConnection_timeout) {
        conTimeOut.set(aConnection_timeout);
    }

    /**
     * @return the so_timeout
     */
    public static String getSo_timeout() {
        String strSoTimeOut=soTimeOut.get();
        if(strSoTimeOut==null){
            strSoTimeOut=so_timeout;
        }
        return strSoTimeOut;
    }

    /**
     * @param aSo_timeout
     *            the so_timeout to set
     */
    public static void setSo_timeout(String aSo_timeout) {
        soTimeOut.set(aSo_timeout);
    }

    /**
     * @return the charset
     */
    public static String getCharset() {
        return charset;
    }

    /**
     * @param aCharset
     *            the charset to set
     */
    public static void setCharset(String aCharset) {
        charset = aCharset;
    }

    /**
     * @return the method
     */
    public static String getMethod() {
        return method;
    }

    /**
     * @param aMethod
     *            the method to set
     */
    public static void setMethod(String aMethod) {
        method = aMethod;
    }

    public static void setSession(String StrSession) {
        String strSession=session.get();
        if(strSession==null){
            session.set(StrSession);
        }
    }
    public static String getSession() {
        String strSession=session.get();
        if(strSession==null){
            strSession="";
        }
        return strSession;
    }
    public static void removeSession(){
        String strSession=session.get();
        if(strSession!=null){
            session.remove();
        }
    }
}
