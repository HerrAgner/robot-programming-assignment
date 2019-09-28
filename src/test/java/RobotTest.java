import com.robot.instructions.Command;
import com.robot.instructions.Direction;
import com.robot.objects.Robot;
import org.junit.Assert;
import org.junit.Test;

public class RobotTest {

    private Robot robot = new Robot(1,1, Direction.EAST);
    private int[] newPosition;

    @Test
    public void robotMovementTest() {
        Assert.assertEquals(robot.getDirection(), Direction.EAST);
        Assert.assertEquals(robot.getPosX(), 1);
        Assert.assertEquals(robot.getPosY(), 1);

        robot.turnDirection(Command.RIGHT);
        Assert.assertEquals(robot.getDirection(), Direction.SOUTH);

        // S S W S
        newPosition = robot.getNewPosition();
        robot.moveToPosition(newPosition);
        Assert.assertSame(robot.getPosY(), 2);

        newPosition = robot.getNewPosition();
        robot.moveToPosition(newPosition);
        Assert.assertSame(robot.getPosY(), 3);

        robot.turnDirection(Command.LEFT);
        newPosition = robot.getNewPosition();
        robot.moveToPosition(newPosition);
        Assert.assertSame(robot.getPosX(), 2);

        robot.turnDirection(Command.RIGHT);
        newPosition = robot.getNewPosition();
        robot.moveToPosition(newPosition);
        Assert.assertSame(robot.getPosY(), 4);

        // right, right, right from south to east
        robot.turnDirection(Command.RIGHT);
        robot.turnDirection(Command.RIGHT);
        robot.turnDirection(Command.RIGHT);
        Assert.assertEquals(robot.getDirection(), Direction.EAST);
    }
}
