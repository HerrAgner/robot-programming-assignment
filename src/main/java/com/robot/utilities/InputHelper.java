package com.robot.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

public final class InputHelper {

    private InputHelper() {
    }

    /**
     * Splits users input on whitespace and adds each part to an array
     *
     * @return String[] with each part of the user input, split on whitespace
     */
    public static String[] inputLines(BufferedReader br) {
        String[] lines = null;
        try {
            lines = br.readLine().trim().split("\\s+");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            lines = new String[]{"quit"};
        }
        return lines;
    }

    /**
     * Input lines are checked if they contain only valid integers, then parsed to int and added to an array.
     *
     * @param input String array with two values
     * @return Array with two integers
     */
    public static int[] convertInputToIntArray(String[] input, int length) {
        int[] values;
        values = Arrays.stream(input).filter(s -> s.matches("[\\d+]{1,9}")).mapToInt(Integer::parseInt).toArray();

        if (values.length != length) {
            System.out.println("Input was not valid positive integers.");
            return null;
        }
        return values;
    }

}
