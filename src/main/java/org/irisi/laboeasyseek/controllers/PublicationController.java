package org.irisi.laboeasyseek.controllers;

import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.model.*;
import com.mongodb.client.result.UpdateResult;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Named;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.irisi.laboeasyseek.configuration.DBConfig;
import org.irisi.laboeasyseek.models.*;
import org.irisi.laboeasyseek.models.Tag;
import org.irisi.laboeasyseek.services.UploadHelper;
import org.irisi.laboeasyseek.utils.SessionUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.regex.Pattern;

import static com.mongodb.client.model.Filters.*;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Named("publicationController")
@SessionScoped
public class PublicationController implements Serializable {

    MongoDatabase mongoDatabase = DBConfig.getDbConfig().getDb();
    private String search = "";

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public void searchByString(String searchString) {
        setSearch(searchString);
        setSearchTag("");
        setSearchCategory("");
        setPageIndex(0);
    }


    private String searchTag = "";

    public String getSearchTag() {
        return searchTag;
    }

    public void setSearchTag(String searchTag) {
        this.searchTag = searchTag;
    }

    public void searchByTag(String searchTag) {
        System.out.println("search by tag---" + searchTag);
        setSearchTag(searchTag);
        setSearch("");
        setSearchCategory("");
        setPageIndex(0);
    }

    private String searchCategory = "";

    public String getSearchCategory() {
        return searchCategory;
    }

    public void setSearchCategory(String searchCategory) {
        this.searchCategory = searchCategory;
    }

    public void searchByCategory(String searchCategory) {
        System.out.println("search by tag---" + searchCategory);
        setSearchCategory(searchCategory);
        setSearch("");
        setSearchTag("");
        setPageIndex(0);
    }

    MongoCollection<User> userCollection = mongoDatabase.getCollection("users", User.class);
    MongoCollection<Publication> publicationCollection = mongoDatabase.getCollection("publications", Publication.class);
    MongoCollection<Tag> tagCollection = mongoDatabase.getCollection("tags", Tag.class);

    private int pageIndex;
    private int pageSize = 6;
    private int pagesNumber;

    public int getPagesNumber() {
        return pagesNumber;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

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

    public void addTag(Tag tag) {
        if (!Objects.equals(tag.getName(), "")) {
            Tag newTag = new Tag();
            newTag.setName(tag.getName());
            tags.add(newTag);
        }
    }

        public List<String> getAllTags() {
        List<String> tagNames = new ArrayList<>();

        FindIterable<Tag> tagFindIterable = null;
        FindIterable<Publication> publicationFindIterable = null;

        Bson projection = Projections.fields(Projections.include("tags"), Projections.excludeId());

        publicationFindIterable = publicationCollection.find().projection(projection);

        for (Publication pub : publicationFindIterable) {
            for (Tag tag : pub.getTags()) {
//                if (!tagList.contains(tag)) {
//                    tagList.add(tag);
//                }
                if (!tagNames.contains(tag.getName())) {
                    tagNames.add(tag.getName());
                }
            }
        }

        return tagNames;
    }

    private String category = "";

    public String getCategory() {
        return category;
    }

    public void handleSetCategory(String category) {
        this.category = category;
    }

    public List<Publication> getPublications() {
        List<Publication> publicationList = new ArrayList<>();
        FindIterable<Publication> publicationFindIterable = null;

        System.out.println("-------------------------------------" + search);

        if (!Objects.equals(search, "")) {
            publicationFindIterable = publicationCollection.find(
                    or(
                            regex("title", ".*" + Pattern.quote(search) + ".*"),
                            regex("description", ".*" + Pattern.quote(search) + ".*"),
                            regex("publisher", ".*" + Pattern.quote(search) + ".*")));
            setSearchTag("");
            setSearchCategory("");


            pagesNumber = (int) publicationCollection.countDocuments(or(
                    regex("title", ".*" + Pattern.quote(search) + ".*"),
                    regex("description", ".*" + Pattern.quote(search) + ".*"),
                    regex("publisher", ".*" + Pattern.quote(search) + ".*")));
        } else if (!Objects.equals(searchCategory, "")) {
            System.out.println("search by category" + searchCategory);
            publicationFindIterable = publicationCollection.find(
                    regex("category", ".*" + Pattern.quote(searchCategory) + ".*"));
            setSearchTag("");


            pagesNumber = (int) publicationCollection.countDocuments(regex("category", ".*" + Pattern.quote(searchCategory) + ".*"));

        } else if (!Objects.equals(searchTag, "")) {

            System.out.println("search by tag" + searchTag);

            publicationFindIterable = publicationCollection.find(
                    regex("tags.name", ".*" + Pattern.quote(searchTag) + ".*"));

            pagesNumber = (int) publicationCollection.countDocuments(regex("tags.name", ".*" + Pattern.quote(searchTag) + ".*"));
        } else {
            publicationFindIterable = publicationCollection.find();
            pagesNumber = (int) publicationCollection.countDocuments();
        }

        System.out.println("index" + pageIndex);

        if (pagesNumber != 0) {
            pagesNumber = (pagesNumber / pageSize) + 1;
        }

        publicationFindIterable = publicationFindIterable.sort(Sorts.descending("created_at"))
                .skip(pageIndex * pageSize)
                .limit(pageSize);
        System.out.println("pagesNumber" + pagesNumber);

        for (Publication pub : publicationFindIterable) {
            System.out.println(pub.toString());
            publicationList.add(pub);
        }

        return publicationList;
    }


    public void addPublication(Publication publication, Event event, Report report, Article article, Image image, org.irisi.laboeasyseek.models.Document document) throws IOException {
        System.out.println("here1");


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

    public String homePage() {
        setTags(new ArrayList<Tag>());
        handleSetCategory("");
        setPublication(null);
        return "home";
    }


    private Publication publication;

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public String postPage(Publication publication) {
        setPublication(publication);

        System.out.println("publication--------------------------------------------------hhh-" + publication.getId());

        Document publicationQuery = new Document().append("_id", new ObjectId(publication.getId()));
        Bson updates = Updates.combine(
                Updates.set("views_number", publication.getViewsNumber() + 1)
        );

        UpdateOptions options = new UpdateOptions().upsert(true);

        try {
            UpdateResult result = publicationCollection.updateOne(publicationQuery, updates, options);
            System.out.println("Modified Publication count: " + result.getModifiedCount());
            System.out.println("Upserted id: " + result.getUpsertedId());
        } catch (MongoException me) {
            System.err.println("Unable to update due to an error: " + me);
        }

        return "post";
    }

    public void processPreviousAction(ActionEvent event) {
        System.out.println("previous" + getPageIndex());
        if (pageIndex > 0) {
            System.out.println("previous2" + getPageIndex());
            setPageIndex(pageIndex - 1);
            System.out.println("previous3" + getPageIndex());
        }
    }

    public void processNextAction(ActionEvent event) {
        System.out.println("next" + getPageIndex());
        if (pageIndex < pagesNumber - 1) {
            System.out.println("next2" + getPageIndex());
            setPageIndex(pageIndex + 1);
            System.out.println("next3" + getPageIndex());
        }
    }


    public String addComment(Comment comment, Publication publication) {

        comment.setCreatedAt(new Date());
        comment.setUser(SessionUtils.getEmail());

        Document publicationQuery = new Document().append("_id", new ObjectId(publication.getId()));
        Bson updates = Updates.combine(
                Updates.addToSet("comments", comment)
        );
        UpdateOptions options = new UpdateOptions().upsert(true);

        try {
            UpdateResult result = publicationCollection.updateOne(publicationQuery, updates, options);
            System.out.println("Modified Publication count: " + result.getModifiedCount());
            System.out.println("Upserted id: " + result.getUpsertedId());

        } catch (MongoException me) {
            System.err.println("Unable to update due to an error: " + me);
        }

        setPublication(publicationCollection.find(eq("_id", new ObjectId(publication.getId()))).first());
        return "post";
    }


    public String addRating(int rating) {

        Rating newRating = new Rating();
        newRating.setRating(rating);
        newRating.setUser(SessionUtils.getEmail());
        System.out.println("rating : " + rating);

        System.out.println("rating : " + rating);

        Document publicationQuery = new Document().append("_id", new ObjectId(publication.getId()));
        Bson updates = Updates.combine(
                Updates.addToSet("ratings", newRating)
        );
        UpdateOptions options = new UpdateOptions().upsert(true);

        try {
            UpdateResult result = publicationCollection.updateOne(publicationQuery, updates, options);
            System.out.println("Modified Publication count: " + result.getModifiedCount());
            System.out.println("Upserted id: " + result.getUpsertedId());

        } catch (MongoException me) {
            System.err.println("Unable to update due to an error: " + me);
        }

        setPublication(publicationCollection.find(eq("_id", new ObjectId(publication.getId()))).first());

        return "post";
    }

}
