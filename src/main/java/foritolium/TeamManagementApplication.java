package foritolium;

import foritolium.model.DBManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TeamManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeamManagementApplication.class, args);
        //some test data:
        DBManager.createTeam("Winners", "team 1 yeah");
        DBManager.createTeam("SuccessTeam", "the best team ever");
        DBManager.createMember("John", "Snow", "Java", DBManager.getTeamByName("SuccessTeam"));
        DBManager.createMember("Walter", "White", "cooking", DBManager.getTeamByName("SuccessTeam"));
        DBManager.createMember("N", "Dz", "everything", DBManager.getTeamByName("Winners"));
//        DBManager.finish();
    }
}
