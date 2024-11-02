public class SOSGame {
    private char currentPlayer;
    private int boardSize;
    private String gameMode;
    private char[][] board;
    private int player1Score;
    private int player2Score;

    // Constructor to initialize the game state
    public SOSGame(int boardSize, String gameMode, char player1Symbol, char player2Symbol) {
        this.boardSize = boardSize;
        this.gameMode = gameMode;
        this.currentPlayer = player1Symbol; // Assume player 1 starts
        board = new char[boardSize][boardSize];

        // Initialize the board with empty characters
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = '-'; // Using '-' to indicate an empty space
            }
        }
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean makeMove(int row, int col) {
        // Check if the move is valid (not already occupied)
        if (row >= 0 && row < boardSize && col >= 0 && col < boardSize && board[row][col] == '-') {
            board[row][col] = currentPlayer; // Place the symbol on the board
            
            // Check for SOS and update scores
            if (checkForSOS(row, col)) {
                if (currentPlayer == 'S') {
                    player1Score++;
                } else {
                    player2Score++;
                }
                return true; // Continue the same player's turn
            }

            // Switch players only if no SOS is formed
            currentPlayer = (currentPlayer == 'S') ? 'O' : 'S';
            return true;
        }
        return false; // Invalid move
    }

    private boolean checkForSOS(int row, int col) {
        // Check all directions for "SOS" formations
        return checkDirection(row, col, 1, 0) || // Horizontal
               checkDirection(row, col, 0, 1) || // Vertical
               checkDirection(row, col, 1, 1) || // Diagonal /
               checkDirection(row, col, 1, -1);   // Diagonal \
    }

    private boolean checkDirection(int row, int col, int deltaRow, int deltaCol) {
        // Check for "SOS" in one direction and the opposite
        StringBuilder line = new StringBuilder();
        
        // Collect characters in the specified direction
        for (int i = -2; i <= 2; i++) {
            int newRow = row + deltaRow * i;
            int newCol = col + deltaCol * i;
            if (newRow >= 0 && newRow < boardSize && newCol >= 0 && newCol < boardSize) {
                line.append(board[newRow][newCol]);
            }
        }

        // Check for the exact "SOS" formation
        return line.toString().contains("SOS");
    }

    public String getGameStatus() {
        // Check if the game is won by the current player
        if (boardIsFull()) {
            if (player1Score > player2Score) {
                return "Player 1 wins";
            } else if (player2Score > player1Score) {
                return "Player 2 wins";
            } else {
                return "Draw";
            }
        }
        return "ongoing"; // Game is still ongoing
    }

    private boolean boardIsFull() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] == '-') {
                    return false; // Found an empty space
                }
            }
        }
        return true; 
    }
}