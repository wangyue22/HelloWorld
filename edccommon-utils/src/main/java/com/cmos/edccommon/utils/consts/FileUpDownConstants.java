package com.cmos.edccommon.utils.consts;

/**
 * @ClassName:  FileUpDownConstants
 * @Description: 上传下载工具常量类
 * @author: 王海洋
 * @date:   2017年10月31日 下午2:17:15
 */
public class FileUpDownConstants {

    public interface GztFile{

        /**
         * onest方式上传下载
         */
        String ONEST = "0";

        /**
         * rnfs方式上传下载
         */
        String RNFS = "1";

        /**
         * onest方式和rnfs方式都上传失败
         */
        String UPLOAD_FAILED = "2";


        /**
         * 未使用onest上传
         */
        String ONEST_NOT_UPLOAD = "0";

        /**
         * 使用onest上传成功
         */
        String ONEST_UPLOAD_SUC = "1";

        /**
         * 使用onest上传失败
         */
        String ONEST_UPLOAD_FAILED = "2";
    }

    /**
     * rnfs传值：上传
     */
    public static final String UPLOAD="upload";
    /**
     * rnfs传值：下载
     */
    public static final String DOWNLOAD="download";


    /**
     * rnfs地址
     * 列如：http://192.168.113.7:30109/rnfs/%s?username=smrz&password=1qaz!QAZ
     */
    public static final String RNFS_URL = "http://%s/rnfs/%s?%s";

    /**
     * rnfs超时时间
     */
    public static final String RNFS_TIME_OUT = "30";

    /**
     * rnfs上传标识
     */
    public static final String UPDOWN_RNFS = "R";

    /**
     * ftp上传标识
     */
    public static final String UPDOWN_FTP = "F";

    /**
     * 文件编码
     */
    public static final String FILE_CHAR_SET = "ISO8859-1";

    /**
     * onest上传下载 url固定前缀
     */
    public static final String ONEST_URL_PREFIX = "oNest";


}
