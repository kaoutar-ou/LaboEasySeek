package org.irisi.laboeasyseek.controllers;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.irisi.laboeasyseek.configuration.DBConfig;
import org.irisi.laboeasyseek.models.Event;
import org.irisi.laboeasyseek.models.Publication;
import org.irisi.laboeasyseek.models.Tag;
import org.irisi.laboeasyseek.models.User;
import org.irisi.laboeasyseek.utils.SessionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Named("publicationController")
@SessionScoped
public class PublicationController implements Serializable {

    MongoDatabase mongoDatabase = DBConfig.getDbConfig().getDb();


    public void addPublication(Publication publication, Event event, Tag tag) {


//        ConnectionString connectionString = new ConnectionString("");
////        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/");
//
//        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
//        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
//
//
//        MongoClientSettings settings = MongoClientSettings.builder()
//                .applyConnectionString(connectionString)
//                .codecRegistry(codecRegistry)
//                .build();
//        MongoDatabase mongoDatabase;
//        MongoClient mongoClient = MongoClients.create(settings);
//            mongoDatabase = mongoClient.getDatabase("app");
//            System.out.println("-------------------------------done-" + mongoClient.listDatabaseNames());



//        ConnectionString connectionString = new ConnectionString("");
//        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
//        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
//
//        MongoClientSettings clientSettings = MongoClientSettings.builder()
//                .applyConnectionString(connectionString)
//                .codecRegistry(codecRegistry)
//                .build();
//
//        MongoDatabase mongoDatabase;
//        try (MongoClient mongoClient = MongoClients.create(clientSettings)) {
//            mongoDatabase = mongoClient.getDatabase("app");
//        }


        MongoCollection<User> userCollection = mongoDatabase.getCollection("users", User.class);
        MongoCollection<Publication> publicationCollection = mongoDatabase.getCollection("publications", Publication.class);

//        Collection<Publication> publications = new ArrayList<>();
//        publications.add(publication);
//        User user = new User();
//        user.setEmail(SessionUtils.getEmail());
//        user.setPublications(publications);
//        userCollection.insertOne(user);

        if (event != null) {
            publication.setEvent(event);
        }
        if (tag != null) {
            publication.setTag(tag);
        }
        publicationCollection.insertOne(publication);
        User user = null;
        user = userCollection.find(eq("email", SessionUtils.getEmail())).first();
        if (user != null) {
//            Document document = new Document("")
//            userCollection.findOneAndUpdate()
            Document userQuery = new Document().append("email", SessionUtils.getEmail());
            Bson updates = Updates.combine(
                    Updates.addToSet("publications", publication)
            );
            UpdateOptions options = new UpdateOptions().upsert(true);

            try {
                UpdateResult result = userCollection.updateOne(userQuery, updates, options);
                System.out.println("Modified User count: " + result.getModifiedCount());
                System.out.println("Upserted id: " + result.getUpsertedId());
            } catch (MongoException me) {
                System.err.println("Unable to update due to an error: " + me);
            }
        }
        else {
            Collection<Publication> publications = new ArrayList<>();
            publications.add(publication);
            user = new User();
            user.setEmail(SessionUtils.getEmail());
            user.setPublications(publications);
            userCollection.insertOne(user);
        }
    }





////        ConnectionString connectionString = new ConnectionString("");
//        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/");
////        ConnectionString connectionString = new ConnectionString("");
//        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
//        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
//
//        MongoClientSettings clientSettings = MongoClientSettings.builder()
//                .applyConnectionString(connectionString)
//                .codecRegistry(codecRegistry)
//                .build();
//
////        try () {
//            MongoClient mongoClient = MongoClients.create(clientSettings);
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
//                    Updates.set("username", "updated username 3")
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
////    }


}
