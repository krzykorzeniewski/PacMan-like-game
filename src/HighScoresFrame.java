import javax.swing.*;
import java.awt.*;


public class HighScoresFrame extends JFrame { //high scores list model
    private HighScoresModel highScoresModel;
    private Image backgroundImage;

    public HighScoresFrame() {
        this.highScoresModel = new HighScoresModel(PacMan.getPacMEN());
        this.backgroundImage = new ImageIcon("src/JListBackgroundImage.png").getImage();

        JList jList = new JList();
        jList.setModel(highScoresModel);
        JPanel jPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(),getHeight(),null);
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
