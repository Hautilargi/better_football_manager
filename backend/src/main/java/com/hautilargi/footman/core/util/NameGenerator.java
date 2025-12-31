package com.hautilargi.footman.core.util;

import java.util.Random;

public class NameGenerator {

    private static final String[] FIRST_NAMES = {
        "Max", "Anna", "Lukas", "Marie", "Paul",
        "Laura", "Jonas", "Sophia", "Leon", "Emma"
    };

    private static final String[] LAST_NAMES = {
        "Müller", "Schmidt", "Schneider", "Fischer", "Weber",
        "Meyer", "Wagner", "Becker", "Hoffmann", "Koch"
    };

    private static final Random RANDOM = new Random();

    public static String getFirstName() {
        return FIRST_NAMES[RANDOM.nextInt(FIRST_NAMES.length)];
    }

    public static String getLastName() {
        return LAST_NAMES[RANDOM.nextInt(LAST_NAMES.length)];
    }
}
