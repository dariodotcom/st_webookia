package it.webookia.backend.meta;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2013-05-18 12:52:48")
/** */
public final class CommentMeta extends org.slim3.datastore.ModelMeta<it.webookia.backend.model.Comment> {

    /** */
    public final org.slim3.datastore.ModelRefAttributeMeta<it.webookia.backend.model.Comment, org.slim3.datastore.ModelRef<it.webookia.backend.model.UserEntity>, it.webookia.backend.model.UserEntity> author = new org.slim3.datastore.ModelRefAttributeMeta<it.webookia.backend.model.Comment, org.slim3.datastore.ModelRef<it.webookia.backend.model.UserEntity>, it.webookia.backend.model.UserEntity>(this, "author", "author", org.slim3.datastore.ModelRef.class, it.webookia.backend.model.UserEntity.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<it.webookia.backend.model.Comment, java.util.Date> date = new org.slim3.datastore.CoreAttributeMeta<it.webookia.backend.model.Comment, java.util.Date>(this, "date", "date", java.util.Date.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<it.webookia.backend.model.Comment, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<it.webookia.backend.model.Comment, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.ModelRefAttributeMeta<it.webookia.backend.model.Comment, org.slim3.datastore.ModelRef<it.webookia.backend.model.Review>, it.webookia.backend.model.Review> review = new org.slim3.datastore.ModelRefAttributeMeta<it.webookia.backend.model.Comment, org.slim3.datastore.ModelRef<it.webookia.backend.model.Review>, it.webookia.backend.model.Review>(this, "review", "review", org.slim3.datastore.ModelRef.class, it.webookia.backend.model.Review.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<it.webookia.backend.model.Comment> text = new org.slim3.datastore.StringAttributeMeta<it.webookia.backend.model.Comment>(this, "text", "text");

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<it.webookia.backend.model.Comment, java.lang.Long> version = new org.slim3.datastore.CoreAttributeMeta<it.webookia.backend.model.Comment, java.lang.Long>(this, "version", "version", java.lang.Long.class);

    private static final CommentMeta slim3_singleton = new CommentMeta();

    /**
     * @return the singleton
     */
    public static CommentMeta get() {
       return slim3_singleton;
    }

    /** */
    public CommentMeta() {
        super("Comment", it.webookia.backend.model.Comment.class);
    }

    @Override
    public it.webookia.backend.model.Comment entityToModel(com.google.appengine.api.datastore.Entity entity) {
        it.webookia.backend.model.Comment model = new it.webookia.backend.model.Comment();
        if (model.getAuthor() == null) {
            throw new NullPointerException("The property(author) is null.");
        }
        model.getAuthor().setKey((com.google.appengine.api.datastore.Key) entity.getProperty("author"));
        model.setDate((java.util.Date) entity.getProperty("date"));
        model.setKey(entity.getKey());
        if (model.getReview() == null) {
            throw new NullPointerException("The property(review) is null.");
        }
        model.getReview().setKey((com.google.appengine.api.datastore.Key) entity.getProperty("review"));
        model.setText((java.lang.String) entity.getProperty("text"));
        model.setVersion((java.lang.Long) entity.getProperty("version"));
        return model;
    }

    @Override
    public com.google.appengine.api.datastore.Entity modelToEntity(java.lang.Object model) {
        it.webookia.backend.model.Comment m = (it.webookia.backend.model.Comment) model;
        com.google.appengine.api.datastore.Entity entity = null;
        if (m.getKey() != null) {
            entity = new com.google.appengine.api.datastore.Entity(m.getKey());
        } else {
            entity = new com.google.appengine.api.datastore.Entity(kind);
        }
        if (m.getAuthor() == null) {
            throw new NullPointerException("The property(author) must not be null.");
        }
        entity.setProperty("author", m.getAuthor().getKey());
        entity.setProperty("date", m.getDate());
        if (m.getReview() == null) {
            throw new NullPointerException("The property(review) must not be null.");
        }
        entity.setProperty("review", m.getReview().getKey());
        entity.setProperty("text", m.getText());
        entity.setProperty("version", m.getVersion());
        entity.setProperty("slim3.schemaVersion", 1);
        return entity;
    }

    @Override
    protected com.google.appengine.api.datastore.Key getKey(Object model) {
        it.webookia.backend.model.Comment m = (it.webookia.backend.model.Comment) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        it.webookia.backend.model.Comment m = (it.webookia.backend.model.Comment) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        it.webookia.backend.model.Comment m = (it.webookia.backend.model.Comment) model;
        return m.getVersion() != null ? m.getVersion().longValue() : 0L;
    }

    @Override
    protected void assignKeyToModelRefIfNecessary(com.google.appengine.api.datastore.AsyncDatastoreService ds, java.lang.Object model) {
        it.webookia.backend.model.Comment m = (it.webookia.backend.model.Comment) model;
        if (m.getAuthor() == null) {
            throw new NullPointerException("The property(author) must not be null.");
        }
        m.getAuthor().assignKeyIfNecessary(ds);
        if (m.getReview() == null) {
            throw new NullPointerException("The property(review) must not be null.");
        }
        m.getReview().assignKeyIfNecessary(ds);
    }

    @Override
    protected void incrementVersion(Object model) {
        it.webookia.backend.model.Comment m = (it.webookia.backend.model.Comment) model;
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
        it.webookia.backend.model.Comment m = (it.webookia.backend.model.Comment) model;
        writer.beginObject();
        org.slim3.datastore.json.Default encoder0 = new org.slim3.datastore.json.Default();
        if(m.getAuthor() != null && m.getAuthor().getKey() != null){
            writer.setNextPropertyName("author");
            encoder0.encode(writer, m.getAuthor(), maxDepth, currentDepth);
        }
        if(m.getDate() != null){
            writer.setNextPropertyName("date");
            encoder0.encode(writer, m.getDate());
        }
        if(m.getKey() != null){
            writer.setNextPropertyName("key");
            encoder0.encode(writer, m.getKey());
        }
        if(m.getReview() != null && m.getReview().getKey() != null){
            writer.setNextPropertyName("review");
            encoder0.encode(writer, m.getReview(), maxDepth, currentDepth);
        }
        if(m.getText() != null){
            writer.setNextPropertyName("text");
            encoder0.encode(writer, m.getText());
        }
        if(m.getVersion() != null){
            writer.setNextPropertyName("version");
            encoder0.encode(writer, m.getVersion());
        }
        writer.endObject();
    }

    @Override
    protected it.webookia.backend.model.Comment jsonToModel(org.slim3.datastore.json.JsonRootReader rootReader, int maxDepth, int currentDepth) {
        it.webookia.backend.model.Comment m = new it.webookia.backend.model.Comment();
        org.slim3.datastore.json.JsonReader reader = null;
        org.slim3.datastore.json.Default decoder0 = new org.slim3.datastore.json.Default();
        reader = rootReader.newObjectReader("author");
        decoder0.decode(reader, m.getAuthor(), maxDepth, currentDepth);
        reader = rootReader.newObjectReader("date");
        m.setDate(decoder0.decode(reader, m.getDate()));
        reader = rootReader.newObjectReader("key");
        m.setKey(decoder0.decode(reader, m.getKey()));
        reader = rootReader.newObjectReader("review");
        decoder0.decode(reader, m.getReview(), maxDepth, currentDepth);
        reader = rootReader.newObjectReader("text");
        m.setText(decoder0.decode(reader, m.getText()));
        reader = rootReader.newObjectReader("version");
        m.setVersion(decoder0.decode(reader, m.getVersion()));
        return m;
    }
}