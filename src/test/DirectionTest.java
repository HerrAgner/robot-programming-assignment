import main.java.com.robot.instructions.Command;
import main.java.com.robot.instructions.Direction;
import org.junit.Assert;
import org.junit.Test;

public class DirectionTest {
    private Direction direction = Direction.NORTH;
//
    @Test
    public void rotateToAllDirections() {
        direction = direction.rotate(Command.RIGHT);
        Assert.assertSame(direction, Direction.EAST);

        direction = direction.rotate(Command.RIGHT);
        Assert.assertSame(direction, Direction.SOUTH);

        direction = direction.rotate(Command.RIGHT);
        Assert.assertSame(direction, Direction.WEST);

        direction = direction.rotate(Command.RIGHT);
        Assert.assertSame(direction, Direction.NORTH);

        direction = direction.rotate(Command.LEFT);
        Assert.assertSame(direction, Direction.WEST);

        direction = direction.rotate(Command.LEFT);
        Assert.assertSame(direction, Direction.SOUTH);

        direction = direction.rotate(Command.LEFT);
        Assert.assertSame(direction, Direction.EAST);

        direction = direction.rotate(Command.LEFT);
        Assert.assertSame(direction, Direction.NORTH);
    }

    @Test
    public void getDirectionFromShortCode() {
        Assert.assertSame(Direction.NORTH, Direction.getDirectionFromChar('N'));
        Assert.assertSame(Direction.EAST, Direction.getDirectionFromChar('E'));
        Assert.assertSame(Direction.SOUTH, Direction.getDirectionFromChar('S'));
        Assert.assertSame(Direction.WEST, Direction.getDirectionFromChar('W'));
    }
}
