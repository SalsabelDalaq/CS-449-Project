public class SOSGame {
    private int boardSize;
    private char[][] board;

    // Method to set the board size
    public void setBoardSize(int size) {
        this.boardSize = size;
        this.board = new char[boardSize][boardSize];
    }

    public int getBoardSize() {
        return boardSize;
    }

    public char[][] getBoard() {
        return board;
    }
}

