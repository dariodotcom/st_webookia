package it.webookia.backend.meta;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2013-05-18 13:02:40")
/** */
public final class UserEntityMeta extends org.slim3.datastore.ModelMeta<it.webookia.backend.model.UserEntity> {

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<it.webookia.backend.model.UserEntity, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<it.webookia.backend.model.UserEntity, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.UnindexedAttributeMeta<it.webookia.backend.model.UserEntity, it.webookia.backend.utils.foreignws.facebook.AccessToken> token = new org.slim3.datastore.UnindexedAttributeMeta<it.webookia.backend.model.UserEntity, it.webookia.backend.utils.foreignws.facebook.AccessToken>(this, "token", "token", it.webookia.backend.utils.foreignws.facebook.AccessToken.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<it.webookia.backend.model.UserEntity, java.lang.Long> version = new org.slim3.datastore.CoreAttributeMeta<it.webookia.backend.model.UserEntity, java.lang.Long>(this, "version", "version", java.lang.Long.class);

    private static final UserEntityMeta slim3_singleton = new UserEntityMeta();

    /**
     * @return the singleton
     */
    public static UserEntityMeta get() {
       return slim3_singleton;
    }

    /** */
    public UserEntityMeta() {
        super("UserEntity", it.webookia.backend.model.UserEntity.class);
    }

    @Override
    public it.webookia.backend.model.UserEntity entityToModel(com.google.appengine.api.datastore.Entity entity) {
        it.webookia.backend.model.UserEntity model = new it.webookia.backend.model.UserEntity();
        model.setKey(entity.getKey());
        it.webookia.backend.utils.foreignws.facebook.AccessToken _token = blobToSerializable((com.google.appengine.api.datastore.Blob) entity.getProperty("token"));
        model.setToken(_token);
        model.setVersion((java.lang.Long) entity.getProperty("version"));
        return model;
    }

    @Override
    public com.google.appengine.api.datastore.Entity modelToEntity(java.lang.Object model) {
        it.webookia.backend.model.UserEntity m = (it.webookia.backend.model.UserEntity) model;
        com.google.appengine.api.datastore.Entity entity = null;
        if (m.getKey() != null) {
            entity = new com.google.appengine.api.datastore.Entity(m.getKey());
        } else {
            entity = new com.google.appengine.api.datastore.Entity(kind);
        }
        entity.setUnindexedProperty("token", serializableToBlob(m.getToken()));
        entity.setProperty("version", m.getVersion());
        entity.setProperty("slim3.schemaVersion", 1);
        return entity;
    }

    @Override
    protected com.google.appengine.api.datastore.Key getKey(Object model) {
        it.webookia.backend.model.UserEntity m = (it.webookia.backend.model.UserEntity) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        it.webookia.backend.model.UserEntity m = (it.webookia.backend.model.UserEntity) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        it.webookia.backend.model.UserEntity m = (it.webookia.backend.model.UserEntity) model;
        return m.getVersion() != null ? m.getVersion().longValue() : 0L;
    }

    @Override
    protected void assignKeyToModelRefIfNecessary(com.google.appengine.api.datastore.AsyncDatastoreService ds, java.lang.Object model) {
    }

    @Override
    protected void incrementVersion(Object model) {
        it.webookia.backend.model.UserEntity m = (it.webookia.backend.model.UserEntity) model;
        long version = m.getVersion() != null ? m.getVersion().longValue() : 0L;
        m.setVersion(Long.valueOf(version + 1L));
    }

    @Override
    protected void prePut(Object model) {
    }

    @Override
    protected void postGet(Object model) {
    }

    @Override
    public String getSchemaVersionName() {
        return "slim3.schemaVersion";
    }

    @Override
    public String getClassHierarchyListName() {
        return "slim3.classHierarchyList";
    }

    @Override
    protected boolean isCipherProperty(String propertyName) {
        return false;
    }

    @Override
    protected void modelToJson(org.slim3.datastore.json.JsonWriter writer, java.lang.Object model, int maxDepth, int currentDepth) {
        it.webookia.backend.model.UserEntity m = (it.webookia.backend.model.UserEntity) model;
        writer.beginObject();
        org.slim3.datastore.json.Default encoder0 = new org.slim3.datastore.json.Default();
        if(m.getKey() != null){
            writer.setNextPropertyName("key");
            encoder0.encode(writer, m.getKey());
        }
        if(m.getToken() != null){
            writer.setNextPropertyName("token");
            encoder0.encode(writer, m.getToken());
        }
        if(m.getVersion() != null){
            writer.setNextPropertyName("version");
            encoder0.encode(writer, m.getVersion());
        }
        writer.endObject();
    }

    @Override
    protected it.webookia.backend.model.UserEntity jsonToModel(org.slim3.datastore.json.JsonRootReader rootReader, int maxDepth, int currentDepth) {
        it.webookia.backend.model.UserEntity m = new it.webookia.backend.model.UserEntity();
        org.slim3.datastore.json.JsonReader reader = null;
        org.slim3.datastore.json.Default decoder0 = new org.slim3.datastore.json.Default();
        reader = rootReader.newObjectReader("key");
        m.setKey(decoder0.decode(reader, m.getKey()));
        reader = rootReader.newObjectReader("token");
        m.setToken(decoder0.decode(reader, m.getToken(), it.webookia.backend.utils.foreignws.facebook.AccessToken.class));
        reader = rootReader.newObjectReader("version");
        m.setVersion(decoder0.decode(reader, m.getVersion()));
        return m;
    }
}