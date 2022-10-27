package org.irisi.laboeasyseek.services;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.InsertOneOptions;
import com.mongodb.client.model.Sorts;
import jakarta.ejb.Stateful;
import org.irisi.laboeasyseek.configuration.DBConfig;
import org.irisi.laboeasyseek.models.Post;
import org.irisi.laboeasyseek.utils.SessionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import static com.mongodb.client.model.Filters.or;
import static com.mongodb.client.model.Filters.regex;

@Stateful
public class PostServiceImpl implements IPostService {

    MongoCollection<Post> postCollection = DBConfig.getPostCollection();

    @Override
    public Boolean addPost(Post post, String category) {
        if (Objects.equals(category, "all")) {
            post.setCategory(null);
        } else {
            post.setCategory(category);
        }
        String publisher = SessionUtils.getUsername();
        System.out.println("publisher: " + publisher);
        if (publisher == null) {
            publisher = "Test user";
        }
        post.setPublisher(publisher);
        try {
            postCollection.insertOne(post);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public String deletePost() {
        return null;
    }

    @Override
    public String updatePost() {
        return null;
    }

    @Override
    public String getPost() {
        return null;
    }


    private int pageIndex = 0;
    private int pageSize = 50;
    private int pagesNumber = 1;

    @Override
    public List<Post> getAllPosts(String search, String searchCategory, String searchTag) {
        List<Post> publicationList = new ArrayList<>();
        FindIterable<Post> publicationFindIterable = null;


        System.out.println("-------------------------------------" + search);

        if (!Objects.equals(search, "")) {
            publicationFindIterable = postCollection.find(
                    or(
                            regex("title", ".*" + Pattern.quote(search) + ".*"),
                            regex("description", ".*" + Pattern.quote(search) + ".*"),
                            regex("publisher", ".*" + Pattern.quote(search) + ".*")));
//            setSearchTag("");
//            setSearchCategory("");


            pagesNumber = (int) postCollection.countDocuments(or(
                    regex("title", ".*" + Pattern.quote(search) + ".*"),
                    regex("description", ".*" + Pattern.quote(search) + ".*"),
                    regex("publisher", ".*" + Pattern.quote(search) + ".*")));
        } else if (!Objects.equals(searchCategory, "")) {
            System.out.println("search by category" + searchCategory);
            publicationFindIterable = postCollection.find(
                    regex("category", ".*" + Pattern.quote(searchCategory) + ".*"));
//            setSearchTag("");


            pagesNumber = (int) postCollection.countDocuments(regex("category", ".*" + Pattern.quote(searchCategory) + ".*"));

        } else if (!Objects.equals(searchTag, "")) {

            System.out.println("search by tag" + searchTag);

            publicationFindIterable = postCollection.find(
                    regex("tags.name", ".*" + Pattern.quote(searchTag) + ".*"));

            pagesNumber = (int) postCollection.countDocuments(regex("tags.name", ".*" + Pattern.quote(searchTag) + ".*"));
        } else {
            publicationFindIterable = postCollection.find();
            pagesNumber = (int) postCollection.countDocuments();
        }

        System.out.println("index" + pageIndex);

        if (pagesNumber != 0) {
            pagesNumber = (pagesNumber / pageSize) + 1;
        }

        publicationFindIterable = publicationFindIterable.sort(Sorts.descending("created_at"))
                .skip(pageIndex * pageSize)
                .limit(pageSize);
        System.out.println("pagesNumber" + pagesNumber);

        for (Post pub : publicationFindIterable) {
            System.out.println(pub.toString());
            publicationList.add(pub);
        }

        return publicationList;
    }
}

