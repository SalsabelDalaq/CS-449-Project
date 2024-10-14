import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SOSUI {
    private SOSGame game;
    private JFrame frame;
    private JButton[][] buttons;
    private JTextArea currentPlayerLabel;

    public SOSUI() {
        frame = new JFrame("SOS Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Get board size and game mode
        String sizeInput = JOptionPane.showInputDialog("Enter board size (e.g., 5 for a 5x5 board):");
        int size = Integer.parseInt(sizeInput);
        String modeInput = JOptionPane.showInputDialog("Choose game mode (Simple or General):");
        game = new SOSGame(size, modeInput);
        buttons = new JButton[size][size];

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(size, size));

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                buttons[i][j] = new JButton();
                final int row = i;
                final int col = j;
                buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        game.makeMove(row, col);
                        updateBoard();
                    }
                });
                buttonPanel.add(buttons[i][j]);
            }
        }

        currentPlayerLabel = new JTextArea("Current turn: " + game.getCurrentPlayer());
        currentPlayerLabel.setEditable(false);
        frame.add(currentPlayerLabel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    private void updateBoard() {
        char[][] board = game.getBoard();
        for (int i = 0; i < game.getBoardSize(); i++) {
            for (int j = 0; j < game.getBoardSize(); j++) {
                buttons[i][j].setText(String.valueOf(board[i][j]));
            }
        }
        currentPlayerLabel.setText("Current turn: " + game.getCurrentPlayer());
    }

    public static void main(String[] args) {
        new SOSUI();
    }
}
