package com.es.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.util.CellRangeAddressList;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;


/**
 * Servlet implementation class UploadFileAction
 */
@Component
public class UploadFileAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadFileAction() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	/**
		String filepath = getServletContext().getRealPath("/")+File.separator+"WEB-INF"+File.separator+"uploadfile"; // 上传文件的目录
		FileUtil fileUtil = new FileUtil(filepath);
		Map<String,File> files = new HashMap<String,File>();
		int code = fileUtil.acceptFiles(request, files);
		System.out.println(code);
		
		//ExcelReader excelReader = new ExcelReader(files.get("file"));
		//List<User> list = new ArrayList<User>();
		//code = excelReader.readExcelContent(list);
		**/
		File file = new File(getServletContext().getRealPath("/")+File.separator+"WEB-INF"+File.separator+"uploadfile"+File.separator+"开课测试.xlsx");
		
		
		
		Workbook wb = new XSSFWorkbook(new FileInputStream(file.getAbsoluteFile()));
		Sheet sheet = wb.createSheet("1231231231");
		
		String[] textlist={"列表1","列表2","列表3","列表4","列表5"};  
		
		DataValidationHelper helper = new XSSFDataValidationHelper((XSSFSheet) sheet);  
		XSSFDataValidationConstraint constraintBoolean = new XSSFDataValidationConstraint(textlist);  
		//起始行 终止行 起始列 终止列，行从0开始，列从1开始
		CellRangeAddressList regionsBoolean = new CellRangeAddressList(9, 10, 10, 10);   
		DataValidation validationBoolean = helper.createValidation(constraintBoolean, regionsBoolean);   
		sheet.addValidationData(validationBoolean);
		FileOutputStream out=new FileOutputStream(file.getAbsoluteFile()); 
		wb.write(out);  
        out.close(); 
		InputStream fis = new BufferedInputStream(new FileInputStream(file.getAbsoluteFile()));
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();
        
		
		
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        //文件名是中文名需要经过url编码
        response.setHeader("Content-Disposition","attachment; filename="+URLEncoder.encode("课程开设.xlsx", "UTF-8"));
        
		response.addHeader("Content-Length", "" + file.length());
		OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
		
        toClient.write(buffer);
        toClient.flush();
        toClient.close();
		
		
		
		/**
		try {  
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
            List<FileItem> items = upload.parseRequest(request);
            //将List对象转换成迭代器对象
            Iterator<FileItem> i = items.iterator();
            while (i.hasNext()) {
            	//从迭代器中获得FileItem对象
                FileItem fi = (FileItem) i.next();
                
                String fileName = fi.getName();
                System.out.println("文件名："+fileName);
                String formName = fi.getFieldName();
                System.out.println("表单名："+formName);
                
                if (fileName != null && formName.equals("file")) {
                    fullFile = new File(new String(fi.getName().getBytes("GBK"), "utf-8")); // 解决文件名乱码问题  
                    savedFile = new File(uploadPath, fullFile.getName());
                    //将文件流中的内容写到文件中去
                    fi.write(savedFile);
                }
            }
            if(savedFile!=null){
            	System.out.println(savedFile);
            	//读取文件
            	InputStream is = new FileInputStream(savedFile.getAbsolutePath());
            	Workbook wb =null;
            	Row row = null;
            	Object cellData = null;
            	List<Map<String,String>> list = null;
            	String extString = savedFile.getName().substring(savedFile.getName().lastIndexOf("."));
            	if(".xls".equals(extString)){
                    wb = new HSSFWorkbook(is);
                }else if(".xlsx".equals(extString)){
                    wb = new XSSFWorkbook(is);
                }
            	if(wb != null)
            	{
            		list = new ArrayList<Map<String,String>>();
            		// Excel的页签数量  
                    int sheet_size = wb.getNumberOfSheets();  
            		for (int index = 0; index < sheet_size; index++) 
            		{  
                        // 每个页签创建一个Sheet对象  
                        Sheet sheet = wb.getSheetAt(index);  
                        // sheet.getPhysicalNumberOfRows()返回该页的总行数  
                        for (int rowIndex = 0; rowIndex < sheet.getPhysicalNumberOfRows(); rowIndex++) 
                        { 
                        	Map<String,String> map = new LinkedHashMap<String,String>();
                        	row = sheet.getRow(rowIndex);
                        	if(row !=null){
                                for (int colIndex=0;colIndex<row.getPhysicalNumberOfCells();colIndex++)
                                {
                                	//根据单元格的类型读取,存入cellData中
                                	Cell cell = row.getCell(colIndex);
                                	switch(cell.getCellType())
                                	{
                                		//数值型
	                                	case Cell.CELL_TYPE_NUMERIC:
	                                	{
	                                		cellData = String.valueOf(cell.getNumericCellValue());
	                                        break;
	                                    }
	                                	//公式型
	                                    case Cell.CELL_TYPE_FORMULA:
	                                    {
	                                        //判断cell是否为日期格式
	                                        if(DateUtil.isCellDateFormatted(cell)){
	                                            //转换为日期格式YYYY-mm-dd
	                                        	cellData = cell.getDateCellValue();
	                                        }else{
	                                            //数字
	                                        	cellData = String.valueOf(cell.getNumericCellValue());
	                                        }
	                                        break;
	                                    }
	                                    //字符串型
	                                    case Cell.CELL_TYPE_STRING:
	                                    {
	                                    	cellData = cell.getRichStringCellValue().getString();
	                                        break;
	                                    }
	                                    default:{
	                                    	cellData = "";
	                                    }
                                	}
                                	//读完一列，存入map
                                	System.out.print(cellData+" ");
                                	 map.put(String.valueOf(colIndex), (String)cellData);
                                }
                                
                               
                            }else{
                                break;
                            }
                        	//读完一行，存入list
                        	System.out.println();
                        	list.add(map);
                        }
                    } 
            	}
            	
            	//写入excel文件
            	// 建立一个Excel
                Workbook book = new HSSFWorkbook();
                // 在对应的Excel中建立一个分表
                Sheet sheet1 =(Sheet) book.createSheet("分表1");
                // 设置相应的行（初始从0开始）
                Row row1 =sheet1.createRow(0);
                // 在所在的行设置所在的单元格（相当于列，初始从0开始,对应的就是A列）
                Cell cell = row1.createCell(0);
                // 写入相关数据到设置的行列中去。
                cell.setCellValue("a");
                // 保存到计算机相应路径
                book.write( new FileOutputStream(exportPath+"啊企鹅额.xls"));
            }
        } catch (Exception e) { 
        	System.out.println(e.getMessage());
        }  
        */
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
