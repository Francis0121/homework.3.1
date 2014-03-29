package exception;

@SuppressWarnings("serial")
public class ReadTypeException extends RuntimeException {
	@Override
	public String getMessage() {
		return "First Line only character to Name Label";
	}
}
