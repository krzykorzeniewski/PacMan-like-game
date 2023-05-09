import javax.swing.*;
import java.util.Vector;

public class HighScoresFrame extends JFrame {

    public HighScoresFrame() {

        JList jList = new JList();
        jList.setModel(new HighScoresModel(PacMan.getUsernames()));
        add(jList);

        pack();
        setSize(400, 600);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

}
