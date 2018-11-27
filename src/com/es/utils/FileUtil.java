package com.es.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.es.globle.Constants;

public final class FileUtil {

	/** 文件的目录 **/
	String filePath = null;
	String[] fileTypes = {
			".xls",
			".xlsx"
	};
	/**
	 * 将request中的文件存入上传目录，放入上传来的文件的Map集合，并返回错误码
	 * @param request
	 * 		HttpServletRequest
	 * @param fileList
	 * 		Map<String,File>
	 * @return code
	 * 		int
	 */
	public int acceptFiles(MultipartRequest request,Map<String,File> fileMap,String key){
		int code = Constants.NO_ERROR_EXIST;
		
	
		MultipartFile file = request.getFile(key);
		//获取文件名
		String fileName = file.getOriginalFilename();
		//获取文件后缀
		String extString = fileName.substring(fileName.lastIndexOf("."));
		
		//检测文件后缀名是否符合要求
		for(String temp:fileTypes){
			if(extString.equals(temp)){
        		code = Constants.NO_ERROR_EXIST;
        		break;
        	}else{
        		code = Constants.FILE_TYPE_ERROR;
        	}
		}
		
		if (code == Constants.NO_ERROR_EXIST) {
            File savedFile = new File(filePath, (new SimpleDateFormat("yyyyMMddHHmmssSSS")).format(new Date())+extString);
            //将文件流中的内容写到文件中去
            try {
				file.transferTo(savedFile);
				fileMap.put("file", savedFile);
			} catch (IllegalStateException | IOException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
        }
		
		return code;
       
		
	}	
	/**
	 * 将request中的文件存入上传目录，放入上传来的文件的Map集合，并返回错误码
	 * @param request
	 * 		HttpServletRequest
	 * @param fileList
	 * 		Map<String,File>
	 * @return code
	 * 		int
	 */
	public int acceptFiles(HttpServletRequest request,Map<String,File> files){
		int code = Constants.NO_ERROR_EXIST;
		
		File savedFile = null;
		// 创建文件项工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();  
        // 设置工厂缓冲区和缓冲目录
        factory.setSizeThreshold(4096); // 设置缓冲区大小，这里是4kb  
        factory.setRepository(null);// 设置缓冲区目录  默认采用系统默认的临时文件路径
        // 创建一个文件上传处理器  
        ServletFileUpload upload = new ServletFileUpload(factory);
        // 设置最大文件尺寸，这里是4MB
        upload.setSizeMax(4194304);
        // 得到所有的文件
        List<FileItem> items;
        
		try {
			items = upload.parseRequest(request);
			 //将List对象转换成迭代器对象
	        Iterator<FileItem> i = items.iterator();
	        System.out.println("123");
	        while (i.hasNext()) {
	        	System.out.println("456");
	        	//从迭代器中获得FileItem对象
	            FileItem fi = (FileItem) i.next();
	            
	            String fileName = new String(fi.getName().getBytes("GBK"), "utf-8");
	            System.out.println("文件名："+fileName);
	            String formName = fi.getFieldName();
	            System.out.println("表单名："+formName);
	            String extString = fileName.substring(fileName.lastIndexOf("."));
	            System.out.println(extString);
	            //检测上传文件的后缀（没有检查魔术数字）
	            
	            for(String temp:fileTypes){
	            	if(extString.equals(temp)){
	            		code = Constants.NO_ERROR_EXIST;
	            		break;
	            	}else{
	            		code = Constants.FILE_TYPE_ERROR;
	            	}
	            }
	            if (code == Constants.NO_ERROR_EXIST) {
	                savedFile = new File(filePath, (new SimpleDateFormat("yyyyMMddHHmmssSSS")).format(new Date())+extString);
	                //将文件流中的内容写到文件中去
	                fi.write(savedFile);
	                //将该文件放入集合中去
	                files.put(formName, savedFile);
	            }
	        }
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
       
		return code;
	}	
	
	public FileUtil() {
		super();
	}
	public FileUtil(String filePath) {
		super();
		this.filePath = filePath;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public String toString() {
		return "FileUtil [filePath=" + filePath + ", fileTypes=" + Arrays.toString(fileTypes) + "]";
	}
	
	
	
}
