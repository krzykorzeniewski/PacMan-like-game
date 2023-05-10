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
        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameBoardFrame gameBoardFrame = new GameBoardFrame();
            }
        });

        JButton highScoresButton = new JButton("High Scores");
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
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        Image img = new ImageIcon("src/PacManBackground.png").getImage();
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
        Dimension d = new Dimension(1920, 1080);
        jPanel.setPreferredSize(d);
        add(jPanel);

        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
