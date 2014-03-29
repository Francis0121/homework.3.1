package util;

import org.apache.poi.xssf.usermodel.XSSFCell;

public class Util {

	public static Integer getCellType(String label) {
		if (label.equals("KEY")) {
			return XSSFCell.CELL_TYPE_NUMERIC;
		} else if (label.equals("AGE")) {
			return XSSFCell.CELL_TYPE_NUMERIC;
		} else if (label.equals("FIRST_NAME")) {
			return XSSFCell.CELL_TYPE_STRING;
		} else if (label.equals("FAIMLY_NAME")) {
			return XSSFCell.CELL_TYPE_STRING;
		} else if (label.equals("UPDATE_CODE")) {
			return XSSFCell.CELL_TYPE_STRING;
		} else {
			return null;
		}
	}
}
