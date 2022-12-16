package org.irisi.laboeasyseek.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import javax.annotation.ManagedBean;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@SessionScoped
@Named("itemsBean")
public class ItemsBean implements Serializable {
    private List<String> items;

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        System.out.println(items);
        this.items = items;
    }
}

