package foritolium;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import foritolium.model.Team;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@UIScope
@SpringView(name = TeamsView.VIEW)
public class TeamsView extends VerticalLayout implements View {
    public static final String VIEW = "teams";

    @Autowired
    private ViewModelBinder binder;

    @PostConstruct
    void init() {
        Window editWin = new Window();
        VerticalLayout lay = new VerticalLayout();
        editWin.setContent(lay);
        TextField nameFld = new TextField();
        nameFld.setValue("Name");
        nameFld.setWidth(300f, Unit.PIXELS);
        lay.addComponent(nameFld);
        TextField descFld = new TextField();
        descFld.setWidth(300f, Unit.PIXELS);
        descFld.setValue("Description");
        lay.addComponent(descFld);

        Button saveBtn = new Button("Save");
        Button deleteBtn = new Button("Delete");
        lay.addComponent(saveBtn);
        lay.addComponent(deleteBtn);
        deleteBtn.setEnabled(false);
        editWin.center();

        Button addTeam = new Button("Add new");
        addTeam.setWidth(300f, Unit.PIXELS);
        addTeam.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                nameFld.setValue("Name");
                descFld.setValue("Description");
                saveBtn.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        binder.createTeam(nameFld.getValue(),
                                descFld.getValue());
                        editWin.close();
                        Notification.show("Created",
                                "new team created",
                                Notification.Type.HUMANIZED_MESSAGE);
                        Page.getCurrent().reload();
                    }
                });
                getUI().addWindow(editWin);
            }
        });
        addComponent(addTeam);
        for (Team team : binder.listAllTeams()) {
            Button teamBtn = new Button(team.getName());
            teamBtn.setWidth(300f, Unit.PIXELS);
            teamBtn.setHeight(25f, Unit.PIXELS);
            teamBtn.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    nameFld.setValue(team.getName());
                    descFld.setValue(team.getDescription());
                    deleteBtn.addClickListener(new Button.ClickListener() {
                        @Override
                        public void buttonClick(Button.ClickEvent event) {
                            binder.removeTeam(team.getTeamId(), team.getName());
                            editWin.close();
                            Notification.show("Team removed",
                                    "team " + team.getName() + " was removed",
                                    Notification.Type.HUMANIZED_MESSAGE);
                            Page.getCurrent().reload();
                        }
                    });
                    saveBtn.addClickListener(new Button.ClickListener() {
                        @Override
                        public void buttonClick(Button.ClickEvent event) {
                            binder.updateTeam(team.getTeamId(),
                                    nameFld.getValue(),
                                    descFld.getValue());
                            editWin.close();
                            Notification.show("Changes saved",
                                    "your changes to team were saved",
                                    Notification.Type.HUMANIZED_MESSAGE);
                            Page.getCurrent().reload();
                        }
                    });
                    deleteBtn.setEnabled(true);
                    getUI().addWindow(editWin);

                }
            });
            addComponent(teamBtn);
        }
    }

    @Override
    public void enter(ViewChangeEvent event) {}
}