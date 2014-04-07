

public class Main {

	public static void main(String[] args) {
		RaceCondition raceCondition = new RaceCondition();

		for (int i = 0; i < 100; i++) {
			String str = raceCondition.fileReader();
			raceCondition.fileWriter(Integer.parseInt(str) + 1);
		}

	}
}
