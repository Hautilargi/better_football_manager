package com.hautilargi.footman.players.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.hautilargi.footman.clubs.model.Team;
import com.hautilargi.footman.util.StringUtils;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
@JsonIncludeProperties({ "id","firstName","lastName" })
public abstract class AbstractPlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String lastname;
    protected String firstName;
    protected String nationality;
    protected int skillLevel;
    protected int age = 17;

    @ManyToOne
    @JoinColumn(name = "team_id")
    protected Team team;

    /**
     * Returns an HTML fragment describing this player (no full document).
     * Safe to embed in a page that lists multiple players.
     */
    public String toHtmlFragment() {
        StringBuilder sb = new StringBuilder();
        sb.append("<section class=\"player\">\n");
        sb.append("  <h3>");
        String fullName = (firstName == null ? "" : firstName) + " " + (lastname == null ? "" : lastname);
        sb.append(StringUtils.escapeHtml(fullName.trim().isEmpty() ? "(kein Name)" : fullName.trim()));
        sb.append("  <p>Skill-Level: ").append(skillLevel).append("</p>\n");
        if (team != null) {
            sb.append("  <p>Team: ")
                    .append(StringUtils.escapeHtml(team.getName() == null ? "(kein Team)" : team.getName()))
                    .append("</p>\n");
        }
        sb.append("</section>\n");
        return sb.toString();
    }

    /* GETTERS AND SETTERS */
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getLastName() {
        return lastname;
    }

    public void setLastName(String lastname) {
        this.lastname = lastname;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }

    public Team getTeam() {
        return this.team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String toString() {
        return String.format("{\"id\":%s,\"firstname\":\"%s\",\"lastname\":\"%s\",\"skillLevel\":%s}", firstName,
                lastname, skillLevel);
    }

}
