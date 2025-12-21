package com.hautilargi.footman.clubs.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hautilargi.footman.players.model.Player;
import com.hautilargi.footman.util.MatchTypes;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@jakarta.persistence.Entity
public class Team {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Player> players;

    @OneToMany(fetch = FetchType.EAGER, mappedBy="team", cascade = CascadeType.ALL, orphanRemoval = true)
    private Map<MatchTypes,Squad> squads;

    private String name;
    private boolean active;
    private long balance;
    private int tier;
    
    public Team() {
    }

    public Team( String name) {
        this.name = name;
        this.players = new ArrayList<>();
        this.squads=new HashMap<MatchTypes,Squad>();
        this.tier=1;
        this.active=true;
    }
    //TODO Move to Service?
    public void addPlayer(Player player) {
        player.setTeam(this);
        this.players.add(player);
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

    public Map<MatchTypes,Squad> getSquads() {
        return squads;
    }
    public void setSquads( Map<MatchTypes,Squad> squads) {
       this.squads=squads;
    }

            
    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public boolean isActive(){
        return this.active;
    }

    public void setActive(boolean active){
        this.active=active;
    }
    public void setTier(int tier){
        this.tier=tier;
    }
    public int getTier(){
        return this.tier;
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
