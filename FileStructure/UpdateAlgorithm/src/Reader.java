import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.SheetName;

import exception.NotExistTypeException;
import exception.ReadTypeException;

/**
 * @author Francis
 * 
 */
public class Reader {

	private Logger logger = LoggerFactory.getLogger(Reader.class);

	/**
	 * Read Excel File
	 * 
	 * @param path
	 *            Absolut Path
	 */
	public ReadList readExcel(String path) {

		List<User> notSortedTransactionUser = new ArrayList<User>();
		List<User> sortedMasterUser = new ArrayList<User>();
		Map<Integer, String> transactionLabel = new LinkedHashMap<Integer, String>();
		Map<Integer, String> masterLabel = new LinkedHashMap<Integer, String>();

		try {
			XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(
					new File(path)));

			int sheetSize = workbook.getNumberOfSheets();
			String sheetName = "";
			for (int sheetNum = 0; sheetNum < sheetSize; sheetNum++) {
				sheetName = workbook.getSheetName(sheetNum).trim();
				logger.info("Sheet Name : [ " + sheetName + " / " + sheetNum + " ] ");
				if(sheetName == null || sheetName.equals("")) continue;				
				
				if(sheetName.equals(SheetName.TRANSACTION.getText())){
					readRow(sheetNum, workbook, transactionLabel, notSortedTransactionUser);
				}else if(sheetName.equals(SheetName.OLD_MASTER.getText())){
					readRow(sheetNum, workbook, masterLabel, sortedMasterUser);
				}
			}
		
			return new ReadList(notSortedTransactionUser, sortedMasterUser, transactionLabel, masterLabel);
		} catch (FileNotFoundException e) {
			logger.error("File Not Found");
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("IO Exception");
			e.printStackTrace();
		}
		
		return null;
	}
	
	private void readRow(int sheetNum, XSSFWorkbook workbook, Map<Integer, String> label, List<User> users){
		XSSFSheet sheet = workbook.getSheetAt(sheetNum);
		
		int rowSize = sheet.getPhysicalNumberOfRows();
		for (int rowNum = 0; rowNum < rowSize; rowNum++) {
			if(rowNum == 0){
				readCellMap(rowNum, sheet, label);
				continue;
			}else{
				users.add(readCell(rowNum, sheet, label));
			}
			
		}
	}
	
	private User readCell(int rowNum, XSSFSheet sheet, Map<Integer, String> labelMap){
		XSSFRow row = sheet.getRow(rowNum);
		
		Integer key = null;
		Integer age = null;
		String firstName = null;
		String familyName = null;
		String updateCode = null;
		
		int cellSize = labelMap.size();
		for (int cellNum = 0; cellNum < cellSize; cellNum++) {
			String label = labelMap.get(cellNum);
			XSSFCell cell = row.getCell(cellNum);
		
			if(cell == null) continue;
			
			if (label.equals("KEY")) {
				if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
					key = (int) cell.getNumericCellValue();
				}else{
					logger.error("Type 불일치");
				}
			} else if (label.equals("AGE")) {
				if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
					age = (int) cell.getNumericCellValue();
				}else{
					logger.error("Type 불일치");
				}
			} else if (label.equals("FIRST_NAME")) {
				if(cell.getCellType() == XSSFCell.CELL_TYPE_STRING){
					firstName = cell.getStringCellValue();
				}else{
					logger.error("Type 불일치");
				}
			} else if (label.equals("FAMILY_NAME")) {
				if(cell.getCellType() == XSSFCell.CELL_TYPE_STRING){
					familyName = cell.getStringCellValue();
				}else{
					logger.error("Type 불일치");
				}
			} else if (label.equals("UPDATE_CODE")) {
				if(cell.getCellType() == XSSFCell.CELL_TYPE_STRING){
					updateCode = cell.getStringCellValue();
				}else{
					logger.error("Type 불일치");
				}
			} else {
				throw new NotExistTypeException();
			}

		}
		return new User(updateCode, key, firstName, familyName, age);
	}

	private void readCellMap(int rowNum, XSSFSheet sheet, Map<Integer, String> label){
		XSSFRow row = sheet.getRow(rowNum);
		
		int cellSize = row.getPhysicalNumberOfCells();
		for (int cellNum = 0; cellNum < cellSize; cellNum++) {
			XSSFCell cell = row.getCell(cellNum);
			int cellType = cell.getCellType();
			
			if(cellType == XSSFCell.CELL_TYPE_STRING){
				String cellName = cell.getStringCellValue();
				label.put(cellNum, cellName.trim());
			}else{
				logger.error("Error : First Input Only Character " + XSSFCell.CELL_TYPE_STRING);
				throw new ReadTypeException();
			}
		}
	}
	
}