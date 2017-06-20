package foritolium.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class Team implements Serializable{
    @Id
    @GenericGenerator(name="team_gen", strategy="increment")
    @GeneratedValue(generator="team_gen")
    @Column(name = "team_id", unique = true, nullable = false)
    private Integer teamId;

    @Column
    private String name;

    @Column
    private String description;

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
