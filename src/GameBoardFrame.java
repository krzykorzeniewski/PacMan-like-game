import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
            try {
                if (Integer.parseInt(width.getText()) < 10 || Integer.parseInt(length.getText()) < 10 || Integer.parseInt(width.getText()) > 100 || Integer.parseInt(length.getText()) > 100) {
                    JOptionPane.showMessageDialog(null, "This size is not allowed, try again!");
                    return;
                } else {
                    gameBoardModel.setBoardDimensions(Integer.parseInt(length.getText()), Integer.parseInt(width.getText()));
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "This size is not allowed, try again!");
                return;
            }
        } else {
            return;
        }

        TableCellRenderer myCellRenderer = (table, value, isSelected, hasFocus, row, column) -> {
            Component c = new DefaultTableCellRenderer().getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (row == 0 && column == 0) {
                PacMan pac = new PacMan();
                pac.setX(row);
                pac.setY(column);
                ImageIcon pacMan = gameBoardModel.pacMan;
                Image scaled = pacMan.getImage().getScaledInstance(table.getRowHeight(row), table.getColumnModel().getColumn(column).getWidth(), Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaled);
                   JLabel jLabel = new JLabel();
                   jLabel.setIcon(scaledIcon);
                   jLabel.setHorizontalAlignment(JLabel.CENTER);
                   jLabel.setVerticalAlignment(JLabel.CENTER);
                   add(jLabel);
                   return jLabel;
            }
            for (int i = 0; i < table.getRowCount(); i++) {
                for (int j = 0; j < table.getColumnCount(); j++) {
                    c.setBackground(Color.BLUE);
                    if (c.getBackground().equals(Color.BLUE)) {
                        int rand = (int)(Math.random()*3);
                        if (rand == 1)
                            c.setBackground(Color.BLACK);
                    }
                    if (row % 2 == 0)
                        c.setBackground(Color.BLACK);
                }
            }
            if (row == 0 || column == 0 || row == table.getRowCount()-1 || column == table.getColumnCount()-1)
                c.setBackground(Color.BLACK);
            if ((row == table.getRowCount()/2 && column == table.getColumnCount()/2) ||  (row == table.getRowCount()/2+1 && column == table.getColumnCount()/2) ||  (row == table.getRowCount()/2+1 && column == table.getColumnCount()/2+1) ||  (row == table.getRowCount()/2 && column == table.getColumnCount()/2+1)) {
                ImageIcon pghost = gameBoardModel.ghost;
                Image scaled = pghost.getImage().getScaledInstance(table.getRowHeight(row), table.getColumnModel().getColumn(column).getWidth(), Image.SCALE_SMOOTH); //  dodawanie obrazka
                ImageIcon scaledIcon = new ImageIcon(scaled);
                   JLabel jLabel = new JLabel();
                   jLabel.setIcon(scaledIcon);
                   jLabel.setHorizontalAlignment(JLabel.CENTER);
                   jLabel.setVerticalAlignment(JLabel.CENTER);
                   add(jLabel);
                   return jLabel;
            }
            return c;
        };

        JTable jTable = new JTable();
        jTable.setBackground(Color.BLACK);
        jTable.setModel(gameBoardModel);
        jTable.setGridColor(Color.BLACK);
        jTable.setDefaultRenderer(Object.class, myCellRenderer);
        add(jTable);
        setSize(600, 480);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
    }

}



