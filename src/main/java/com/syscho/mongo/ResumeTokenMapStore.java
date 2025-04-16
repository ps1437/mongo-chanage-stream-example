package com.syscho.mongo;

import com.hazelcast.map.MapStore;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import org.bson.BsonDocument;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ResumeTokenMapStore implements MapStore<String, BsonDocument> {

    private final MongoCollection<Document> tokenCollection;

    public ResumeTokenMapStore(MongoClient mongoClient) {
        MongoDatabase db = mongoClient.getDatabase("ecommerce");
        tokenCollection = db.getCollection("resumeTokens");
    }

    @Override
    public void store(String key, BsonDocument value) {
        Document doc = new Document("_id", key)
                .append("token", value.toJson());
        tokenCollection.replaceOne(Filters.eq("_id", key), doc, new ReplaceOptions().upsert(true));
    }

    @Override
    public void storeAll(Map<String, BsonDocument> map) {

    }

    @Override
    public void delete(String s) {

    }

    @Override
    public void deleteAll(Collection<String> collection) {

    }

    @Override
    public BsonDocument load(String key) {
        Document doc = tokenCollection.find(Filters.eq("_id", key)).first();
        return doc != null ? BsonDocument.parse(doc.getString("token")) : null;
    }

    @Override
    public Iterable<String> loadAllKeys() {
        List<String> keys = new ArrayList<>();
        tokenCollection.find().forEach(doc -> keys.add(doc.getString("_id")));
        return keys;
    }

    @Override
    public Map<String, BsonDocument> loadAll(Collection<String> keys) {
        return keys.stream().collect(Collectors.toMap(k -> k, this::load));
    }
}
