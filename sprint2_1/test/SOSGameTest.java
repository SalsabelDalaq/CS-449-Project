import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class SOSGameTest {
    private SOSGame game;

    @Before
    public void setUp() {
        game = new SOSGame(5, "Simple"); // Create a 5x5 board in Simple mode
    }

    @Test
    public void testInitialBoard() {
        char[][] expectedBoard = new char[5][5];
        assertArrayEquals(expectedBoard, game.getBoard());
    }

    @Test
    public void testMakeMove() {
        game.makeMove(0, 0);
        assertEquals('O', game.getCurrentPlayer());
        assertEquals('S', game.getBoard()[0][0]);
    }

    @Test
    public void testSwitchPlayer() {
        game.makeMove(0, 0); // S's turn
        game.makeMove(1, 0); // O's turn
        assertEquals('S', game.getCurrentPlayer());
    }

    @Test
    public void testGameMode() {
        assertEquals("Simple", game.getGameMode());
    }

    @Test
    public void testInvalidMove() {
        game.makeMove(0, 0);
        game.makeMove(0, 0); // Attempting to make a move on the same spot
        assertEquals('O', game.getCurrentPlayer()); // Current player should not change
    }
}
