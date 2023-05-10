import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Menu extends JFrame {

    public Menu() {
        generateMenu();
    }

    public void generateMenu() {
        Font font = new Font("Monospaced", Font.ITALIC | Font.BOLD, 30);
        JButton newGameButton = new JButton("New Game");
        newGameButton.setBorderPainted(false);
        newGameButton.setContentAreaFilled(false);
        newGameButton.setFont(font);
        newGameButton.setForeground(Color.WHITE);
        newGameButton.setToolTipText("Start new game");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameBoardFrame gameBoardFrame = new GameBoardFrame();
            }
        });

        JButton highScoresButton = new JButton("High Scores");
        highScoresButton.setBorderPainted(false);
        highScoresButton.setContentAreaFilled(false);
        highScoresButton.setFont(font);
        highScoresButton.setForeground(Color.WHITE);
        highScoresButton.setToolTipText("Open high scores table");
        highScoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (PacMan.getUsernames() == null) {
                    JOptionPane.showMessageDialog(null, "High scores list is empty!");
                }
                else {
                    HighScoresFrame highScoresFrame = new HighScoresFrame();
                }
            }
        });

        JButton exitButton = new JButton("Exit");
        exitButton.setBorderPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setFont(font);
        exitButton.setForeground(Color.WHITE);
        exitButton.setToolTipText("Exit game");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

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
