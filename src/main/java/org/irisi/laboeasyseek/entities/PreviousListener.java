package org.irisi.laboeasyseek.entities;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.event.AbortProcessingException;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.event.ActionListener;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.irisi.laboeasyseek.controllers.XMLController;

import java.io.Serializable;

@Named
@SessionScoped
public class PreviousListener implements Serializable, ActionListener {
    private static final long serialVersionUID = -7752358388239089979L;

    @Inject
    private XMLController xmlController;

    @Override
    public void processAction( ActionEvent event ) throws AbortProcessingException {

    }
}