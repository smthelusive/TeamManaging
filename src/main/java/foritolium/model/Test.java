package foritolium.model;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        DBManager.createTeam("Team1", "team 1 yeah");
        DBManager.createTeam("Team2", "the best team ever");
        DBManager.createMember("Walter", "White", "cooking", DBManager.getTeamByName("Team2"));
        DBManager.createMember("Walt", "Junior", "walking", DBManager.getTeamByName("Team2"));

        List<Member> members = DBManager.listAllMembers();
        for (Member member : members) {
            System.out.println("Name: " + member.getName() +
            ", Surname: " + member.getSurname() +
            ", Skills: " + member.getSkills() +
            ", Team: " + member.getTeam().getName());
        }
        DBManager.finish();
    }
}
