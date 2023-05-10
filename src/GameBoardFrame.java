import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
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

        TableCellRenderer myCellRenderer = new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = new DefaultTableCellRenderer().getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (row == 0 || row == table.getRowCount()-1 || column == 0 || column == table.getColumnCount()-1)
                    c.setBackground(Color.BLACK);
                else {
                    c.setBackground(Color.MAGENTA);
                    int rand = (int)(Math.random()*table.getColumnCount()+1);
                    if ((row % 4 == 0 || column % 2 == 0) || (column+row) % rand == 0)
                        c.setBackground(Color.BLACK);
                }
                return c;
            }
        };

        JTable jTable = new JTable();
        jTable.setDefaultRenderer(Object.class, myCellRenderer);
        jTable.setModel(gameBoardModel);
        jTable.setBackground(Color.BLACK);
        jTable.setGridColor(Color.WHITE);
        add(jTable);

        setLayout(new GridBagLayout());;
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
