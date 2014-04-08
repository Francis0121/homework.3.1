import java.util.Arrays;
import java.util.List;

import poi.Reader;
import poi.Writer;

/**
 * @author Francis
 * @version 0.1
 * @since 14.04.08
 */
public class Main {

	public static String DIRECTORY_PATH = "ReplacementSelection.xlsx";

	public static String SHEET_NAME = "File";
	public static String RUN_SHEET_NAME = "Run";

	public static void main(String[] args) {

		Reader reader = new Reader();

		List<Integer> keyList = reader.read(DIRECTORY_PATH, SHEET_NAME);

		List<Integer> recordSizeList = Arrays.asList(4, 5, 6);

		Writer writer = new Writer();
		for (Integer recordSize : recordSizeList) {
			writer.write(DIRECTORY_PATH, RUN_SHEET_NAME + recordSize,
					recordSize, keyList);
		}

	}

}
