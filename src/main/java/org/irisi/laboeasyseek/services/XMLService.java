package org.irisi.laboeasyseek.services;

import jakarta.faces.context.FacesContext;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.irisi.laboeasyseek.entities.*;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class XMLService {

//    private static final String fileLocation = "./root.xml";
    private static final String filepath = "./roottest.xml";

    private static final String fileLocation = (FacesContext.getCurrentInstance( ).getExternalContext( ).getRealPath("") + File.separator + "resources" + File.separator  + "XMLDataBase"+File.separator +"root.xml").replace("\\", "/");

    public XMLService() {
        File folder = new File(fileLocation);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

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

    public static void addPost(Post post) throws JAXBException {
        System.out.println(fileLocation);
        File tempFile = new File(fileLocation);
        if (tempFile.exists()) {
            Posts data = (Posts) readFromFile(fileLocation);
            data.getPosts().add(post);
            writeToFile(data, fileLocation);
        }
        else  {
            List<Post> postList = new ArrayList<>();
            postList.add(post);
            Posts posts = new Posts(postList);
            writeToFile(posts, fileLocation);
        }
    }

    public static void deletePostByID(Long id) throws JAXBException {
        Posts data = (Posts) readFromFile(fileLocation);
        data.getPosts().removeIf((Post post) -> id.equals(post.getId()));
        writeToFile(data, fileLocation);
    }

    public static List<Post> getAllPosts() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        List<Post> postList = new ArrayList<>();

        FileInputStream fileIS = new FileInputStream(new File(fileLocation));
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document xmlDocument = builder.parse(fileIS);
        XPath xPath = XPathFactory.newInstance().newXPath();
        String expression = "/root/post";
//        String expression = "/root/post" +
//                "[./name[contains(.,'j')] or @id=47]";
        NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node nNode = nodeList.item(i);

            System.out.println("Element type :" + nNode.getNodeName());
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                Post post = new Post();
                post.setId(Long.parseLong(eElement.getAttribute("id")));
                if(eElement.hasChildNodes()) {
                    System.out.println("***************" + eElement.getElementsByTagName("title").item(0));
                    if (eElement.getElementsByTagName("title").item(0) != null) {
                        post.setTitle(eElement.getElementsByTagName("title").item(0).getTextContent());
                    }
                    System.out.println("Element type :" + eElement.getElementsByTagName("description").item(0).getTextContent());

                    System.out.println("***************" + eElement.getElementsByTagName("description").item(0).getTextContent());
                    if (eElement.getElementsByTagName("description").item(0) != null) {
                        post.setDescription(eElement.getElementsByTagName("description").item(0).getTextContent());
                    }
                    if (eElement.getElementsByTagName("photo").item(0) != null) {
                        System.out.println("***************" + eElement.getElementsByTagName("photo").item(0).getTextContent());
                        post.setPhoto(eElement.getElementsByTagName("photo").item(0).getTextContent());
                    }
                    if (eElement.getElementsByTagName("website").item(0) != null) {
                        post.setWebsite(eElement.getElementsByTagName("website").item(0).getTextContent());
                    }
                    if (eElement.getElementsByTagName("category").item(0) != null) {
                        post.setCategory(eElement.getElementsByTagName("category").item(0).getTextContent());
                    }
                    if (eElement.getElementsByTagName("publisher").item(0) != null) {
                        post.setPublisher(eElement.getElementsByTagName("publisher").item(0).getTextContent());
                    }
                    if (eElement.getElementsByTagName("eventName").item(0) != null) {
                        post.setEventName(eElement.getElementsByTagName("eventName").item(0).getTextContent());
                    }
                    if (eElement.getElementsByTagName("eventDate").item(0) != null) {
                        post.setEventDate(eElement.getElementsByTagName("eventDate").item(0).getTextContent());
                    }
                    if (eElement.getElementsByTagName("eventLocal").item(0) != null) {
                        post.setEventLocal(eElement.getElementsByTagName("eventLocal").item(0).getTextContent());
                    }
                    if (eElement.getElementsByTagName("tag").item(0) != null) {
                        post.setTag(eElement.getElementsByTagName("tag").item(0).getTextContent());
                    }
                    if (eElement.getElementsByTagName("version").item(0) != null) {
                        post.setVersion(eElement.getElementsByTagName("version").item(0).getTextContent());
                    }
                }
                System.out.println("-----------------________________-----------------__________-----------____________ :" + post.getTitle());


                postList.add(post);
            }
        }

        return postList;
    }

    public static List<Post> searchPostsByContent(String searchString) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        List<Post> postList = new ArrayList<>();

        FileInputStream fileIS = new FileInputStream(new File(fileLocation));
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document xmlDocument = builder.parse(fileIS);
        XPath xPath = XPathFactory.newInstance().newXPath();
        String expression = "/root/post" +
                "[./title[contains(.,'"+searchString+"')] or ./description[contains(.,'\"+searchString+\"')] or ./eventName[contains(.,'"+searchString+"')] or ./eventLocal[contains(.,'"+searchString+"')]]";
        NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node nNode = nodeList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
//                Post post = new Post(Long.parseLong(eElement.getAttribute("id")), eElement.getElementsByTagName("name").item(0).getTextContent());


//                Post post = new Post();
//                post.setId(Long.parseLong(eElement.getAttribute("id")));
//                post.setTitle(eElement.getElementsByTagName("title").item(0).getTextContent());
//                postList.add(post);


                Post post = new Post();
                post.setId(Long.parseLong(eElement.getAttribute("id")));
                if(eElement.hasChildNodes()) {
                    System.out.println("***************" + eElement.getElementsByTagName("title").item(0));
                    if (eElement.getElementsByTagName("title").item(0) != null) {
                        post.setTitle(eElement.getElementsByTagName("title").item(0).getTextContent());
                    }
//                }
//                if(eElement.hasAttribute("description")) {
                    System.out.println("Element type :" + eElement.getElementsByTagName("description").item(0).getTextContent());
                    if (eElement.getElementsByTagName("description").item(0) != null) {
                        post.setDescription(eElement.getElementsByTagName("description").item(0).getTextContent());
                    }
//                }
//                if(eElement.hasAttribute("photo")) {

                    if (eElement.getElementsByTagName("photo").item(0) != null) {
                        post.setPhoto(eElement.getElementsByTagName("photo").item(0).getTextContent());
                    }

                    if (eElement.getElementsByTagName("website").item(0) != null) {
                        post.setWebsite(eElement.getElementsByTagName("website").item(0).getTextContent());
                    }
                    if (eElement.getElementsByTagName("category").item(0) != null) {
                        post.setCategory(eElement.getElementsByTagName("category").item(0).getTextContent());
                    }
                    if (eElement.getElementsByTagName("publisher").item(0) != null) {
                        post.setPublisher(eElement.getElementsByTagName("publisher").item(0).getTextContent());
                    }
                    if (eElement.getElementsByTagName("eventName").item(0) != null) {
                        post.setEventName(eElement.getElementsByTagName("eventName").item(0).getTextContent());
                    }
                    if (eElement.getElementsByTagName("eventDate").item(0) != null) {
                        post.setEventDate(eElement.getElementsByTagName("eventDate").item(0).getTextContent());
                    }
                    if (eElement.getElementsByTagName("eventLocal").item(0) != null) {
                        post.setEventLocal(eElement.getElementsByTagName("eventLocal").item(0).getTextContent());
                    }
                    if (eElement.getElementsByTagName("tag").item(0) != null) {
                        post.setTag(eElement.getElementsByTagName("tag").item(0).getTextContent());
                    }
                    if (eElement.getElementsByTagName("version").item(0) != null) {
                        post.setVersion(eElement.getElementsByTagName("version").item(0).getTextContent());
                    }
                }
                System.out.println("-----------------________________-----------------__________-----------____________ :" + post.getTitle());


                postList.add(post);
            }
        }

        return postList;
    }


    //// Publication

    public static int getXMLUserIndex(User user) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
//        List<User> userList = new ArrayList<>();

        FileInputStream fileIS = new FileInputStream(new File(filepath));
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document xmlDocument = builder.parse(fileIS);
        XPath xPath = XPathFactory.newInstance().newXPath();
        String expression = "/root/user";

        NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node nNode = nodeList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                if(Objects.equals(eElement.getElementsByTagName("email").item(0).getTextContent(), user.getEmail())) {
                    return i;
                }
//                User user_ = new User(Long.parseLong(eElement.getAttribute("id")), eElement.getElementsByTagName("name").item(0).getTextContent());

//                postList.add(post);
            }
        }

        return -1;
    }


    private static void writePublicationToFile(Users data, String filePath) throws JAXBException
    {
        JAXBContext jaxbContext = JAXBContext.newInstance(Users.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(data, new File(filePath));
    }

    private static Users readPublicationFromFile(String filePath) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Users.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (Users) jaxbUnmarshaller.unmarshal(new File(filePath));
    }

    public static void addPublication(Publication publication, User user) throws JAXBException, XPathExpressionException, IOException, ParserConfigurationException, SAXException {
        File tempFile = new File(filepath);
        if (tempFile.exists()) {
            Users users = (Users) readPublicationFromFile(filepath);
            int userIndex = getXMLUserIndex(user);
            System.out.println(userIndex);
            if (userIndex >= 0) {
                users.getUsers().get(userIndex).getPublications().getPublications().add(publication);
                writePublicationToFile(users, filepath);
            }
            else {
                user.getPublications().getPublications().add(publication);
                users.getUsers().add(user);
                writePublicationToFile(users, filepath);
            }
        }
        else  {
            List<User> userList = new ArrayList<>();
            List<Publication> publicationList = new ArrayList<>();
            publicationList.add(publication);
            Publications publications = new Publications(publicationList);
            user.setPublications(publications);
            userList.add(user);
            Users users1 = new Users(userList);
            writePublicationToFile(users1, filepath);
        }
    }



    public static List<User> getAllUsersPublications() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        List<User> userList = new ArrayList<>();

        FileInputStream fileIS = new FileInputStream(new File(filepath));
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document xmlDocument = builder.parse(fileIS);
        XPath xPath = XPathFactory.newInstance().newXPath();
        String expression = "/root/user" +
                "[./email[contains(.,'kaoutar477@gmail.com')]]";
        NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);

        System.out.println(nodeList.getLength());

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node nNode = nodeList.item(i);

            System.out.println("Element type :" + nNode.getNodeName());
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                User user = new User();
                user.setId(Long.parseLong(eElement.getAttribute("id")));
////                Publication publication = new Publication(Long.parseLong(eElement.getAttribute("id")), eElement.getElementsByTagName("name").item(0).getTextContent());
//                Publication publication = new Publication();
//                publication.setId(Long.parseLong(eElement.getAttribute("id")));
//                publication.setTitle(eElement.getElementsByTagName("title").item(0).getTextContent());
//                publication.setDescription(eElement.getElementsByTagName("description").item(0).getTextContent());
//
//                System.out.println(publication.getTitle());
                userList.add(user);
            }
        }

        return userList;
    }
}