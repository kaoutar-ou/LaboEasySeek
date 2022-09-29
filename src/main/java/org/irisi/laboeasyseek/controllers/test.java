package org.irisi.laboeasyseek.controllers;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.irisi.laboeasyseek.entities.*;
import org.irisi.laboeasyseek.services.UserrService;
import org.irisi.laboeasyseek.services.XMLService;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Named("testController")
@SessionScoped
public class test implements Serializable {

    private static final long serialVersionUID = -5433850275007415405L;

    public static void main(String[] args) throws JAXBException, IOException, XPathExpressionException, ParserConfigurationException, SAXException {
//        test();

//        Posts data = readFromFile(fileLocation);

//        Scanner scanner = new Scanner(System.in);
//        int i = scanner.nextInt();
//        deletePostByID(data, (long) i);
//
//        i = scanner.nextInt();
//
//        if (i == 5) {
//            Post post = new Post();
//            post.setId(1L);
//            post.setTitle("test");
//            addPost(data, post);
//        }

//        System.out.println("hi");
//        xpathTestFct();

//        test();


//        // Add pub
        UserrService userrService = new UserrService();

        List<Photo> photos = new ArrayList<>();
        photos.add(new Photo(1L, "jjjjjj"));
        photos.add(new Photo(1L, "jjjj5j"));
        Media media = new Media(photos);

        Publication publication = new Publication();
        publication.setId(1L);
        publication.setTitle("tttt");
        publication.setDescription("dddd");
        publication.setDocuments(null);
        publication.setMedia(media);
//        Userr userr = userrService.findUserByEmail(((HttpSession)(FacesContext.getCurrentInstance()
//                .getExternalContext())).getAttribute("email").toString());

        Userr userr = userrService.findUserByEmail("kaoutar477@gmail.com");

        User user = new User();
        user.setId(userr.getId());
        user.setEmail(userr.getEmail());

        XMLService.addPublication(publication, user);

        List<User> userList = XMLService.getAllUsersPublications();
    }


    public static void xpathTestFct() throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        FileInputStream fileIS = new FileInputStream(new File(fileLocation));
        System.out.println("hi1");
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document xmlDocument = builder.parse(fileIS);
        XPath xPath = XPathFactory.newInstance().newXPath();
        String expression = "/root/post";
        NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
        System.out.println("hi2");

        System.out.println(nodeList.getLength());

        List<Post> postList = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node nNode = nodeList.item(i);
//            System.out.println(nNode.getNodeValue());
            System.out.println("Element type :" + nNode.getNodeName());
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                System.out.println("Title : "
                        + eElement
                        .getElementsByTagName("name")
                        .item(0)
                        .getTextContent());
//                System.out.println("Text : "
//                        + eElement
//                        .getElementsByTagName("text")
//                        .item(0)
//                        .getTextContent());
                System.out.println("test : " + eElement.getAttribute("id"));
//                System.out.println("test : " + );

                Post post = new Post(Long.parseLong(eElement.getAttribute("id")), eElement.getElementsByTagName("name").item(0).getTextContent());
//                post.setId(Long.parseLong(eElement.getAttribute("id")));
//                post.setName(eElement
//                        .getElementsByTagName("name")
//                        .item(0)
//                        .getTextContent());

                postList.add(post);
            }
        }
    }

    private List<Post> posts;

    public List<Post> getPosts() throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {

        FileInputStream fileIS = new FileInputStream(new File(fileLocation));
        System.out.println("hi1");
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document xmlDocument = builder.parse(fileIS);
        XPath xPath = XPathFactory.newInstance().newXPath();
        String expression = "/root/post";
        NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
        System.out.println("hi2");

        System.out.println(nodeList.getLength());

        List<Post> postList = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node nNode = nodeList.item(i);
//            System.out.println(nNode.getNodeValue());
            System.out.println("Element type :" + nNode.getNodeName());
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                System.out.println("Title : "
                        + eElement
                        .getElementsByTagName("name")
                        .item(0)
                        .getTextContent());
//                System.out.println("Text : "
//                        + eElement
//                        .getElementsByTagName("text")
//                        .item(0)
//                        .getTextContent());
                System.out.println("test : " + eElement.getAttribute("id"));
//                System.out.println("test : " + );

                Post post = new Post(Long.parseLong(eElement.getAttribute("id")), eElement.getElementsByTagName("name").item(0).getTextContent());

//                Post post = new Post();
//                post.setId(Long.parseLong(eElement.getAttribute("id")));
//                post.setName(eElement.getAttribute("name"));

                postList.add(post);
            }
        }

//        Posts postsList = new Posts(postList);
//        posts = postsList.getPosts();
//        return posts;
        return postList;
    }


    private static final String fileLocation = "./root.xml";

    private static void writeToFile(Posts data, String filePath) throws JAXBException
    {
        JAXBContext jaxbContext = JAXBContext.newInstance(Posts.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(data, new File(filePath));
    }

    private static Posts readFromFile(String filePath) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Posts.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (Posts) jaxbUnmarshaller.unmarshal(new File(filePath));
    }

    public static void test() throws JAXBException, IOException {

        List<Post> postList = new ArrayList<>();
        for (int j = 0; j < 5; j++) {

            Post post = new Post();
            post.setId((long) j);
            post.setName("j"+j);

            postList.add(post);
        }

        Posts posts = new Posts(postList);

        writeToFile(posts, fileLocation);
    }

    public static void addPost(Posts data, Post post) throws JAXBException {
        data.getPosts().add(post);
        writeToFile(data, fileLocation);
    }

    public static void deletePostByID(Posts data, Long id) throws JAXBException {
        data.getPosts().removeIf((Post post) -> id.equals(post.getId()));
        writeToFile(data, fileLocation);
    }

}
