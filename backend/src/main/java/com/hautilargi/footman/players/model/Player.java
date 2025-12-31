package com.hautilargi.footman.players.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.hibernate.annotations.Cascade;

import com.hautilargi.footman.clubs.model.Squad;
import com.hautilargi.footman.core.util.PlayerStatus;
import com.hautilargi.footman.core.util.Positions;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@jakarta.persistence.Entity
@Inheritance(strategy = jakarta.persistence.InheritanceType.TABLE_PER_CLASS)
public class Player extends AbstractPlayer {

    private static final int POSITIONBIASPOSITIVE=10;
    private static final int POSITIONBIASNEGATIVE=-10;
    private static final int MINSKILL=10;


    @ManyToMany(mappedBy = "squadMembers")
    private Set<Squad> squads;

    @ElementCollection ( fetch = FetchType.EAGER)
    private Map<Positions,Integer> positionStrenght;

    @OneToOne (cascade = CascadeType.ALL)
    private PlayerStats stats;

    private long salery;
    private PlayerStatus playerStatus;
    private int talent;

    private int stamina;
    private int speed;
    private int passing;
    private int shooting;
    private int defense;
    private int dribbling;
    private int intelligence;
    private int goalkeeping;

    public Player() {
        this.squads = new HashSet<>();
    }

    public Player(String lastname, String firstName, int skillLevel, int age, Positions preferredPosition) {
        this.squads = new HashSet<>();
        this.lastname = lastname;
        this.firstName = firstName;
        this.skillLevel = skillLevel;
        this.age = age;
        this.salery = 1000;
        this.nationality = "de";
        this.playerStatus = PlayerStatus.ACTIVE;
        this.talent=skillLevel;
        setInitialSkillsForPosition(preferredPosition, skillLevel);
        this.stats=new PlayerStats();
        positionStrenght=new HashMap<>();
        for (Positions position : Positions.values()) { 
            if(position.equals(preferredPosition)){
                positionStrenght.put(position, 100);
            }
            else{
                positionStrenght.put(position, 10);
            }
        }
    }

    // TODO Move Logic to service?
    private void setInitialSkillsForPosition(Positions position, int skillTarget) {
        Random random = new Random();
        switch (position) {
            case GOALKEEPER:
                this.goalkeeping = skillTarget + random.nextInt(-5, 5);
                this.stamina = skillTarget + random.nextInt(-5, 5);
                this.passing = MINSKILL  + random.nextInt(-5, 5);
                this.shooting = MINSKILL  + random.nextInt(-5, 5);;
                this.defense = MINSKILL  + random.nextInt(-5, 5);
                this.dribbling = MINSKILL  + random.nextInt(-5, 5);
                this.speed = MINSKILL  + random.nextInt(-5, 5);
                this.intelligence = MINSKILL  + random.nextInt(-5, 5);
                break;
            case DEFENDER:
                this.goalkeeping = MINSKILL  + random.nextInt(-5, 5);
                this.stamina = skillTarget + random.nextInt(-5, 5);
                this.passing = skillTarget + random.nextInt(-5, POSITIONBIASPOSITIVE+11);
                this.shooting = skillTarget + random.nextInt(POSITIONBIASNEGATIVE-5, 0);
                this.defense = skillTarget + random.nextInt(-5, POSITIONBIASPOSITIVE+11);
                this.dribbling = skillTarget + random.nextInt(POSITIONBIASNEGATIVE-5, 0);
                this.speed = skillTarget + random.nextInt(-5, 5);
                this.intelligence = skillTarget + random.nextInt(-5, 5);
                break;
            case MIDFIELDER:
                this.goalkeeping = MINSKILL  + random.nextInt(-5, 5);
                this.stamina = skillTarget + random.nextInt(-5, 5);
                this.passing = skillTarget + random.nextInt(-5, POSITIONBIASPOSITIVE+11);
                this.shooting = skillTarget + random.nextInt(POSITIONBIASNEGATIVE-5, 0);
                this.defense = skillTarget + random.nextInt(POSITIONBIASNEGATIVE-5, 0);
                this.dribbling =  skillTarget + random.nextInt(-5, 5);
                this.speed = skillTarget + random.nextInt(-5, 5);
                this.intelligence = skillTarget + random.nextInt(-5, POSITIONBIASPOSITIVE+11);
                break;
            case STRIKER:
                this.goalkeeping = MINSKILL  + random.nextInt(-5, 5);
                this.stamina = skillTarget + random.nextInt(-5, 5);
                this.passing = skillTarget + random.nextInt(POSITIONBIASNEGATIVE-5, 0);
                this.shooting = skillTarget + random.nextInt(-5, POSITIONBIASPOSITIVE+11);
                this.defense =  skillTarget + random.nextInt(POSITIONBIASNEGATIVE-5, 0);
                this.dribbling = skillTarget + random.nextInt(-5, POSITIONBIASPOSITIVE+11);
                this.speed = skillTarget + random.nextInt(-5, 5);
                this.intelligence = skillTarget + random.nextInt(-5, 5);
                break;
            default:
                break;
        }
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getPassing() {
        return passing;
    }

    public void setPassing(int passing) {
        this.passing = passing;
    }

    public int getShooting() {
        return shooting;
    }

    public void setShooting(int shooting) {
        this.shooting = shooting;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getDribbling() {
        return dribbling;
    }

    public void setDribbling(int dribbling) {
        this.dribbling = dribbling;
    }

    public long getSalery() {
        return this.salery;
    }

    public void setSalery(long salery) {
        this.salery = salery;
    }

    public void setSquads(Set<Squad> squads) {
        this.squads = squads;
    }

    public Set<Squad> getSquads() {
        return this.squads;
    }

    public void setPlayerStatus(PlayerStatus newStatus) {
        this.playerStatus = newStatus;
    }

    public PlayerStatus getPlayerStatus() {
        return this.playerStatus;
    }

    public int getGoalkeeping() {
        return goalkeeping;
    }

    public void setGoalkeeping(int goalkeeping) {
        this.goalkeeping = goalkeeping;
    }

    public int getTalent() {
        return talent;
    }

    public void setTalent(int talent) {
        this.talent = talent;
    }

    public static int getMinskill() {
        return MINSKILL;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public Map<Positions, Integer> getPositionStrenght() {
        return positionStrenght;
    }

    public void setPositionStrenght(Map<Positions, Integer> positionStrenght) {
        this.positionStrenght = positionStrenght;
    }

    public PlayerStats getStats() {
        return stats;
    }

    public void setStats(PlayerStats stats) {
        this.stats = stats;
    }


    //Helpers
    public void increaseReds(){
        this.getStats().setRed(this.getStats().getRed()+1);
        this.getStats().setPlayerStatus(PlayerStatus.SUSPENDED);
        this.getStats().setRemainingDaysForStatus(3);
    }

    public void increseYellows(){
        this.getStats().setYellow(this.getStats().getYellow()+1);
    }
    
    public void increseGoals(){
        this.getStats().setGoals(this.getStats().getGoals()+1);
    }

    public void increseGamesPlayed(){
        this.getStats().setGamesPlayed(this.getStats().getGamesPlayed()+1);
    }
}
