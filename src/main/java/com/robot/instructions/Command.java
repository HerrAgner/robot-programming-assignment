package com.robot.instructions;

import java.util.HashMap;
import java.util.Map;

public enum Command {
    FORWARD('F'),
    LEFT('L'),
    RIGHT('R');

    private static final Map<Character, Command> CODE = new HashMap<>();

    static {
        for (Command c : values()) {
            CODE.put(c.shortCode, c);
        }
    }

    private final char shortCode;

    Command(char r) {
        this.shortCode = r;
    }

    /**
     * Returns enum based on char
     *
     * @param code Char F, L or R is valid characters
     * @return Enum command based on character
     */
    public static Command getCommandFromChar(char code) {
        return CODE.get(code);
    }
}

