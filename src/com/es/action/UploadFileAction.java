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
		String filepath = getServletContext().getRealPath("/")+File.separator+"WEB-INF"+File.separator+"uploadfile"; // �ϴ��ļ���Ŀ¼
		FileUtil fileUtil = new FileUtil(filepath);
		Map<String,File> files = new HashMap<String,File>();
		int code = fileUtil.acceptFiles(request, files);
		System.out.println(code);
		
		//ExcelReader excelReader = new ExcelReader(files.get("file"));
		//List<User> list = new ArrayList<User>();
		//code = excelReader.readExcelContent(list);
		**/
		File file = new File(getServletContext().getRealPath("/")+File.separator+"WEB-INF"+File.separator+"uploadfile"+File.separator+"���β���.xlsx");
		
		
		
		Workbook wb = new XSSFWorkbook(new FileInputStream(file.getAbsoluteFile()));
		Sheet sheet = wb.createSheet("1231231231");
		
		String[] textlist={"�б�1","�б�2","�б�3","�б�4","�б�5"};  
		
		DataValidationHelper helper = new XSSFDataValidationHelper((XSSFSheet) sheet);  
		XSSFDataValidationConstraint constraintBoolean = new XSSFDataValidationConstraint(textlist);  
		//��ʼ�� ��ֹ�� ��ʼ�� ��ֹ�У��д�0��ʼ���д�1��ʼ
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
        //�ļ�������������Ҫ����url����
        response.setHeader("Content-Disposition","attachment; filename="+URLEncoder.encode("�γ̿���.xlsx", "UTF-8"));
        
		response.addHeader("Content-Length", "" + file.length());
		OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
		
        toClient.write(buffer);
        toClient.flush();
        toClient.close();
		
		
		
		/**
		try {  
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
            List<FileItem> items = upload.parseRequest(request);
            //��List����ת���ɵ���������
            Iterator<FileItem> i = items.iterator();
            while (i.hasNext()) {
            	//�ӵ������л��FileItem����
                FileItem fi = (FileItem) i.next();
                
                String fileName = fi.getName();
                System.out.println("�ļ�����"+fileName);
                String formName = fi.getFieldName();
                System.out.println("������"+formName);
                
                if (fileName != null && formName.equals("file")) {
                    fullFile = new File(new String(fi.getName().getBytes("GBK"), "utf-8")); // ����ļ�����������  
                    savedFile = new File(uploadPath, fullFile.getName());
                    //���ļ����е�����д���ļ���ȥ
                    fi.write(savedFile);
                }
            }
            if(savedFile!=null){
            	System.out.println(savedFile);
            	//��ȡ�ļ�
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
            		// Excel��ҳǩ����  
                    int sheet_size = wb.getNumberOfSheets();  
            		for (int index = 0; index < sheet_size; index++) 
            		{  
                        // ÿ��ҳǩ����һ��Sheet����  
                        Sheet sheet = wb.getSheetAt(index);  
                        // sheet.getPhysicalNumberOfRows()���ظ�ҳ��������  
                        for (int rowIndex = 0; rowIndex < sheet.getPhysicalNumberOfRows(); rowIndex++) 
                        { 
                        	Map<String,String> map = new LinkedHashMap<String,String>();
                        	row = sheet.getRow(rowIndex);
                        	if(row !=null){
                                for (int colIndex=0;colIndex<row.getPhysicalNumberOfCells();colIndex++)
                                {
                                	//���ݵ�Ԫ������Ͷ�ȡ,����cellData��
                                	Cell cell = row.getCell(colIndex);
                                	switch(cell.getCellType())
                                	{
                                		//��ֵ��
	                                	case Cell.CELL_TYPE_NUMERIC:
	                                	{
	                                		cellData = String.valueOf(cell.getNumericCellValue());
	                                        break;
	                                    }
	                                	//��ʽ��
	                                    case Cell.CELL_TYPE_FORMULA:
	                                    {
	                                        //�ж�cell�Ƿ�Ϊ���ڸ�ʽ
	                                        if(DateUtil.isCellDateFormatted(cell)){
	                                            //ת��Ϊ���ڸ�ʽYYYY-mm-dd
	                                        	cellData = cell.getDateCellValue();
	                                        }else{
	                                            //����
	                                        	cellData = String.valueOf(cell.getNumericCellValue());
	                                        }
	                                        break;
	                                    }
	                                    //�ַ�����
	                                    case Cell.CELL_TYPE_STRING:
	                                    {
	                                    	cellData = cell.getRichStringCellValue().getString();
	                                        break;
	                                    }
	                                    default:{
	                                    	cellData = "";
	                                    }
                                	}
                                	//����һ�У�����map
                                	System.out.print(cellData+" ");
                                	 map.put(String.valueOf(colIndex), (String)cellData);
                                }
                                
                               
                            }else{
                                break;
                            }
                        	//����һ�У�����list
                        	System.out.println();
                        	list.add(map);
                        }
                    } 
            	}
            	
            	//д��excel�ļ�
            	// ����һ��Excel
                Workbook book = new HSSFWorkbook();
                // �ڶ�Ӧ��Excel�н���һ���ֱ�
                Sheet sheet1 =(Sheet) book.createSheet("�ֱ�1");
                // ������Ӧ���У���ʼ��0��ʼ��
                Row row1 =sheet1.createRow(0);
                // �����ڵ����������ڵĵ�Ԫ���൱���У���ʼ��0��ʼ,��Ӧ�ľ���A�У�
                Cell cell = row1.createCell(0);
                // д��������ݵ����õ�������ȥ��
                cell.setCellValue("a");
                // ���浽�������Ӧ·��
                book.write( new FileOutputStream(exportPath+"������.xls"));
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
