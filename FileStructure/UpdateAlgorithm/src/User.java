import java.util.Comparator;

/**
 * Data Transfer Object - User Information
 * 
 * @author Francis
 * 
 * @version 0.2
 * 
 * @since 14.04.01
 */
public class User implements Comparator<User> {

	private String updateCode;

	private Integer key;

	private String firstName;

	private String familyName;

	private Integer age;

	private String timeStamp;

	public User() {
		super();
	}

	/**
	 * Master Data
	 * 
	 * @param key
	 *            Integer Primary Key
	 * @param firstName
	 *            String NULL
	 * @param familyName
	 *            String NULL
	 * @param age
	 *            Intger NULL
	 */
	public User(Integer key, String firstName, String familyName, Integer age) {
		super();
		this.key = key;
		this.firstName = firstName;
		this.familyName = familyName;
		this.age = age;
	}

	/**
	 * Transaction Data
	 * 
	 * @param updateCode
	 *            String NOT NULL
	 * @param key
	 *            Integer Primary key
	 * @param firstName
	 *            String NULL
	 * @param familyName
	 *            String NULL
	 * @param age
	 *            Integer NULL
	 * @param timeStamp
	 *            String NOU NULL
	 */
	public User(String updateCode, Integer key, String firstName,
			String familyName, Integer age, String timeStamp) {
		super();
		this.updateCode = updateCode;
		this.key = key;
		this.firstName = firstName;
		this.familyName = familyName;
		this.age = age;
		this.timeStamp = timeStamp;
	}

	@Override
	public int compare(User user1, User user2) {
		// Key Sort
		int sort = user1.getKey().compareTo(user2.getKey());
		if (sort == 0) {
			// key 가 같다면 timestamp sort
			sort = user1.getTimeStamp().compareTo(user2.getTimeStamp());
		}
		return sort;
	}

	public Integer getAge() {
		return age;
	}

	public String getFamilyName() {
		return familyName;
	}

	public String getFirstName() {
		return firstName;
	}

	public Integer getKey() {
		return key;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public String getUpdateCode() {
		return updateCode;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public void setUpdateCode(String updateCode) {
		this.updateCode = updateCode;
	}

	@Override
	public String toString() {
		return "User [updateCode=" + updateCode + ", key=" + key
				+ ", firstName=" + firstName + ", familyName=" + familyName
				+ ", age=" + age + ", timeStamp=" + timeStamp + "]";
	}

}
