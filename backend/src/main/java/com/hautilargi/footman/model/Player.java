package com.hautilargi.footman.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@jakarta.persistence.Entity
public class Player {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lastname;
    private String firstName;
    private int skillLevel;
       
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    public Player() {
    }

    public Player( String lastname, String firstName, int skillLevel) {
        this.lastname = lastname;
        this.firstName = firstName;
        this.skillLevel = skillLevel;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return this.team;
    }

    public Long getId() {
        return id;
    }

    /*GETTERS AND SETTERS */
    public void setId(Long id) {
        this.id = id;
    }
    public String getLastName() {
        return lastname;
    }
    public void setLastName(String lastname) {
        this.lastname = lastname;
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


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"id\": ");
        sb.append(id == null ? "null" : id.toString());
        sb.append(", \"lastname\": ");
        if (lastname == null) sb.append("null"); else sb.append('"').append(escapeJson(lastname)).append('"');
        sb.append(", \"firstName\": ");
        if (firstName == null) sb.append("null"); else sb.append('"').append(escapeJson(firstName)).append('"');
        sb.append(", \"skillLevel\": ");
        sb.append(skillLevel);
        sb.append("}");
        return sb.toString();
    }



    /**
     * Returns an HTML fragment describing this player (no full document).
     * Safe to embed in a page that lists multiple players.
     */
    public String toHtmlFragment() {
        StringBuilder sb = new StringBuilder();
        sb.append("<section class=\"player\">\n");
        sb.append("  <h3>");
        String fullName = (firstName == null ? "" : firstName) + " " + (lastname == null ? "" : lastname);
        sb.append(escapeHtml(fullName.trim().isEmpty() ? "(kein Name)" : fullName.trim()));
        sb.append(" (id=").append(id).append(")</h3>\n");
        sb.append("  <p>Skill-Level: ").append(skillLevel).append("</p>\n");
        if (team != null) {
            sb.append("  <p>Team: ").append(escapeHtml(team.getName() == null ? "(kein Team)" : team.getName())).append("</p>\n");
        }
        sb.append("</section>\n");
        return sb.toString();
    }

    private static String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    private static String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

}
