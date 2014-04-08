package poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Francis
 * @version 0.1
 * @since 14.04.08
 */
public class Reader {

	private Logger logger = LoggerFactory.getLogger(Reader.class);

	public static Integer ROW_NUMBER = 0;

	public List<Integer> read(String path, String sheetName) {

		List<Integer> keyList = new ArrayList<Integer>();

		try {
			XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(
					new File(path)));

			XSSFSheet sheet = workbook.getSheet(sheetName);
			XSSFRow row = sheet.getRow(ROW_NUMBER);
			for (int cellNum = 0; cellNum < row.getPhysicalNumberOfCells(); cellNum++) {
				XSSFCell cell = row.getCell(cellNum);
				Integer key = (int) cell.getNumericCellValue();
				keyList.add(key);
			}

			logger.debug("Size " +keyList.size());
			logger.debug(keyList.toString());
		} catch (FileNotFoundException e) {
			logger.error("FileNotFoundException");
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("IOException");
			e.printStackTrace();
		}

		return keyList;
	}
}
