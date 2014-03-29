import java.util.Comparator;

/**
 * Data Transfer Object - User Information
 * 
 * @author Francis
 * 
 * @version 0.1
 * 
 * @since 14.03.29
 */
public class User implements Comparator<User> {

	private String updateCode;

	private Integer key;

	private String firstName;

	private String familyName;

	private Integer age;

	public User() {
		super();
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
	 */
	public User(String updateCode, Integer key, String firstName,
			String familyName, Integer age) {
		super();
		this.updateCode = updateCode;
		this.key = key;
		this.firstName = firstName;
		this.familyName = familyName;
		this.age = age;
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

	public String getUpdateCode() {
		return updateCode;
	}

	public void setUpdateCode(String updateCode) {
		this.updateCode = updateCode;
	}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public int compare(User user1, User user2) {
		return user1.getKey().compareTo(user2.getKey());
	}

	@Override
	public String toString() {
		return "User [updateCode=" + updateCode + ", key=" + key
				+ ", firstName=" + firstName + ", familyName=" + familyName
				+ ", age=" + age + "]";
	}

}
