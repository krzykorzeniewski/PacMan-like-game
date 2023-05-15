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
    private JLabel timeLabel;
    private JLabel healthPointsLabel;
    private List<Ghost> ghosts = new ArrayList<>();
    private GameBoardModel gameBoardModel;
    private PacMan pac;
    private ImageIcon boostImg = new ImageIcon("src/Boost.png");
    private ImageIcon ghostImg = new ImageIcon("src/Ghost.png");
    private ImageIcon pacManImg = new ImageIcon("src/PacManPaint.png");
    private ImageIcon pacManClosedImg = new ImageIcon("src/PacManClosedPaint.png");
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
        this.ghostSpeed = 150;
        jTable.setBackground(Color.BLACK);
        jTable.setModel(gameBoardModel);
        jTable.setGridColor(Color.GRAY);
        jTable.setCellSelectionEnabled(false);
        jTable.setCellSelectionEnabled(false);
        setLayout(new GridBagLayout());
        this.add(healthPointsLabel);
        this.add(jTable);
        this.add(pointsLabel);
        this.add(timeLabel);
        jTable.setValueAt(pac, pac.getX(), pac.getY());
        for (int i = 0; i < 3; i++) {
            Ghost ghost = new Ghost(jTable.getRowCount() / 2 + i, jTable.getColumnCount() / 2 + i);
            ghosts.add(ghost);
            jTable.setValueAt(ghost, jTable.getRowCount() / 2 + i, jTable.getColumnCount() / 2 + i);
        }

        setLocationRelativeTo(null);
        setSize(1000, 800);
        setVisible(true);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        doLayout();


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
                    c.setBackground(Color.BLACK);
                    c.setForeground(Color.BLACK);
                    ImageIcon imageIcon = new ImageIcon("src/Pellet.png");
                    Image scaledImage = imageIcon.getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH);
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
                else if (value.getClass().equals(PacMan.class)){
                   c.setBackground(null);
                   c.setForeground(null);
                   Image scaledImage = pacManImg.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
                   Image scaledClosedImage = pacManClosedImg.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
                   if (animCounter % 2 == 0)
                        super.setIcon(new ImageIcon(scaledImage));
                   else
                        super.setIcon(new ImageIcon(scaledClosedImage));
                   animCounter++;
                }
                else {
                    c.setBackground(null);
                    c.setForeground(Color.WHITE);
                    super.setText("?");
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
                    pointsLabel.setBackground(Color.BLACK);
                    pointsLabel.setForeground(Color.ORANGE);
                    pointsLabel.setOpaque(true);
                }
            }
        });
        Thread healthPointsCounter = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    healthPointsLabel.setText("health points "+pac.getHealthPoints()+" ");
                    healthPointsLabel.setBackground(Color.BLACK);
                    healthPointsLabel.setForeground(Color.ORANGE);
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
                Object pacX = pac.getX();
                Object pacY = pac.getY();

                try {
                    switch (e.getKeyCode()) {
                        case 37:
                            pacX = pac.getX();
                            pacY = pac.getY()-1;
                            break;
                        case 38:
                            pacX = pac.getX()-1;
                            pacY = pac.getY();
                            break;
                        case 39:
                            pacX = pac.getX();
                            pacY = pac.getY()+1;
                            break;
                        case 40:
                            pacX = pac.getX()+1;
                            pacY = pac.getY();
                            break;
                    }
                    if (!collision((int) pacX, (int) pacY)) {
                        makeAMove((int)pacX, (int)pacY);
                    }
                    for (Ghost ghost : ghosts) {
                        if (ghost.getX() == pac.getX() && ghost.getY() == pac.getY()) {
                            pac.setHealthPoints(pac.getHealthPoints() - 1);
                            jTable.setValueAt(1, pac.getX(), pac.getY());
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
            timeLabel.setBackground(Color.BLACK);
            timeLabel.setForeground(Color.ORANGE);
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
        for (int i = 0; i < ghosts.size(); i++)
            generateBonus(ghosts.get(i));
    }

    public boolean collision (int pacX, int pacY) {
        if (jTable.getValueAt(pacX, pacY).equals(0))
            return true;
        return false;
    }

    public void makeAMove (int pacX, int pacY) {
        jTable.setValueAt(2, pac.getX(), pac.getY());
        if (jTable.getValueAt(pacX, pacY).equals(1))
            pac.addPoint();
        else if (jTable.getValueAt(pacX, pacY).equals(10)) {
            ghostSpeed = 10;
        }
        else if (jTable.getValueAt(pacX, pacY).equals(11)) {
            ghostSpeed = 300;
        }
        else if (jTable.getValueAt(pacX, pacY).equals(12)) {
            pac.setPoints(pac.getPoints()+50);
        }
        else if (jTable.getValueAt(pacX, pacY).equals(13)) {
            for (int i = 0; i < 3; i++) {
                Ghost ghost = new Ghost(jTable.getRowCount() / 2 + i, jTable.getColumnCount() / 2 + i);
                ghosts.add(ghost);
                jTable.setValueAt(ghost, jTable.getRowCount() / 2 + i, jTable.getColumnCount() / 2 + i);
                moveGhost(i);
            }
        }
        else if (jTable.getValueAt(pacX, pacY).equals(14)) {
            for (int i = 0; i < jTable.getRowCount(); i++) {
                for (int j = 0; j < jTable.getColumnCount(); j++) {
                    if (jTable.getValueAt(i,j).getClass().equals(Ghost.class)) {
                        jTable.setValueAt(1, i, j);
                    }
                }
            }
        }
        jTable.setValueAt(pac, pacX, pacY);
        pac.setX(pacX);
        pac.setY(pacY);
        jTable.repaint();
    }
    public void generateBonus(Ghost ghost) {
        int randomRow = (int)(Math.random()*jTable.getRowCount());
        int randomColumn = (int)(Math.random()*jTable.getColumnCount());
        double rand = Math.random()*1;
        int bomba = (int)(Math.random()*1000000);
        if (rand <= 0.25 && rand >= 0) {
            for (int i = 0; i < jTable.getRowCount(); i++) {
                for (int j = 0; j < jTable.getColumnCount(); j++) {
                    if (!jTable.getValueAt(i,j).equals(0) && !jTable.getValueAt(i,j).getClass().equals(PacMan.class) && !jTable.getValueAt(i,j).getClass().equals(Ghost.class)&& jTable.getValueAt(i,j).equals(2) && !jTable.getValueAt(i,j).equals(1) && i == randomRow && j == randomColumn) {
                        jTable.setValueAt(10, i, j);
                        return;
                    }
                }
            }

            System.out.println(ghost.getBonuses()[0]);
        }
        else if (rand <= 0.50 && rand > 0.25) {
            for (int i = 0; i < jTable.getRowCount(); i++) {
                for (int j = 0; j < jTable.getColumnCount(); j++) {
                    if (!jTable.getValueAt(i,j).equals(0) && !jTable.getValueAt(i,j).getClass().equals(PacMan.class) && !jTable.getValueAt(i,j).getClass().equals(Ghost.class) && jTable.getValueAt(i,j).equals(2) && !jTable.getValueAt(i,j).equals(1)&& i == randomRow && j == randomColumn) {
                        jTable.setValueAt(11, i, j);
                        return;
                    }
                }
            }
            System.out.println(ghost.getBonuses()[1]);
        }
        else if (rand > 0.50 && rand <= 0.75) {
            for (int i = 0; i < jTable.getRowCount(); i++) {
                for (int j = 0; j < jTable.getColumnCount(); j++) {
                    if (!jTable.getValueAt(i,j).equals(0) && !jTable.getValueAt(i,j).getClass().equals(PacMan.class) && !jTable.getValueAt(i,j).getClass().equals(Ghost.class) && jTable.getValueAt(i,j).equals(2) && !jTable.getValueAt(i,j).equals(1)&& i == randomRow && j == randomColumn)  {
                        jTable.setValueAt(12, i, j);
                        return;
                    }
                }
            }
            System.out.println(ghost.getBonuses()[2]);
        }
        else {
            for (int i = 0; i < jTable.getRowCount(); i++) {
                for (int j = 0; j < jTable.getColumnCount(); j++) {
                    if (!jTable.getValueAt(i,j).equals(0) && !jTable.getValueAt(i,j).getClass().equals(PacMan.class) && !jTable.getValueAt(i,j).getClass().equals(Ghost.class) && jTable.getValueAt(i,j).equals(2) && !jTable.getValueAt(i,j).equals(1) && i == randomRow && j == randomColumn) {
                        jTable.setValueAt(13, i, j);
                        return;
                    }
                }
            }
            System.out.println(ghost.getBonuses()[3]);
        }
        if (bomba == 30) {
            for (int i = 0; i < jTable.getRowCount(); i++) {
                for (int j = 0; j < jTable.getColumnCount(); j++) {
                    if (!jTable.getValueAt(i,j).equals(0) && !jTable.getValueAt(i,j).getClass().equals(PacMan.class) && !jTable.getValueAt(i,j).getClass().equals(Ghost.class) && jTable.getValueAt(i,j).equals(2) && !jTable.getValueAt(i,j).equals(1) && i == randomRow && j == randomColumn) {
                        jTable.setValueAt(14, i, j);
                        return;
                    }
                }
            }
            System.out.println(ghost.getBonuses()[4]);
        }

    }

}




