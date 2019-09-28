import com.robot.instructions.Command;
import org.junit.Assert;
import org.junit.Test;

public class CommandTest {

    @Test
    public void getCommandFromShortCode() {
        Assert.assertSame(Command.FORWARD, Command.getCommandFromChar('F'));
        Assert.assertSame(Command.RIGHT, Command.getCommandFromChar('R'));
        Assert.assertSame(Command.LEFT, Command.getCommandFromChar('L'));
        Assert.assertNotSame(Command.FORWARD, Command.getCommandFromChar('L'));
        Assert.assertNull(Command.getCommandFromChar('K'));
        Assert.assertNull(Command.getCommandFromChar('4'));
    }
}
