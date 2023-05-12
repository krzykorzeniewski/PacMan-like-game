import javax.swing.*;



public class HighScoresFrame extends JFrame {

    public HighScoresFrame() {

        JList jList = new JList();
        jList.setModel(new HighScoresModel(PacMan.getUsernames()));
        JScrollPane jScrollPane = new JScrollPane(jList);
        add(jScrollPane);

        pack();
        setSize(400, 600);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

}
