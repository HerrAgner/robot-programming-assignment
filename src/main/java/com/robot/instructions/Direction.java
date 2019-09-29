package com.robot.instructions;

import java.util.HashMap;
import java.util.Map;

public enum Direction {
    NORTH(0, 'N'),
    EAST(1, 'E'),
    SOUTH(2, 'S'),
    WEST(3, 'W');

    private static final Map<Integer, Direction> VALUE = new HashMap<>();
    private static final Map<Character, Direction> CODE = new HashMap<>();

    static {
        for (Direction d : values()) {
            VALUE.put(d.index, d);
            CODE.put(d.shortCode, d);
        }
    }

    private final int index;
    private final char shortCode;

    Direction(int index, char shortCode) {
        this.index = index;
        this.shortCode = shortCode;
    }

    /**
     * Returns enum based on char
     *
     * @param code Char N, E, S or W is valid characters
     * @return Enum command based on character
     */
    public static Direction getDirectionFromChar(char code) {
        return CODE.get(code);
    }

    /**
     * Calculates the new direction based on Command LEFT or RIGHT
     *
     * @param turn Command.LEFT or Command.RIGHT
     * @return Direction based on rotation
     */
    public Direction rotate(Command turn) {
        int currentTurn;
        switch (turn) {
            case RIGHT:
                currentTurn = 1;
                break;
            case LEFT:
                currentTurn = 3;
                break;
            default:
                currentTurn = 0;
                break;
        }
        return VALUE.get((this.index + currentTurn) % 4);
    }
}