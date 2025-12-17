package com.hautilargi.footman.core;


import java.util.Random;

import com.hautilargi.footman.model.Player;
import com.hautilargi.footman.model.Team;

public class MatchProcessor {

   private static final Random RANDOM = new Random();

    public static int[] processMatch(Team teamA,Team teamB, boolean heimvorteilA) {
        double staerkeA = durchschnittsStaerke(teamA);
        double staerkeB = durchschnittsStaerke(teamB);

        if (heimvorteilA) {
            staerkeA *= 1.05; // +5% Heimvorteil
        }

        int toreA = berechneTore(staerkeA, staerkeB);
        int toreB = berechneTore(staerkeB, staerkeA);

        return new int[]{toreA, toreB};
    }

    private static int berechneTore(double eigeneStaerke, double gegnerStaerke) {
        int chancen = 5 + RANDOM.nextInt(6); // 5–10 Torchancen
        int tore = 0;

        for (int i = 0; i < chancen; i++) {
            double basisWahrscheinlichkeit = eigeneStaerke / (eigeneStaerke + gegnerStaerke);
            double zufall = RANDOM.nextDouble() * 0.4 + 0.8; // Upset-Faktor (0.8–1.2)

            if (basisWahrscheinlichkeit * zufall > 0.55) {
                tore++;
            }
        }

        return Math.min(tore, 5); // max. 5 Tore
    }

    private static double durchschnittsStaerke(Team team) {
        return team.getPlayers().stream()
                   .mapToInt(Player::getSkillLevel)
                   .average()
                   .orElse(50);
    }
}

