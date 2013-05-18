package it.webookia.backend.utils.storage;

/**
 * Objects implementing this interface can be stored into storage via
 * {@link StorageFacade} instances.
 * */
public interface Storable {
    /**
     * Returns the id that should be used by {@link StorageFacade} to store the
     * element.
     * 
     * @return the id corresponding to <b>this</b> object.
     * */
    public String getId();
}
