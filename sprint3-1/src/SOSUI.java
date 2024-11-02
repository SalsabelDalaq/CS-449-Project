import javax.swing.*;
import java.awt.*;

public class SOSUI extends JFrame {
    private SOSGame game;
    private JButton[][] buttons;
    private JPanel boardPanel;
    private JComboBox<String> player1ComboBox;
    private JComboBox<String> player2ComboBox;
    private JTextField boardSizeTextField;
    private JComboBox<String> gameModeComboBox;
    private JLabel statusLabel;
    private JLabel turnLabel;

    public SOSUI() {
        setTitle("SOS Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        createPlayerSelectionPanel();
        startNewGame();
    }

    private void createPlayerSelectionPanel() {
        JPanel playerSelectionPanel = new JPanel();
        player1ComboBox = new JComboBox<>(new String[]{"S", "O"});
        player2ComboBox = new JComboBox<>(new String[]{"S", "O"});
        gameModeComboBox = new JComboBox<>(new String[]{"Simple", "General"});
        boardSizeTextField = new JTextField(3);

        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(e -> startNewGame());

        statusLabel = new JLabel("Status: Ongoing");
        turnLabel = new JLabel("Current Player: S");

        playerSelectionPanel.add(new JLabel("Player 1 (Blue):"));
        playerSelectionPanel.add(player1ComboBox);
        playerSelectionPanel.add(new JLabel("Player 2 (Red):"));
        playerSelectionPanel.add(player2ComboBox);
        playerSelectionPanel.add(new JLabel("Game Mode:"));
        playerSelectionPanel.add(gameModeComboBox);
        playerSelectionPanel.add(new JLabel("Board Size:"));
        playerSelectionPanel.add(boardSizeTextField);
        playerSelectionPanel.add(newGameButton);
        playerSelectionPanel.add(statusLabel);
        playerSelectionPanel.add(turnLabel);

        add(playerSelectionPanel, BorderLayout.NORTH);
    }

    private void startNewGame() {
        char player1Symbol = player1ComboBox.getSelectedItem().toString().charAt(0);
        char player2Symbol = player2ComboBox.getSelectedItem().toString().charAt(0);

        if (player1Symbol == player2Symbol) {
            JOptionPane.showMessageDialog(this, "Player 1 and Player 2 cannot have the same symbol.");
            return;
        }

        int boardSize;
        try {
            boardSize = Integer.parseInt(boardSizeTextField.getText());
            if (boardSize < 3) {
                JOptionPane.showMessageDialog(this, "Board size must be at least 3.");
                return;
            }
            game = new SOSGame(boardSize, gameModeComboBox.getSelectedItem().toString(), player1Symbol, player2Symbol);
            resetBoard(boardSize);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid board size! Please enter a number.");
        }
    }

    private void resetBoard(int boardSize) {
        if (boardPanel != null) {
            remove(boardPanel);
        }

        boardPanel = new JPanel(new GridLayout(boardSize, boardSize));
        buttons = new JButton[boardSize][boardSize];

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                buttons[i][j] = new JButton("-");
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                int row = i, col = j;

                // Change button background based on player's turn
                buttons[i][j].addActionListener(e -> makeMove(row, col));
                boardPanel.add(buttons[i][j]);
            }
        }

        add(boardPanel, BorderLayout.CENTER);
        revalidate();
        repaint();

        statusLabel.setText("Status: Ongoing");
        turnLabel.setText("Current Player: " + game.getCurrentPlayer());
    }

    private void makeMove(int row, int col) {
        char playerSymbol = game.getCurrentPlayer();
        if (game.makeMove(row, col)) {
            buttons[row][col].setText(String.valueOf(playerSymbol));
            updateGameStatus();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid move! Try again.");
        }
    }

    private void updateGameStatus() {
        String status = game.getGameStatus();
        statusLabel.setText("Status: " + status);

        if (!status.equals("ongoing")) {
            JOptionPane.showMessageDialog(this, "Game Over! " + status);
            // Restarting the game after it ends could be optional
            // Uncomment the following line if you want to reset the game automatically
            // startNewGame();
        } else {
            turnLabel.setText("Current Player: " + game.getCurrentPlayer());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SOSUI ui = new SOSUI();
            ui.setVisible(true);
        });
    }
}