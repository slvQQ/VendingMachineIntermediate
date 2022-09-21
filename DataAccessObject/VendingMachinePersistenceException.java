package DataAccessObject;

public class VendingMachinePersistenceException extends Throwable {


    public VendingMachinePersistenceException (String message) {
        super(message);
    }

    public VendingMachinePersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
