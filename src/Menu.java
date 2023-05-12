
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Menu extends JFrame {

    public Menu() {
        generateMenu();
    }

    public void generateMenu() {
        Font font = new Font("Monospaced", Font.ITALIC | Font.BOLD, 40);
        JButton newGameButton = new JButton("New Game");
        newGameButton.setBorderPainted(false);
        newGameButton.setContentAreaFilled(false);
        newGameButton.setFont(font);
        newGameButton.setForeground(Color.WHITE);
        newGameButton.setToolTipText("Start new game");
        KeyStroke keyStrokeToClose = KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK|KeyEvent.SHIFT_DOWN_MASK);
        newGameButton.addActionListener(e -> {
            InputMap inputMap = newGameButton.getInputMap();
            inputMap.put(keyStrokeToClose, this); // nevermind

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
                        PacMan pacMan = new PacMan(pacName);
                    }
                }
            });
        });

        JButton highScoresButton = new JButton("High Scores");
        highScoresButton.setBorderPainted(false);
        highScoresButton.setContentAreaFilled(false);
        highScoresButton.setFont(font);
        highScoresButton.setForeground(Color.WHITE);
        highScoresButton.setToolTipText("Open high scores table");
        highScoresButton.addActionListener(e -> {
            if (PacMan.getUsernames() == null) {
                JOptionPane.showMessageDialog(null, "High scores list is empty!");
            }
            else {
                HighScoresFrame highScoresFrame = new HighScoresFrame();
            }
        });

        JButton exitButton = new JButton("Exit");
        exitButton.setBorderPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setFont(font);
        exitButton.setForeground(Color.WHITE);
        exitButton.setToolTipText("Exit game");
        exitButton.addActionListener(e -> System.exit(0));

        Image img = new ImageIcon("src/Background.jpeg").getImage();
        JPanel jPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(img, 0, 0, getWidth(),getHeight(),null);
            }
        };
        jPanel.setLayout(new FlowLayout());
        jPanel.add(newGameButton);
        jPanel.add(highScoresButton);
        jPanel.add(exitButton);
        jPanel.setOpaque(true);
        add(jPanel);

        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

    }
}
