import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SOSGameTest {
    private SOSGame game;

    @Test
    void testHumanBlueVsComputerRed_SimpleGame() {
        game = new SOSGame(3, "Simple", 'S', 'O', true, false);

        // Human Blue (S) makes a move
        game.makeMove(0, 0); // S (Player 1)

        // Computer Red (O) makes a move
        game.makeComputerMove();

        // Human Blue (S) makes another move
        game.makeMove(0, 1); // S (Player 1)

        // Check that the game is ongoing
        assertEquals("ongoing", game.getGameStatus(), "Game should be ongoing.");
    }

    @Test
    void testComputerBlueVsHumanRed_GeneralGame() {
        game = new SOSGame(4, "General", 'S', 'O', false, true);

        // Computer Blue (S) makes a move
        game.makeComputerMove();

        // Human Red (O) makes a move
        game.makeMove(1, 0); // O (Player 2)

        // Check that the game is ongoing
        assertEquals("ongoing", game.getGameStatus(), "Game should be ongoing.");
    }

    @Test
    void testComputerVsComputer_SimpleGame() {
        game = new SOSGame(3, "Simple", 'S', 'O', false, false);

        // Both players are computers, they make moves
        game.makeComputerMove();
        game.makeComputerMove();
        game.makeComputerMove();
        
        // Check that the game is ongoing (no winner yet)
        assertEquals("ongoing", game.getGameStatus(), "Game should be ongoing.");
    }

    @Test
    void testComputerVsComputer_GeneralGame() {
        game = new SOSGame(4, "General", 'S', 'O', false, false);

        // Both players are computers, they make moves
        game.makeComputerMove();
        game.makeComputerMove();
        game.makeComputerMove();
        
        // Check that the game is ongoing (no winner yet)
        assertEquals("ongoing", game.getGameStatus(), "Game should be ongoing.");
    }

    @Test
    void testComputerMove_MultipleTimes() {
        game = new SOSGame(3, "Simple", 'S', 'O', false, false);

        // Test that the computer makes valid moves
        game.makeComputerMove();
        game.makeComputerMove();
        game.makeComputerMove();

        // Ensure the game is still ongoing
        assertEquals("ongoing", game.getGameStatus(), "The game should be ongoing after multiple computer moves.");
    }
}
