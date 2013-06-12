package it.webookia.backend.utils.storage;

public class StorageException extends Exception {

    private static final long serialVersionUID = 6543690213058772850L;

    public StorageException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public StorageException(Throwable cause) {
        super(cause);
    }
}
