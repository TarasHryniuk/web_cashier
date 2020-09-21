package cashier.exception;


public class AmountManagerException extends Exception {

	public AmountManagerException() {}
	
	public AmountManagerException(String message) {
		super(message);
	}
	
	public AmountManagerException(Throwable cause) {
		super(cause);
	}
	
	public AmountManagerException(String message, Throwable cause) {
		super(message, cause);
	}

}
