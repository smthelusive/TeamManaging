package foritolium.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class Member implements Serializable{
    @Id
    @GenericGenerator(name="member_gen", strategy="increment")
    @GeneratedValue(generator="member_gen")
    @Column(unique = true, nullable = false)
    private Integer memberId;

    private String name;

    @Column
    private String surname;

    @Column
    private String skills;

    @ManyToOne
    @JoinColumn(name="team_id")
    private Team team;

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Integer getMemberId() {
        return memberId;
    }
}
