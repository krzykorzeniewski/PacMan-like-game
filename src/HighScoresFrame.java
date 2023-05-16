import javax.swing.*;
import java.awt.*;


public class HighScoresFrame extends JFrame {
    private HighScoresModel highScoresModel;

    public HighScoresFrame() {
        this.highScoresModel = new HighScoresModel(PacMan.getUsernames());
        Image img = new ImageIcon("src/JListBackground.png").getImage();

        JList jList = new JList();
        jList.setModel(highScoresModel);
        JPanel jPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(img, 0, 0, getWidth(),getHeight(),null);
            }
        };
        jPanel.add(jList);

        JScrollPane jScrollPane = new JScrollPane(jPanel);
        add(jScrollPane);

        pack();
        setSize(400, 300);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

    public HighScoresModel getHighScoresModel() {
        return highScoresModel;
    }
}
