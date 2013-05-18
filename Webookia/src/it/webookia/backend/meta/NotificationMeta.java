package it.webookia.backend.meta;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2013-05-18 11:57:24")
/** */
public final class NotificationMeta extends org.slim3.datastore.ModelMeta<it.webookia.backend.model.Notification> {

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<it.webookia.backend.model.Notification, java.util.Date> date = new org.slim3.datastore.CoreAttributeMeta<it.webookia.backend.model.Notification, java.util.Date>(this, "date", "date", java.util.Date.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<it.webookia.backend.model.Notification, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<it.webookia.backend.model.Notification, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.ModelRefAttributeMeta<it.webookia.backend.model.Notification, org.slim3.datastore.ModelRef<it.webookia.backend.model.UserEntity>, it.webookia.backend.model.UserEntity> receiver = new org.slim3.datastore.ModelRefAttributeMeta<it.webookia.backend.model.Notification, org.slim3.datastore.ModelRef<it.webookia.backend.model.UserEntity>, it.webookia.backend.model.UserEntity>(this, "receiver", "receiver", org.slim3.datastore.ModelRef.class, it.webookia.backend.model.UserEntity.class);

    /** */
    public final org.slim3.datastore.ModelRefAttributeMeta<it.webookia.backend.model.Notification, org.slim3.datastore.ModelRef<it.webookia.backend.model.UserEntity>, it.webookia.backend.model.UserEntity> sender = new org.slim3.datastore.ModelRefAttributeMeta<it.webookia.backend.model.Notification, org.slim3.datastore.ModelRef<it.webookia.backend.model.UserEntity>, it.webookia.backend.model.UserEntity>(this, "sender", "sender", org.slim3.datastore.ModelRef.class, it.webookia.backend.model.UserEntity.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<it.webookia.backend.model.Notification> targetId = new org.slim3.datastore.StringAttributeMeta<it.webookia.backend.model.Notification>(this, "targetId", "targetId");

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<it.webookia.backend.model.Notification, it.webookia.backend.enums.NotificationType> type = new org.slim3.datastore.CoreAttributeMeta<it.webookia.backend.model.Notification, it.webookia.backend.enums.NotificationType>(this, "type", "type", it.webookia.backend.enums.NotificationType.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<it.webookia.backend.model.Notification, java.lang.Long> version = new org.slim3.datastore.CoreAttributeMeta<it.webookia.backend.model.Notification, java.lang.Long>(this, "version", "version", java.lang.Long.class);

    private static final NotificationMeta slim3_singleton = new NotificationMeta();

    /**
     * @return the singleton
     */
    public static NotificationMeta get() {
       return slim3_singleton;
    }

    /** */
    public NotificationMeta() {
        super("Notification", it.webookia.backend.model.Notification.class);
    }

    @Override
    public it.webookia.backend.model.Notification entityToModel(com.google.appengine.api.datastore.Entity entity) {
        it.webookia.backend.model.Notification model = new it.webookia.backend.model.Notification();
        model.setDate((java.util.Date) entity.getProperty("date"));
        model.setKey(entity.getKey());
        if (model.getReceiver() == null) {
            throw new NullPointerException("The property(receiver) is null.");
        }
        model.getReceiver().setKey((com.google.appengine.api.datastore.Key) entity.getProperty("receiver"));
        if (model.getSender() == null) {
            throw new NullPointerException("The property(sender) is null.");
        }
        model.getSender().setKey((com.google.appengine.api.datastore.Key) entity.getProperty("sender"));
        model.setTargetId((java.lang.String) entity.getProperty("targetId"));
        model.setType(stringToEnum(it.webookia.backend.enums.NotificationType.class, (java.lang.String) entity.getProperty("type")));
        model.setVersion((java.lang.Long) entity.getProperty("version"));
        return model;
    }

    @Override
    public com.google.appengine.api.datastore.Entity modelToEntity(java.lang.Object model) {
        it.webookia.backend.model.Notification m = (it.webookia.backend.model.Notification) model;
        com.google.appengine.api.datastore.Entity entity = null;
        if (m.getKey() != null) {
            entity = new com.google.appengine.api.datastore.Entity(m.getKey());
        } else {
            entity = new com.google.appengine.api.datastore.Entity(kind);
        }
        entity.setProperty("date", m.getDate());
        if (m.getReceiver() == null) {
            throw new NullPointerException("The property(receiver) must not be null.");
        }
        entity.setProperty("receiver", m.getReceiver().getKey());
        if (m.getSender() == null) {
            throw new NullPointerException("The property(sender) must not be null.");
        }
        entity.setProperty("sender", m.getSender().getKey());
        entity.setProperty("targetId", m.getTargetId());
        entity.setProperty("type", enumToString(m.getType()));
        entity.setProperty("version", m.getVersion());
        entity.setProperty("slim3.schemaVersion", 1);
        return entity;
    }

    @Override
    protected com.google.appengine.api.datastore.Key getKey(Object model) {
        it.webookia.backend.model.Notification m = (it.webookia.backend.model.Notification) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        it.webookia.backend.model.Notification m = (it.webookia.backend.model.Notification) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        it.webookia.backend.model.Notification m = (it.webookia.backend.model.Notification) model;
        return m.getVersion() != null ? m.getVersion().longValue() : 0L;
    }

    @Override
    protected void assignKeyToModelRefIfNecessary(com.google.appengine.api.datastore.AsyncDatastoreService ds, java.lang.Object model) {
        it.webookia.backend.model.Notification m = (it.webookia.backend.model.Notification) model;
        if (m.getReceiver() == null) {
            throw new NullPointerException("The property(receiver) must not be null.");
        }
        m.getReceiver().assignKeyIfNecessary(ds);
        if (m.getSender() == null) {
            throw new NullPointerException("The property(sender) must not be null.");
        }
        m.getSender().assignKeyIfNecessary(ds);
    }

    @Override
    protected void incrementVersion(Object model) {
        it.webookia.backend.model.Notification m = (it.webookia.backend.model.Notification) model;
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
        it.webookia.backend.model.Notification m = (it.webookia.backend.model.Notification) model;
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
        if(m.getReceiver() != null && m.getReceiver().getKey() != null){
            writer.setNextPropertyName("receiver");
            encoder0.encode(writer, m.getReceiver(), maxDepth, currentDepth);
        }
        if(m.getSender() != null && m.getSender().getKey() != null){
            writer.setNextPropertyName("sender");
            encoder0.encode(writer, m.getSender(), maxDepth, currentDepth);
        }
        if(m.getTargetId() != null){
            writer.setNextPropertyName("targetId");
            encoder0.encode(writer, m.getTargetId());
        }
        if(m.getType() != null){
            writer.setNextPropertyName("type");
            encoder0.encode(writer, m.getType());
        }
        if(m.getVersion() != null){
            writer.setNextPropertyName("version");
            encoder0.encode(writer, m.getVersion());
        }
        writer.endObject();
    }

    @Override
    protected it.webookia.backend.model.Notification jsonToModel(org.slim3.datastore.json.JsonRootReader rootReader, int maxDepth, int currentDepth) {
        it.webookia.backend.model.Notification m = new it.webookia.backend.model.Notification();
        org.slim3.datastore.json.JsonReader reader = null;
        org.slim3.datastore.json.Default decoder0 = new org.slim3.datastore.json.Default();
        reader = rootReader.newObjectReader("date");
        m.setDate(decoder0.decode(reader, m.getDate()));
        reader = rootReader.newObjectReader("key");
        m.setKey(decoder0.decode(reader, m.getKey()));
        reader = rootReader.newObjectReader("receiver");
        decoder0.decode(reader, m.getReceiver(), maxDepth, currentDepth);
        reader = rootReader.newObjectReader("sender");
        decoder0.decode(reader, m.getSender(), maxDepth, currentDepth);
        reader = rootReader.newObjectReader("targetId");
        m.setTargetId(decoder0.decode(reader, m.getTargetId()));
        reader = rootReader.newObjectReader("type");
        m.setType(decoder0.decode(reader, m.getType(), it.webookia.backend.enums.NotificationType.class));
        reader = rootReader.newObjectReader("version");
        m.setVersion(decoder0.decode(reader, m.getVersion()));
        return m;
    }
}