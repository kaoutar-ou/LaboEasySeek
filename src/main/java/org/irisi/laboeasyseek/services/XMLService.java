package org.irisi.laboeasyseek.services;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.irisi.laboeasyseek.entities.Post;
import org.irisi.laboeasyseek.entities.Posts;

import java.io.File;
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

}
