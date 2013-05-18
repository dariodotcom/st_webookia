package it.webookia.backend.meta;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2013-05-18 11:20:06")
/** */
public final class ReviewMeta extends org.slim3.datastore.ModelMeta<it.webookia.backend.model.Review> {

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<it.webookia.backend.model.Review, java.util.Date> date = new org.slim3.datastore.CoreAttributeMeta<it.webookia.backend.model.Review, java.util.Date>(this, "date", "date", java.util.Date.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<it.webookia.backend.model.Review, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<it.webookia.backend.model.Review, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<it.webookia.backend.model.Review, java.lang.Integer> mark = new org.slim3.datastore.CoreAttributeMeta<it.webookia.backend.model.Review, java.lang.Integer>(this, "mark", "mark", int.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<it.webookia.backend.model.Review> text = new org.slim3.datastore.StringAttributeMeta<it.webookia.backend.model.Review>(this, "text", "text");

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<it.webookia.backend.model.Review, java.lang.Long> version = new org.slim3.datastore.CoreAttributeMeta<it.webookia.backend.model.Review, java.lang.Long>(this, "version", "version", java.lang.Long.class);

    private static final ReviewMeta slim3_singleton = new ReviewMeta();

    /**
     * @return the singleton
     */
    public static ReviewMeta get() {
       return slim3_singleton;
    }

    /** */
    public ReviewMeta() {
        super("Review", it.webookia.backend.model.Review.class);
    }

    @Override
    public it.webookia.backend.model.Review entityToModel(com.google.appengine.api.datastore.Entity entity) {
        it.webookia.backend.model.Review model = new it.webookia.backend.model.Review();
        model.setDate((java.util.Date) entity.getProperty("date"));
        model.setKey(entity.getKey());
        model.setMark(longToPrimitiveInt((java.lang.Long) entity.getProperty("mark")));
        model.setText((java.lang.String) entity.getProperty("text"));
        model.setVersion((java.lang.Long) entity.getProperty("version"));
        return model;
    }

    @Override
    public com.google.appengine.api.datastore.Entity modelToEntity(java.lang.Object model) {
        it.webookia.backend.model.Review m = (it.webookia.backend.model.Review) model;
        com.google.appengine.api.datastore.Entity entity = null;
        if (m.getKey() != null) {
            entity = new com.google.appengine.api.datastore.Entity(m.getKey());
        } else {
            entity = new com.google.appengine.api.datastore.Entity(kind);
        }
        entity.setProperty("date", m.getDate());
        entity.setProperty("mark", m.getMark());
        entity.setProperty("text", m.getText());
        entity.setProperty("version", m.getVersion());
        entity.setProperty("slim3.schemaVersion", 1);
        return entity;
    }

    @Override
    protected com.google.appengine.api.datastore.Key getKey(Object model) {
        it.webookia.backend.model.Review m = (it.webookia.backend.model.Review) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        it.webookia.backend.model.Review m = (it.webookia.backend.model.Review) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        it.webookia.backend.model.Review m = (it.webookia.backend.model.Review) model;
        return m.getVersion() != null ? m.getVersion().longValue() : 0L;
    }

    @Override
    protected void assignKeyToModelRefIfNecessary(com.google.appengine.api.datastore.AsyncDatastoreService ds, java.lang.Object model) {
    }

    @Override
    protected void incrementVersion(Object model) {
        it.webookia.backend.model.Review m = (it.webookia.backend.model.Review) model;
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
        it.webookia.backend.model.Review m = (it.webookia.backend.model.Review) model;
        writer.beginObject();
        org.slim3.datastore.json.Default encoder0 = new org.slim3.datastore.json.Default();
        if(m.getDate() != null){
            writer.setNextPropertyName("date");
            encoder0.encode(writer, m.getDate());
        }
        if(m.getKey() != null){
            writer.setNextPropertyName("key");
            encoder0.encode(writer, m.getKey());
        }
        writer.setNextPropertyName("mark");
        encoder0.encode(writer, m.getMark());
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
    protected it.webookia.backend.model.Review jsonToModel(org.slim3.datastore.json.JsonRootReader rootReader, int maxDepth, int currentDepth) {
        it.webookia.backend.model.Review m = new it.webookia.backend.model.Review();
        org.slim3.datastore.json.JsonReader reader = null;
        org.slim3.datastore.json.Default decoder0 = new org.slim3.datastore.json.Default();
        reader = rootReader.newObjectReader("date");
        m.setDate(decoder0.decode(reader, m.getDate()));
        reader = rootReader.newObjectReader("key");
        m.setKey(decoder0.decode(reader, m.getKey()));
        reader = rootReader.newObjectReader("mark");
        m.setMark(decoder0.decode(reader, m.getMark()));
        reader = rootReader.newObjectReader("text");
        m.setText(decoder0.decode(reader, m.getText()));
        reader = rootReader.newObjectReader("version");
        m.setVersion(decoder0.decode(reader, m.getVersion()));
        return m;
    }
}