import java.util.List;
import java.util.Map;

/**
 * @author Francis
 * 
 * @version 0.1
 * 
 * @since 14.03.29
 */
public class ReadList {

	private List<User> transactionUser;

	private List<User> masterUser;

	private Map<Integer, String> transactionLabel;

	private Map<Integer, String> masterLabel;

	public ReadList() {
		super();
	}

	public ReadList(List<User> transactionUser, List<User> masterUser,
			Map<Integer, String> transactionLabel,
			Map<Integer, String> masterLabel) {
		super();
		this.transactionUser = transactionUser;
		this.masterUser = masterUser;
		this.transactionLabel = transactionLabel;
		this.masterLabel = masterLabel;
	}

	public List<User> getTransactionUser() {
		return transactionUser;
	}

	public void setTransactionUser(List<User> transactionUser) {
		this.transactionUser = transactionUser;
	}

	public List<User> getMasterUser() {
		return masterUser;
	}

	public Map<Integer, String> getTransactionLabel() {
		return transactionLabel;
	}

	public Map<Integer, String> getMasterLabel() {
		return masterLabel;
	}

	@Override
	public String toString() {
		return "ReadList [transactionUser=" + transactionUser + ", masterUser="
				+ masterUser + ", transactionLabel=" + transactionLabel
				+ ", masterLabel=" + masterLabel + "]";
	}

}
