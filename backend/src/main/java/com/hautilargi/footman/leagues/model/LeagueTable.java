package com.hautilargi.footman.leagues.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeagueTable {
    

    private final List<LeagueTableEntry> tableEntries = new ArrayList<>();

    public void teamHinzufuegen(LeagueTableEntry tableEntry) {
        tableEntries.add(tableEntry);
    }

    public void spielEintragen(LeagueTableEntry heim, LeagueTableEntry gast, int toreHeim, int toreGast) {
        heim.spielErgebnis(toreHeim, toreGast);
        gast.spielErgebnis(toreGast, toreHeim);
    }

    private void sortiere() {
        Collections.sort(tableEntries);
    }

    public List<LeagueTableEntry> getTableEntries(){
        return this.tableEntries;
    }

    public void printToConsole() {
        sortiere();
        System.out.println("=== Tabelle ===");
        int platz = 1;
        for (LeagueTableEntry team : tableEntries) {
            System.out.printf("%2d. %s%n", platz++, team);
        }
    }

}
