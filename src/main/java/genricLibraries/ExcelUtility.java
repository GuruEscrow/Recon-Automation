package genricLibraries;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * This class contains reusable methods to perform actions on excel file 
 * @author Guruprasad
 */

public class ExcelUtility {

	private Workbook workbook;
	private DataFormatter df;

	/*
	 * This method is used to initialize excel file
	 * 
	 * @param excelPath
	 */
	public void excelInit(String excelPath) {

		FileInputStream fis = null;

		try {
			fis = new FileInputStream(excelPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			workbook = WorkbookFactory.create(fis);
		} catch (EncryptedDocumentException | IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * This method is used to read single data from excel
	 * 
	 * @param sheetMame
	 * 
	 * @param rowNum
	 * 
	 * @param cellNum
	 * 
	 * @return
	 */
	public String readDataFromExcel(String sheetName, int rowNum, int cellNum) {

		df = new DataFormatter();
		return df.formatCellValue(workbook.getSheet(sheetName).getRow(rowNum).getCell(cellNum));
	}

	/*
	 * This method is used to read multiple data from excel
	 * 
	 * @param sheetName
	 * 
	 * @return
	 */
	public Map<String, String> readDataFromExcel(String sheetName) {

		Map<String, String> map = new HashMap<String, String>();
		df = new DataFormatter();
		Sheet sheet = workbook.getSheet(sheetName);

		for (int i = 10; i <= sheet.getLastRowNum(); i++) {

            if(sheet.getRow(i).getCell(0).equals("")) {
            	break;
            }
			String key = df.formatCellValue(sheet.getRow(i).getCell(0));
			String value = df.formatCellValue(sheet.getRow(i).getCell(1));
			map.put(key, value);
		}
		return map;
	}
	
	/*
	 * To Fetch the data as json format
	 */
	public Map<String, String> getDataFromExcelAsJson(String sheetName) throws JsonProcessingException {

		Map<String, String> map = new HashMap<String, String>();
		df = new DataFormatter();
		Sheet sheet = workbook.getSheet(sheetName);

		for (int i = 10; i <= sheet.getLastRowNum(); i++) {

            if(sheet.getRow(i).getCell(0).equals("")) {
            	break;
            }
			String tran_date = df.formatCellValue(sheet.getRow(i).getCell(0));
			String chq_no = df.formatCellValue(sheet.getRow(i).getCell(1));
			String particulars = df.formatCellValue(sheet.getRow(i).getCell(2));
			String dr_amount = df.formatCellValue(sheet.getRow(i).getCell(2));
			String cr_amount = df.formatCellValue(sheet.getRow(i).getCell(2));
			
			//Extracting the UTR from particulars
			String utr = null;
			String [] particulars_array = particulars.split("/");
			
			if(particulars_array[0].equals("IMPS")) {
				// 2 index is the UTR in imps
				utr = particulars_array[2];
			}else if(particulars_array[0].equals("NEFT")) {
				//  1st index is the UTR in NEFT
				utr = particulars_array[1];
			}else if(particulars_array[0].equals("IFT")) {
				// 1st index is the UTR in IFT
				utr = particulars_array[1];
			}else if(particulars_array[0].equals("RTGS")) {
				// 1st index is the UTR in RTGS
				utr = particulars_array[1];
			}else {
				System.out.println(Arrays.toString(particulars_array));
			}
			
			if(utr!=null) {
				ObjectMapper mapper = new ObjectMapper();
				
				Map<String, String> stmJsonMap = new HashMap<String, String>();
				stmJsonMap.put("tran_date", tran_date);
				stmJsonMap.put("utr", tran_date);
				stmJsonMap.put("particulars", tran_date);
				
				if(!dr_amount.equals("")) {
					stmJsonMap.put("amount", dr_amount);
					stmJsonMap.put("drcr", "DR");
				}else if(!cr_amount.equals("")) {
					stmJsonMap.put("amount", cr_amount);
					stmJsonMap.put("drcr", "CR");
				}
				
				String stmJsonString = mapper.writeValueAsString(stmJsonMap);
				
				map.put(utr, stmJsonString);
			}
		}
		return map;
	}

	public int getLastRowNum(String sheetName) {
		Sheet sheet = workbook.getSheet(sheetName);

		return sheet.getPhysicalNumberOfRows();
	}

	/*
	 * This method is used to write and save data to excel
	 * 
	 * @param sheetName
	 * 
	 * @param rowNum
	 * 
	 * @param cellNum
	 * 
	 * @param value
	 * 
	 * @param excelpath
	 */
	public void writeToExcel(String sheetName, int rowNum, int cellNum, String value, String excelpath) {
		Sheet sheet = workbook.getSheet(sheetName);
		Cell cell = null;
		try {
			cell = sheet.getRow(rowNum).createCell(cellNum);
		} catch (Exception e) {
			sheet.createRow(rowNum);
			cell = sheet.getRow(rowNum).createCell(cellNum);
		}

		cell.setCellValue(value);
		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream(excelpath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			workbook.write(fos);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * This method is used to close excel workbook
	 * 
	 */
	public void closeExcel() {

		try {
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
