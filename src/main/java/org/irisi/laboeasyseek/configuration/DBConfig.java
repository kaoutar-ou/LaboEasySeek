package org.irisi.laboeasyseek.configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.irisi.laboeasyseek.models.Post;
import org.irisi.laboeasyseek.models.User;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;


public class DBConfig {

    private static final DBConfig dbConfig = new DBConfig();

    private final MongoDatabase db;

    public MongoDatabase getDb() {
        return db;
    }

    public static MongoCollection<User> getUserCollection() {
        return userCollection;
    }
    public static MongoCollection<Post> getPostCollection() {
        return postCollection;
    }

    static MongoCollection<User> userCollection = DBConfig.getDbConfig().getDb().getCollection("users", User.class);
    static MongoCollection<Post> postCollection = DBConfig.getDbConfig().getDb().getCollection("posts", Post.class);

    private DBConfig() {
        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/");
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);

        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(codecRegistry)
                .build();

        MongoClient mongoClient = MongoClients.create(clientSettings);
        this.db = mongoClient.getDatabase("db-labo-1");
    }

    public static DBConfig getDbConfig() {
        return dbConfig;
    }


}
