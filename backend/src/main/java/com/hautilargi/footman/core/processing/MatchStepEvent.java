package com.hautilargi.footman.core.processing;
import com.hautilargi.footman.matches.model.MatchEvent;
import com.hautilargi.footman.matches.model.MatchEvent.Type;

public class MatchStepEvent {

    private int sideChange;
    private int zoneChange;
    private int timeTaken;
    private boolean playContinued;
    private MatchEvent.Type triggeredEvent;
    private boolean playerChange;
    private ComplexMatchProcessor.StepResult result;
    
    

    public MatchStepEvent(int sideChange, int zoneChange, int timeTaken, boolean playContinued, Type triggeredEvent,
            boolean playerChange, ComplexMatchProcessor.StepResult result) {
        this.sideChange = sideChange;
        this.zoneChange = zoneChange;
        this.timeTaken = timeTaken;
        this.playContinued = playContinued;
        this.triggeredEvent = triggeredEvent;
        this.playerChange = playerChange;
        this.result=result;
    }


    
    public int getSideChange() {
        return sideChange;
    }
    public void setSideChange(int sideChange) {
        this.sideChange = sideChange;
    }
    public int getZoneChange() {
        return zoneChange;
    }
    public void setZoneChange(int zoneChange) {
        this.zoneChange = zoneChange;
    }
    public boolean isPlayContinued() {
        return playContinued;
    }
    public void setPlayContinued(boolean playContinued) {
        this.playContinued = playContinued;
    }
    public MatchEvent.Type getTriggeredEvent() {
        return triggeredEvent;
    }
    public void setTriggeredEvent(MatchEvent.Type triggeredEvent) {
        this.triggeredEvent = triggeredEvent;
    }
    public boolean isPlayerChange() {
        return playerChange;
    }
    public void setPlayerChange(boolean playerChange) {
        this.playerChange = playerChange;
    }
    public int getTimeTaken() {
        return timeTaken;
    }
    public void setTimeTaken(int timeTaken) {
        this.timeTaken = timeTaken;
    }



    public ComplexMatchProcessor.StepResult getResult() {
        return result;
    }



    public void setResult(ComplexMatchProcessor.StepResult result) {
        this.result = result;
    }

    
}