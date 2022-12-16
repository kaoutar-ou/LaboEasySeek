package org.irisi.laboeasyseek.controllers;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@RequestScoped
@Named("routeController")
public class RouteController implements Serializable {

    public String goTo(String page) {
        return page;
    }
}
