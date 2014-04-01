import java.util.List;

/**
 * Update Alogrithm
 * 
 * @author Francis
 * 
 * @version 0.1
 * 
 * @since 14.03.29
 */
public class Main {

	public static String ABSOLUTE_PATH = "C:\\Users\\Francis\\git\\school3-1\\FileStructure\\UpdateAlgorithm\\UpdateAlogrithm.xlsx";

	public static void main(String[] args) {

		Transition transition = new Transition();
		String fileName = transition.writeTransitionExcel(ABSOLUTE_PATH);
		
		Reader reader = new Reader();
		ReadList readList = reader.readExcel(ABSOLUTE_PATH, fileName);
		List<User> sortedTransactionUser = readList.getTransactionUser();
		sortedTransactionUser.sort(new User());
		readList.setTransactionUser(sortedTransactionUser);
		transition.writeSortedTransitionExcel(ABSOLUTE_PATH,
				sortedTransactionUser);

		NewMaster newMaster = new NewMaster(readList);
		newMaster.writeExcle(ABSOLUTE_PATH);
	}

}
