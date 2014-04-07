import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	private static Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		logger.info("Improve Race Condition Start");
		RaceCondition raceCondition = new RaceCondition(1);

		for (int i = 0; i < 100; i++) {
			String str = raceCondition.fileReader();
			raceCondition.fileWriter(Integer.parseInt(str) + 1);
		}
		logger.info("Improve Race Condition End");
	}
}
