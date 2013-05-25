package it.webookia.backend.utils.storage;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * Encapsulates the process of storing elements of generic type in the storage.
 * Elements must implements {@link Storable}.
 * */
public class StorageFacade<T extends Storable> {

    private Class<T> type;

    /**
     * Constructs a new instance of {@link StorageFacade} that handles entities
     * of given type.
     * 
     * @param type
     */
    public StorageFacade(Class<T> type) {
        this.type = type;
    }

    /**
     * Retrieves an object linked to the given key from the storage.
     * 
     * @param key
     *            - the key to which the object is linked to.
     * @return the retrieved object, null if the object does not exsist.
     * */
    public T get(String key) {
        Key k;

        try {
            k = KeyFactory.stringToKey(key);
        } catch (IllegalArgumentException e) {
            return null;
        }
        return Datastore.get(type, k);
    }

    /**
     * Persists an element into the storage. The key is automatically retrieved
     * from the given object as it implements the {@link Storable} interface.
     * Already existing objects are replaced.
     * 
     * @param value
     *            - the value to store.
     * */
    public void persist(T value) {
        Datastore.put(value);
    }
}