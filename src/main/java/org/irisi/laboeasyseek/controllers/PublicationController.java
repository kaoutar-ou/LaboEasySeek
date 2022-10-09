package org.irisi.laboeasyseek.controllers;

import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.model.Filters;
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
    }

//    private boolean showTest = false;
//
//    public boolean isShowTest() {
//        return showTest;
//    }
//
//    public void handleSetShowTest(boolean showTest) {
//        this.showTest = showTest;
//    }

    MongoCollection<User> userCollection = mongoDatabase.getCollection("users", User.class);
    MongoCollection<Publication> publicationCollection = mongoDatabase.getCollection("publications", Publication.class);
    MongoCollection<Event> eventCollection = mongoDatabase.getCollection("events", Event.class);
    MongoCollection<Article> articleCollection = mongoDatabase.getCollection("articles", Article.class);
    MongoCollection<Report> reportCollection = mongoDatabase.getCollection("reports", Report.class);
    MongoCollection<Tag> tagCollection = mongoDatabase.getCollection("tags", Tag.class);
    MongoCollection<Media> mediaCollection = mongoDatabase.getCollection("medias", Media.class);
    MongoCollection<Image> imageCollection = mongoDatabase.getCollection("images", Image.class);
    MongoCollection<org.irisi.laboeasyseek.models.Document> documentCollection = mongoDatabase.getCollection("documents", org.irisi.laboeasyseek.models.Document.class);


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


    public List<Tag> getAllTags() {
        List<Tag> tagList = new ArrayList<>();
        FindIterable<Tag> tagFindIterable = null;
        tagFindIterable = tagCollection.find();
        for (Tag pub : tagFindIterable) {
            System.out.println(pub.toString());
            tagList.add(pub);
        }
        return tagList;
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
            publicationFindIterable = publicationCollection.find(or(regex("title", ".*" + Pattern.quote(search) + ".*"), regex("description", ".*" + Pattern.quote(search) + ".*"), regex("publisher", ".*" + Pattern.quote(search) + ".*")));
            setSearchTag("");
            setSearchCategory("");
//            for (Publication pub : publicationFindIterable) {
//                System.out.println(pub.toString());
//                publicationList.add(pub);
//            }
        } else if(!Objects.equals(searchCategory, "")) {
            System.out.println("search by category" + searchCategory);
            publicationFindIterable = publicationCollection.find(regex("category", ".*" + Pattern.quote(searchCategory) + ".*"));
            setSearchTag("");
//            for (Publication pub : publicationFindIterable) {
//                System.out.println(pub.toString());
//                publicationList.add(pub);
//            }
        } else if (!Objects.equals(searchTag, "")) {

            System.out.println("search by tag" + searchTag);

            publicationFindIterable = publicationCollection.find(regex("tags.name", ".*" + Pattern.quote(searchTag) + ".*"));

//            for (Publication pub : publicationFindIterable) {
//                System.out.println(pub.toString());
//                publicationList.add(pub);
//            }

        } else {
            publicationFindIterable = publicationCollection.find();
//            for (Publication pub : publicationFindIterable) {
//                System.out.println(pub.toString());
//                publicationList.add(pub);
//            }
        }

        for (Publication pub : publicationFindIterable) {
            System.out.println(pub.toString());
            publicationList.add(pub);
        }

        Collections.reverse(publicationList);
        return publicationList;
    }


    public void addPublication(Publication publication, Event event, Report report, Article article, Image image, org.irisi.laboeasyseek.models.Document document) {
        System.out.println("here1");

        List<String> categories = Arrays.asList("article", "report", "event", "other");

        System.out.println("here2");
        if (category != null && categories.contains(category)) {
            publication.setCategory(category);
        }

        if (Objects.equals(publication.getCategory(), "event")) {
            if (event != null
                    && (!Objects.equals(event.getName(), "")
                    || !Objects.equals(event.getDate(), "")
                    || !Objects.equals(event.getLocal(), ""))) {
                eventCollection.insertOne(event);
                publication.setEvent(event);
            }
        }

        if (Objects.equals(publication.getCategory(), "report")) {
            if (report != null
                    && (!Objects.equals(report.getTitle(), "")
                    || !Objects.equals(report.getVersion(), ""))) {
                reportCollection.insertOne(report);
                publication.setReport(report);
            }
        }

        if (Objects.equals(publication.getCategory(), "article")) {
            if (article != null
                    && (!Objects.equals(article.getTitle(), "")
                    || !Objects.equals(article.getContent(), ""))) {
                articleCollection.insertOne(article);
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


    public Publication processUpload(Publication publication, Image imagePart, org.irisi.laboeasyseek.models.Document documentPart) {

        System.out.println("pub------------------------------------------------dd---");
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
                imageCollection.insertOne(image);
                media.setImage(image);
            }

        }
        if (documentPart != null) {
            String title = uploadHelper.processUpload(documentPart.getPart(), publication.getTitle());
            media.setType(title);

            org.irisi.laboeasyseek.models.Document document = new org.irisi.laboeasyseek.models.Document();
            document.setTitle(title);
            document.setType(media.getType());
            documentCollection.insertOne(document);
            media.setDocument(document);

        }

        mediaCollection.insertOne(media);
        publication.setMedia(media);
        return publication;
    }

    public String homePage() {
        setTags(new ArrayList<Tag>());
        handleSetCategory("");
        return "home";
    }
}
