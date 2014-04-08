import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import poi.Reader;

/**
 * @author Francis
 * @version 0.1
 * @since 14.04.08
 */
public class Main {

	private static Logger logger = LoggerFactory.getLogger(Reader.class);

	public static String DIRECTORY_PATH = "ReplacementSelection.xlsx";

	public static String SHEET_NAME = "File";

	public static void main(String[] args) {

		Reader reader = new Reader();

		List<Integer> keyList = reader.read(DIRECTORY_PATH, SHEET_NAME);

		List<Integer> recordSizeList = Arrays.asList(4, 5, 6);

		for (Integer recordSize : recordSizeList) {
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
				logger.info("Run " + run + " : " + records.toString());
			}
		}
	}

}
