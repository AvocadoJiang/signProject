package com.es.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import com.es.globle.Constants;

/**
 * @author Anson
 *	Copyright by EasyShare 2017
 *  
 *  All right reserved
 *
 *  Created on 2017年6月25日 下午4:45:35
 *  静态类
 * 工具类，为各个类提供服务
 */
public class Utils {

	/**
	 * 生成主键id
	 * @return
	 * 		String
	 */
	public static String getGenerateKey()
	{
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        return uuid;
	}
	
	/**
	 * 检测账户是否包含特殊字符
	 * @param account
	 * 		String
	 * @return
	 * 		int
	 */
	public static int isValideAccount(String account) {
		String rule = "^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]+$";//匹配特殊字符，除@//^(?=[0-9a-zA-Z@_.]+$)
		Pattern pattern=Pattern.compile(rule);
		Matcher matcher=pattern.matcher(account);
		System.out.println("正则匹配结果： "+matcher.matches());
		int errorCode=Constants.NO_ERROR_EXIST;//初始化为无错误
		if(!matcher.matches())
		{
			errorCode=Constants.USER_ACCOUNT_CONTAIN_INVALIDE_WORD;
		}
		return errorCode;
	}
	
	/**
	 * 检查邮箱是否符合邮箱格式
	 * @param content
	 * 		 待检测的字符串
	 * @return
	 * 		boolean
	 */
	public static boolean isEmail(String email)
	{
		Matcher matcher=null;
		boolean ans=false;
		if(email!=null)
		{
			String rule = "^[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?$";
			Pattern pattern=Pattern.compile(rule);
			matcher=pattern.matcher(email);
			ans=matcher.matches();
		}
		return ans;
	}
	
	
	
	
	/**
	 * 检查手机格式是否正确
	 * @param phone
	 * 		待检测的字符串
	 * @return
	 * 		boolean
	 */
	public static boolean isPhone(String phone)
	{
		Matcher matcher=null;
		boolean ans=false;
		if(phone!=null)
		{
			String rule="(13\\d|14[57]|15[^4,\\D]|17[678]|18\\d)\\d{8}|170[059]\\d{7}";
			Pattern pattern=Pattern.compile(rule);
			matcher=pattern.matcher(phone);
			ans=matcher.matches();
		}
		return ans;
	}
	
	
	/**
	 * @param secur
	 * 		待加密的字符串
	 * 将字符串进行MD5加密
	 */
	public static String toMD5(String secur)
	{
		String result=null;
		try 
		{
			MessageDigest md=MessageDigest.getInstance("MD5");
			md.update(secur.getBytes());
			result=new BigInteger(1, md.digest()).toString(32);
		} catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 将时间字符串转换成sql的Date类型
	 * 
	 * @param ymd
	 * 			yyyy-MM-dd
	 * @return
	 * 			sql的Date类型
	 */
	public static final Date stringToDate(String ymd)
	{
		Date sqlDate=null;
		try 
		{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date date;
			date = sdf.parse(ymd);
			sqlDate=new Date(date.getTime());
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		return sqlDate;
	}
	
	/**
	 * change String to exact date
	 * @param ymdhms
	 * 		yyyy-dd-MM HH:mm:SS
	 * @return
	 *  sql Date
	 */
	public static final Date stringToExactDate(String ymdhms)
	{
		Date sqlDate=null;
		try 
		{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			java.util.Date date;
			date = sdf.parse(ymdhms);
			sqlDate=new Date(date.getTime());
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		return sqlDate;
	}
	
	
	/**
	 * 生成随机编号
	 * @return
	 * 		String
	 */
	public static String createRandomName()
	{
		DateFormat dateFormat=new SimpleDateFormat("yyyyMMddhhmmss");
		return dateFormat.format(new java.util.Date())+(int)(Math.random()*89+10);
	}
	/**
	 * 通过当前系统时间，创建随机路径名称
	 * @return
	 * 		String
	 */
	public static String createRandomPath()
	{
		DateFormat dateFormat=new SimpleDateFormat("yyyyMMdd");
		return dateFormat.format(new java.util.Date());
	}
	
	
	
	/**
	 * 保存图片
	 * @param images
	 * 		List<byte[]> - 图片集合
	 * @param parentPath
	 * 		String - 保存图片的父类路径
	 * @return
	 * 		List<String>
	 */
	public static List<String> savePic(List<byte[]> images, String parentPath) {
		List<String> filenames=new ArrayList<String>();
		Lock lock = new ReentrantLock();
		for(byte[] image:images)
		{
			lock.lock();
			try
			{
				//Globle.uploadPics(userId, image);
				String filename=Utils.createRandomName()+".png";
				String path=parentPath+File.separator+filename;
				Utils.getFile(image, path);
				filenames.add(filename);
			} finally 
			{
				lock.unlock();
			}
		}
		return filenames;
	}

	
	/**
	 * 根据byte数组，生成文件
	 * @param filePath 
	 * 		boolean - 保存文件是否成功；true：成功；false：失败
	 */
	public static boolean getFile(byte[] bfile, String filePath) {
		boolean flag=false;
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = new File(filePath);
		try {
			File dir = file.getParentFile();
			if (!dir.exists()) {// 判断文件目录是否存在
				dir.mkdirs();
				dir.setReadable(true);
				dir.setWritable(true);
			}
			file.createNewFile();
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(bfile);
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return flag;
	}


	/**
	 * 获取当前年份
	 * @param date
	 * 		util Date - 待解析的时间
	 * @return
	 * 		String
	 */
	public static String formatYear(java.util.Date date) {
		return new SimpleDateFormat("yyyy").format(date);
	}



	/**
	 * 获取月份
	 * @param date
	 * 		util Date - 待解析的时间
	 * @return
	 * 		String
	 */
	public static String formatMonth(java.util.Date date) {
		return new SimpleDateFormat("MM").format(date);
	}



	/**
	 * 获取日期
	 * @param date
	 * 		util Date - 待解析的时间
	 * @return
	 * 		String
	 */
	public static String formatDay(java.util.Date date) {
		return new SimpleDateFormat("dd").format(date);
	}
	
	
	/**
	 * 分析表单数据，获取表单域及文件域的所有数据
	 * @param request
	 * 		HttpServletRequest
	 * @param parmas
	 * 		Map<String, String> - 表单域数据整理并返回的集合
	 * @return
	 * 		List<btye[]> - 对于文件与所需返回的文件数据
	 */
	public static List<byte[]> analysisForm(HttpServletRequest request, Map<String, String> parmas) {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		List<FileItem> items = null;
		List<byte[]> images = new ArrayList<byte[]>();
		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException e) {
			e.printStackTrace();
		} // 解析request请求
		Iterator<FileItem> iter = items.iterator();
		while (iter.hasNext()) {
			FileItem item = (FileItem) iter.next();
			if (item.isFormField()) { // 如果是表单域 ，就是非文件上传元素
				try {
					String name=item.getFieldName().trim();
					String value=item.getString("utf-8").trim();
					parmas.put(name, value.trim());
					System.out.println(name+" : "+value.trim());
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			} else {
				String fieldName = item.getFieldName().trim(); // 文件域中name属性的值
				try {
					BufferedImage bi = ImageIO.read(item.getInputStream());
					if(bi!=null)
					{
						parmas.put(fieldName, item.getString("utf-8").trim());
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ImageIO.write(bi, "png", baos);
						images.add(baos.toByteArray());
						baos.close();
						System.out.println("单次上传发现文件：" + baos.toByteArray());
					}
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return images;
	}
	
	/**
	 * 发送验证码
	 * @param phone
	 * @param identifyCode
	 * @return
	 */
	public synchronized static boolean sendMsg(String phone, String identifyCode)
	{
		boolean status=false;
		int webCode=0;
		int exeCode=0;
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod("http://gbk.sms.webchinese.cn"); 
		post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");//在头文件中设置转码
		NameValuePair[] data ={ 
				new NameValuePair("Uid", "George Anson"),
				new NameValuePair("Key", "9898a3e0712f474c97ab"),
				new NameValuePair("smsMob",phone),
				new NameValuePair("smsText","\n验证码: "+identifyCode+"\r\n您的ESH验证码不区分大小写("+identifyCode+")")};
		try {
			post.setRequestBody(data);
			client.executeMethod(post);
			webCode= post.getStatusCode();
			exeCode = new Integer(new String(post.getResponseBodyAsString().getBytes("gbk")));   
			if(webCode==200&&exeCode>0)
			{
				status=true;
			}
		} catch (IOException e) {
			System.out.println("短信发送失败："+webCode+"; 发送状态："+exeCode);
		}
		return status;
	}

	/**
	 * 获取验证码字符串
	 * @return
	 */
	public static String drawIdentifyCode(int length) {
		String codeNumber=new String("");
		for(int i=0;i<length;i++)
		{
			int j=new Random().nextInt(Constants.IMAGE_CHAR.length());
			codeNumber+=Constants.IMAGE_CHAR.substring(j, j+1);
		}
		return codeNumber;
	}
	
    

    
    public static String sendPost(String url, Map<String, String> params){  
        URL u = null;
        BufferedReader in = null;
        String result = "";
        HttpURLConnection con = null;  
        // 构建请求参数  
        StringBuffer sb = new StringBuffer();  
        if (params != null) {  
            for (Entry<String, String> e : params.entrySet()) {  
                sb.append(e.getKey());  
                sb.append("=");  
                sb.append(e.getValue());  
                sb.append("&");  
            }  
            sb.substring(0, sb.length() - 1);  
        }  
        System.out.println("send_url:" + url);  
        System.out.println("send_data:" + sb.toString());  
        // 尝试发送请求  
        try {  
            u = new URL(url);  
            con = (HttpURLConnection) u.openConnection();  
            //// POST 只能为大写，严格限制，post会不识别  
            con.setRequestMethod("POST");  
            con.setDoOutput(true);  
            con.setDoInput(true);  
            con.setUseCaches(false);  
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  
            OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");  
            osw.write(sb.toString());  
            osw.flush();  
            osw.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (con != null) {  
                con.disconnect();  
            }  
        }  
     // 定义BufferedReader输入流来读取URL的响应
        try {
			in = new BufferedReader(
			        new InputStreamReader(con.getInputStream(), "utf8"));
	        String line;
	        while ((line = in.readLine()) != null) {
	            result += line;
	        }
	        
	        if(in!=null){
	            in.close();
	        }
        } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return result;
    }
}
