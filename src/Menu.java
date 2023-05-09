import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        setLayout(new FlowLayout());

        add(newGameButton);
        add(highScoresButton);
        add(exitButton);

        setSize(400, 600);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

}
