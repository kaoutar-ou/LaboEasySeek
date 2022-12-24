package org.irisi.laboeasyseek.ws;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/rest")
public class RestApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(JmsWS.class);
        return classes;
    }
}
