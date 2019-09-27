package com.robot;

import com.robot.instructions.Command;
import com.robot.instructions.Direction;
import com.robot.objects.Board;
import com.robot.objects.Robot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Game {

    private Board board;
    private Robot robot;
    private BufferedReader br;
    private boolean running = true;
    private boolean testMode = false;

    public Game() {
        this.br = new BufferedReader(new InputStreamReader(System.in));
    }

    public Game(Board board) {
        this.br = new BufferedReader(new InputStreamReader(System.in));
        this.board = board;
    }

    /**
     * Only used for testing. Exit condition after validation in methods.
     *
     * @param br Buffered reader with custom instream for testing purposes.
     */
    public Game(BufferedReader br) {
        this.br = br;
        this.testMode = true;
        this.board = new Board(5, 5);
    }

    public boolean run() {
        running = initializeBoard();
        running = initializeRobot();
        running = controlRobot();

        return running;
    }

    /**
     * Initialized the board size with width and height.
     *
     * @return false when you type "quit", otherwise loops until correct values are entered.
     */
    private boolean initializeBoard() {
        boolean boardCheck = false;

        if (!this.running) {
            return false;
        }

        System.out.println("Type \"quit\" to exit.");
        while (!boardCheck) {
            int[] values = null;
            System.out.println("Submit board width and height. Enter two numbers, separate values with a space");

            // Adds input in array, separates input on whitespace characters
            String[] lines = new String[0];
            try {
                lines = this.br.readLine().trim().split("\\s+");
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Exit condition
            if (lines[0].equals("quit")) return false;

            //  Input lines are checked if they contain only numbers, then parsed to int and added to an array.
            if (lines.length == 2 && (lines[0].matches("\\d+") && lines[1].matches("\\d+"))) {
                values = Arrays.stream(lines).mapToInt(Integer::parseInt).toArray();
            } else {
                System.out.println("Input was not positive numbers.");
            }

            // Validation and creation of the board with input values
            if (nullAndValueCheck(values)) {
                board = new Board(values[0], values[1]);
                boardCheck = true;
            } else {
                System.out.println("Incorrect input. Enter two numbers.\n");
                if (testMode) return false;
            }
        }
        return true;
    }

    /**
     * Initialized the robot with starting position and an initial direction.
     *
     * @return false when you type "quit", otherwise loops until correct values are entered.
     */
    private boolean initializeRobot() {
        boolean robotCheck = false;
        int[] values = null;
        Character direction = null;

        if (!this.running) {
            return false;
        }

        // Repeats until everything is validated to true.
        while (!robotCheck) {
            System.out.println("Submit robot starting position and direction. " +
                    "Enter two numbers and a direction (i.e. 5 5 N). " +
                    "Separate values with a space.");

            // Adds input in array, separates input on whitespace characters
            String[] lines = new String[0];
            try {
                lines = this.br.readLine().trim().split("\\s+");
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Exit condition
            if (lines[0].equals("quit")) return false;

            /*
            Checks for exactly 3 inputs.
            First two are checked if they contain only numbers, then parsed to int and added to an array.
            Last input is compared against the Direction-enum. If it exists as a shortCode there, return true.
             */
            if (lines.length == 3) {
                if (lines[0].matches("\\d+") && lines[1].matches("\\d+")) {
                    int[] tempValues = {Integer.parseInt(lines[0]), Integer.parseInt(lines[1])};
                    values = Arrays.stream(tempValues).toArray();
                } else {
                    System.out.println("Input was not positive numbers.");
                }
                if (Direction.getDirectionFromChar(lines[2].charAt(0)) != null) {
                    direction = lines[2].charAt(0);
                } else {
                    System.out.println("Incorrect direction. Try one of the following: N, E, S or W.");
                }
            } else {
                System.out.println("Incorrect number of values.");
            }

            // Validation and creation of the robot with input values
            if (nullAndValueCheck(values) && directionCheck(direction) && isValidMove(values)) {
                robot = new Robot(values[0], values[1], Direction.getDirectionFromChar(direction));
                robotCheck = true;
            } else if (testMode) {
                return false;
            }
        }
        return true;
    }

    /**
     * Input one line with one or many L, R or F to move and turn the robot.
     * L = Turn left
     * R = Turn right
     * F = Walk forward
     */
    private boolean controlRobot() {
        if (!this.running) {
            return false;
        }
        System.out.println(
                "Give directions to the robot:\n" +
                        "L = Turn left\n" +
                        "R = Turn right\n" +
                        "F = Walk forward\n");

        // Adds each character of the input as a value in an array
        char[] commands = new char[0];
        try {
            commands = this.br.readLine().trim().toCharArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Loops through the char-array and moves the robot based each character
        for (char inputChar : commands) {

            // Checks if inputChar is a valid Command
            if (Command.getCommandFromChar(inputChar) != null) {
                switch (Command.getCommandFromChar(inputChar)) {
                    case RIGHT:
                        robot.turnDirection(Command.RIGHT);
                        break;
                    case LEFT:
                        robot.turnDirection(Command.LEFT);
                        break;
                    // Validates new position, then either moves the robot or print out a crash with x,y and direction
                    case FORWARD:
                        int[] newPosition = robot.getNewPosition();
                        if (isValidMove(newPosition)) {
                            robot.moveToPosition(newPosition);
                        } else {
                            System.out.printf("crash facing %s at x: %d, y: %d\n",
                                    robot.getDirection(),
                                    robot.getPosX(),
                                    robot.getPosY());
                            return false;
                        }
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + inputChar);
                }
            } else {
                System.out.printf("Invalid input \'%s\'", inputChar);
                return false;
            }
        }
        // Prints out the robots final location after the command loop
        System.out.println(robot.report());
        return true;
    }

    /**
     * Null and length validation for values
     *
     * @param values Position values. Need exactly two.
     * @return true if values aren't null and at the right length
     */
    private boolean nullAndValueCheck(int[] values) {
        return values != null && values.length == 2;
    }

    /**
     * Null validation for direction
     *
     * @param direction char for direction
     * @return true if direction is not null
     */
    private boolean directionCheck(Character direction) {
        return direction != null;
    }

    /**
     * Returns true if x and y is greater than 0 and less than board length and height
     *
     * @param position new position coordinates
     * @return true if position is in bounds of the game board.
     */
    private boolean isValidMove(int[] position) {
        return position[0] < board.getWidth() && position[0] >= 0 && position[1] < board.getHeight() && position[1] >= 0;
    }
}