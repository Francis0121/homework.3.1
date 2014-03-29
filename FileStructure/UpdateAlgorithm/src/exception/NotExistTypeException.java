package exception;

/**
 * @author Francis
 * 
 * @version 0.1
 * 
 * @since 14.03.29
 */
@SuppressWarnings("serial")
public class NotExistTypeException extends RuntimeException {
	@Override
	public String getMessage() {
		return "Type Not Exist";
	}
}
