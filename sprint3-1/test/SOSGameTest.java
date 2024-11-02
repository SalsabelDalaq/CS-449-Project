import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SOSGameTest {
    private SOSGame game;

    @Test
    void testPlayerOneWinsWithSOS() {
        game = new SOSGame(3, "Simple", 'S', 'O');
        game.makeMove(0, 0); // S (Player 1)
        game.makeMove(1, 0); // S (Player 2) - irrelevant move
        game.makeMove(0, 1); // O (Player 1)
        game.makeMove(1, 1); // O (Player 2) - irrelevant move
        game.makeMove(0, 2); // S (Player 1) - forms "SOS"

        assertEquals("Player 1 wins", game.getGameStatus(), "Player 1 should win with a horizontal SOS.");
    }

    @Test
    void testPlayerTwoWinsWithMultipleSOS() {
        game = new SOSGame(4, "General", 'S', 'O');
        game.makeMove(0, 0); // S (Player 1)
        game.makeMove(0, 1); // S (Player 2)
        game.makeMove(0, 2); // O (Player 1)
        game.makeMove(1, 0); // S (Player 2)
        game.makeMove(1, 1); // O (Player 1) - irrelevant move
        game.makeMove(1, 2); // S (Player 2) - forms SOS, continues turn
        game.makeMove(2, 0); // O (Player 1) - irrelevant move
        game.makeMove(2, 1); // S (Player 2)
        game.makeMove(2, 2); // O (Player 1) - irrelevant move
        game.makeMove(2, 3); // S (Player 2) - forms another SOS

        assertEquals("Player 2 wins", game.getGameStatus(), "Player 2 should win with two SOS formations.");
    }

    @Test
    void testDrawGameWithEqualSOS() {
        game = new SOSGame(3, "Simple", 'S', 'O');
        game.makeMove(0, 0); // S (Player 1)
        game.makeMove(0, 1); // O (Player 2)
        game.makeMove(1, 0); // S (Player 1)
        game.makeMove(1, 1); // O (Player 2)
        game.makeMove(0, 2); // S (Player 1) - forms SOS
        game.makeMove(2, 0); // S (Player 2) - forms SOS

        // Now board is full and each player has 1 SOS
        assertEquals("Draw", game.getGameStatus(), "The game should be a draw as both players have the same number of SOS.");
    }

    @Test
    void testInvalidMove() {
        game = new SOSGame(3, "Simple", 'S', 'O');
        game.makeMove(0, 0); // S (Player 1)
        assertFalse(game.makeMove(0, 0), "The move should be invalid, position already taken.");
    }
}