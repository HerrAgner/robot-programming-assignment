package com.robot.utilities;

import com.robot.objects.Room;

public final class ValidationHelper {

    private ValidationHelper() {
    }

    /**
     * Validates so that no sides of the room is 0
     *
     * @param width width of the room
     * @param depth depth of the room
     * @return true if values are bigger than 0
     */
    public static boolean roomSizeCheck(int width, int depth) {
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
    public static boolean isValidPosition(int[] position, Room room) {
        if (position[0] < room.getWidth() && position[0] >= 0 && position[1] < room.getDepth() && position[1] >= 0)
            return true;
        else {
            System.out.println("Invalid position");
            return false;
        }
    }
}
