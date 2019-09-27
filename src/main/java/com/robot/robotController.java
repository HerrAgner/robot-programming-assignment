package main.java.com.robot;

import main.java.com.robot.instructions.Command;
import main.java.com.robot.instructions.Direction;
import main.java.com.robot.objects.Room;
import main.java.com.robot.objects.Robot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class robotController {

    private Room room;
    private Robot robot;
    private BufferedReader br;
    private boolean running = true;
    private boolean testMode = false;

    public robotController() {
        this.br = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Only used for testing. Exit condition after validation in methods.
     * And custom in-stream for testing values.
     *
     * @param br Buffered reader with custom in-stream for testing purposes.
     */
    public robotController(BufferedReader br) {
        this.br = br;
        this.testMode = true;
        this.room = new Room(5, 5);
    }

    public boolean run() {
        running = initializeRoom();
        running = initializeRobot();
        running = controlRobot();

        return running;
    }

    /**
     * Initialized the room size with width and depth.
     *
     * @return false when you type "quit", otherwise loops until correct values are entered.
     */
    private boolean initializeRoom() {
        boolean roomCheck = false;

        if (!this.running) {
            return false;
        }

        System.out.println("Type \"quit\" to exit.");
        while (!roomCheck) {
            int[] values = null;
            System.out.println("\nSubmit room width and depth. Enter two numbers, separate values with a space.");

            // Adds input in array, separates input on whitespace characters
            String[] lines = new String[0];
            try {
                lines = this.br.readLine().trim().split("\\s+");
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Exit condition
            if (lines[0].equals("quit")) return false;

            if (lines.length == 2) {
                values = convertInputToIntArray(lines);
            } else {
                System.out.println("Incorrect number of values. Enter only two.");
            }

            // Validation and creation of the room with input values
            if (values != null && roomSizeCheck(values[0], values[1])) {
                room = new Room(values[0], values[1]);
                roomCheck = true;
            } else {
                System.out.println("Incorrect input. Enter two numbers.");
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


            // Checks for exactly 3 inputs.
            // First two are checked if they contain only integers.
            if (lines.length == 3) {
                values = convertInputToIntArray(lines);
            } else {
                System.out.println("Incorrect number of values.");
            }

            // Last input is compared against the Direction-enum. If it exists as a shortCode there, return true.
            if (lines[2].length() == 1 && Direction.getDirectionFromChar(lines[2].charAt(0)) != null) {
                direction = lines[2].charAt(0);
            } else {
                System.out.println("Incorrect direction. Try one of the following: N, E, S or W.");
            }

            // Validation and creation of the robot with input values
            if (values != null && direction != null && isValidPosition(values)) {
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
            String input = this.br.readLine().trim();
            // Exit condition
            if (input.equals("quit")) return false;
            commands = input.toCharArray();
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
                        if (isValidPosition(newPosition)) {
                            robot.moveToPosition(newPosition);
                        } else {
                            System.out.printf("Collision facing %s at x: %d, y: %d\n",
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
     * Validates so that no sides of the room is 0
     *
     * @param width width of the room
     * @param depth depth of the room
     * @return true if values are bigger than 0
     */
    private boolean roomSizeCheck(int width, int depth) {
        if (width > 0 && depth > 0) {
            return true;
        } else {
            System.out.println("Room must be wider and deeper than 0.");
            return false;
        }
    }

    /**
     * Returns true if x and y is greater than 0 and less than the room width and depth.
     *
     * @param position new position coordinates
     * @return true if position is in bounds of the room.
     */
    private boolean isValidPosition(int[] position) {
        if (position[0] < room.getWidth() && position[0] >= 0 && position[1] < room.getDepth() && position[1] >= 0)
            return true;
        else {
            System.out.println("Invalid position.");
            return false;
        }
    }

    /**
     * Input lines are checked if they contain only valid integers, then parsed to int and added to an array.
     *
     * @param input String array with two values
     * @return Array with two integers
     */
    private int[] convertInputToIntArray(String[] input) {
        int[] values = null;
        if (input[0].matches("\\d+") && input[1].matches("\\d+")) {
            try {
                values = Arrays.stream(input).filter(s -> s.matches("\\d+")).mapToInt(Integer::parseInt).toArray();
            } catch (NumberFormatException e) {
                System.out.println("Input is too long.");
            }
        } else {
            System.out.println("Input was not valid positive integers.");
        }
        return values;
    }
}