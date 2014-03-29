import java.util.List;
import java.util.Map;

public class ReadList {

	private List<User> notSortedTransactionUser;

	private List<User> sortedMasterUser;

	private Map<Integer, String> transactionLabel;

	private Map<Integer, String> masterLabel;

	public ReadList() {
		super();
	}

	public ReadList(List<User> notSortedTransactionUser,
			List<User> sortedMasterUser, Map<Integer, String> transactionLabel,
			Map<Integer, String> masterLabel) {
		super();
		this.notSortedTransactionUser = notSortedTransactionUser;
		this.sortedMasterUser = sortedMasterUser;
		this.transactionLabel = transactionLabel;
		this.masterLabel = masterLabel;
	}

	public Map<Integer, String> getMasterLabel() {
		return masterLabel;
	}

	public List<User> getNotSortedTransactionUser() {
		return notSortedTransactionUser;
	}

	public List<User> getSortedMasterUser() {
		return sortedMasterUser;
	}

	public Map<Integer, String> getTransactionLabel() {
		return transactionLabel;
	}

	@Override
	public String toString() {
		return "ReadList\n notSortedTransactionUser="
				+ notSortedTransactionUser + "\n sortedMasterUser="
				+ sortedMasterUser + "\n transactionLabel=" + transactionLabel
				+ "\n masterLabel=" + masterLabel;
	}

}
