import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

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

    public SOSUI() {
        // Set up the JFrame settings
        setTitle("SOS Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the player selection panel
        createPlayerSelectionPanel();

        // Start a new game
        startNewGame();

        // Set the JFrame to be visible
        setVisible(true);
    }

    private void createPlayerSelectionPanel() {
        JPanel playerSelectionPanel = new JPanel();

        // Player selections for symbols and types
        player1ComboBox = new JComboBox<>(new String[]{"S", "O"});
        player2ComboBox = new JComboBox<>(new String[]{"S", "O"});
        player1TypeComboBox = new JComboBox<>(new String[]{"Human", "Computer"});
        player2TypeComboBox = new JComboBox<>(new String[]{"Human", "Computer"});

        gameModeComboBox = new JComboBox<>(new String[]{"Simple", "General"});
        boardSizeTextField = new JTextField(3);

        // Buttons for starting a new game, saving, and replaying
        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(e -> startNewGame());

        JButton saveGameButton = new JButton("Save Game");
        saveGameButton.addActionListener(e -> saveGame());

        JButton loadGameButton = new JButton("Load Game");
        loadGameButton.addActionListener(e -> loadGame());

        JButton replayButton = new JButton("Replay Game");
        replayButton.addActionListener(e -> replayGame());

        // Labels for game status and scores
        statusLabel = new JLabel("Status: Ongoing");
        turnLabel = new JLabel("Current Player: Blue");
        player1ScoreLabel = new JLabel("Player 1 (Blue) Score: 0");
        player2ScoreLabel = new JLabel("Player 2 (Red) Score: 0");

        // Adding components to the panel
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
        playerSelectionPanel.add(saveGameButton);
        playerSelectionPanel.add(loadGameButton);
        playerSelectionPanel.add(replayButton);
        playerSelectionPanel.add(statusLabel);
        playerSelectionPanel.add(turnLabel);
        playerSelectionPanel.add(player1ScoreLabel);
        playerSelectionPanel.add(player2ScoreLabel);

        // Adding the panel to the top of the frame
        add(playerSelectionPanel, BorderLayout.NORTH);
    }

    private void startNewGame() {
        int boardSize;
        try {
            boardSize = Integer.parseInt(boardSizeTextField.getText());
            if (boardSize < 3) {
                JOptionPane.showMessageDialog(this, "Board size must be at least 3.");
                return;
            }

            game = new SOSGame(boardSize, gameModeComboBox.getSelectedItem().toString(),
                    player1ComboBox.getSelectedItem().toString().charAt(0),
                    player2ComboBox.getSelectedItem().toString().charAt(0),
                    player1TypeComboBox.getSelectedItem().toString().equals("Human"),
                    player2TypeComboBox.getSelectedItem().toString().equals("Human"));

            resetBoard(boardSize);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid board size! Please enter a valid number.");
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
                buttons[i][j].addActionListener(e -> makeMove(row, col));
                boardPanel.add(buttons[i][j]);
            }
        }

        add(boardPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void makeMove(int row, int col) {
        if (game.makeMove(row, col)) {
            buttons[row][col].setText(String.valueOf(game.getCurrentPlayer()));
            buttons[row][col].setForeground(game.getCurrentPlayer() == 'S' ? Color.BLUE : Color.RED);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid move! Try again.");
        }
    }

    private void saveGame() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            try {
                game.saveGameLog(filePath);
                JOptionPane.showMessageDialog(this, "Game saved successfully!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving game: " + ex.getMessage());
            }
        }
    }

    private void loadGame() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            try {
                game.loadGameLog(filePath);
                replayGame();
                JOptionPane.showMessageDialog(this, "Game loaded successfully!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error loading game: " + ex.getMessage());
            }
        }
    }

    private void replayGame() {
        List<String> moveLog = game.getMoveLog();
        resetBoard(game.getBoardSize());

        for (String move : moveLog) {
            String[] parts = move.split(" ");
            char player = parts[0].charAt(0);
            int row = Integer.parseInt(parts[1]);
            int col = Integer.parseInt(parts[2]);

            buttons[row][col].setText(String.valueOf(player));
            buttons[row][col].setForeground(player == 'S' ? Color.BLUE : Color.RED);
        }
    }

    public static void main(String[] args) {
        new SOSUI();
    }
}
