package org.irisi.laboeasyseek.entities;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.event.AbortProcessingException;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.event.ActionListener;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.irisi.laboeasyseek.controllers.PublicationController;
import org.irisi.laboeasyseek.controllers.XMLController;

import java.io.Serializable;

@Named("previousListenerBean")
@SessionScoped
public class PreviousListener implements Serializable, ActionListener {
    private static final long serialVersionUID = -7752358385239089979L;

    @Inject
    private PublicationController publicationController;

    @Override
    public void processAction( ActionEvent event ) throws AbortProcessingException {
        if (publicationController.getPageIndex() > 0) {
            publicationController.setPageIndex(publicationController.getPageIndex() - 1);
        }
    }
}