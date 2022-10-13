package org.irisi.laboeasyseek.configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.irisi.laboeasyseek.models.Publication;
import org.irisi.laboeasyseek.models.User;

import java.util.ArrayList;
import java.util.Collection;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;


public class DBConfig {

    private static final DBConfig dbConfig = new DBConfig();

    private final MongoDatabase db;

    public MongoDatabase getDb() {
        return db;
    }

    private DBConfig() {
        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/");
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);

        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(codecRegistry)
                .build();

        MongoClient mongoClient = MongoClients.create(clientSettings);
        this.db = mongoClient.getDatabase("ttt");
//        this.db = mongoClient.getDatabase("laboeasyseek");
    }

    public static DBConfig getDbConfig() {
        return dbConfig;
    }


//    public static void main(String[] args) {
//        ConnectionString connectionString = new ConnectionString("");
//        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
//        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
//
//        MongoClientSettings clientSettings = MongoClientSettings.builder()
//                .applyConnectionString(connectionString)
//                .codecRegistry(codecRegistry)
//                .build();
//
//        try (MongoClient mongoClient = MongoClients.create(clientSettings)) {
//            MongoDatabase db = mongoClient.getDatabase("app");
//            MongoCollection<User> userCollection = db.getCollection("users", User.class);
//            MongoCollection<Publication> publicationCollection = db.getCollection("publications", Publication.class);
////            System.out.println(users.listIndexes());
//            System.out.println("----------------------------------------------------------------------------------------");
//            System.out.println("users.find(eq(\"email\", \"kaoutar@gmail.com\")).first()");
//            System.out.println(userCollection.find(eq("email", "hahaha2@gmail.com")).first());
//            System.out.println("----------------------------------------------------------------------------------------");
//
//            Document query = new Document().append("email", "hahaha2@gmail.com");
//
//            Bson updates = Updates.combine(
//                    Updates.set("username", "updated username 2")
//            );
//
//            UpdateOptions options = new UpdateOptions().upsert(true);
//
//            try {
//                UpdateResult result = userCollection.updateOne(query, updates, options);
//                System.out.println("Modified document count: " + result.getModifiedCount());
//                System.out.println("Upserted id: " + result.getUpsertedId());
//            } catch (MongoException me) {
//                System.err.println("Unable to update due to an error: " + me);
//            }
//        }
//    }
//
//
//
////    //    MongoClient mongoClient = MongoClients.create();
////    public static void main(String[] args) {
////    //    MongoClient mongoClient = MongoClients.create("");
////    //    MongoDatabase database = mongoClient.getDatabase("app");
////    //    MongoCollection<User> collection = database.getCollection("users", User.class);
////    //    System.out.println(collection);
////    //    System.out.println(collection.find(eq("email", "hahaha2@gmail.com")).first());
////
////
////
////    //        User user = new User();
////    //        user.setUsername("hahaha2");
////    //        user.setEmail("hahaha2@gmail.com");
////    //        user.setPassword("hahaha2");
////    //        collection.insertOne(user);
////
////
////
////        ConnectionString connectionString = new ConnectionString("");
////        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
////        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
////
////        MongoClientSettings clientSettings = MongoClientSettings.builder()
////                .applyConnectionString(connectionString)
////                .codecRegistry(codecRegistry)
////                .build();
////
////        try (MongoClient mongoClient = MongoClients.create(clientSettings)) {
////            MongoDatabase db = mongoClient.getDatabase("app");
////            MongoCollection<User> userCollection = db.getCollection("users", User.class);
////            MongoCollection<Publication> publicationCollection = db.getCollection("publications", Publication.class);
//////            System.out.println(users.listIndexes());
////            System.out.println("----------------------------------------------------------------------------------------");
////            System.out.println("users.find(eq(\"email\", \"kaoutar@gmail.com\")).first()");
////            System.out.println(userCollection.find(eq("email", "hahaha2@gmail.com")).first());
////            System.out.println("----------------------------------------------------------------------------------------");
//////            Publication publication = new Publication();
//////            publication.setTitle("pub2");
//////            publication.setDescription("pub2 desc");
//////            publication.setViewsNumber(0);
//////            publicationCollection.insertOne(publication);
//////            Collection<Publication> publications = new ArrayList<>();
//////            publications.add(publication);
//////            User user = new User();
//////            user.setEmail("user2@gmail.com");
//////            user.setPublications(publications);
//////            userCollection.insertOne(user);
////
//////            User user1 = userCollection.find(eq("email", "hahaha2@gmail.com")).first();
//////
//////            if (user1 != null) {
//////                System.out.println("______________________--------------____________");
//////                user1.setEmail("hhhh@gmail.com");
//////
//////                Document filterByUserId = new Document("_id", user1.getId());
//////                FindOneAndReplaceOptions returnDocAfterReplace = new FindOneAndReplaceOptions().returnDocument(ReturnDocument.AFTER);
//////
//////                User updatedUser = userCollection.findOneAndReplace(filterByUserId, user1, returnDocAfterReplace);
//////                System.out.println("User replaced:\t" + updatedUser);
//////
////////                userCollection.findOneAndUpdate(eq("email", "hahaha2@gmail.com"), (Bson) user1);
//////            }
////
////            Document query = new Document().append("email", "hahaha2@gmail.com");
////
////            Bson updates = Updates.combine(
////                    Updates.set("username", "updated username")
////            );
////
////            UpdateOptions options = new UpdateOptions().upsert(true);
////
////            try {
////                UpdateResult result = userCollection.updateOne(query, updates, options);
////                System.out.println("Modified document count: " + result.getModifiedCount());
////                System.out.println("Upserted id: " + result.getUpsertedId());
////            } catch (MongoException me) {
////                System.err.println("Unable to update due to an error: " + me);
////            }
////        }
////    }
}
