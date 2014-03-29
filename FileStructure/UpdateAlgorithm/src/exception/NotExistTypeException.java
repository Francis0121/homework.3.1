package exception;

@SuppressWarnings("serial")
public class NotExistTypeException extends RuntimeException{
	@Override
	public String getMessage() {
		return "Type Not Exist";
	}
}
