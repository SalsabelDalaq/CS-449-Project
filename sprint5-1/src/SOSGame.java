import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SOSGame {
    private List<String> moveLog;  // To store moves as strings
    private int boardSize;
    private char[][] board;
    private boolean isPlayer1Human, isPlayer2Human;
    private String gameMode;

    public SOSGame(int boardSize, String gameMode, char player1Symbol, char player2Symbol, boolean isPlayer1Human, boolean isPlayer2Human) {
        this.boardSize = boardSize;
        this.board = new char[boardSize][boardSize];
        this.moveLog = new ArrayList<>();
        this.isPlayer1Human = isPlayer1Human;
        this.isPlayer2Human = isPlayer2Human;
        this.gameMode = gameMode;

        // Initialize the board with empty cells
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = '-';
            }
        }
    }

    public boolean makeMove(int row, int col) {
        if (row < 0 || col < 0 || row >= boardSize || col >= boardSize || board[row][col] != '-') {
            return false;  // Invalid move
        }

        char currentPlayer = getCurrentPlayer();
        board[row][col] = currentPlayer;

        // Log the move
        moveLog.add(currentPlayer + " " + row + " " + col);

        return true;
    }

    public List<String> getMoveLog() {
        return new ArrayList<>(moveLog);
    }

    public void saveGameLog(String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String move : moveLog) {
                writer.write(move);
                writer.newLine();
            }
        }
    }

    public void loadGameLog(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            moveLog.clear();
            String line;
            while ((line = reader.readLine()) != null) {
                moveLog.add(line);
                String[] parts = line.split(" ");
                char player = parts[0].charAt(0);
                int row = Integer.parseInt(parts[1]);
                int col = Integer.parseInt(parts[2]);
                board[row][col] = player;
            }
        }
    }

    public int getBoardSize() {
        return boardSize;
    }

    public char getCurrentPlayer() {
        // Alternate players based on the move log size
        return moveLog.size() % 2 == 0 ? 'S' : 'O';
    }

    public void checkGameStatus() {
        // Dummy implementation for game status check
    }

    public boolean isPlayer1Human() {
        return isPlayer1Human;
    }

    public boolean isPlayer2Human() {
        return isPlayer2Human;
    }
}
