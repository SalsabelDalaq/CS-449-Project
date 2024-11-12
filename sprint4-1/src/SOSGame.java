import java.util.Random;

public class SOSGame {
    private char currentPlayer;
    private int boardSize;
    private String gameMode;
    private char[][] board;
    private int player1Score;
    private int player2Score;
    private boolean isPlayer1Human;
    private boolean isPlayer2Human;
    private Random random;

    public SOSGame(int boardSize, String gameMode, char player1Symbol, char player2Symbol, boolean isPlayer1Human, boolean isPlayer2Human) {
        this.boardSize = boardSize;
        this.gameMode = gameMode;
        this.currentPlayer = player1Symbol; // Assume player 1 starts
        this.isPlayer1Human = isPlayer1Human;
        this.isPlayer2Human = isPlayer2Human;
        this.random = new Random();
        board = new char[boardSize][boardSize];

        // Initialize the board with empty characters
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = '-'; // Using '-' to indicate an empty space
            }
        }
    }

    public int getBoardSize() {
        return this.boardSize;
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean makeMove(int row, int col) {
        if (row >= 0 && row < boardSize && col >= 0 && col < boardSize && board[row][col] == '-') {
            board[row][col] = currentPlayer;
            currentPlayer = (currentPlayer == 'S') ? 'O' : 'S';
            return true;
        }
        return false;
    }

    public boolean makeComputerMove() {
        int row, col;
        do {
            row = random.nextInt(boardSize);
            col = random.nextInt(boardSize);
        } while (board[row][col] != '-');
        return makeMove(row, col);
    }

    public String getGameStatus() {
        if (boardFull()) return "It's a draw!";
        return "ongoing";
    }

    private boolean boardFull() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] == '-') return false;
            }
        }
        return true;
    }

    public boolean isPlayer1Human() {
        return isPlayer1Human;
    }

    public boolean isPlayer2Human() {
        return isPlayer2Human;
    }
}
