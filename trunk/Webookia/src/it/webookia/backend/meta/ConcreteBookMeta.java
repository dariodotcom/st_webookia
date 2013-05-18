package it.webookia.backend.meta;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2013-05-18 12:52:48")
/** */
public final class ConcreteBookMeta extends org.slim3.datastore.ModelMeta<it.webookia.backend.model.ConcreteBook> {

    /** */
    public final org.slim3.datastore.ModelRefAttributeMeta<it.webookia.backend.model.ConcreteBook, org.slim3.datastore.ModelRef<it.webookia.backend.model.BookDetails>, it.webookia.backend.model.BookDetails> bookDetails = new org.slim3.datastore.ModelRefAttributeMeta<it.webookia.backend.model.ConcreteBook, org.slim3.datastore.ModelRef<it.webookia.backend.model.BookDetails>, it.webookia.backend.model.BookDetails>(this, "bookDetails", "bookDetails", org.slim3.datastore.ModelRef.class, it.webookia.backend.model.BookDetails.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<it.webookia.backend.model.ConcreteBook, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<it.webookia.backend.model.ConcreteBook, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.ModelRefAttributeMeta<it.webookia.backend.model.ConcreteBook, org.slim3.datastore.ModelRef<it.webookia.backend.model.UserEntity>, it.webookia.backend.model.UserEntity> owner = new org.slim3.datastore.ModelRefAttributeMeta<it.webookia.backend.model.ConcreteBook, org.slim3.datastore.ModelRef<it.webookia.backend.model.UserEntity>, it.webookia.backend.model.UserEntity>(this, "owner", "owner", org.slim3.datastore.ModelRef.class, it.webookia.backend.model.UserEntity.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<it.webookia.backend.model.ConcreteBook, it.webookia.backend.enums.PrivacyLevel> privacy = new org.slim3.datastore.CoreAttributeMeta<it.webookia.backend.model.ConcreteBook, it.webookia.backend.enums.PrivacyLevel>(this, "privacy", "privacy", it.webookia.backend.enums.PrivacyLevel.class);

    /** */
    public final org.slim3.datastore.ModelRefAttributeMeta<it.webookia.backend.model.ConcreteBook, org.slim3.datastore.ModelRef<it.webookia.backend.model.Review>, it.webookia.backend.model.Review> review = new org.slim3.datastore.ModelRefAttributeMeta<it.webookia.backend.model.ConcreteBook, org.slim3.datastore.ModelRef<it.webookia.backend.model.Review>, it.webookia.backend.model.Review>(this, "review", "review", org.slim3.datastore.ModelRef.class, it.webookia.backend.model.Review.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<it.webookia.backend.model.ConcreteBook, it.webookia.backend.enums.BookStatus> status = new org.slim3.datastore.CoreAttributeMeta<it.webookia.backend.model.ConcreteBook, it.webookia.backend.enums.BookStatus>(this, "status", "status", it.webookia.backend.enums.BookStatus.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<it.webookia.backend.model.ConcreteBook, java.lang.Long> version = new org.slim3.datastore.CoreAttributeMeta<it.webookia.backend.model.ConcreteBook, java.lang.Long>(this, "version", "version", java.lang.Long.class);

    private static final ConcreteBookMeta slim3_singleton = new ConcreteBookMeta();

    /**
     * @return the singleton
     */
    public static ConcreteBookMeta get() {
       return slim3_singleton;
    }

    /** */
    public ConcreteBookMeta() {
        super("ConcreteBook", it.webookia.backend.model.ConcreteBook.class);
    }

    @Override
    public it.webookia.backend.model.ConcreteBook entityToModel(com.google.appengine.api.datastore.Entity entity) {
        it.webookia.backend.model.ConcreteBook model = new it.webookia.backend.model.ConcreteBook();
        if (model.getBookDetails() == null) {
            throw new NullPointerException("The property(bookDetails) is null.");
        }
        model.getBookDetails().setKey((com.google.appengine.api.datastore.Key) entity.getProperty("bookDetails"));
        model.setKey(entity.getKey());
        if (model.getOwner() == null) {
            throw new NullPointerException("The property(owner) is null.");
        }
        model.getOwner().setKey((com.google.appengine.api.datastore.Key) entity.getProperty("owner"));
        model.setPrivacy(stringToEnum(it.webookia.backend.enums.PrivacyLevel.class, (java.lang.String) entity.getProperty("privacy")));
        if (model.getReview() == null) {
            throw new NullPointerException("The property(review) is null.");
        }
        model.getReview().setKey((com.google.appengine.api.datastore.Key) entity.getProperty("review"));
        model.setStatus(stringToEnum(it.webookia.backend.enums.BookStatus.class, (java.lang.String) entity.getProperty("status")));
        model.setVersion((java.lang.Long) entity.getProperty("version"));
        return model;
    }

    @Override
    public com.google.appengine.api.datastore.Entity modelToEntity(java.lang.Object model) {
        it.webookia.backend.model.ConcreteBook m = (it.webookia.backend.model.ConcreteBook) model;
        com.google.appengine.api.datastore.Entity entity = null;
        if (m.getKey() != null) {
            entity = new com.google.appengine.api.datastore.Entity(m.getKey());
        } else {
            entity = new com.google.appengine.api.datastore.Entity(kind);
        }
        if (m.getBookDetails() == null) {
            throw new NullPointerException("The property(bookDetails) must not be null.");
        }
        entity.setProperty("bookDetails", m.getBookDetails().getKey());
        if (m.getOwner() == null) {
            throw new NullPointerException("The property(owner) must not be null.");
        }
        entity.setProperty("owner", m.getOwner().getKey());
        entity.setProperty("privacy", enumToString(m.getPrivacy()));
        if (m.getReview() == null) {
            throw new NullPointerException("The property(review) must not be null.");
        }
        entity.setProperty("review", m.getReview().getKey());
        entity.setProperty("status", enumToString(m.getStatus()));
        entity.setProperty("version", m.getVersion());
        entity.setProperty("slim3.schemaVersion", 1);
        return entity;
    }

    @Override
    protected com.google.appengine.api.datastore.Key getKey(Object model) {
        it.webookia.backend.model.ConcreteBook m = (it.webookia.backend.model.ConcreteBook) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        it.webookia.backend.model.ConcreteBook m = (it.webookia.backend.model.ConcreteBook) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        it.webookia.backend.model.ConcreteBook m = (it.webookia.backend.model.ConcreteBook) model;
        return m.getVersion() != null ? m.getVersion().longValue() : 0L;
    }

    @Override
    protected void assignKeyToModelRefIfNecessary(com.google.appengine.api.datastore.AsyncDatastoreService ds, java.lang.Object model) {
        it.webookia.backend.model.ConcreteBook m = (it.webookia.backend.model.ConcreteBook) model;
        if (m.getBookDetails() == null) {
            throw new NullPointerException("The property(bookDetails) must not be null.");
        }
        m.getBookDetails().assignKeyIfNecessary(ds);
        if (m.getOwner() == null) {
            throw new NullPointerException("The property(owner) must not be null.");
        }
        m.getOwner().assignKeyIfNecessary(ds);
        if (m.getReview() == null) {
            throw new NullPointerException("The property(review) must not be null.");
        }
        m.getReview().assignKeyIfNecessary(ds);
    }

    @Override
    protected void incrementVersion(Object model) {
        it.webookia.backend.model.ConcreteBook m = (it.webookia.backend.model.ConcreteBook) model;
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
        it.webookia.backend.model.ConcreteBook m = (it.webookia.backend.model.ConcreteBook) model;
        writer.beginObject();
        org.slim3.datastore.json.Default encoder0 = new org.slim3.datastore.json.Default();
        if(m.getBookDetails() != null && m.getBookDetails().getKey() != null){
            writer.setNextPropertyName("bookDetails");
            encoder0.encode(writer, m.getBookDetails(), maxDepth, currentDepth);
        }
        if(m.getKey() != null){
            writer.setNextPropertyName("key");
            encoder0.encode(writer, m.getKey());
        }
        if(m.getOwner() != null && m.getOwner().getKey() != null){
            writer.setNextPropertyName("owner");
            encoder0.encode(writer, m.getOwner(), maxDepth, currentDepth);
        }
        if(m.getPrivacy() != null){
            writer.setNextPropertyName("privacy");
            encoder0.encode(writer, m.getPrivacy());
        }
        if(m.getReview() != null && m.getReview().getKey() != null){
            writer.setNextPropertyName("review");
            encoder0.encode(writer, m.getReview(), maxDepth, currentDepth);
        }
        if(m.getStatus() != null){
            writer.setNextPropertyName("status");
            encoder0.encode(writer, m.getStatus());
        }
        if(m.getVersion() != null){
            writer.setNextPropertyName("version");
            encoder0.encode(writer, m.getVersion());
        }
        writer.endObject();
    }

    @Override
    protected it.webookia.backend.model.ConcreteBook jsonToModel(org.slim3.datastore.json.JsonRootReader rootReader, int maxDepth, int currentDepth) {
        it.webookia.backend.model.ConcreteBook m = new it.webookia.backend.model.ConcreteBook();
        org.slim3.datastore.json.JsonReader reader = null;
        org.slim3.datastore.json.Default decoder0 = new org.slim3.datastore.json.Default();
        reader = rootReader.newObjectReader("bookDetails");
        decoder0.decode(reader, m.getBookDetails(), maxDepth, currentDepth);
        reader = rootReader.newObjectReader("key");
        m.setKey(decoder0.decode(reader, m.getKey()));
        reader = rootReader.newObjectReader("owner");
        decoder0.decode(reader, m.getOwner(), maxDepth, currentDepth);
        reader = rootReader.newObjectReader("privacy");
        m.setPrivacy(decoder0.decode(reader, m.getPrivacy(), it.webookia.backend.enums.PrivacyLevel.class));
        reader = rootReader.newObjectReader("review");
        decoder0.decode(reader, m.getReview(), maxDepth, currentDepth);
        reader = rootReader.newObjectReader("status");
        m.setStatus(decoder0.decode(reader, m.getStatus(), it.webookia.backend.enums.BookStatus.class));
        reader = rootReader.newObjectReader("version");
        m.setVersion(decoder0.decode(reader, m.getVersion()));
        return m;
    }
}