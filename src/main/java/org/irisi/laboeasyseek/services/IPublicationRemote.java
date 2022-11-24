package org.irisi.laboeasyseek.services;

import jakarta.ejb.Remote;
import org.irisi.laboeasyseek.models.*;

import java.io.IOException;
import java.util.List;

@Remote
public interface IPublicationRemote {
    List<Publication> getPublications();
    void addPublication(Publication publication, Event event, Report report, Article article, Image image, org.irisi.laboeasyseek.models.Document document) throws IOException;
}
