import javax.swing.*;
import java.awt.*;

public class GameBoardFrame extends JFrame {

    public GameBoardFrame() {
        GameBoardModel gameBoardModel = new GameBoardModel();
        JPanel jPanel = new JPanel(new GridLayout(2, 2));

        jPanel.add(new JLabel("length:"));
        JTextField length = new JTextField();
        jPanel.add(length);

        jPanel.add(new JLabel("width:"));
        JTextField width = new JTextField();
        jPanel.add(width);

        int result = JOptionPane.showConfirmDialog(null, jPanel, "Set gameboard size", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            if (Integer.parseInt(width.getText()) < 10 || Integer.parseInt(length.getText()) < 10 || Integer.parseInt(width.getText()) > 100 || Integer.parseInt(length.getText()) > 100) {
                JOptionPane.showMessageDialog(null, "This size is not allowed, try again!");
                return;
            }
            else {
                gameBoardModel.setBoardDimensions(Integer.parseInt(length.getText()), Integer.parseInt(width.getText()));
                JPanel namePanel = new JPanel(new GridLayout(1, 1));
                namePanel.add(new JLabel("username:"));
                JTextField name = new JTextField();
                namePanel.add(name);
                JOptionPane.showConfirmDialog(null, namePanel, "Enter your username", JOptionPane.OK_CANCEL_OPTION);
                String pacManName = name.getText();
                PacMan pacMan = new PacMan(pacManName);
                System.out.println(PacMan.getUsernames());
            }
        }
        else {
            return;
        }

        JTable jTable = new JTable();
        jTable.setModel(gameBoardModel);
        jTable.setBackground(Color.BLACK);
        add(jTable);

        setLayout(new GridBagLayout());;
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

}
