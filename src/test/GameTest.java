import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;

import main.java.com.robot.Game;
import org.junit.Assert;
import org.junit.Test;

import org.junit.runners.Parameterized;
import org.junit.runner.RunWith;

@RunWith(Parameterized.class)
public class GameTest {
    private String input;
    private Boolean expectedResult;
    private Game game;

    public GameTest(String input, Boolean expectedResult) {
        this.input = input;
        this.expectedResult = expectedResult;
    }

    // Add more commands here to test the game. Board size, robot placement then navigation commands.
    @Parameterized.Parameters
    public static Collection inputCommands() {
        return Arrays.asList(new Object[][]{
                {"5 5\n1 2 N\nRFRFFRFRF", true},
                {"5 5\n0 0 E\nRFLFFLRF", true},
                {"20 20\n2 3 W\nRLRLRL", true},
                {"        50 50  \n  25     25  S\n   F", true},
                {"1 1\n0 0 W\nD", false},
                {"-3 5\n0 0 E\nR", false},
                {"4 E\n0 0 E\nL", false},
                {"5 5\nE 0 E\nR", false},
                {"5 5\n-3 0 E\nR", false},
                {"5 5\n0 -4 E\nR", false},
                {"5 5\n0 0 Z\nR", false},
                {"5 5\n0 0 E A F\nR", false}

        });
    }

    @Test
    public void testGameWithParameters() {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        game = new Game(br);
        System.out.printf("Parameterized inputs are : \n%s\n", input);
        Assert.assertEquals(expectedResult, game.run());
    }
}