
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Menu extends JFrame {
    static int counter = 0;
    public Menu() {
        generateMenu();
    }

    public void generateMenu() {
        generateButtonsAndBackground();

        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

    }

    public JButton gameButton() {
        Font font = new Font("Monospaced", Font.ITALIC | Font.BOLD, 40);
        JButton newGameButton = new JButton("New Game");
        newGameButton.setBorderPainted(false);
        newGameButton.setContentAreaFilled(false);
        newGameButton.setFont(font);
        newGameButton.setForeground(Color.WHITE);
        newGameButton.setToolTipText("Start new game");
        newGameButton.addActionListener(e -> {
            GameBoardFrame gameBoardFrame = new GameBoardFrame();
            gameBoardFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    JPanel jPanel = new JPanel(new GridLayout(1,1));
                    JLabel jLabel = new JLabel("username:");
                    jPanel.add(jLabel);
                    JTextField jTextField = new JTextField();
                    jPanel.add(jTextField);
                    int res = JOptionPane.showConfirmDialog(null, jPanel,"Enter username",  JOptionPane.OK_CANCEL_OPTION);
                    if (res == JOptionPane.OK_OPTION) {
                        String pacName = jTextField.getText();
                        PacMan.getUsernames().get(counter++).setUsername(pacName);
                    }
                }
            });
        });
        return newGameButton;
    }
    public JButton exitButton() {
        Font font = new Font("Monospaced", Font.ITALIC | Font.BOLD, 40);
        JButton exitButton = new JButton("Exit");
        exitButton.setBorderPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setFont(font);
        exitButton.setForeground(Color.WHITE);
        exitButton.setToolTipText("Exit game");
        exitButton.addActionListener(e -> System.exit(0));
        return exitButton;
    }
    public JButton highScoresButton() {
        Font font = new Font("Monospaced", Font.ITALIC | Font.BOLD, 40);
        JButton highScoresButton = new JButton("High Scores");
        highScoresButton.setBorderPainted(false);
        highScoresButton.setContentAreaFilled(false);
        highScoresButton.setFont(font);
        highScoresButton.setForeground(Color.WHITE);
        highScoresButton.setToolTipText("Open high scores table");
        highScoresButton.addActionListener(e -> {
            if (PacMan.getUsernames().isEmpty()) {
                JOptionPane.showMessageDialog(null, "High scores list is empty!");
            }
            else {
                HighScoresFrame highScoresFrame = new HighScoresFrame();
            }
        });
        return highScoresButton;
    }
    public void generateButtonsAndBackground () {
        Image img = new ImageIcon("src/Background.jpeg").getImage();
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
}
