package org.irisi.laboeasyseek.services;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.irisi.laboeasyseek.entities.Post;
import org.irisi.laboeasyseek.entities.Posts;
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

public class XMLService {

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

    public static void addPost(Post post) throws JAXBException {
        File tempFile = new File(fileLocation);
        if (tempFile.exists()) {
            Posts data = readFromFile(fileLocation);
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
        Posts data = readFromFile(fileLocation);
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
        String expression = "/root/post" +
                "[./name[contains(.,'j')] or @id=47]";
        NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node nNode = nodeList.item(i);

            System.out.println("Element type :" + nNode.getNodeName());
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                Post post = new Post(Long.parseLong(eElement.getAttribute("id")), eElement.getElementsByTagName("name").item(0).getTextContent());

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
                "[./name[contains(.,'"+searchString+"')]]";
        NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node nNode = nodeList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                Post post = new Post(Long.parseLong(eElement.getAttribute("id")), eElement.getElementsByTagName("name").item(0).getTextContent());
                postList.add(post);
            }
        }

        return postList;
    }
}
