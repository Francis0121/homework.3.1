import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.SheetName;

/**
 * 난수를 이용한 User 생성기
 * 
 * @author 20093267 김성근
 * 
 * @since 14.04.01
 * 
 * @version 0.2
 * 
 */
public class Transition {

	private static Logger logger = LoggerFactory.getLogger(Transition.class);

	private static String[] updateCodes = { "I", "D", "U", "I", "D", "U", "I",
			"D", "U", "I", "D", "U", "I", "D", "U", "I", "D", "U", "I", "D",
			"U", "I", "D", "U" };

	private static Integer[] ages = { null, null, null, null, null, 10, 13, 16,
			19, 22, 25, 29, 33, 37, 41, 45 };

	private static String[] firstNames = { "", "", "", "", "", "Albas",
			"Hekin", "Tom", "Little", "Few", "Han", "Pea", "Dong", "Bush",
			"Kelly" };

	private static String[] familyNames = { "", "", "", "", "", "Kim", "Kwon",
			"Ka", "Koo", "Lee", "Hong", "Park", "Ryu", "Cheon", "Ohh" };

	private static String[] nameLabels = { "UPDATE_CODE", "KEY", "FIRST_NAME",
			"FAMILY_NAME", "AGE", "TIME_STAMP" };

	private User getUser() {
		String updateCode = getUpdateCode();
		if (updateCode.equals("D")) {
			return new User(updateCode, getKey(), null, null, null,
					getTimeStamp());
		} else if (updateCode.equals("U")) {
			return new User(updateCode, getKey(), getFirstName(),
					getFamilyNames(), getAges(), getTimeStamp());
		} else if (updateCode.equals("I")) {
			return new User(updateCode, getKey(), getFirstName(),
					getFamilyNames(), getAges(), getTimeStamp());
		} else {
			logger.error("Not Allow update code");
			return null;
		}
	}

	private Integer getAges() {
		Random random = new Random(System.currentTimeMillis());
		return ages[random.nextInt(14)];
	}

	private String getFirstName() {
		Random random = new Random(System.currentTimeMillis());
		return firstNames[random.nextInt(14)];
	}

	private String getFamilyNames() {
		Random random = new Random(System.currentTimeMillis());
		return familyNames[random.nextInt(14)];
	}

	private String getUpdateCode() {
		Random random = new Random(System.currentTimeMillis());
		return updateCodes[random.nextInt(23)];
	}

	private Integer getKey() {
		Random random = new Random(System.currentTimeMillis());
		return random.nextInt(19) + 1;
	}

	private String getTimeStamp() {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		return sd.format(new Date());
	}

	public String writeTransitionExcel(String path) {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
		String fileName = SheetName.TRANSACTION.getText()
				+ sd.format(new Date());
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(path));

			XSSFSheet sheet = workbook.createSheet(fileName);
			XSSFRow row = sheet.createRow(0);

			for (int i = 0; i < nameLabels.length; i++) {
				XSSFCell cell = row.createCell(i);
				cell.setCellValue(nameLabels[i]);
			}

			for (int j = 0; j < 60; j++) {
				writeRow(sheet, getUser(), j + 1);
			}

			FileOutputStream stream = null;
			stream = new FileOutputStream(path);
			workbook.write(stream);
			stream.close();

			return fileName;
		} catch (FileNotFoundException e) {
			logger.error("File Not Found");
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("IO Exception");
			e.printStackTrace();
		}

		return null;
	}
	
	public void writeSortedTransitionExcel(String path, List<User> userList){
		
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(path));

			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
			XSSFSheet sheet = workbook.createSheet(SheetName.SORTED_TRANSACTION.getText() + sd.format(new Date()));
			XSSFRow row = sheet.createRow(0);

			for (int i = 0; i < nameLabels.length; i++) {
				XSSFCell cell = row.createCell(i);
				cell.setCellValue(nameLabels[i]);
			}
			
			int rowNum = 1;
			for(User user : userList){
				writeRow(sheet, user, rowNum++);
			}

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

	private void writeRow(XSSFSheet sheet, User user, int rowNum) {

		XSSFRow row = sheet.createRow(rowNum++);
		for (int i = 0; i < nameLabels.length; i++) {
			XSSFCell cell = row.createCell(i);
			String label = nameLabels[i];
			if (label.equals("UPDATE_CODE")) {
				cell.setCellValue(user.getUpdateCode());
			} else if (label.equals("KEY")) {
				cell.setCellValue(user.getKey());
			} else if (label.equals("TIME_STAMP")) {
				cell.setCellValue(user.getTimeStamp());
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
