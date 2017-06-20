package foritolium;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import foritolium.model.Member;

import javax.annotation.PostConstruct;

@SpringView(name = MembersView.VIEW)
public class MembersView extends VerticalLayout implements View {
    public static final String VIEW = "members";

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
        TextField surnameFld = new TextField();
        surnameFld.setWidth(300f, Unit.PIXELS);
        surnameFld.setValue("Surname");
        lay.addComponent(surnameFld);
        TextArea skillsArea = new TextArea();
        skillsArea.setHeight(50f, Unit.PIXELS);
        skillsArea.setValue("Skills");
        lay.addComponent(skillsArea);
        TextField teamNameFld = new TextField();
        teamNameFld.setWidth(300f, Unit.PIXELS);
        teamNameFld.setValue("Team2");
        lay.addComponent(teamNameFld);

        Button saveBtn = new Button("Save");
        Button deleteBtn = new Button("Delete");
        lay.addComponent(saveBtn);
        lay.addComponent(deleteBtn);
        deleteBtn.setEnabled(false);
        editWin.center();

        Button addMember = new Button("Add new");
        addMember.setWidth(300f, Unit.PIXELS);
        addMember.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                nameFld.setValue("Name");
                surnameFld.setValue("Surname");
                skillsArea.setValue("Skills");
                teamNameFld.setValue("Team2");
                saveBtn.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        binder.createMember(nameFld.getValue(),
                                surnameFld.getValue(),
                                skillsArea.getValue(),
                                binder.getTeamByName(teamNameFld.getValue()));
                        editWin.close();
                        Notification.show("Created",
                                "new member created",
                                Notification.Type.HUMANIZED_MESSAGE);
                        Page.getCurrent().reload();
                    }
                });
                getUI().addWindow(editWin);
            }
        });
        addComponent(addMember);
        for (Member member : binder.listAllMembers()) {
            Button memBtn = new Button(member.getName() + " " + member.getSurname());
            memBtn.setWidth(300f, Unit.PIXELS);
            memBtn.setHeight(25f, Unit.PIXELS);
            memBtn.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    nameFld.setValue(member.getName());
                    surnameFld.setValue(member.getSurname());
                    skillsArea.setValue(member.getSkills());
                    teamNameFld.setValue(member.getTeam().getName());

                    deleteBtn.addClickListener(new Button.ClickListener() {
                        @Override
                        public void buttonClick(Button.ClickEvent event) {
                            binder.removeMember(member.getMemberId());
                            editWin.close();
                            Notification.show("Member removed",
                                    "member " + member.getName() + " " + member.getSurname() + " was removed",
                                    Notification.Type.HUMANIZED_MESSAGE);
                            Page.getCurrent().reload();
                        }
                    });
                    saveBtn.addClickListener(new Button.ClickListener() {
                        @Override
                        public void buttonClick(Button.ClickEvent event) {
                            binder.updateMember(member.getMemberId(),
                                    nameFld.getValue(),
                                    surnameFld.getValue(),
                                    skillsArea.getValue(),
                                    binder.getTeamByName(teamNameFld.getValue()));
                            editWin.close();
                            Notification.show("Changes saved",
                                    "your changes to member were saved",
                                    Notification.Type.HUMANIZED_MESSAGE);
                        Page.getCurrent().reload();
                        }
                    });
                    deleteBtn.setEnabled(true);
                    getUI().addWindow(editWin);

                }
            });
            addComponent(memBtn);
        }
    }

    @Override
    public void enter(ViewChangeEvent event) {}
}