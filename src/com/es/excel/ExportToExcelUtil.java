package com.es.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
//import org.apache.poi.ss.usermodel.ClientAnchor.AnchorType;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.util.CollectionUtils;

import com.es.utils.Utils;

/**
 * @author Anson<br>
 *	Copyright by EasyShare 2017<br>
 *  
 *  All right reserved<br>
 *
 *  Created on 2016年8月15日 上午10:17:59<br>
 *  
 *  导出工具<br>
 *  批量导出值excel，分文件，生成压缩包<br>
 */
public class ExportToExcelUtil<T> {

	/** 单个xls最大数据量 **/
	private static int NUM=500;
	/** xls标题 **/
	private static String TITLE="";
	/** 列宽 **/
	private static short DEFAULT_COLUMN_WIDTH=18;
	/** 标题字体大小 **/
	private static short TITLE_FONT_HEIGHT=15;
	/** 图片高度 **/
	private static int HIGHT_FOR_PIC=60;

	/**
	 * 大量数据批量导出并压缩文件
	 * @param headers
	 * 		表头
	 * @param result
	 * 		结果集
	 * @param out
	 * 		输出流
	 * @param request
	 * 		HttpServletRequest
	 * @throws IOException
	 * 		文件读写异常
	 */
	public void exportExcel(String[] headers, List<T> result, OutputStream out, HttpServletRequest request) throws IOException{
		//计算文件数量
		int n=fileNUMS(result);		
		//保存文件名
		List<String> fileNames=new ArrayList<String>();
		for(int j=0;j<n;j++)
		{
			//获取每份xls的文件数据
			Collection<T> res=getPerFileData(j, n, result);
			//生成工作簿
			Workbook workbook=new HSSFWorkbook();
			HSSFSheet sheet=(HSSFSheet) workbook.createSheet(TITLE);
			sheet.setDefaultColumnWidth(DEFAULT_COLUMN_WIDTH);
			String path=request.getServletContext().getRealPath(File.separator)+"WEB-INF"+File.separator+"export"+File.separator+getFileName()+"-"+j+".xls";
			fileNames.add(path);
			File file = new File(path);
			File dir = file.getParentFile();
			if (!dir.exists())
			{// 判断文件目录是否存在
				dir.mkdirs();
				dir.setReadable(true);
				dir.setWritable(true);
			}
			file.createNewFile();
			FileOutputStream fos=new FileOutputStream(file);
			//生成样式表
			HSSFCellStyle style=(HSSFCellStyle) workbook.createCellStyle();
			//设置样式
			HSSFPatriarch patriarch=setXLSStyle(style, sheet, headers, workbook);
			HSSFRow row=sheet.createRow(1);
			//生成表头
			createHeader(headers, row, style);
			//获取属性集合
			 
			String[] columns=null;
			if(result!=null)
			{
				int index=2;
				int k=0;
				for(T t:res)
				{
					Class<?> clazz = t.getClass();
					columns=getFieldName(clazz);
					row=sheet.createRow(index);
					index++;
					for(int i=0;i<columns.length;i++)
					{
						HSSFCell cell=row.createCell(i);
						//处理类版本号问题，去除版本号属性
						k=i+1;
						if(k==columns.length)
						{
							k=columns.length-1;
						}
						//获取反射数据
						Object value=reflectGetFunction(columns[k], t);
						String textValue=null;
						//数据类型转换
						textValue = matchDate(columns[k], i, row, sheet, value, patriarch, workbook, index);
						//填入xls单元格
						fillData(textValue, cell);
					}
				}
			}
			workbook.write(fos);
			//生成文件并压缩
			CreateAndZipFile(fileNames, request, out);
		}
	}

	public String[] getFieldName(Class<?> clazz){
		Field[] fields=clazz.getDeclaredFields();  
	    String[] fieldNames=new String[fields.length];  
	    for(int i=0;i<fields.length;i++)
	    {   
	        fieldNames[i]=fields[i].getName();  
	    }
	    return fieldNames; 
	}

	/**
	 * 创建并压缩
	 * @param fileNames
	 * 		excel单个文件名称集合
	 * @param request
	 * 		HttpServletRequest
	 * @param out
	 * 		文件输出流，将文件流写入磁盘
	 */
	private void CreateAndZipFile(List<String> fileNames, HttpServletRequest request, OutputStream out) {
		File zip=new File(request.getServletContext().getRealPath(File.separator)+"WEB-INF"+File.separator+"export"+File.separator+TITLE);
		File srcFile[] =new File[fileNames.size()];
		for(int i=0,n1=fileNames.size();i<n1;i++)
		{
			srcFile[i]=new File(fileNames.get(i));
		}
		//压缩文件
		ZipFiles(srcFile, zip);
		FileInputStream fis;
		try {
			fis = new FileInputStream(zip);
			byte[] buffer=new byte[4096];
			int read=0;
			while((read=fis.read(buffer))!=-1)
			{
				out.write(buffer, 0, read);
			}
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 填入数据值xls
	 * @param textValue
	 * 		待写入的文件内容
	 * @param cell
	 * 		需写入的excel单元格
	 */
	private void fillData(String textValue, HSSFCell cell) {
		if(textValue!=null)
		{
			Pattern p=Pattern.compile("^//d+(//.//d+)?$");
			Matcher matcher=p.matcher(textValue);
			if(matcher.matches())
			{
				cell.setCellValue(textValue);
			}else
			{
				HSSFRichTextString richTextString=new HSSFRichTextString(textValue);
				cell.setCellValue(richTextString);
			}
		}
	}

	/**
	 * 数据类型转换
	 * @param col
	 * 		列
	 * @param i
	 * 		列数
	 * @param row
	 * 		行
	 * @param sheet
	 * 		表格对象
	 * @param value
	 * 		待处理的数据
	 * @param patriarch
	 * 		patriarch
	 * @param workbook
	 * 		工作簿对象
	 * @param index
	 * 		数据位置
	 * @return
	 * 		单元格内容
	 */
	private String matchDate(String col, int i, HSSFRow row, HSSFSheet sheet, Object value, HSSFPatriarch patriarch,
			Workbook workbook, int index) {
		String textValue=null;
		if(value==null)
		{
			textValue="";
		}else if(value instanceof Date)
		{
			Date date=(Date) value;
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			textValue=sdf.format(date);
		}else if(value instanceof byte[])
		{
			row.setHeightInPoints(HIGHT_FOR_PIC);
			sheet.setColumnWidth(i, (short)(27.5*80));
			byte[] bsValue=(byte[]) value;
			HSSFClientAnchor anchor=new HSSFClientAnchor(0, 0, 1023, 255, (short)6, index, (short)6, index);
			//anchor.setAnchorType(AnchorType.DONT_MOVE_DO_RESIZE);
			patriarch.createPicture(anchor, workbook.addPicture(bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
		}else if(col.equals("utype"))
		{
			textValue=((String)value).replaceAll("_", " ");
		}else
		{
			textValue=value.toString();
		}
		return textValue;
	}

	/**
	 * 反射get方法
	 * @param col
	 * 		属性名
	 * @param t
	 * 		数据对象
	 * @return
	 * 		数据值
	 */
	private Object reflectGetFunction(String col, T t) {
		String fieldName = col;
		String getMethodName = "get"+fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		Object value=null;
        Class<?> clazz = t.getClass();   
        Method getMethod;
		try {
			getMethod = clazz.getMethod(getMethodName, new Class[]{});
			value = getMethod.invoke(t, ((Object[])new Class[]{}));
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}   
		return value;
	}

	/**
	 * 生成表头
	 * @param headers
	 * 		表头
	 * @param row
	 * 		excel行
	 * @param style
	 * 		excel样式
	 */
	private void createHeader(String[] headers, HSSFRow row, HSSFCellStyle style) {
		for(int i=0;i<headers.length;i++)
		{
			HSSFCell cell=row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text=new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
	}

	/**
	 * 设置xls表样式
	 * @param style
	 * 		excel样式
	 * @param sheet
	 * 		表格
	 * @param headers
	 * 		表头
	 * @param workbook
	 * 		工作簿
	 * @return
	 * 		HSSFPartrich
	 */
	private HSSFPatriarch setXLSStyle(HSSFCellStyle style, HSSFSheet sheet, String[] headers, Workbook workbook) {
		style.setFillForegroundColor(HSSFColor.GOLD.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		//设置字体
		HSSFFont font=(HSSFFont) workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style.setFont(font);
		
		style.setWrapText(true);
		
		HSSFPatriarch patriarch=sheet.createDrawingPatriarch();
		//设置头
		HSSFCellStyle titleStyle=(HSSFCellStyle) workbook.createCellStyle();
		
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
		titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		HSSFFont titleFont=(HSSFFont) workbook.createFont();
		
		titleFont.setFontHeightInPoints(TITLE_FONT_HEIGHT);
		titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		
		titleStyle.setFont(titleFont);
		
		sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 0, (short)(headers.length-1)));
		HSSFRow rowHeader=sheet.createRow(0);
		HSSFCell cellHeader=rowHeader.createCell(0);
		HSSFRichTextString textHeader=new HSSFRichTextString(TITLE);
		cellHeader.setCellStyle(titleStyle);
		cellHeader.setCellValue(textHeader);
		return patriarch;
	}

	/**
	 * 获取每个文件的数据
	 * @param j
	 * 		当前excel表格编号
	 * @param n
	 * 		excel文件数量
	 * @param result
	 * 		总数据量
	 * @return 
	 * 		单份excel数据集合
	 */
	private Collection<T> getPerFileData(int j, int n, List<T> result) {
		Collection<T> res=null;
		if(!CollectionUtils.isEmpty(result))
		{
			if(j==n-1)
			{
				if(result.size()%NUM==0)
				{
					res=result.subList(NUM*j, NUM*(j+1));
				}else
				{
					res=result.subList(NUM*j, NUM*j+result.size()%NUM);
				}
			}else
			{
				res=result.subList(NUM*j, NUM*(j+1));
			}
		}
		return res;
	}

	/**
	 * 计算文件数量
	 * @param result
	 * 		总共数据集合
	 * @return
	 * 		需生成的excel表格数量
	 */
	private int fileNUMS(List<T> result) {
		int n=0;
		if(!CollectionUtils.isEmpty(result))
		{
			if(result.size()%NUM==0)
			{
				n=result.size()/NUM;
			}else
			{
				n=result.size()/NUM+1;
			}
		}else
		{
			n=1;
		}
		return n;
	}

	/**
	 * zip files
	 * @param srcFile
	 * 		文件集合
	 * @param zip
	 * 		待压缩生成的压缩包
	 */
	private void ZipFiles(File[] srcFile, File zip) {
		byte[] buffer=new byte[1024];
		try {
			ZipOutputStream outputStream=new ZipOutputStream(new FileOutputStream(zip));
			for(int i=0;i<srcFile.length;i++)
			{
				FileInputStream fis=new FileInputStream(srcFile[i]);
				outputStream.putNextEntry(new ZipEntry(srcFile[i].getName()));
				int read=0;
				while((read=fis.read(buffer))>0)
				{
					outputStream.write(buffer, 0, read);
				}
				outputStream.closeEntry();
				fis.close();
			}
			outputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 生成文件名称
	 * @return
	 * 		文件名称
	 */
	public static String getFileName() {
		String fileName=Utils.createRandomName();
		return fileName;
	}
	
	/**
	 * 设置响应头
	 * @param response
	 * 		HttpServletResponse
	 * @param fileName
	 * 		压缩包文件名称
	 */
	public void setResponseHeader(HttpServletResponse response, String fileName)
	{
		TITLE=fileName+".zip";
		response.reset();
		response.setContentType("application/octet-stream;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename="+TITLE);
		response.addHeader("Pargam", "no-cache");
		response.addHeader("Cache-Control", "no-cache");
	}
}
