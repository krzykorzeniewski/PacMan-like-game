import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
            } else {
                gameBoardModel.setBoardDimensions(Integer.parseInt(length.getText()), Integer.parseInt(width.getText()));
            }
        } else {
            return;
        }

        TableCellRenderer myCellRenderer = (table, value, isSelected, hasFocus, row, column) -> {
            Component c = new DefaultTableCellRenderer().getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (row == 0 || column == 0 || row == table.getRowCount()-1 || column == table.getColumnCount()-1)
                c.setBackground(Color.BLACK);
            else {
                for (int i = 0; i < table.getRowCount()/2; i++) {
                    for (int j = 0; j < table.getColumnCount()/2; j++) {
                        c.setBackground(Color.BLUE);
                        if (c.getBackground().equals(Color.BLUE)) {
                            int rand = (int) (Math.random() * 3);
                            if (rand == 1)
                                c.setBackground(Color.BLACK);
                        }
                        if (row % 2 == 0) {
                                c.setBackground(Color.BLACK);
                        }
                    }
                }
            }
            if ((row == table.getRowCount()/2 && column == table.getColumnCount()/2) ||  (row == table.getRowCount()/2+1 && column == table.getColumnCount()/2) ||  (row == table.getRowCount()/2+1 && column == table.getColumnCount()/2+1) ||  (row == table.getRowCount()/2 && column == table.getColumnCount()/2+1))
                c.setBackground(Color.YELLOW);
            return c;
        };

        Image img = new ImageIcon("src/PacMan.png").getImage();


        JTable jTable = new JTable();
        jTable.setBackground(Color.BLACK);
        jTable.setModel(gameBoardModel);
        jTable.setGridColor(Color.WHITE);
        jTable.setDefaultRenderer(Object.class, myCellRenderer);
        add(jTable);
        setLayout(new GridBagLayout());

        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

}



