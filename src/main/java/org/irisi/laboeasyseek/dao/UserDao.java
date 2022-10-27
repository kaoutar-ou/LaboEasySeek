package org.irisi.laboeasyseek.dao;

import com.mongodb.client.MongoCollection;
import org.irisi.laboeasyseek.configuration.DBConfig;
import org.irisi.laboeasyseek.models.User;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.or;

public class UserDao {
    MongoCollection<User> userCollection = DBConfig.getUserCollection();

    public User findUserByEmailOrUsername(String input) {
        User user = null;

        user = userCollection.find(
                or(
                        eq("email", input),
                        eq("username", input)
                )
        ).first();

        return user;
    }
}
