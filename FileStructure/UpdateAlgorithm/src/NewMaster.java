import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
 * @version 0.1
 * 
 * @since 14.03.29
 */
public class NewMaster {

	private static Logger logger = LoggerFactory.getLogger(NewMaster.class);

	private ReadList readList;

	public NewMaster() {
		super();
	}

	public NewMaster(ReadList readList) {
		this.readList = readList;
	}

	public void writeExcle(String path) {
		if (readList == null)
			throw new RuntimeException();

		try {		
			XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(path));

			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
			XSSFSheet sheet = workbook.createSheet(SheetName.NEW_MASTER.getText()+sd.format(new Date()));
			XSSFRow row = sheet.createRow(0);

			Map<Integer, String> masterLabel = readList.getMasterLabel();
			for (int i = 0; i < masterLabel.size(); i++) {
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

	private void updateAlogorithm(XSSFSheet sheet) {
		List<User> transactionUser = readList.getTransactionUser();
		List<User> masterUser = readList.getMasterUser();

		int tPoint = 0;
		int mPoint = 0;
		int row = 1;

		User tUser = transactionUser.get(tPoint);
		User mUser = masterUser.get(mPoint);

		while (tPoint < transactionUser.size() && mPoint < masterUser.size()) {
			if (mUser.getKey() < tUser.getKey()) {
				writeRow(sheet, mUser, row++);
				if (++mPoint < masterUser.size()) {
					mUser = masterUser.get(mPoint);
				}
			} else {
				String updateCode = tUser.getUpdateCode();
				if (mUser.getKey() == tUser.getKey()) {
					if (updateCode.equals("I")) {
						logger.error("Duplicate record Key " + tUser);
						if (++tPoint < transactionUser.size()) {
							tUser = transactionUser.get(tPoint);
						}
					} else if (updateCode.equals("U")) {
						User uUser = new User(mUser.getKey(),
								tUser.getFirstName() != null && !tUser.getFirstName().equals("") ? tUser.getFirstName() : mUser.getFirstName(),
								tUser.getFamilyName() != null && !tUser.getFamilyName().equals("") ? tUser.getFamilyName() : mUser.getFamilyName(),
								tUser.getAge() != null && !tUser.getAge().equals("")? tUser.getAge() : mUser.getAge());

						tPoint += 1;
						if (tPoint < transactionUser.size()) {
							tUser = transactionUser.get(tPoint);
						}else if(tPoint == transactionUser.size()){
							tUser = new User();
						}
						
						if(!mUser.getKey().equals(tUser.getKey())){
							if (++mPoint < masterUser.size()) {
								mUser = masterUser.get(mPoint);
							}
							writeRow(sheet, uUser, row++);
						}else{
							mUser = uUser;
						}
					} else if (updateCode.equals("D")) {
						if (++tPoint < transactionUser.size()) {
							tUser = transactionUser.get(tPoint);
						}
						
						if (++mPoint < masterUser.size()) {
							mUser = masterUser.get(mPoint);
						}
					} else {
						logger.error("Invalid update code " + tUser);
						if (++tPoint < transactionUser.size()) {
							tUser = transactionUser.get(tPoint);
						}
					}
				} else {
					if (updateCode.equals("I")) {
						User uUser = tUser;
						if (++tPoint < transactionUser.size()) {
							tUser = transactionUser.get(tPoint);
						}
						
						if(!uUser.getKey().equals(tUser.getKey())){
							writeRow(sheet, uUser, row++);
						}else{
							mUser = uUser;
							mPoint--;
						}
					} else if (updateCode.equals("U")) {
						logger.error("No matching master record for trans key " + tUser);
						if (++tPoint < transactionUser.size()) {
							tUser = transactionUser.get(tPoint);
						}
					} else if (updateCode.equals("D")) {
						logger.error("No matching master record for trans key " + tUser);
						if (++tPoint < transactionUser.size()) {
							tUser = transactionUser.get(tPoint);
						}
					} else {
						logger.error("Invalid update code " + tUser);
						if (++tPoint < transactionUser.size()) {
							tUser = transactionUser.get(tPoint);
						}
					}
				}
			}
		}

		for (int i = tPoint; i < transactionUser.size(); i++) {
			tUser = transactionUser.get(i);
			String updateCode = tUser.getUpdateCode();
			if (updateCode.equals("I")) {
				writeRow(sheet, tUser, row++);
				if (++tPoint < transactionUser.size()) {
					tUser = transactionUser.get(tPoint);
				}
			} else if (updateCode.equals("U")) {
				logger.error("No matching master record for trans key " + tUser);
				if (++tPoint < transactionUser.size()) {
					tUser = transactionUser.get(tPoint);
				}
			} else if (updateCode.equals("D")) {
				logger.error("No matching master record for trans key " + tUser);
				if (++tPoint < transactionUser.size()) {
					tUser = transactionUser.get(tPoint);
				}
			} else {
				logger.error("Invalid update code " + tUser);
				if (++tPoint < transactionUser.size()) {
					tUser = transactionUser.get(tPoint);
				}
			}
		}
		
		if(mPoint++ < masterUser.size()){
			writeRow(sheet, mUser, row++);
		}
		for (int i = mPoint; i < masterUser.size(); i++) {
			mUser = masterUser.get(i);
			writeRow(sheet, mUser, row++);
		}
	}

	private void writeRow(XSSFSheet sheet, User user, int rowNum) {
		XSSFRow row = sheet.createRow(rowNum);
		Map<Integer, String> masterLabel = readList.getMasterLabel();
		for (int i = 0; i < masterLabel.size(); i++) {
			XSSFCell cell = row.createCell(i);
			String label = masterLabel.get(i);
			if (label.equals("KEY")) {
				cell.setCellValue(user.getKey());
			} else if (label.equals("AGE")) {
				if (user.getAge() == null)
					cell.setAsActiveCell();
				else
					cell.setCellValue(user.getAge());
			} else if (label.equals("FIRST_NAME")) {
				if (user.getFirstName() == null)
					cell.setAsActiveCell();
				else
					cell.setCellValue(user.getFirstName());
			} else if (label.equals("FAMILY_NAME")) {
				if (user.getFamilyName() == null)
					cell.setAsActiveCell();
				else
					cell.setCellValue(user.getFamilyName());
			}
		}
	}
	
}
