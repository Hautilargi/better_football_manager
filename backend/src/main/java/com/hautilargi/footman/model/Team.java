package com.hautilargi.footman.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@jakarta.persistence.Entity
public class Team {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Player> players;

    public Team() {
    }

    public Team( String name) {
        this.name = name;
        this.players = new ArrayList<>();
    }
    /*GETTERS AND SETTERS */
    public Long getId() {
        return id;
    }
    public void setId(Long id) {    
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
   public void setPlayers(List<Player> players) {
        this.players = players;
    }
    public List<Player> getPlayers() {
        return this.players;
    }

    public void addPlayer(Player player) {
        this.players.add(player);
        player.setTeam(this);
    }

    public String toHtmlString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<section class=\"team\">\n");
        sb.append("  <h2>");
        sb.append(escapeHtml(name == null ? "(no name)" : name));
        sb.append(" (id=").append(id).append(")</h2>\n");
        sb.append("  <ul>\n");
        if (players != null && !players.isEmpty()) {
            for (Player p : players) {
                sb.append("    <li>");
                String playerName = (p.getFirstName() == null ? "" : p.getFirstName()) + " " + (p.getLastName() == null ? "" : p.getLastName());
                sb.append(escapeHtml(playerName.trim()));
                sb.append(" — Skill: ").append(p.getSkillLevel());
                sb.append("</li>\n");
            }
        } else {
            sb.append("    <li><em>Keine Spieler</em></li>\n");
        }
        sb.append("  </ul>\n");
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"id\": ");
        sb.append(id == null ? "null" : id.toString());
        sb.append(", \"name\": ");
        if (name == null) {
            sb.append("null");
        } else {
            sb.append('"').append(escapeJson(name)).append('"');
        }
        sb.append("}");
        return sb.toString();
    }

    
}
