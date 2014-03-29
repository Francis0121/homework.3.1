package util;
/**
 * Sheet Name 에대한 Enum 적용
 * 
 * @author Francis
 *
 * @version 0.1
 * 
 * @since 14.03.29
 * 
 */
public enum SheetName {
	
	TRANSACTION("Transaction"), 
	OLD_MASTER("OldMaster"), 
	NEW_MASTER("NewMaster");

	private String text;

	SheetName(String text) {
		this.text = text;
	}

	public String getText() {
		return this.text;
	}

	public static SheetName fromString(String text) {
		if (text != null) {
			for (SheetName sn : SheetName.values()) {
				if (text.equalsIgnoreCase(sn.text)) {
					return sn;
				}
			}
		}
		return null;
	}
	
}
