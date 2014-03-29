import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Francis
 * 
 */
public class Reader {

	Logger logger = LoggerFactory.getLogger(Reader.class);

	/**
	 * Read Excel File
	 * 
	 * @param path
	 *            Absolut Path
	 */
	public void readExcel(String path) {

		try {
			XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(path)));

			int sheetNum = workbook.getNumberOfSheets();
			for (int i = 0; i < sheetNum; i++) {
				System.out.println("Sheet : " + workbook.getSheetName(i));
				XSSFSheet sheet = workbook.getSheetAt(i);

				int rows = sheet.getPhysicalNumberOfRows();
				for (int r = 0; r < rows; r++) {
					XSSFRow row = sheet.getRow(r);
					System.out.println("Row : " + row.getRowNum());

					int cells = row.getPhysicalNumberOfCells();
					for (int c = 0; c < cells; c++) {
						XSSFCell cell = row.getCell(c);
						int cellType = cell.getCellType();

						switch (cellType) {
						case 0:
							System.out.println(cell.getNumericCellValue());
							break;
						case 1:
							System.out.println(cell.getStringCellValue());
							break;
						}

					}
				}
			}

		} catch (FileNotFoundException e) {
			logger.error("File Not Found");
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("IO Exception");
			e.printStackTrace();
		}
	}
}
