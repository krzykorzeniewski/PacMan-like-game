import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class GameBoardFrame extends JFrame{
    private int bonusCounter=0;
    private JPanel jPanel;
    private JTable jTable;
    private JLabel pointsLabel;
    private JLabel timeLabel;
    private JLabel healthPointsLabel;
    private List<Ghost> ghosts = new ArrayList<>();
    private GameBoardModel gameBoardModel;
    private PacMan pac;
    private ImageIcon bombImg = new ImageIcon("src/Bomb.png");
    private ImageIcon boostImg = new ImageIcon("src/Boost.jpeg");
    private ImageIcon ghostImg = new ImageIcon("src/Ghost.png");
    private ImageIcon pacManImg = new ImageIcon("src/PacMan.png");
    private ImageIcon pacManUpImg = new ImageIcon("src/PacManUp.png");
    private ImageIcon pacManLeftImg = new ImageIcon("src/PacManLeft.png");
    private ImageIcon pacManDownImg = new ImageIcon("src/PacManDown.png");

    private ImageIcon pacManClosedImg = new ImageIcon("src/PacManClosed.png");

    private ImageIcon pelletImg = new ImageIcon("src/Pellet.png");
    private ImageIcon brickImg = new ImageIcon("src/Brick.png");
    private Image scaledBrickImage = brickImg.getImage().getScaledInstance(55, 55, Image.SCALE_SMOOTH);
    private Image scaledBombImage = bombImg.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
    private Image scaledBoostImage = boostImg.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
    private Image scaledPelletImage = pelletImg.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH);
    private Image scaledGhostImage = ghostImg.getImage().getScaledInstance(45, 35, Image.SCALE_SMOOTH);
    private Image scaledPacImage = pacManImg.getImage().getScaledInstance(45, 35, Image.SCALE_SMOOTH);
    private Image scaledPacRightImage = pacManImg.getImage().getScaledInstance(45, 35, Image.SCALE_SMOOTH);
    private Image scaledPacUpImage = pacManUpImg.getImage().getScaledInstance(45, 35, Image.SCALE_SMOOTH);
    private Image scaledPacLeftImage = pacManLeftImg.getImage().getScaledInstance(45, 35, Image.SCALE_SMOOTH);
    private Image scaledPacDownImage = pacManDownImg.getImage().getScaledInstance(45, 35, Image.SCALE_SMOOTH);
    private Image scaledPacClosedImage = pacManClosedImg.getImage().getScaledInstance(45, 35, Image.SCALE_SMOOTH);
    private int counter;
    private int animCounter;
    private int ghostSpeed;


    public GameBoardFrame(GameBoardModel gameBoardModel) {
        this.gameBoardModel = gameBoardModel;
        this.pac = new PacMan(1, 1);
        this.pointsLabel = new JLabel();
        this.timeLabel = new JLabel();
        this.jTable = new JTable();
        this.healthPointsLabel = new JLabel();
        this.ghostSpeed = 200;
        jTable.setBackground(Color.BLACK);
        jTable.setModel(gameBoardModel);
        jTable.setGridColor(Color.BLACK);
        jTable.setCellSelectionEnabled(false);
        this.setLayout(new BorderLayout());
        JPanel jPanel1 = new JPanel();
        jPanel1.add(pointsLabel);
        jPanel1.add(timeLabel);
        jPanel1.add(healthPointsLabel);
        this.add(jTable, BorderLayout.CENTER);
        this.add(jPanel1, BorderLayout.SOUTH);
        jTable.setValueAt(pac, pac.getX(), pac.getY());
        for (int i = 0; i < 3; i++) {
            Ghost ghost = new Ghost(jTable.getRowCount() / 2 + i, jTable.getColumnCount() / 2 + i);
            ghosts.add(ghost);
            jTable.setValueAt(ghost, jTable.getRowCount() / 2 + i, jTable.getColumnCount() / 2 + i);
        }
        jTable.setRowHeight(50);
        jTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        pack();
        setSize(jTable.getWidth()+100, jTable.getHeight()+100);
        setPreferredSize(new Dimension(jTable.getWidth()+100, jTable.getHeight()+100));
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DefaultTableCellRenderer myRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value , isSelected, hasFocus, row,column);
                if (value.equals(0)) {
                    c.setBackground(Color.BLACK);
                    c.setForeground(Color.BLACK);
                    super.setIcon(new ImageIcon(scaledBrickImage));
                }
                else if (value.equals(1)) {
                    c.setBackground(Color.BLACK);
                    c.setForeground(Color.BLACK);
                    super.setIcon(new ImageIcon(scaledPelletImage));
                }
                else if (value.equals(2)) {
                    c.setBackground(Color.BLACK);
                    c.setForeground(Color.BLACK);
                    super.setIcon(null);
                }
                else if (value.getClass().equals(Ghost.class)) {
                    c.setBackground(null);
                    c.setForeground(Color.BLACK);;
                    super.setIcon(new ImageIcon(scaledGhostImage));
                }
                else if (value.getClass().equals(PacMan.class)){
                   c.setBackground(null);
                   c.setForeground(Color.BLACK);
                   if (animCounter % 2 == 0)
                        super.setIcon(new ImageIcon(scaledPacImage));
                   else
                        super.setIcon(new ImageIcon(scaledPacClosedImage));
                   animCounter++;
                }
                else if (value.equals(14)) {
                    c.setBackground(null);
                    c.setForeground(Color.BLACK);
                    super.setIcon(new ImageIcon(scaledBombImage));
                }
                else {
                    c.setBackground(null);
                    c.setForeground(Color.BLACK);
                    super.setIcon(new ImageIcon(scaledBoostImage));
                }
                return c;
            }
        };
        jTable.setDefaultRenderer(Object.class, myRenderer);
        Thread ghostsMovement = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    for (int i = 0; i < ghosts.size(); i++) {
                        moveGhost(i);
                        try {
                            Thread.sleep(ghostSpeed);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
        Thread timer = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    startTimer();
                }
            }
        });
        Thread pointCounter = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    pointsLabel.setText("score: "+pac.getPoints()+" ");
                    pointsLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    pointsLabel.setForeground(Color.BLACK);
                    pointsLabel.setOpaque(true);
                }
            }
        });
        Thread healthPointsCounter = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    healthPointsLabel.setText("health points "+pac.getHealthPoints()+" ");
                    healthPointsLabel.setForeground(Color.BLACK);
                    healthPointsLabel.setOpaque(true);
                }
            }
        });
        Thread bonusGenerator = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(5000);
                        generateBonuses();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
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
                int pacX = pac.getX();
                int pacY = pac.getY();

                try {
                    switch (e.getKeyCode()) {
                        case 37:
                            pacX = pac.getX();
                            pacY = pac.getY()-1;
                            scaledPacImage = scaledPacLeftImage;
                            break;
                        case 38:
                            pacX = pac.getX()-1;
                            pacY = pac.getY();
                            scaledPacImage = scaledPacUpImage;
                            break;
                        case 39:
                            pacX = pac.getX();
                            pacY = pac.getY()+1;
                            scaledPacImage = scaledPacRightImage;
                            break;
                        case 40:
                            pacX = pac.getX()+1;
                            pacY = pac.getY();
                            scaledPacImage = scaledPacDownImage;
                            break;
                    }
                    if (!collision(pacX,  pacY)) {
                        makeAMove(pacX, (pacY));
                    }
                    for (Ghost ghost : ghosts) {
                        if (ghost.getX() == pac.getX() && ghost.getY() == pac.getY()) {
                            pac.setHealthPoints(pac.getHealthPoints() - 1);
                            jTable.setValueAt(Ghost.class, pac.getX(), pac.getY());
                            jTable.setValueAt(pac, 1, 1);
                            pac.setX(1);
                            pac.setY(1);
                        }
                        if (pac.getHealthPoints() == 0)
                            pac.setAlive(false);
                    }
                    if (!pac.isAlive()) {
                        dispose();
                    }
                    if (checkWinCondition()) {
                        dispose();
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

        ghostsMovement.start();
        timer.start();
        pointCounter.start();
        healthPointsCounter.start();
        bonusGenerator.start();

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
                return;
            }
            jTable.setValueAt(ghosts.get(index), newX, newY);
            ghosts.get(index).setX(newX);
            ghosts.get(index).setY(newY);
            jTable.repaint();
        }
    }
    public void startTimer() {
        try {
            Thread.sleep(1000);
            counter++;
            timeLabel.setText("time: "+counter+" ");
            timeLabel.setForeground(Color.BLACK);
            timeLabel.setOpaque(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public boolean checkWinCondition () {
        int counter = 0;
        for (int i = 0; i < jTable.getRowCount(); i++) {
            for (int j = 0; j < jTable.getColumnCount(); j++) {
                if (jTable.getValueAt(i, j).equals(1))
                    counter++;
            }
        }
        if (counter == 0)
            return true;
        return false;
    }

    public void generateBonuses() {
        if (bonusCounter<6) {
            for (int i = 0; i < ghosts.size(); i++)
                generateBonus(ghosts.get(i));
            bonusCounter++;
        }
        else
            return;
    }

    public boolean collision (int pacX, int pacY) {
        if (jTable.getValueAt(pacX, pacY).equals(0))
            return true;
        return false;
    }

    public void makeAMove (int pacX, int pacY) {
        if (jTable.getValueAt(pacX, pacY).equals(1)) {
            pac.addPoint();
        }
        else if (jTable.getValueAt(pacX, pacY).equals(10)) {
            ghostSpeed = 50;
            bonusCounter--;
        }
        else if (jTable.getValueAt(pacX, pacY).equals(11)) {
            ghostSpeed = 300;
            bonusCounter--;
        }
        else if (jTable.getValueAt(pacX, pacY).equals(12)) {
            pac.setPoints(pac.getPoints()+50);
            bonusCounter--;
        }
        else if (jTable.getValueAt(pacX, pacY).equals(13)) {
            for (int i = 0; i < 1; i++) {
                Ghost ghost = new Ghost(jTable.getRowCount() / 2 + i, jTable.getColumnCount() / 2 + i);
                ghosts.add(ghost);
                jTable.setValueAt(ghost, jTable.getRowCount() / 2 + i, jTable.getColumnCount() / 2 + i);
                moveGhost(i);
            }
            bonusCounter--;
        }
        else if (jTable.getValueAt(pacX, pacY).equals(14)) {
            ghosts.clear();
            for (int i = 0; i < jTable.getRowCount(); i++) {
                for (int j = 0; j < jTable.getColumnCount(); j++) {
                    if (jTable.getValueAt(i,j).getClass().equals(Ghost.class)) {
                        jTable.setValueAt(1, i, j);
                    }
                }
            }
            bonusCounter--;
        }
        jTable.setValueAt(2, pac.getX(), pac.getY());
        pac.setX(pacX);
        pac.setY(pacY);
        jTable.setValueAt(pac, pacX, pacY);
        jTable.repaint();
    }
    public void generateBonus(Ghost ghost) {
        double rand = Math.random()*1;
        int bomba = (int)(Math.random()*10);
        if (rand <= 0.25 && rand >= 0) {
            setBonusAtCell(10);
        }
        else if (rand <= 0.50 && rand > 0.25) {
            setBonusAtCell(11);
        }
        else if (rand > 0.50 && rand <= 0.75) {
            setBonusAtCell(12);
        }
        else {
            setBonusAtCell(13);
        }
        if (bomba == 1) {
           setBonusAtCell(14);
        }

    }
    public void setBonusAtCell(int index) {
        int randomRow = (int)(Math.random()*jTable.getRowCount());
        int randomColumn = (int)(Math.random()*jTable.getColumnCount());
        for (int i = 0; i < jTable.getRowCount(); i++) {
            for (int j = 0; j < jTable.getColumnCount(); j++) {
                if (!jTable.getValueAt(i,j).equals(0) && !jTable.getValueAt(i,j).getClass().equals(PacMan.class) && !jTable.getValueAt(i,j).getClass().equals(Ghost.class) && jTable.getValueAt(i,j).equals(2) && !jTable.getValueAt(i,j).equals(1)&& i == randomRow && j == randomColumn)  {
                    jTable.setValueAt(index, i, j);
                    return;
                }
            }
        }
    }

}




