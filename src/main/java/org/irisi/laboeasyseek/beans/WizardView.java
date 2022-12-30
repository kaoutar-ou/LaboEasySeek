package org.irisi.laboeasyseek.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Getter;
import org.primefaces.model.menu.MenuModel;

import java.io.Serializable;

@ViewScoped
@Named("wizardView")
@Getter
public class WizardView implements Serializable {

    private int activeIndex = 0;
    private final int minIndex = 0;
    private final int maxIndex = 2;

    public void handleTabChange(int index) {
        if (index <= maxIndex && index >= minIndex) {
            activeIndex = index;
        }
    }

}
