import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class GameBoardFrame extends JFrame{
    private JTable jTable;
    private JLabel pointsLabel;
    private List<Ghost> ghosts = new ArrayList<>();
    private GameBoardModel gameBoardModel;
    private PacMan pac;
    private ImageIcon ghostImg = new ImageIcon("src/Ghost.png");
    private ImageIcon pacMan = new ImageIcon("src/PacMan.png");


    public GameBoardFrame(GameBoardModel gameBoardModel) {
        this.jTable = new JTable();
        jTable.setBackground(Color.BLACK);
        jTable.setModel(gameBoardModel);
        jTable.setGridColor(Color.GRAY);
        jTable.setCellSelectionEnabled(false);
        jTable.setCellSelectionEnabled(false);
        add(jTable);
        this.gameBoardModel = gameBoardModel;
        this.pac = new PacMan(0, 0);
        jTable.setValueAt(pac, pac.getX(), pac.getY());
        for (int i = 0; i < 4; i++) {
            Ghost ghost = new Ghost(jTable.getRowCount() / 2 + i, jTable.getColumnCount() / 2 + i);
            ghosts.add(ghost);
            jTable.setValueAt(ghost, jTable.getRowCount() / 2 + i, jTable.getColumnCount() / 2 + i);
        }
        pointsLabel = new JLabel("Points");
        pointsLabel.setText(String.valueOf(pac.getPoints()));
        //add(pointsLabel);
        setLayout(new GridBagLayout());
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DefaultTableCellRenderer myRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value , isSelected, hasFocus, row,column);
                if (value.equals(0)) {
                    c.setBackground(Color.BLUE);
                    c.setForeground(Color.BLUE);
                }else if (value.equals(1)) {
                    c.setBackground(Color.GREEN);
                    c.setForeground(Color.GREEN);
//                    JLabel jLabel = new JLabel();
//                    ImageIcon imageIcon = new ImageIcon("src/Pellet.png");
//                    Image scaledImage = imageIcon.getImage().getScaledInstance(5, 5, Image.SCALE_SMOOTH);
//                    jLabel.setIcon(new ImageIcon(scaledImage));
//                    jLabel.setHorizontalAlignment(SwingConstants.CENTER);
//                    jLabel.setVerticalAlignment(SwingConstants.CENTER);
//                    return jLabel;
                }
                else if (value.equals(2)) {
                    c.setBackground(Color.BLACK);
                    c.setForeground(Color.BLACK);
                }
                else if (value.getClass().equals(Ghost.class)) {
                    c.setBackground(Color.WHITE);
                    c.setForeground(Color.WHITE);
                }
                else {
                    c.setBackground(Color.YELLOW);
                    c.setForeground(Color.YELLOW);
                }
                return c;
            }
        };
        jTable.setDefaultRenderer(Object.class, myRenderer);
        int randomDirection = (int)(Math.random()*4+1);
        if (!jTable.getValueAt(ghosts.get(0).getX(), ghosts.get(0).getY() - 1).equals(0) && randomDirection == 1) {
            jTable.setValueAt(1, ghosts.get(0).getX(), ghosts.get(0).getY());
            jTable.setValueAt(ghosts.get(0), ghosts.get(0).getX(), ghosts.get(0).getY() - 1);
            ghosts.get(0).setY(ghosts.get(0).getY() - 1);
            jTable.repaint();
        } else if (!jTable.getValueAt(ghosts.get(0).getX() - 1, ghosts.get(0).getY()).equals(0) && randomDirection == 2) {
            jTable.setValueAt(1, ghosts.get(0).getX(), ghosts.get(0).getY());
            jTable.setValueAt(ghosts.get(0), ghosts.get(0).getX() - 1, ghosts.get(0).getY());
            ghosts.get(0).setX(ghosts.get(0).getX() - 1);//gora
            jTable.repaint();
        } else if (!jTable.getValueAt(ghosts.get(0).getX(), ghosts.get(0).getY() + 1).equals(0)) {
            jTable.setValueAt(1, ghosts.get(0).getX(), ghosts.get(0).getY());
            jTable.setValueAt(ghosts.get(0), ghosts.get(0).getX(), ghosts.get(0).getY() + 1);
            ghosts.get(0).setY(ghosts.get(0).getY() + 1);//prawo
            jTable.repaint();
        } else if  (!jTable.getValueAt(ghosts.get(0).getX() + 1, ghosts.get(0).getY()).equals(0)) {
            jTable.setValueAt(1, ghosts.get(0).getX(),ghosts.get(0).getY());
            jTable.setValueAt(ghosts.get(0), ghosts.get(0).getX() + 1,ghosts.get(0).getY()); //dol
            ghosts.get(0).setX(ghosts.get(0).getX() + 1);
            jTable.repaint();
        }
        jTable.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                try {
                    switch (e.getKeyCode()) {
                        case 37:
                            if (!jTable.getValueAt(pac.getX(), pac.getY() - 1).equals(0)) {
                                jTable.setValueAt(2, pac.getX(), pac.getY());
                                jTable.setValueAt(pac, pac.getX(), pac.getY() - 1);
                                pac.setY(pac.getY() - 1);
                                pac.addPoint();
                                moveGhost();
                                jTable.repaint();
                            }
                            break;
                        case 38:
                            if (!jTable.getValueAt(pac.getX() - 1, pac.getY()).equals(0)) {
                                jTable.setValueAt(2, pac.getX(), pac.getY());
                                jTable.setValueAt(pac, pac.getX() - 1, pac.getY());
                                pac.setX(pac.getX() - 1);//gora
                                pac.addPoint();
                                moveGhost();
                                jTable.repaint();
                            }
                            break;
                        case 39:
                            if (!jTable.getValueAt(pac.getX(), pac.getY() + 1).equals(0)) {
                                jTable.setValueAt(2, pac.getX(), pac.getY());
                                jTable.setValueAt(pac, pac.getX(), pac.getY() + 1);
                                pac.setY(pac.getY() + 1);//prawo
                                pac.addPoint();
                                moveGhost();
                                jTable.repaint();
                            }
                            break;
                        case 40:
                            if (!jTable.getValueAt(pac.getX() + 1, pac.getY()).equals(0)) {
                                jTable.setValueAt(2, pac.getX(), pac.getY());
                                jTable.setValueAt(pac, pac.getX() + 1, pac.getY()); //dol
                                pac.setX(pac.getX() + 1);
                                pac.addPoint();
                                moveGhost();
                                jTable.repaint();
                            }
                            break;
                    }
                } catch (ArrayIndexOutOfBoundsException ae) {
                    jTable.setValueAt(pac, pac.getX(), pac.getY());
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.isControlDown() && e.isShiftDown() && (e.getKeyChar() == 'q' || e.getKeyChar() == 'Q'))
                    dispose();
            }
        });


    }
    public void moveGhost() {
        int randomDirection = (int)(Math.random()*4+1);
        if (!jTable.getValueAt(ghosts.get(0).getX(), ghosts.get(0).getY() - 1).equals(0) && randomDirection == 1) {
            jTable.setValueAt(1, ghosts.get(0).getX(), ghosts.get(0).getY());
            jTable.setValueAt(ghosts.get(0), ghosts.get(0).getX(), ghosts.get(0).getY() - 1);
            ghosts.get(0).setY(ghosts.get(0).getY() - 1);
            jTable.repaint();
        } else if (!jTable.getValueAt(ghosts.get(0).getX() - 1, ghosts.get(0).getY()).equals(0) && randomDirection == 2) {
            jTable.setValueAt(1, ghosts.get(0).getX(), ghosts.get(0).getY());
            jTable.setValueAt(ghosts.get(0), ghosts.get(0).getX() - 1, ghosts.get(0).getY());
            ghosts.get(0).setX(ghosts.get(0).getX() - 1);//gora
            jTable.repaint();
        } else if (!jTable.getValueAt(ghosts.get(0).getX(), ghosts.get(0).getY() + 1).equals(0)&& randomDirection == 3) {
            jTable.setValueAt(1, ghosts.get(0).getX(), ghosts.get(0).getY());
            jTable.setValueAt(ghosts.get(0), ghosts.get(0).getX(), ghosts.get(0).getY() + 1);
            ghosts.get(0).setY(ghosts.get(0).getY() + 1);//prawo
            jTable.repaint();
        } else if  (!jTable.getValueAt(ghosts.get(0).getX() + 1, ghosts.get(0).getY()).equals(0) && randomDirection == 4) {
            jTable.setValueAt(1, ghosts.get(0).getX(),ghosts.get(0).getY());
            jTable.setValueAt(ghosts.get(0), ghosts.get(0).getX() + 1,ghosts.get(0).getY()); //dol
            ghosts.get(0).setX(ghosts.get(0).getX() + 1);
            jTable.repaint();
        }
    }

}




