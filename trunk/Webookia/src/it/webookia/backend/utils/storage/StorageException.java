package it.webookia.backend.utils.storage;

/**
 * Represents an error that may occur while performing queries to the storage.
 * 
 */
public class StorageException extends Exception {

    private static final long serialVersionUID = 6543690213058772850L;

    public StorageException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public StorageException(Throwable cause) {
        super(cause);
    }
}
