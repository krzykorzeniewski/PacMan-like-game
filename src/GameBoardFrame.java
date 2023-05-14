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
    private ImageIcon pacManImg = new ImageIcon("src/PacMan.png");


    public GameBoardFrame(GameBoardModel gameBoardModel) {
        this.jTable = new JTable();
        jTable.setBackground(Color.BLACK);
        jTable.setModel(gameBoardModel);
        jTable.setGridColor(Color.GRAY);
        jTable.setCellSelectionEnabled(false);
        jTable.setCellSelectionEnabled(false);
        this.add(jTable);
        this.gameBoardModel = gameBoardModel;
        this.pac = new PacMan(1, 1);
        jTable.setValueAt(pac, pac.getX(), pac.getY());
        for (int i = 0; i < 3; i++) {
            Ghost ghost = new Ghost(jTable.getRowCount() / 2 + i, jTable.getColumnCount() / 2 + i);
            ghosts.add(ghost);
            jTable.setValueAt(ghost, jTable.getRowCount() / 2 + i, jTable.getColumnCount() / 2 + i);
        }
        setLayout(new GridBagLayout());
        setLocationRelativeTo(null);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 800);
        setVisible(true);


        DefaultTableCellRenderer myRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value , isSelected, hasFocus, row,column);
                if (value.equals(0)) {
                    c.setBackground(Color.BLUE);
                    c.setForeground(Color.BLUE);
                    super.setIcon(null);
                }
                else if (value.equals(1)) {
                    c.setBackground(null);
                    c.setForeground(Color.BLACK);
                    ImageIcon imageIcon = new ImageIcon("src/Pellet.png");
                    Image scaledImage = imageIcon.getImage().getScaledInstance(2, 2, Image.SCALE_SMOOTH);
                    super.setIcon(new ImageIcon(scaledImage));
                }
                else if (value.equals(2)) {
                    c.setBackground(Color.BLACK);
                    c.setForeground(Color.BLACK);
                    super.setIcon(null);
                }
                else if (value.getClass().equals(Ghost.class)) {
                    c.setBackground(null);
                    c.setForeground(null);
                    Image scaledImage = ghostImg.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
                    super.setIcon(new ImageIcon(scaledImage));
                }
                else {
                   c.setBackground(null);
                   c.setForeground(null);
                   Image scaledImage = pacManImg.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
                   super.setIcon(new ImageIcon(scaledImage));
                }
                return c;
            }
        };
        jTable.setDefaultRenderer(Object.class, myRenderer);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    for (int i = 0; i < ghosts.size(); i++) {
                        moveGhost(i);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
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
                                if (jTable.getValueAt(pac.getX(), pac.getY() - 1).equals(1))
                                    pac.addPoint();
                                jTable.repaint();
                            }
                            break;
                        case 38:
                            if (!jTable.getValueAt(pac.getX() - 1, pac.getY()).equals(0)) {
                                jTable.setValueAt(2, pac.getX(), pac.getY());
                                jTable.setValueAt(pac, pac.getX() - 1, pac.getY());
                                pac.setX(pac.getX() - 1);//gora
                                if (jTable.getValueAt(pac.getX() - 1, pac.getY()).equals(1))
                                    pac.addPoint();
                                for (int i = 0; i < ghosts.size(); i++)
                                jTable.repaint();
                            }
                            break;
                        case 39:
                            if (!jTable.getValueAt(pac.getX(), pac.getY() + 1).equals(0)) {
                                jTable.setValueAt(2, pac.getX(), pac.getY());
                                jTable.setValueAt(pac, pac.getX(), pac.getY() + 1);
                                pac.setY(pac.getY() + 1);//prawo
                                if (jTable.getValueAt(pac.getX(), pac.getY() + 1).equals(1))
                                    pac.addPoint();
                                for (int i = 0; i < ghosts.size(); i++)
                                jTable.repaint();
                            }
                            break;
                        case 40:
                            if (!jTable.getValueAt(pac.getX() + 1, pac.getY()).equals(0)) {
                                jTable.setValueAt(2, pac.getX(), pac.getY());
                                jTable.setValueAt(pac, pac.getX() + 1, pac.getY()); //dol
                                pac.setX(pac.getX() + 1);
                                if (jTable.getValueAt(pac.getX() + 1, pac.getY()).equals(1))
                                    pac.addPoint();
                                for (int i = 0; i < ghosts.size(); i++)
                                jTable.repaint();
                            }
                            break;
                    }
                    for (Ghost ghost : ghosts) {
                        if (ghost.getX() == pac.getX() && ghost.getY() == pac.getY())
                            pac.setAlive(false);
                    }

                    if (!pac.isAlive())
                        dispose();
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

        thread.start();

    }
    public void moveGhost(int index) {
        int randomDirection = (int)(Math.random()*4+1);
        int newX = ghosts.get(index).getX();
        int newY = ghosts.get(index).getY();

        switch (randomDirection) {
            case 1:
                newY--;
                break;
            case 2:
                newX--;
                break;
            case 3:
                newY++;
                break;
            case 4:
                newX++;
                break;
            default:
                break;
        }
    Object value = jTable.getValueAt(newX, newY);
    if (!value.equals(0)) {
        if (value.equals(1)) {
            jTable.setValueAt(1, ghosts.get(index).getX(), ghosts.get(index).getY());
        } else if (value.equals(2)) {
            jTable.setValueAt(2, ghosts.get(index).getX(), ghosts.get(index).getY());
        } else {
            System.out.println("dupa");
            return;
        }
        jTable.setValueAt(ghosts.get(index), newX, newY);
        ghosts.get(index).setX(newX);
        ghosts.get(index).setY(newY);
        jTable.repaint();
    }
    }

}




