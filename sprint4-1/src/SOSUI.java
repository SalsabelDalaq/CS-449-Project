import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class SOSUI extends JFrame {
    private SOSGame game;
    private JButton[][] buttons;
    private JPanel boardPanel;
    private JComboBox<String> player1ComboBox;
    private JComboBox<String> player2ComboBox;
    private JComboBox<String> player1TypeComboBox;
    private JComboBox<String> player2TypeComboBox;
    private JTextField boardSizeTextField;
    private JComboBox<String> gameModeComboBox;
    private JLabel statusLabel;
    private JLabel turnLabel;
    private JLabel player1ScoreLabel;
    private JLabel player2ScoreLabel;

    private int player1Score = 0; // Player 1 score (Blue)
    private int player2Score = 0; // Player 2 score (Red)

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

        // Choose S or O for each player
        player1ComboBox = new JComboBox<>(new String[]{"S", "O"});
        player2ComboBox = new JComboBox<>(new String[]{"S", "O"});
        
        // Choose Human or Computer for each player
        player1TypeComboBox = new JComboBox<>(new String[]{"Human", "Computer"});
        player2TypeComboBox = new JComboBox<>(new String[]{"Human", "Computer"});
        
        gameModeComboBox = new JComboBox<>(new String[]{"Simple", "General"});
        boardSizeTextField = new JTextField(3);

        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(e -> startNewGame());

        statusLabel = new JLabel("Status: Ongoing");
        turnLabel = new JLabel("Current Player: Blue"); // Player 1 (Blue) starts

        // Create score labels for both players
        player1ScoreLabel = new JLabel("Player 1 (Blue) Score: 0");
        player2ScoreLabel = new JLabel("Player 2 (Red) Score: 0");

        playerSelectionPanel.add(new JLabel("Player 1 (Blue):"));
        playerSelectionPanel.add(player1ComboBox);
        playerSelectionPanel.add(new JLabel("Type:"));
        playerSelectionPanel.add(player1TypeComboBox);
        playerSelectionPanel.add(new JLabel("Player 2 (Red):"));
        playerSelectionPanel.add(player2ComboBox);
        playerSelectionPanel.add(new JLabel("Type:"));
        playerSelectionPanel.add(player2TypeComboBox);
        playerSelectionPanel.add(new JLabel("Game Mode:"));
        playerSelectionPanel.add(gameModeComboBox);
        playerSelectionPanel.add(new JLabel("Board Size:"));
        playerSelectionPanel.add(boardSizeTextField);
        playerSelectionPanel.add(newGameButton);
        playerSelectionPanel.add(statusLabel);
        playerSelectionPanel.add(turnLabel);
        playerSelectionPanel.add(player1ScoreLabel);
        playerSelectionPanel.add(player2ScoreLabel);

        add(playerSelectionPanel, BorderLayout.NORTH);
    }

    private void startNewGame() {
        char player1Symbol = player1ComboBox.getSelectedItem().toString().charAt(0); // Blue
        char player2Symbol = player2ComboBox.getSelectedItem().toString().charAt(0); // Red
        boolean isPlayer1Human = player1TypeComboBox.getSelectedItem().toString().equals("Human");
        boolean isPlayer2Human = player2TypeComboBox.getSelectedItem().toString().equals("Human");

        int boardSize;
        try {
            boardSize = Integer.parseInt(boardSizeTextField.getText());
            if (boardSize < 3) {
                JOptionPane.showMessageDialog(this, "Board size must be at least 3.");
                return;
            }

            game = new SOSGame(boardSize, gameModeComboBox.getSelectedItem().toString(), player1Symbol, player2Symbol, isPlayer1Human, isPlayer2Human);
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

                // Make the move when the button is clicked
                buttons[i][j].addActionListener(e -> makeMove(row, col));
                boardPanel.add(buttons[i][j]);
            }
        }

        add(boardPanel, BorderLayout.CENTER);
        revalidate();
        repaint();

        statusLabel.setText("Status: Ongoing");
        turnLabel.setText("Current Player: Blue");
    }

    private void makeMove(int row, int col) {
        if (game.makeMove(row, col)) {
            buttons[row][col].setText(String.valueOf(game.getCurrentPlayer()));
            buttons[row][col].setForeground(game.getCurrentPlayer() == 'S' ? Color.BLUE : Color.RED);
            updateGameStatus();

            if (!game.isPlayer1Human() && game.getCurrentPlayer() == 'S') {
                game.makeComputerMove();
                updateGameStatus();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid move! Try again.");
        }
    }

    private void updateGameStatus() {
        statusLabel.setText("Status: " + game.getGameStatus());
        turnLabel.setText("Current Player: " + game.getCurrentPlayer());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SOSUI().setVisible(true));
    }
}