package com.cmos.commons.utils;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.cmos.onest.ONestUtil;

public class FileDeal {
	
	private static String BUCKET = "edc-test";
	/**
	 *
	 * @param path  存储路径不带前面/， 示例 10085file/371/20170224/aa.jpg  
	 * @param fileContent 文件内容
	 * @param invalidDate 失效时间,格式 yyyy-MM-dd
	 * @return  返回上传结果
	 * 
	 */
	   public static Boolean upload(String path,InputStream in,String invalidDate){
		   Boolean  result  = false;
		 try {
				ONestUtil.uploadAndGetPrivateUrl(BUCKET , path, in,invalidDate)	;
				result = true;
		} catch (Exception e) {
			
		}
		   return result;
	   }
	   
	   
		/**
		 *
		 * @param path  存储路径不带前面/， 示例 10085file/371/20170224/aa.jpg
		 * @param fileContent 文件内容
		 * @return  返回上传结果
		 */
		   public static Boolean upload(String path,InputStream in){
			   Boolean  result  = false;
			 try {
			     ONestUtil.uploadAndGetPrivateUrl(BUCKET , path, in)	;
				 result = true;
			} catch (Exception e) {
				
			}
			   return result;
		   }
	   
		   /**
			 *
			 * @param path  存储路径不带前面/， 示例 10085file/371/20170224/aa.jpg
			 * @param fileContent 文件内容,以iso-8859-1 编码上传
			 * @param invalidDate 失效时间,格式 yyyy-MM-dd
			 * @return  返回上传结果
			 * 
			 */
			   public static Boolean upload(String path,String fileContent ,String invalidDate){
				   Boolean  result  = false;
				 try {
					 InputStream in = IOUtils.toInputStream(fileContent,"iso-8859-1");
					 ONestUtil.uploadAndGetPrivateUrl(BUCKET , path, in,invalidDate)	;
					 in.close();
					 result = true;
				} catch (Exception e) {
					
					
				}
				   return result;
			   }
			   
		/**
		 *
		 * @param path  存储路径不带前面/， 示例 10085file/371/20170224/aa.jpg
		 * @param fileContent 文件内容,以iso-8859-1 编码上传
		 * @return  返回上传结果
		 */
		   public static Boolean upload(String path,String fileContent){
			   Boolean  result  = false;
			 try {
				 InputStream in = IOUtils.toInputStream(fileContent,"iso-8859-1");
			     ONestUtil.uploadAndGetPrivateUrl(BUCKET , path, in)	;
			     in.close();
				 result = true;
			} catch (Exception e) {
				
			}
			   return result;
		   }
		   
		   
		
			   
			   
			
		   
	   
		   
	    /**
		 *
		 * @param path  存储路径不带前面/， 示例 10085file/371/20170224/aa.jpg
		 * @return  返回下载图片流  
		 */
		   public static InputStream download(String path){
			   InputStream  result  = null;
			 try {
					 result =   ONestUtil.download(BUCKET, path);
			} catch (Exception e) {
				
			}
			   return result;
		   }
		   
		    /**
			 *
			 * @param path  存储路径不带前面/， 示例 10085file/371/20170224/aa.jpg
			 * @return  返回下载图片内容以iso8859-1格式  
			 */
			   public static String download2String(String path){
				   String  result  = null;
				 try {
					 InputStream in =   ONestUtil.download(BUCKET, path);
						 IOUtils.toString(in);
				} catch (Exception e) {
					
				}
				   return result;
			   }
				   
		    /**
			 *
			 * @param path  存储路径不带前面/， 示例 10085file/371/20170224/aa.jpg
			 * @return  返回下载图片内容以iso8859-1格式  
			 */
			   public static Boolean deleteFile(String path){
				   Boolean  result  = false;
				 try {
					 result =  ONestUtil.deleteObject(BUCKET, path);
				} catch (Exception e) {
					
				}
				   return result;
			   }
	    
	    

}
