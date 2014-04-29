/**
 * <h1>Item Object</h1>
 * 
 * @author Francis
 * 
 * @version 0.1
 * 
 * @since 14.04.29
 */
public class Item {

	private String key;

	private Integer height;

	public Item() {
		super();
	}

	/**
	 * Key 값은 스트링으로 들어온다.
	 * 
	 * @param key
	 *            ex) one two thr fou fiv six sev eig nin ten els twe
	 */
	public Item(String key) {
		this.key = key;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	@Override
	public String toString() {
		return "Item [key=" + key + ", height=" + height + "] \n ";
	}

}
