package org.irisi.laboeasyseek.services;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import jakarta.ejb.Stateful;
import org.bson.conversions.Bson;
import org.irisi.laboeasyseek.configuration.DBConfig;
import org.irisi.laboeasyseek.models.*;
import org.irisi.laboeasyseek.utils.SessionUtils;

import java.io.IOException;
import java.util.*;

import static com.mongodb.client.model.Filters.eq;

@Stateful
public class PublicationImpl implements IPublicationRemote {

    MongoDatabase mongoDatabase = DBConfig.getDbConfig().getDb();


    MongoCollection<User> userCollection = mongoDatabase.getCollection("users", User.class);
    MongoCollection<Publication> publicationCollection = mongoDatabase.getCollection("publications", Publication.class);
    MongoCollection<Tag> tagCollection = mongoDatabase.getCollection("tags", Tag.class);


    private List<Tag> tags = new ArrayList<Tag>();

    public List<Tag> getTags() {
        if (tags.size() == 0) {
            tags.add(new Tag());
        }
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public List<Publication> getPublications() {
        return null;
    }


    private String category = "";

    public String getCategory() {
        return category;
    }

    public void handleSetCategory(String category) {
        this.category = category;
    }



    @Override
    public void addPublication(Publication publication, Event event, Report report, Article article, Image image, Document document) throws IOException {
        List<String> categories = Arrays.asList("article", "report", "event", "other");

        System.out.println("here2");
        if (category != null && categories.contains(category)) {
            publication.setCategory(category);
        }

        if (Objects.equals(publication.getCategory(), "event") || Objects.equals(publication.getCategory(), "") || Objects.equals(publication.getCategory(), null)) {
            if (event != null
                    && (!Objects.equals(event.getName(), "")
                    || !Objects.equals(event.getDate(), "")
                    || !Objects.equals(event.getLocal(), ""))) {
//                eventCollection.insertOne(event);
                publication.setEvent(event);
            }
        }

        if (Objects.equals(publication.getCategory(), "report") || Objects.equals(publication.getCategory(), "") || Objects.equals(publication.getCategory(), null)) {
            if (report != null
                    && (!Objects.equals(report.getTitle(), "")
                    || !Objects.equals(report.getVersion(), ""))) {
//                reportCollection.insertOne(report);
                publication.setReport(report);
            }
        }

        if (Objects.equals(publication.getCategory(), "article") || Objects.equals(publication.getCategory(), "") || Objects.equals(publication.getCategory(), null)) {
            if (article != null
                    && (!Objects.equals(article.getTitle(), "")
                    || !Objects.equals(article.getContent(), ""))) {
//                articleCollection.insertOne(article);
                publication.setArticle(article);
            }
        }


        System.out.println("tags---------------------------------------------------");
        System.out.println(tags.toString());

        List<Tag> tagList = new ArrayList<>();
        for (Tag tag : tags
        ) {
            if (tag != null
                    && !Objects.equals(tag.getName(), "")) {
                Tag existingTag = null;
                existingTag = tagCollection.find(eq("name", tag.getName())).first();
                if (existingTag != null) {
                    tagList.add(existingTag);
                } else {
                    Tag newTag = new Tag();
                    newTag.setName(tag.getName());
                    tagList.add(newTag);
                }
            }
        }

        System.out.println("tags---------------------------------------------------");
        System.out.println(tagList);

        if (tagList.size() > 0) {
            publication.setTags(tagList);
        }

        if ((image != null && image.getPart() != null) || (document != null && document.getPart() != null)) {
            System.out.println("img------------------------------------------------dd---" + image.getPart());
            publication = processUpload(publication, image, document);
        }

        publication.setCreatedAt(new Date());
        publicationCollection.insertOne(publication);

        User user = null;
        user = userCollection.find(eq("email", SessionUtils.getEmail())).first();
        if (user != null) {

            org.bson.Document userQuery = new org.bson.Document().append("email", SessionUtils.getEmail());
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
        } else {
            Collection<Publication> publications = new ArrayList<>();
            publications.add(publication);
            user = new User();
            user.setEmail(SessionUtils.getEmail());
            user.setPublications(publications);
            userCollection.insertOne(user);
        }

        setTags(new ArrayList<Tag>());
        handleSetCategory("");
    }


    public Publication processUpload(Publication publication, Image imagePart, org.irisi.laboeasyseek.models.Document documentPart) throws IOException {

        UploadHelper uploadHelper = new UploadHelper();
        Media media = new Media();

        if (imagePart != null) {
            String title = uploadHelper.processUpload(imagePart.getPart(), publication.getTitle());
            media.setType(title);

            String[] types = new String[]{"gif", "jpg", "png", "jpeg"};
            List<String> ttt = Arrays.asList(types);
            System.out.println("file type : " + media.getType());
            if (ttt.contains(media.getType())) {
                Image image = new Image();
                image.setTitle(title);
                image.setType(media.getType());
//                imageCollection.insertOne(image);
                media.setImage(image);
            }
        }

        if (documentPart != null && documentPart.getPart() != null && !Objects.equals(documentPart.getPart().getSubmittedFileName(), "")) {
            String title = uploadHelper.processUpload(documentPart.getPart(), publication.getTitle());
            media.setType(title);


            org.irisi.laboeasyseek.models.Document document = new org.irisi.laboeasyseek.models.Document();
            document.setTitle(title);
            if (document.getTitle() != null) {
                document.setType(media.getType());
                List<String> listWords = new ArrayList<>();
                if (!Objects.equals(media.getType(), "pdf")) {
                    listWords = UploadHelper.generateWordCloud(title);
                } else {
                    String file = UploadHelper.pdfToTextFile(title);
                    listWords = UploadHelper.generateWordCloud(file);
                }
                document.setFilePath(title.split("\\.")[0] + ".png");
                document.setKeywords(listWords);
//                documentCollection.insertOne(document);
                media.setDocument(document);
            }


        }

//        mediaCollection.insertOne(media);
        publication.setMedia(media);
        return publication;
    }
}