package poi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
public class Writer {

	private static Logger logger = LoggerFactory.getLogger(Writer.class);

	public void write(String path, String sheetName, int recordSize,
			List<Integer> keyList) {
		try {
			XSSFWorkbook workbook;
			workbook = new XSSFWorkbook(new FileInputStream(path));
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
			XSSFSheet sheet = workbook.createSheet(sheetName
					+ sd.format(new Date()));

			XSSFRow row = sheet.createRow(0);
			XSSFCell cell = row.createCell(0);
			cell.setCellValue("Create Run " + recordSize);

			logger.info("------------------------------");
			logger.info("Start Create Run " + recordSize);
			logger.info("------------------------------");

			List<Integer> unfrozens = new ArrayList<Integer>();
			List<Integer> frozens = new ArrayList<Integer>();
			List<Integer> records = new ArrayList<Integer>();

			for (int i = 0; i < recordSize; i++) {
				unfrozens.add(keyList.get(i));
			}

			logger.debug(unfrozens.toString());

			Integer run = 1;
			for (int i = recordSize; i < keyList.size(); i++) {

				while (unfrozens.size() != 0) {
					Integer smallest = Collections.min(unfrozens);
					records.add(smallest);
					unfrozens.remove(smallest);
					Integer key = i < keyList.size() ? keyList.get(i++) : null;

					if (key != null) {
						if (key < smallest)
							frozens.add(key);
						else
							unfrozens.add(key);
					}
					logger.debug("Smallest : " + smallest + " Key : " + key);
					logger.debug("Unfrozen : " + unfrozens.toString());
					logger.debug("Frozen : " + frozens.toString());
					logger.debug("Record : " + records.toString());
					logger.debug("------------------------------");
				}
				logger.info("Run " + run + " : " + records.toString());
				createRow(sheet, run, records);
				unfrozens = frozens;
				frozens = new ArrayList<>();
				records = new ArrayList<>();
				run++;
				i--;
			}

			if (unfrozens.size() != 0) {
				while (unfrozens.size() != 0) {
					Integer smallest = Collections.min(unfrozens);
					records.add(smallest);
					unfrozens.remove(smallest);
					logger.debug("Unfrozen : " + unfrozens.toString());
					logger.debug("Frozen : " + frozens.toString());
					logger.debug("Record : " + records.toString());
					logger.debug("------------------------------");
				}
				createRow(sheet, run, records);
				logger.info("Run " + run + " : " + records.toString());
			}

			FileOutputStream stream = new FileOutputStream(path);
			workbook.write(stream);
			stream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createRow(XSSFSheet sheet, Integer rowNum,
			List<Integer> records) {
		XSSFRow row = sheet.createRow(rowNum);
		XSSFCell cell = row.createCell(0);
		cell.setCellValue("Run " + rowNum);
		for (int i = 0; i < records.size(); i++) {
			cell = row.createCell(i + 1);
			cell.setCellValue(records.get(i));
		}
	}
}
