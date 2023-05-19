import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Menu extends JFrame{ //buttons and background pic
    public Menu() {
        generateMenu();
    }

    public void generateMenu() {
        generateButtonsAndBackground();
        try {
            readFromFile("HighScores.txt");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        pack();
        setVisible(true);
        setSize(600,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public JButton gameButton() {
        Font font = new Font("Monospaced", Font.BOLD, 40);
        JButton newGameButton = new JButton("New Game");
        newGameButton.setBorderPainted(false);
        newGameButton.setContentAreaFilled(false);
        newGameButton.setFont(font);
        newGameButton.setForeground(Color.YELLOW);
        newGameButton.setToolTipText("Start new game");
        newGameButton.addActionListener(e -> {
            JPanel jPanel = new JPanel(new GridLayout(2, 2));

            jPanel.add(new JLabel("length:"));
            JTextField length = new JTextField();
            jPanel.add(length);

            jPanel.add(new JLabel("width:"));
            JTextField width = new JTextField();
            jPanel.add(width);
            int result = JOptionPane.showConfirmDialog(null, jPanel, "Set gameboard size", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                try {
                    if (Integer.parseInt(width.getText()) < 10 || Integer.parseInt(length.getText()) < 10 || Integer.parseInt(width.getText()) > 100 || Integer.parseInt(length.getText()) > 100) {
                        JOptionPane.showMessageDialog(null, "This size is not allowed, try again!");
                        return;
                    } else {
                        GameBoardModel gameBoardModel = new GameBoardModel(Integer.parseInt(width.getText()), Integer.parseInt(length.getText()));
                        GameBoardFrame gameBoardFrame = new GameBoardFrame(gameBoardModel);
                        gameBoardFrame.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                JPanel jPanel = new JPanel(new GridLayout(1, 1));
                                JLabel jLabel = new JLabel("username:");
                                jPanel.add(jLabel);
                                JTextField jTextField = new JTextField();
                                jPanel.add(jTextField);
                                int res = JOptionPane.showConfirmDialog(null, jPanel, "Game has ended! Enter your username!", JOptionPane.OK_CANCEL_OPTION);
                                if (res == JOptionPane.OK_OPTION) {
                                    String pacName = jTextField.getText();
                                    for (int i = 0; i < PacMan.getPacMEN().size(); i++) {
                                        if (PacMan.getPacMEN().get(i).getUsername() == null)
                                            PacMan.getPacMEN().get(i).setUsername(pacName);
                                    }
                                    PacMan.getPacMEN().sort((PacMan a, PacMan b) -> b.getPoints() - a.getPoints());
                                    try {
                                        writeToFile();
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            }
                        });
                    }
                } catch (NumberFormatException NFE) {
                    JOptionPane.showMessageDialog(null, "This size is not allowed, try again!");
                    return;
                }
            } else {
                return;
            }

        });
        return newGameButton;
    }


    public JButton exitButton() {
        Font font = new Font("Monospaced",  Font.BOLD, 40);
        JButton exitButton = new JButton("Exit");
        exitButton.setBorderPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setFont(font);
        exitButton.setForeground(Color.CYAN);
        exitButton.setToolTipText("Exit game");
        exitButton.addActionListener(e -> System.exit(0));
        return exitButton;
    }
    public JButton highScoresButton() {
        Font font = new Font("Monospaced",  Font.BOLD, 40);
        JButton highScoresButton = new JButton("High Scores");
        highScoresButton.setBorderPainted(false);
        highScoresButton.setContentAreaFilled(false);
        highScoresButton.setFont(font);
        highScoresButton.setForeground(Color.GREEN);
        highScoresButton.setToolTipText("Open high scores table");
        highScoresButton.addActionListener(e -> {
            if (PacMan.getPacMEN().isEmpty()) {
                JOptionPane.showMessageDialog(null, "High scores list is empty!");
            }
            else {
                HighScoresFrame highScoresFrame = new HighScoresFrame();
            }
        });
        return highScoresButton;
    }
    public void generateButtonsAndBackground () {
        Image img = new ImageIcon("src/BackgroundImage.jpeg").getImage();
        JPanel jPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(img, 0, 0, getWidth(),getHeight(),null);
            }
        };
        jPanel.setLayout(new FlowLayout());
        jPanel.add(gameButton());
        jPanel.add(highScoresButton());
        jPanel.add(exitButton());
        jPanel.setOpaque(true);
        add(jPanel);
    }
    public void writeToFile() throws IOException {
        File file = new File("HighScores.txt");
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            for (int i = 0; i < PacMan.getPacMEN().size(); i++) {
                outputStream.writeObject(PacMan.getPacMEN().get(i));
            }
        }
    }
    public void readFromFile(String file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream inputStream = new ObjectInputStream((new FileInputStream(file)))) {
            while (true) {
                try {
                    PacMan pac = (PacMan) inputStream.readObject();
                    PacMan.getPacMEN().add(pac);
                } catch (EOFException ex) {
                    break;
                }
            }
        }
    }
}
