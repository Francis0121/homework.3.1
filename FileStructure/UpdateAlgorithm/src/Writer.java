import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.SheetName;

/**
 * @author Francis
 * 
 */
public class Writer {

	private static Logger logger = LoggerFactory.getLogger(Writer.class);

	private ReadList readList;

	public Writer() {
		super();
	}

	public Writer(ReadList readList) {
		this.readList = readList;
	}
	
	public void writeExcle(String path){
		if(readList == null)
			throw new RuntimeException();
	
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(path));

			XSSFSheet sheet = workbook.createSheet(SheetName.NEW_MASTER.getText());
			XSSFRow row = sheet.createRow(0);
						
			Map<Integer, String> masterLabel = readList.getMasterLabel();
			for(int i=0; i<masterLabel.size(); i++){
				XSSFCell cell = row.createCell(i);
				cell.setCellValue(masterLabel.get(i));
			}
			
			updateAlogorithm(sheet);
			
			FileOutputStream stream = null;
			stream = new FileOutputStream(path);
			workbook.write(stream);
			stream.close();
		} catch (FileNotFoundException e) {
			logger.error("File Not Found");
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("IO Exception");
			e.printStackTrace();
		}
		
	}
	
	private void updateAlogorithm(XSSFSheet sheet){
		List<User> transactionUser = readList.getNotSortedTransactionUser();
		transactionUser.sort(new User());
		List<User> masterUser = readList.getSortedMasterUser();
			
		int tPoint = 0;
		int mPoint = 0;
		int row = 1;
		
		User tUser = transactionUser.get(tPoint);
		User mUser = masterUser.get(mPoint);
		
		while(tPoint < transactionUser.size() && mPoint < masterUser.size() ){
			if(mUser.getKey() < tUser.getKey() ){
				writeRow(sheet, mUser, row++);
				if(++mPoint < masterUser.size()){
					mUser = masterUser.get(mPoint);
				}
			}else{
				String updateCode = tUser.getUpdateCode();
				if (mUser.getKey() == tUser.getKey()) {
					if (updateCode.equals("I")) {
						logger.error("Duplicate record Key");
						if(++tPoint < transactionUser.size()){
							tUser = transactionUser.get(tPoint);
						}
					}else if (updateCode.equals("U")) {
						User uUser = new User(mUser.getKey(),
								tUser.getFamilyName() != null ? tUser.getFamilyName() : mUser.getFamilyName(),
								tUser.getFirstName() != null ? tUser.getFirstName() : mUser.getFirstName(),
								tUser.getAge() != null ? tUser.getAge() : mUser.getAge());
					
						writeRow(sheet, uUser, row++);
						
						if(++mPoint < masterUser.size()){
							mUser = masterUser.get(mPoint);
						}
						if(++tPoint < transactionUser.size()){
							tUser = transactionUser.get(tPoint);
						}
					}else if (updateCode.equals("D")) {
						if(++mPoint < masterUser.size()){
							mUser = masterUser.get(mPoint);
						}
						if(++tPoint < transactionUser.size()){
							tUser = transactionUser.get(tPoint);
						}	
					}else{
						logger.error("Invalid update code");
						if(++tPoint < transactionUser.size()){
							tUser = transactionUser.get(tPoint);
						}	
					}
				}else{
					if (updateCode.equals("I")) {
						writeRow(sheet, tUser, row++);
						if(++tPoint < transactionUser.size()){
							tUser = transactionUser.get(tPoint);
						}
					}else if (updateCode.equals("U")) {
						logger.error("No matching master record for trans key");
						if(++tPoint < transactionUser.size()){
							tUser = transactionUser.get(tPoint);
						}
					}else if (updateCode.equals("D")) {
						logger.error("No matching master record for trans key");
						if(++tPoint < transactionUser.size()){
							tUser = transactionUser.get(tPoint);
						}
					}else{
						logger.error("Invalid update code");
						if(++tPoint < transactionUser.size()){
							tUser = transactionUser.get(tPoint);
						}	
					}
				}
			}
		}
		
		for(int i = tPoint; i < transactionUser.size(); i++){
			writeRow(sheet, tUser, row++);
			tUser = transactionUser.get(i);
		}
		
		for(int i = mPoint; i < masterUser.size(); i++){
			tUser = masterUser.get(i);
			writeRow(sheet, mUser, row++);
		}
	}
	
	private void writeRow(XSSFSheet sheet, User user, int rowNum){
		XSSFRow row =  sheet.createRow(rowNum);
		Map<Integer, String> masterLabel = readList.getMasterLabel();
		for(int i=0; i<masterLabel.size(); i++){
			XSSFCell cell = row.createCell(i);
			String label = masterLabel.get(i);
			if (label.equals("KEY")) {
				cell.setCellValue(user.getKey());
			} else if (label.equals("AGE")) {
				if(user.getAge() == null)
					cell.setAsActiveCell();
				else
					cell.setCellValue(user.getAge());				
			} else if (label.equals("FIRST_NAME")) {
				if(user.getFirstName() == null)
					cell.setAsActiveCell();
				else
					cell.setCellValue(user.getFirstName());
			} else if (label.equals("FAMILY_NAME")) {
				if(user.getFamilyName() == null)
					cell.setAsActiveCell();
				else
					cell.setCellValue(user.getFamilyName());
			} 
		}
	}
}
