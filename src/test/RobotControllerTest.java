import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;

import main.java.com.robot.RobotController;
import org.junit.Assert;
import org.junit.Test;

import org.junit.runners.Parameterized;
import org.junit.runner.RunWith;

@RunWith(Parameterized.class)
public class RobotControllerTest {
    private String input;
    private Boolean expectedResult;
    private RobotController robotController;

    private final static String MOCK_ROOM = "5 5";
    private final static String MOCK_ROBOT = "1 1 E";
    private final static String MOCK_NAV = "FRL";

    public RobotControllerTest(String input, Boolean expectedResult) {
        this.input = input;
        this.expectedResult = expectedResult;
    }

    // Add more commands here to test the robot controller. Room size, robot placement then navigation commands.
    @Parameterized.Parameters
    public static Collection inputCommands() {
        return Arrays.asList(new Object[][]{
                {"5 5\n1 2 N\nRFRFFRFRF", true},
                {"5 5\n0 0 E\nRFLFFLRF", true},
                {"20 20\n2 3 W\nRLRLRL", true},
                {"        50 50  \n  25     25  S\n   F", true},
                {MOCK_ROOM + "\n" + MOCK_ROBOT + "\nD", false},
                {"-3 5\n" + MOCK_ROBOT + "\n" + MOCK_NAV, false},
                {"4 E\n" + MOCK_ROBOT + " \n" + MOCK_NAV, false},
                {"4 4 2\n" + MOCK_ROBOT + " \n" + MOCK_NAV, false},
                {MOCK_ROOM + "\nE 0 E\n" + MOCK_NAV, false},
                {MOCK_ROOM + "\n-3 0 E\n" + MOCK_NAV, false},
                {MOCK_ROOM + "\n0 -4 E\n" + MOCK_NAV, false},
                {MOCK_ROOM + "\n0 0 Z\n" + MOCK_NAV, false},
                {MOCK_ROOM + "\n0 0 E A F\n" + MOCK_NAV, false},
                {"0 0\n0 0 E\n" + MOCK_NAV, false}

        });
    }

    // Test will run with different parameters every time.
    // One time for each object in inputCommands-array
    @Test
    public void testControllerWithParameters() {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        robotController = new RobotController(br);
        System.out.printf("Parameterized inputs are : \n%s\n", input);
        Assert.assertEquals(expectedResult, robotController.run());
    }
}