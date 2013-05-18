package it.webookia.backend.meta;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2013-05-18 12:52:48")
/** */
public final class BookDetailsMeta extends org.slim3.datastore.ModelMeta<it.webookia.backend.model.BookDetails> {

    /** */
    public final org.slim3.datastore.StringCollectionAttributeMeta<it.webookia.backend.model.BookDetails, java.util.List<java.lang.String>> authors = new org.slim3.datastore.StringCollectionAttributeMeta<it.webookia.backend.model.BookDetails, java.util.List<java.lang.String>>(this, "authors", "authors", java.util.List.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<it.webookia.backend.model.BookDetails, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<it.webookia.backend.model.BookDetails, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<it.webookia.backend.model.BookDetails> publisher = new org.slim3.datastore.StringAttributeMeta<it.webookia.backend.model.BookDetails>(this, "publisher", "publisher");

    /** */
    public final org.slim3.datastore.StringAttributeMeta<it.webookia.backend.model.BookDetails> thumbnail = new org.slim3.datastore.StringAttributeMeta<it.webookia.backend.model.BookDetails>(this, "thumbnail", "thumbnail");

    /** */
    public final org.slim3.datastore.StringAttributeMeta<it.webookia.backend.model.BookDetails> title = new org.slim3.datastore.StringAttributeMeta<it.webookia.backend.model.BookDetails>(this, "title", "title");

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<it.webookia.backend.model.BookDetails, java.lang.Long> version = new org.slim3.datastore.CoreAttributeMeta<it.webookia.backend.model.BookDetails, java.lang.Long>(this, "version", "version", java.lang.Long.class);

    private static final BookDetailsMeta slim3_singleton = new BookDetailsMeta();

    /**
     * @return the singleton
     */
    public static BookDetailsMeta get() {
       return slim3_singleton;
    }

    /** */
    public BookDetailsMeta() {
        super("BookDetails", it.webookia.backend.model.BookDetails.class);
    }

    @Override
    public it.webookia.backend.model.BookDetails entityToModel(com.google.appengine.api.datastore.Entity entity) {
        it.webookia.backend.model.BookDetails model = new it.webookia.backend.model.BookDetails();
        model.setAuthors(toList(java.lang.String.class, entity.getProperty("authors")));
        model.setKey(entity.getKey());
        model.setPublisher((java.lang.String) entity.getProperty("publisher"));
        model.setThumbnail((java.lang.String) entity.getProperty("thumbnail"));
        model.setTitle((java.lang.String) entity.getProperty("title"));
        model.setVersion((java.lang.Long) entity.getProperty("version"));
        return model;
    }

    @Override
    public com.google.appengine.api.datastore.Entity modelToEntity(java.lang.Object model) {
        it.webookia.backend.model.BookDetails m = (it.webookia.backend.model.BookDetails) model;
        com.google.appengine.api.datastore.Entity entity = null;
        if (m.getKey() != null) {
            entity = new com.google.appengine.api.datastore.Entity(m.getKey());
        } else {
            entity = new com.google.appengine.api.datastore.Entity(kind);
        }
        entity.setProperty("authors", m.getAuthors());
        entity.setProperty("publisher", m.getPublisher());
        entity.setProperty("thumbnail", m.getThumbnail());
        entity.setProperty("title", m.getTitle());
        entity.setProperty("version", m.getVersion());
        entity.setProperty("slim3.schemaVersion", 1);
        return entity;
    }

    @Override
    protected com.google.appengine.api.datastore.Key getKey(Object model) {
        it.webookia.backend.model.BookDetails m = (it.webookia.backend.model.BookDetails) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        it.webookia.backend.model.BookDetails m = (it.webookia.backend.model.BookDetails) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        it.webookia.backend.model.BookDetails m = (it.webookia.backend.model.BookDetails) model;
        return m.getVersion() != null ? m.getVersion().longValue() : 0L;
    }

    @Override
    protected void assignKeyToModelRefIfNecessary(com.google.appengine.api.datastore.AsyncDatastoreService ds, java.lang.Object model) {
    }

    @Override
    protected void incrementVersion(Object model) {
        it.webookia.backend.model.BookDetails m = (it.webookia.backend.model.BookDetails) model;
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
        it.webookia.backend.model.BookDetails m = (it.webookia.backend.model.BookDetails) model;
        writer.beginObject();
        org.slim3.datastore.json.Default encoder0 = new org.slim3.datastore.json.Default();
        if(m.getAuthors() != null){
            writer.setNextPropertyName("authors");
            writer.beginArray();
            for(java.lang.String v : m.getAuthors()){
                encoder0.encode(writer, v);
            }
            writer.endArray();
        }
        if(m.getKey() != null){
            writer.setNextPropertyName("key");
            encoder0.encode(writer, m.getKey());
        }
        if(m.getPublisher() != null){
            writer.setNextPropertyName("publisher");
            encoder0.encode(writer, m.getPublisher());
        }
        if(m.getThumbnail() != null){
            writer.setNextPropertyName("thumbnail");
            encoder0.encode(writer, m.getThumbnail());
        }
        if(m.getTitle() != null){
            writer.setNextPropertyName("title");
            encoder0.encode(writer, m.getTitle());
        }
        if(m.getVersion() != null){
            writer.setNextPropertyName("version");
            encoder0.encode(writer, m.getVersion());
        }
        writer.endObject();
    }

    @Override
    protected it.webookia.backend.model.BookDetails jsonToModel(org.slim3.datastore.json.JsonRootReader rootReader, int maxDepth, int currentDepth) {
        it.webookia.backend.model.BookDetails m = new it.webookia.backend.model.BookDetails();
        org.slim3.datastore.json.JsonReader reader = null;
        org.slim3.datastore.json.Default decoder0 = new org.slim3.datastore.json.Default();
        reader = rootReader.newObjectReader("authors");
        {
            java.util.ArrayList<java.lang.String> elements = new java.util.ArrayList<java.lang.String>();
            org.slim3.datastore.json.JsonArrayReader r = rootReader.newArrayReader("authors");
            if(r != null){
                reader = r;
                int n = r.length();
                for(int i = 0; i < n; i++){
                    r.setIndex(i);
                    java.lang.String v = decoder0.decode(reader, (java.lang.String)null)                    ;
                    if(v != null){
                        elements.add(v);
                    }
                }
                m.setAuthors(elements);
            }
        }
        reader = rootReader.newObjectReader("key");
        m.setKey(decoder0.decode(reader, m.getKey()));
        reader = rootReader.newObjectReader("publisher");
        m.setPublisher(decoder0.decode(reader, m.getPublisher()));
        reader = rootReader.newObjectReader("thumbnail");
        m.setThumbnail(decoder0.decode(reader, m.getThumbnail()));
        reader = rootReader.newObjectReader("title");
        m.setTitle(decoder0.decode(reader, m.getTitle()));
        reader = rootReader.newObjectReader("version");
        m.setVersion(decoder0.decode(reader, m.getVersion()));
        return m;
    }
}