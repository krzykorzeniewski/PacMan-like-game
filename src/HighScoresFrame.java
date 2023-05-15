import javax.swing.*;



public class HighScoresFrame extends JFrame {
    private HighScoresModel highScoresModel;

    public HighScoresFrame() {
        this.highScoresModel = new HighScoresModel(PacMan.getUsernames());

        JList jList = new JList();
        jList.setModel(highScoresModel);
        JScrollPane jScrollPane = new JScrollPane(jList);
        add(jScrollPane);

        pack();
        setSize(400, 600);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

    public HighScoresModel getHighScoresModel() {
        return highScoresModel;
    }
}
