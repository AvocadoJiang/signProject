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

	/** �ļ���Ŀ¼ **/
	String filePath = null;
	String[] fileTypes = {
			".xls",
			".xlsx"
	};
	/**
	 * ��request�е��ļ������ϴ�Ŀ¼�������ϴ������ļ���Map���ϣ������ش�����
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
		//��ȡ�ļ���
		String fileName = file.getOriginalFilename();
		//��ȡ�ļ���׺
		String extString = fileName.substring(fileName.lastIndexOf("."));
		
		//����ļ���׺���Ƿ����Ҫ��
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
            //���ļ����е�����д���ļ���ȥ
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
	 * ��request�е��ļ������ϴ�Ŀ¼�������ϴ������ļ���Map���ϣ������ش�����
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
		// �����ļ����
        DiskFileItemFactory factory = new DiskFileItemFactory();  
        // ���ù����������ͻ���Ŀ¼
        factory.setSizeThreshold(4096); // ���û�������С��������4kb  
        factory.setRepository(null);// ���û�����Ŀ¼  Ĭ�ϲ���ϵͳĬ�ϵ���ʱ�ļ�·��
        // ����һ���ļ��ϴ�������  
        ServletFileUpload upload = new ServletFileUpload(factory);
        // ��������ļ��ߴ磬������4MB
        upload.setSizeMax(4194304);
        // �õ����е��ļ�
        List<FileItem> items;
        
		try {
			items = upload.parseRequest(request);
			 //��List����ת���ɵ���������
	        Iterator<FileItem> i = items.iterator();
	        System.out.println("123");
	        while (i.hasNext()) {
	        	System.out.println("456");
	        	//�ӵ������л��FileItem����
	            FileItem fi = (FileItem) i.next();
	            
	            String fileName = new String(fi.getName().getBytes("GBK"), "utf-8");
	            System.out.println("�ļ�����"+fileName);
	            String formName = fi.getFieldName();
	            System.out.println("������"+formName);
	            String extString = fileName.substring(fileName.lastIndexOf("."));
	            System.out.println(extString);
	            //����ϴ��ļ��ĺ�׺��û�м��ħ�����֣�
	            
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
	                //���ļ����е�����д���ļ���ȥ
	                fi.write(savedFile);
	                //�����ļ����뼯����ȥ
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
