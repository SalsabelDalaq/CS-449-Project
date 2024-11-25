import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class SOSGameTest {
    private SOSGame game;

    @Test
    void testHumanVsHuman_SimpleGame() {
        game = new SOSGame(3, "Simple", 'S', 'O', true, true);

        // Human Blue (S) moves
        game.makeMove(0, 0); // S
        game.makeMove(0, 1); // O
        game.makeMove(1, 1); // S
        game.makeMove(1, 2); // O
        game.makeMove(2, 2); // S

        // Save and load the game log
        try {
            game.saveGameLog("simple_human_vs_human.txt");
            SOSGame loadedGame = new SOSGame(3, "Simple", 'S', 'O', true, true);
            loadedGame.loadGameLog("simple_human_vs_human.txt");

            assertEquals(game.getMoveLog(), loadedGame.getMoveLog(), "Move logs should match after saving and loading.");
        } catch (IOException e) {
            fail("Exception occurred during save/load: " + e.getMessage());
        }
    }

    @Test
    void testHumanVsHuman_GeneralGame_Replay() {
        game = new SOSGame(4, "General", 'S', 'O', true, true);

        // Human Blue (S) moves
        game.makeMove(0, 0); // S
        game.makeMove(0, 1); // O
        game.makeMove(1, 1); // S
        game.makeMove(2, 2); // O

        // Replay the game
        try {
            game.saveGameLog("general_human_vs_human.txt");
            SOSGame replayGame = new SOSGame(4, "General", 'S', 'O', true, true);
            replayGame.loadGameLog("general_human_vs_human.txt");

            assertEquals(game.getMoveLog(), replayGame.getMoveLog(), "Move logs should match during replay.");
        } catch (IOException e) {
            fail("Exception occurred during replay: " + e.getMessage());
        }
    }

    @Test
    void testHumanVsComputer_SimpleGame_Replay() {
        game = new SOSGame(3, "Simple", 'S', 'O', true, false);

        // Human Blue (S) moves
        game.makeMove(0, 0); // S
        game.makeMove(0, 1); // O (Computer)

        // Replay the game
        try {
            game.saveGameLog("simple_human_vs_computer.txt");
            SOSGame replayGame = new SOSGame(3, "Simple", 'S', 'O', true, false);
            replayGame.loadGameLog("simple_human_vs_computer.txt");

            assertEquals(game.getMoveLog(), replayGame.getMoveLog(), "Move logs should match during replay.");
        } catch (IOException e) {
            fail("Exception occurred during replay: " + e.getMessage());
        }
    }

    @Test
    void testComputerVsComputer_GeneralGame_Replay() {
        game = new SOSGame(4, "General", 'S', 'O', false, false);

        // Simulate computer moves
        game.makeMove(0, 0); // Computer 1
        game.makeMove(0, 1); // Computer 2
        game.makeMove(1, 0); // Computer 1

        // Replay the game
        try {
            game.saveGameLog("general_computer_vs_computer.txt");
            SOSGame replayGame = new SOSGame(4, "General", 'S', 'O', false, false);
            replayGame.loadGameLog("general_computer_vs_computer.txt");

            assertEquals(game.getMoveLog(), replayGame.getMoveLog(), "Move logs should match during replay.");
        } catch (IOException e) {
            fail("Exception occurred during replay: " + e.getMessage());
        }
    }

    @Test
    void testComputerVsComputer_SimpleGame_Record() {
        game = new SOSGame(3, "Simple", 'S', 'O', false, false);

        // Simulate computer moves
        game.makeMove(0, 0); // Computer 1
        game.makeMove(0, 1); // Computer 2

        // Save the game log
        try {
            game.saveGameLog("simple_computer_vs_computer.txt");
        } catch (IOException e) {
            fail("Exception occurred during save: " + e.getMessage());
        }
    }
}
