package org.irisi.laboeasyseek.utils;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import org.irisi.laboeasyseek.dao.EntityManagerFactorySingleton;
import org.irisi.laboeasyseek.models.Comment;
import org.irisi.laboeasyseek.models.Message;

//import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class NLPUtils {
//    private static final long serialVersionUID = 42L;

    static StanfordCoreNLP  pipeline;
//    public static void init()
//    {
//        Properties props = new Properties();
//        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
//        pipeline = new StanfordCoreNLP(props);
//    }
    public static int estimatingSentiment(String text)
    {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        pipeline = new StanfordCoreNLP(props);

        int sentimentInt = 2;
        String sentimentName;
        Annotation annotation = pipeline.process(text);
        for(CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class))
        {
            Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
            sentimentInt = RNNCoreAnnotations.getPredictedClass(tree);
            sentimentName = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
            System.out.println(sentimentName + "\t" + sentimentInt + "\t" + sentence);
        }
        return sentimentInt;
    }
}

class CoreNLP {

    private EntityManager em;
    private EntityTransaction et;

    public CoreNLP() {
        em = EntityManagerFactorySingleton.getEntityManagerFactory().createEntityManager();
        et = em.getTransaction();

//        em = PGConfig.getEm();
//        et = PGConfig.getEt();
    }

    public void setCommentSentiment () {
        List<Comment> comments = new ArrayList<>();
        try {
            et.begin();
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Comment> criteriaQuery = criteriaBuilder.createQuery(Comment.class);
            Root<Comment> root = criteriaQuery.from(Comment.class);
            criteriaQuery.select(root);

            TypedQuery<Comment> typedQuery = em.createQuery(criteriaQuery);
            comments = typedQuery.getResultList();

            for (Comment comment : comments) {
                if (comment.getSentiment() == 2) {
                    if (!comment.getContent().trim().endsWith(".")) {
                        comment.setContent(comment.getContent() + " .");
                    }
                    int sent = NLPUtils.estimatingSentiment(comment.getContent());
                    System.out.println("comment : " + comment.getContent() + ", sentiment : " + sent);
                    comment.setSentiment(sent);
                    em.merge(comment);
                }
            }

            et.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

//        String text = "It's awful . I hate it . I love it . Good . Bad . I like this post . "
//                + "I don't like this post . Great . I don't like it . ";
//        String text = "Awful .";
//        String text = "It's awful";
////        NLPUtils.init();
//        NLPUtils.estimatingSentiment(text);

        CoreNLP coreNLP = new CoreNLP();
        coreNLP.setCommentSentiment();

    }
}
