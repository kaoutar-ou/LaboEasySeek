package org.irisi.laboeasyseek.controllers;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.irisi.laboeasyseek.configuration.DBConfig;
import org.irisi.laboeasyseek.models.User;

public class TestController {

    MongoDatabase mongoDatabase = DBConfig.getDbConfig().getDb();

    MongoCollection<User> userCollection = DBConfig.getUserCollection();

    public static void main(String[] args) {
        System.out.println("Hello World!");

        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        user.setEmail("test");
        DBConfig.getUserCollection().insertOne(user);

    }
}
