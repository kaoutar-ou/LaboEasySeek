package org.irisi.laboeasyseek.controllers;

import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.MongoClient;
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
import org.irisi.laboeasyseek.entities.Post;
import org.irisi.laboeasyseek.entities.UploadManagedBean;
import org.irisi.laboeasyseek.models.*;
import org.irisi.laboeasyseek.models.Tag;
import org.irisi.laboeasyseek.services.UploadHelper;
import org.irisi.laboeasyseek.utils.SessionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Named("publicationController")
@SessionScoped
public class PublicationController implements Serializable {

    MongoDatabase mongoDatabase = DBConfig.getDbConfig().getDb();

    MongoCollection<User> userCollection = mongoDatabase.getCollection("users", User.class);
    MongoCollection<Publication> publicationCollection = mongoDatabase.getCollection("publications", Publication.class);
    MongoCollection<Event> eventCollection = mongoDatabase.getCollection("events", Event.class);
    MongoCollection<Tag> tagCollection = mongoDatabase.getCollection("tags", Tag.class);
    MongoCollection<Media> mediaCollection = mongoDatabase.getCollection("medias", Media.class);
    MongoCollection<Image> imageCollection = mongoDatabase.getCollection("images", Image.class);




    private List<Tag> tags = new ArrayList<Tag>();

    public List<Tag> getTags() {
        if(tags.size()==0) {
            tags.add(new Tag());
        }
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void addTag(Tag tag) {
        if (!Objects.equals(tag.getName(), "")) {
            Tag newTag = new Tag();
            newTag.setName(tag.getName());
            tags.add(newTag);
        }
    }





    public List<Publication> getPublications() {
        List<Publication> publicationList = new ArrayList<>();
        FindIterable<Publication> publicationFindIterable = publicationCollection.find();
        for (Publication pub: publicationFindIterable) {
            System.out.println(pub.toString());
            publicationList.add(pub);
        }
        return publicationList;
    }

    public void addPublication(Publication publication, Event event, UploadManagedBean uploadManagedBean) {

        if (event != null
                && (!Objects.equals(event.getName(), "")
                || !Objects.equals(event.getDate(), "")
                || !Objects.equals(event.getLocal(), ""))) {
            eventCollection.insertOne(event);
            publication.setEvent(event);
        }

        System.out.println("tags---------------------------------------------------");
        System.out.println(tags.toString());

        List<Tag> tagList = new ArrayList<>();
        for (Tag tag: tags
             ) {


            if (tag != null
                    && !Objects.equals(tag.getName(), "")) {
                Tag existingTag = null;
                existingTag = tagCollection.find(eq("name", tag.getName())).first();
                if (existingTag != null) {
                    tagList.add(existingTag);
                }
                else {
                    Tag newTag = new Tag();
                    newTag.setName(tag.getName());
                    tagCollection.insertOne(newTag);
                    tagList.add(newTag);
                }
            }
        }

        System.out.println("tags---------------------------------------------------");
        System.out.println(tagList);

        if (tagList.size() > 0) {
            publication.setTags(tagList);
        }

        if (uploadManagedBean != null && uploadManagedBean.getPart() != null) {
            publication = processUpload(publication, uploadManagedBean);
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

        setTags(new ArrayList<Tag>());

    }


    public Publication processUpload(Publication publication, UploadManagedBean uploadManagedBean) {
        System.out.println("file to upload : " + uploadManagedBean.getPart());
        UploadHelper uploadHelper = new UploadHelper();
        Media media = new Media();

//        Image image = new Image();
//        image.setTitle(uploadHelper.processUpload(uploadManagedBean.getPart(),publication.getTitle()));
//        imageCollection.insertOne(image);
//        media.setImage(image);

        media.setTitle(uploadHelper.processUpload(uploadManagedBean.getPart(),publication.getTitle()));
        mediaCollection.insertOne(media);
        publication.setMedia(media);
        return publication;
    }

    public String homePage() {
        setTags(new ArrayList<Tag>());
        return "home";
    }
}
