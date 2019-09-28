package com.robot.objects;

import com.robot.instructions.Command;
import com.robot.instructions.Direction;

public class Robot {

    private int posX;
    private int posY;
    private Direction direction;

    public Robot(int startX, int startY, Direction startDirection) {
        posX = startX;
        posY = startY;
        direction = startDirection;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public Direction getDirection() {
        return direction;
    }

    private void setPosX(int posX) {
        this.posX = posX;
    }

    private void setPosY(int posY) {
        this.posY = posY;
    }

    private void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Turns the robot to either left or right.
     *
     * @param turn Command.RIGHT or Command.LEFT
     */
    public void turnDirection(Command turn) {
        setDirection(getDirection().rotate(turn));
    }

    /**
     * Get a new location based on the position and direction the robot is facing.
     *
     * @return new position
     */
    public int[] getNewPosition() {
        int newX = getPosX();
        int newY = getPosY();

        switch (getDirection()) {
            case NORTH:
                newY--;
                break;
            case EAST:
                newX++;
                break;
            case SOUTH:
                newY++;
                break;
            case WEST:
                newX--;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + getDirection());
        }
        return new int[]{newX, newY};
    }

    /**
     * Moves the robot specified location.
     *
     * @param coordinates  Need exactly two int values
     */
    public void moveToPosition(int[] coordinates) {
        if (coordinates.length != 2) return;

        setPosX(coordinates[0]);
        setPosY(coordinates[1]);
    }

    /**
     * Reports the robots location and direction
     * @return String with info about position and direction
     */
    public String report() {
        return String.format("Report: %d %d %s\n", getPosX(), getPosY(), getDirection());
    }
}
