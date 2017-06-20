package foritolium;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import foritolium.model.DBManager;
import foritolium.model.Member;
import foritolium.model.Team;

import java.util.ArrayList;
import java.util.List;

@SpringComponent
@UIScope
public class ViewModelBinder {

    public void createTeam(String name, String description) {
        DBManager.createTeam(name, description);
    }

    public void createMember(String name, String surname, String skills, Team team) {
        DBManager.createMember(name, surname, skills, team);
    }

    public List<Team> listAllTeams() {
        return DBManager.listAllTeams();
    }

    public List<String> getTeamNames() {
        List<String> names = new ArrayList<>();
        for (Team team : listAllTeams()) {
            names.add(team.getName());
        }
        return names;
    }

    public List<Member> listAllMembers() {
        return DBManager.listAllMembers();
    }

    public void updateMember(int memberId, String name, String surname,
                                    String skills, Team team) {
        DBManager.updateMember(memberId, name, surname, skills, team);
    }

    public void updateTeam(int teamId, String name, String description) {
        DBManager.updateTeam(teamId, name, description);
    }

    public Team getTeamByName(String name) {
        return DBManager.getTeamByName(name);
    }

    public List<Member> getMembersByTeam(String name) {
        return DBManager.getMembersByTeam(name);
    }

    public void removeTeam(int teamId, String name) {
        DBManager.removeTeam(teamId, name);
    }

    public void removeMember(int memberId) {
        DBManager.removeMember(memberId);
    }

}