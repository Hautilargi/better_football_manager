package com.hautilargi.footman.model;

public class MatchEvent {

    public enum Type { GOAL, YELLOW, RED }

    private int minute;
    private Type type;
    private Player player;
    private String description;

    public MatchEvent(int minute, Type type, Player player, String description) {
        this.minute = minute;
        this.type = type;
        this.player = player;
        this.description = description;
    }

    public int getMinute() {
        return minute;
    }   
    public Type getType() {
        return type;
    }       
    public Player getPlayer() {
        return player;
    }   
    public String getDescription() {
        return description;
    }
    @Override
    public String toString() {
        return "Minute: " + minute + ", Type: " + type + ", Player: " + player.getFirstName() + " " + player.getLastName() + ", Description: " + description;
    }
}