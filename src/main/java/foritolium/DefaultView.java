package foritolium;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;

@SpringView(name = DefaultView.VIEW)
public class DefaultView extends VerticalLayout implements View {
    public static final String VIEW = "";

    @PostConstruct
    void init() {
        addComponent(new Label("This is an application for managing teams and members"));
    }

    @Override
    public void enter(ViewChangeEvent event) {}
}
