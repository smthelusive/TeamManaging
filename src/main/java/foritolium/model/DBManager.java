package foritolium.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class DBManager {
    private static SessionFactory sf;
    private static Session session;
    private static Transaction tx;
    private static void init() {
        sf = new Configuration().configure()
                .addAnnotatedClass(Team.class)
                .addAnnotatedClass(Member.class)
                .buildSessionFactory();
        session = sf.openSession();
    }

    public static void createTeam(String name, String description) {
        if (session == null || !session.isOpen()) {
            init();
        }
        tx = session.beginTransaction();
        Team team = new Team();
        team.setName(name);
        team.setDescription(description);
        session.save(team);
        tx.commit();
    }

    public static void createMember(String name, String surname, String skills, Team team) {
        if (session == null || !session.isOpen()) {
            init();
        }
        tx = session.beginTransaction();
        Member member = new Member();
        member.setName(name);
        member.setSurname(surname);
        member.setSkills(skills);
        member.setTeam(session.load(Team.class, team.getTeamId()));
        session.save(member);
        tx.commit();
    }

    @SuppressWarnings("unchecked")
    public static List<Team> listAllTeams() {
        if (session == null || !session.isOpen()) {
            init();
        }
        List<Team> teams = session.createCriteria(Team.class).list();
        return teams;
    }

    @SuppressWarnings("unchecked")
    public static List<Member> listAllMembers() {
        if (session == null || !session.isOpen()) {
            init();
        }
        return session.createCriteria(Member.class).list();
    }

    public static void updateMember(int memberId, String name, String surname,
                                    String skills, Team team) {
        if (session == null || !session.isOpen()) {
            init();
        }
        tx = session.beginTransaction();
        Member member = session.get(Member.class, memberId);
        member.setName(name);
        member.setSurname(surname);
        member.setSkills(skills);
        member.setTeam(session.load(Team.class, team.getTeamId()));
        session.save(member);
        tx.commit();
    }

    public static void updateTeam(int teamId, String name, String description) {
        if (session == null || !session.isOpen()) {
            init();
        }
        tx = session.beginTransaction();
        Team team = session.get(Team.class, teamId);
        team.setName(name);
        team.setDescription(description);
        session.save(team);
        tx.commit();
    }

    public static Team getTeamByName(String name) {
        if (session == null || !session.isOpen()) {
            init();
        }
        return session.get(Team.class, (Integer)session
                .createSQLQuery("select team_id from Team where name = :teamname")
                .setParameter("teamname", name).list().get(0));
    }
    @SuppressWarnings("unchecked")
    public static List<Member> getMembersByTeam(String name) {
        if (session == null || !session.isOpen()) {
            init();
        }
        return session.createSQLQuery("select * from Member where Member.team_id " +
                "in (select team_id from Team where Team.name = :teamname)")
                .setParameter("teamname", name).list();
    }
    public static void removeTeam(int teamId, String name) {
        if (session == null || !session.isOpen()) {
            init();
        }
        for (Member member : getMembersByTeam(name)) {
            updateMember(member.getMemberId(), member.getName(), member.getSurname(),
                    member.getSkills(), null);
        }
        tx = session.beginTransaction();
        Team team = session.get(Team.class, teamId);
        session.delete(team);
        tx.commit();
    }

    public static void removeMember(int memberId) {
        if (session == null || !session.isOpen()) {
            init();
        }
        tx = session.beginTransaction();
        Member member = session.get(Member.class, memberId);
        session.delete(member);
        tx.commit();
    }

    public static void finish() {
        session.close();
        sf.close();
    }
}
