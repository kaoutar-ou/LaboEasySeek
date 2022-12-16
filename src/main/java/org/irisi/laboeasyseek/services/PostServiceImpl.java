package org.irisi.laboeasyseek.services;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import jakarta.ejb.Stateful;
import org.irisi.laboeasyseek.beans.CalendarView;
import org.irisi.laboeasyseek.beans.CommentBean;
import org.irisi.laboeasyseek.beans.ItemsBean;
import org.irisi.laboeasyseek.beans.PostBean;
import org.irisi.laboeasyseek.dao.EntityManagerFactorySingleton;
import org.irisi.laboeasyseek.models.*;
import org.irisi.laboeasyseek.utils.NLPUtils;
import org.irisi.laboeasyseek.utils.SessionUtils;

import jakarta.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;


@Stateful
public class PostServiceImpl implements IPostService, Serializable {

    private EntityManager em;
    private EntityTransaction et;

    public PostServiceImpl() {
        em = EntityManagerFactorySingleton.getEntityManagerFactory().createEntityManager();
        et = em.getTransaction();
    }

    @Override
        public Boolean addPost(String postCategory, PostBean postBean, CalendarView calendarView, String imageTitle, String imageType, String documentTitle, String documentType, List<String> listWords, ItemsBean itemsBean) {
        Post post = new Post();

        try {
            et.begin();


            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);
            Root<Category> itemRoot = criteriaQuery.from(Category.class);

            Predicate predicateCategory
                    = criteriaBuilder.equal(itemRoot.get("name"), "other");

            if (postBean.getPostCategory() != "" && postBean.getPostCategory() != null) {
                predicateCategory = criteriaBuilder.equal(itemRoot.get("name"), postBean.getPostCategory());
            }

            Category category = null;
            criteriaQuery.where(predicateCategory);
            List<Category> items = em.createQuery(criteriaQuery).getResultList();
            if (items.size() > 0) {
                category = items.get(0);
            }

            post.setCategory(category);


            if (imageTitle != null) {
                Image image = new Image();
                image.setTitle(imageTitle);
                image.setType(imageType);
                em.persist(image);
                post.setImage(image);
            }

            if (documentTitle != null) {
                Document document = new Document();
                document.setTitle(documentTitle);
                document.setType(documentType);
                document.setFilePath(documentTitle.split("\\.")[0] + ".png");
                for (String word : listWords) {

                    CriteriaQuery<Keyword> keywordCriteriaQuery = criteriaBuilder.createQuery(Keyword.class);
                    Root<Keyword> keywordRoot = keywordCriteriaQuery.from(Keyword.class);
                    Keyword keyword = null;
                    Predicate predicateKeyword
                            = criteriaBuilder.equal(keywordRoot.get("name"), word);
                    keywordCriteriaQuery.where(predicateKeyword);
                    List<Keyword> keywords = em.createQuery(keywordCriteriaQuery).getResultList();
                    if (keywords.size() > 0) {
                        keyword = keywords.get(0);
                    }

                    if (keyword == null) {
                        keyword = new Keyword();
                        keyword.setName(word);
                        em.persist(keyword);
                    }
                    document.getKeywords().add(keyword);
                }
                em.persist(document);
                post.setDocument(document);
            }

            User user = null;

            if (SessionUtils.getUserId() != null) {
                user = em.find(User.class, Long.parseLong(SessionUtils.getUserId()));
            }

            post.setTitle(postBean.getPostTitle());
            post.setDescription(postBean.getPostDescription());
            post.setContent(postBean.getPostContent());
            post.setVersion(postBean.getPostVersion());
            post.setLocation(postBean.getPostLocation());
            post.setUser(user);
            post.setComments(new ArrayList<>());
            post.setDate(calendarView.getDate10());
            post.setTime(calendarView.getDate14());

            em.persist(post);

            et.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

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
    @Transactional
    public Post getPost(Long id) {
        Post newPost = null;
        try {
            et.begin();
            Post post = em.find(Post.class, id);
            newPost = post;
            newPost.setComments(post.getComments());

            newPost.getDocument().setKeywords(post.getDocument().getKeywords());
            et.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newPost;
    }

    @Override
    public List<Comment> getPostComments(Long id) {
        List<Comment> comments = new ArrayList<>();
        try {
            et.begin();
            Post post = em.find(Post.class, id);
            comments.addAll(post.getComments());
            System.out.println("comments size: " + comments.size());
            et.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return comments;
    }


    public List<Keyword> getPostKeywords(Long id) {
        List<Keyword> keywords = new ArrayList<>();
        try {
            et.begin();
            Post post = em.find(Post.class, id);
            keywords.addAll(post.getDocument().getKeywords());


            et.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("keywords size: " + keywords.size());
        System.out.println("keywords: " + keywords.get(0).getName());
        return keywords;
    }

    private int pageIndex = 0;
    private int pageSize = 50;
    private int pagesNumber = 1;


    public List<Post> getAllPosts(String search, String searchCategory, String searchTag) {
        List<Post> posts = new ArrayList<>();
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Post> criteriaQuery = criteriaBuilder.createQuery(Post.class);
            Root<Post> root = criteriaQuery.from(Post.class);
            criteriaQuery.select(root);

            TypedQuery<Post> typedQuery = em.createQuery(criteriaQuery);
            posts = typedQuery.getResultList();

            if (search != null && !search.isEmpty()) {
                posts = posts.stream()
                        .filter(post -> post.getTitle().contains(search) || (post.getDescription() != null && post.getDescription().contains(search))
                                || (post.getContent() != null && post.getContent().contains(search) ) || (post.getLocation() != null && post.getLocation().contains(search))
                                || (post.getDate() != null && post.getDate().toString().contains(search)) || (post.getTime() != null &&  post.getTime().toString().contains(search))
                                || (post.getDocument() != null && post.getDocument().getKeywords().stream().anyMatch(keyword -> keyword.getName().contains(search))))
                        .collect(Collectors.toList());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    public List<Post> getAllPostsTest(String search, String searchCategory, String searchTag) {
        List<Post> publicationList = new ArrayList<>();

        System.out.println("search = " + search);
        System.out.println("searchCategory = " + searchCategory);
        System.out.println("searchTag = " + searchTag);

        try {

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Post> criteriaQuery = criteriaBuilder.createQuery(Post.class);
            Root<Post> root = criteriaQuery.from(Post.class);
            criteriaQuery.select(root);




            if (search != null && !search.isEmpty()) {
                Predicate predicateTitle = criteriaBuilder.like(root.get("title"), "%" + search + "%", '\\');
                Predicate predicateDescription = criteriaBuilder.like(root.get("description"), "%" + search + "%", '\\');
                Predicate predicateContent = criteriaBuilder.like(root.get("content"), "%" + search + "%", '\\');
                Predicate predicateVersion = criteriaBuilder.like(root.get("version"), "%" + search + "%", '\\');
                Predicate predicateSearch = criteriaBuilder.or(predicateTitle, predicateDescription, predicateContent, predicateVersion);
                criteriaQuery.where(predicateSearch);
            }

            TypedQuery<Post> typedQuery = em.createQuery(criteriaQuery);
            List<Post> posts =  typedQuery.getResultList();


            publicationList = posts;

        } catch (Exception e) {
            e.printStackTrace();
        }



        return publicationList;
    }


//    @EJB
//    private SentimentAnalyzerBeanLocal sentimentAnalyzerBean;
//    private static final long serialVersionUID = 1L;
    @Override
    public Boolean addComment(CommentBean commentBean, Long postId, Long userId) {




//    StanfordCoreNLP pipeline;

//        Properties props = new Properties();
//        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
//        pipeline = new StanfordCoreNLP(props);
//
//        int sentimentInt = 2;
//        String sentimentName;
//        Annotation annotation = pipeline.process(commentBean.getContent().replaceAll("\\.", ""));
//        for(CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class))
//        {
//            Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
//            sentimentInt = RNNCoreAnnotations.getPredictedClass(tree);
//            sentimentName = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
//            System.out.println(sentimentName + "\t" + sentimentInt + "\t" + sentence);
//        }

//        Sentiment sentiment = sentimentAnalyzerBean.findSentiment(commentBean.getContent().replaceAll("\\.", ""));

        try {
            et.begin();
            Post post = em.find(Post.class, postId);
            User user = em.find(User.class, userId);
            Comment comment = new Comment();
//            NLPUtils.init();
            comment.setContent(commentBean.getContent());
            comment.setPost(post);
            comment.setUser(user);
//            System.out.println("sentiment: " + sentiment);
            comment.setSentiment(2);
            em.persist(comment);
            et.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            et.rollback();
        }
        return false;
    }
}

