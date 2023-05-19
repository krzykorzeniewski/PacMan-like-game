import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameBoardFrame extends JFrame{ //board frame
    private Clip eatingClip;
    {
        try {
            eatingClip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    private Clip losingClip;
    {
        try {
            losingClip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    private AudioInputStream audioInputStream;
    private JPanel gamePanel;
    private JPanel infoPanel;
    private JTable jTable;
    private JLabel pointsLabel;
    private JLabel timeLabel;
    private JLabel healthPointsLabel;
    private List<Ghost> ghosts = new ArrayList<>();
    private GameBoardModel gameBoardModel;
    private PacMan pac;
    private ImageIcon bombImg;
    private ImageIcon boostImg;
    private ImageIcon ghostImg;
    private ImageIcon pacManImg;
    private ImageIcon pacManUpImg;
    private ImageIcon pacManLeftImg;
    private ImageIcon pacManDownImg;
    private ImageIcon pacManClosedImg;
    private ImageIcon pelletImg;
    private Image scaledBombImage;
    private Image scaledBoostImage;
    private Image scaledPelletImage;
    private Image scaledGhostImage;
    private Image scaledPacImage;
    private Image scaledPacRightImage;
    private Image scaledPacUpImage;
    private Image scaledPacLeftImage;
    private Image scaledPacDownImage;
    private Image scaledPacClosedImage;
    private int counter;
    private int animCounter;
    private int ghostSpeed;

    public GameBoardFrame(GameBoardModel gameBoardModel) {
        this.gameBoardModel = gameBoardModel;
        this.pac = new PacMan(1, 1);
        this.pointsLabel = new JLabel();
        this.timeLabel = new JLabel();
        this.healthPointsLabel = new JLabel();
        this.ghostSpeed = 150;

        createGameTable();
        createImages();
        createFrame();

        jTable.setDefaultRenderer(Object.class, createCustomRenderer());
        Thread ghostsMovement = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()) {
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
                while (!Thread.interrupted()) {
                    startTimer();
                }
            }
        });
        Thread pointCounter = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()) {
                    pointsLabel.setText("score: "+pac.getPoints()+" ");
                    pointsLabel.setFont(new Font("Arial", Font.BOLD, 20));
                    pointsLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    pointsLabel.setBackground(Color.BLACK);
                    pointsLabel.setForeground(Color.YELLOW);
                    pointsLabel.setOpaque(true);
                }
            }
        });
        Thread healthPointsCounter = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()) {
                    healthPointsLabel.setText("health points "+pac.getHealthPoints()+" ");
                    healthPointsLabel.setFont(new Font("Arial", Font.BOLD, 20));
                    healthPointsLabel.setBackground(Color.BLACK);
                    healthPointsLabel.setForeground(Color.YELLOW);
                    healthPointsLabel.setOpaque(true);
                }
            }
        });
        Thread bonusGenerator = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()) {
                    try {
                        Thread.sleep(5000);
                        generateBonuses();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Thread isGameOver = new Thread(new Runnable() {
            @Override
            public void run() {
                while(!Thread.interrupted()) {
                    if (pac.getHealthPoints() < 0) {
                        pac.setAlive(false);
                    }
                    if (!pac.isAlive()) {
                        ghostsMovement.interrupt();
                        timer.interrupt();
                        pointCounter.interrupt();
                        healthPointsCounter.interrupt();
                        bonusGenerator.interrupt();
                        dispose();
                    }
                    if (checkWinCondition()) {
                        ghostsMovement.interrupt();
                        timer.interrupt();
                        pointCounter.interrupt();
                        healthPointsCounter.interrupt();
                        bonusGenerator.interrupt();
                        dispose();
                    }
                }
            }
        });

        jTable.addKeyListener(createKeyMyKeyListener());

        ghostsMovement.start();
        timer.start();
        pointCounter.start();
        healthPointsCounter.start();
        bonusGenerator.start();
        isGameOver.start();

    }
    public void moveGhost(int index) {
            int randomDirection = (int)(Math.random()*4+1);
            int nextX = ghosts.get(index).getX();
            int nextY = ghosts.get(index).getY();

        switch (randomDirection) {
            case 1 -> nextY--;
            case 2 -> nextX--;
            case 3 -> nextY++;
            case 4 -> nextX++;
            default -> {
            }
        }
        if (!jTable.getValueAt(nextX, nextY).equals(0)) {
            if (jTable.getValueAt(nextX, nextY).equals(1)) {
                jTable.setValueAt(1, ghosts.get(index).getX(), ghosts.get(index).getY());
            }
            else if (jTable.getValueAt(nextX, nextY).equals(2)) {
                jTable.setValueAt(2, ghosts.get(index).getX(), ghosts.get(index).getY());
            }
            else if (jTable.getValueAt(nextX, nextY).getClass().equals(PacMan.class)) {
                try {
                    playLosingSound();
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                    throw new RuntimeException(e);
                }
                pac.setHealthPoints(pac.getHealthPoints() - 1);
                jTable.setValueAt(1, ghosts.get(index).getX(), ghosts.get(index).getY());
                jTable.setValueAt(pac, 1, 1);
                pac.setX(1);
                pac.setY(1);
            }
            else return;
            jTable.setValueAt(ghosts.get(index), nextX, nextY);
            ghosts.get(index).setX(nextX);
            ghosts.get(index).setY(nextY);
            jTable.repaint();
        }
    }
    public void startTimer() {
        try {
            Thread.sleep(1000);
            counter++;
            timeLabel.setText("time: "+counter+" ");
            timeLabel.setFont(new Font("Arial", Font.BOLD, 20));
            timeLabel.setBackground(Color.BLACK);
            timeLabel.setForeground(Color.YELLOW);
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

    public void makeAMove (int pacX, int pacY) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (jTable.getValueAt(pacX, pacY).equals(1)) {
            playSound();
            pac.addPoint();
        }
        else if (jTable.getValueAt(pacX, pacY).equals(10)) {
            playSound();
            if (ghostSpeed > 50)
                ghostSpeed -= 50;
        }
        else if (jTable.getValueAt(pacX, pacY).equals(11)) {
            playSound();
            ghostSpeed += 50;
        }
        else if (jTable.getValueAt(pacX, pacY).equals(12)) {
            playSound();
            pac.setPoints(pac.getPoints()+50);
        }
        else if (jTable.getValueAt(pacX, pacY).equals(13)) {
            playSound();
            for (int i = 0; i < 1; i++) {
                Ghost ghost = new Ghost(jTable.getRowCount() / 2 + i, jTable.getColumnCount() / 2 + i);
                ghosts.add(ghost);
                jTable.setValueAt(ghost, jTable.getRowCount() / 2 + i, jTable.getColumnCount() / 2 + i);
                moveGhost(i);
            }
        }
        else if (jTable.getValueAt(pacX, pacY).equals(14)) {
            playSound();
            ghosts.clear();
            for (int i = 0; i < jTable.getRowCount(); i++) {
                for (int j = 0; j < jTable.getColumnCount(); j++) {
                    if (jTable.getValueAt(i,j).getClass().equals(Ghost.class)) {
                        jTable.setValueAt(1, i, j);
                    }
                }
            }
        }
        jTable.setValueAt(2, pac.getX(), pac.getY());
        pac.setX(pacX);
        pac.setY(pacY);
        jTable.setValueAt(pac, pacX, pacY);
        jTable.repaint();
    }
    public void generateBonus(Ghost ghost) {
        double rand = Math.random()*1;
        int bomba = (int)(Math.random()*20);
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
    public void playSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if (!eatingClip.isActive()) {
            audioInputStream = AudioSystem.getAudioInputStream(new File("src/EatingSound.wav").getAbsoluteFile());
            eatingClip = AudioSystem.getClip();
            eatingClip.open(audioInputStream);
            eatingClip.start();
        }
    }
    public void playLosingSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if (!losingClip.isActive()) {
            audioInputStream = AudioSystem.getAudioInputStream(new File("src/LoseSound.wav").getAbsoluteFile());
            losingClip = AudioSystem.getClip();
            losingClip.open(audioInputStream);
            losingClip.start();
        }
    }
    public void createGameTable() {
        this.jTable = new JTable();
        jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable.setBackground(Color.BLACK);
        jTable.setModel(gameBoardModel);
        jTable.setGridColor(Color.BLACK);
        jTable.setCellSelectionEnabled(false);
        jTable.setSelectionModel(createSelectionModel());
        jTable.setColumnSelectionAllowed(false);
        jTable.setRowHeight(50);
        jTable.getColumnModel().getColumn(0).setWidth(50);
        jTable.setValueAt(pac, pac.getX(), pac.getY());
        for (int i = 0; i < 3; i++) {
            Ghost ghost = new Ghost(jTable.getRowCount() / 2 + i, jTable.getColumnCount() / 2);
            ghosts.add(ghost);
            jTable.setValueAt(ghost, jTable.getRowCount() / 2 + i, jTable.getColumnCount() / 2);
        }
    }
    public void createFrame () {
        this.gamePanel = new JPanel();
        this.gamePanel.setLayout(new BorderLayout());
        this.gamePanel.add(jTable, BorderLayout.CENTER);
        JScrollPane jScrollPane = new JScrollPane(gamePanel);

        this.infoPanel = new JPanel();
        this.infoPanel.setBackground(Color.BLACK);
        this.infoPanel.add(pointsLabel);
        this.infoPanel.add(timeLabel);
        this.infoPanel.add(healthPointsLabel);

        setLayout(new BorderLayout());
        add(jScrollPane, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.SOUTH);

        pack();
        setSize(jTable.getWidth()+100, jTable.getHeight()+100);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    public DefaultTableCellRenderer createCustomRenderer () {
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
                else  {
                    c.setBackground(null);
                    c.setForeground(Color.BLACK);
                    super.setIcon(new ImageIcon(scaledBoostImage));
                }
                return c;
            }
        };
        return myRenderer;
    }
    public KeyListener createKeyMyKeyListener () {
        KeyListener myKeyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int pacX = pac.getX();
                int pacY = pac.getY();

                try {
                    switch (e.getKeyCode()) {
                        case 37 -> {
                            pacX = pac.getX();
                            pacY = pac.getY() - 1;
                            scaledPacImage = scaledPacLeftImage;
                        }
                        case 38 -> {
                            pacX = pac.getX() - 1;
                            pacY = pac.getY();
                            scaledPacImage = scaledPacUpImage;
                        }
                        case 39 -> {
                            pacX = pac.getX();
                            pacY = pac.getY() + 1;
                            scaledPacImage = scaledPacRightImage;
                        }
                        case 40 -> {
                            pacX = pac.getX() + 1;
                            pacY = pac.getY();
                            scaledPacImage = scaledPacDownImage;
                        }
                    }
                    if (!collision(pacX,  pacY)) {
                        try {
                            makeAMove(pacX, (pacY));
                        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                            ex.printStackTrace();
                        }
                    }

                    checkCollisionWithGhost();

                } catch (ArrayIndexOutOfBoundsException ae) {
                    jTable.setValueAt(pac, pac.getX(), pac.getY());
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.isControlDown() && e.isShiftDown() && (e.getKeyChar() == 'q' || e.getKeyChar() == 'Q'))
                    dispose();
            }
        };
        return myKeyListener;
    }
    public void checkCollisionWithGhost() {
        for (Ghost ghost : ghosts) {
            if (ghost.getX() == pac.getX() && ghost.getY() == pac.getY()) {
                    try {
                        playLosingSound();
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                        ex.printStackTrace();
                    }
                pac.setHealthPoints(pac.getHealthPoints() - 1);
                jTable.setValueAt(ghost, pac.getX(), pac.getY());
                jTable.setValueAt(pac, 1, 1);
                pac.setX(1);
                pac.setY(1);
            }
        }
    }
    public void createImages() {
        this.bombImg = new ImageIcon("src/Bomb.png");
        this.boostImg = new ImageIcon("src/Boost.jpeg");
        this.ghostImg = new ImageIcon("src/Ghost.png");
        this.pacManImg = new ImageIcon("src/PacMan.png");
        this.pacManUpImg = new ImageIcon("src/PacManUp.png");
        this.pacManLeftImg = new ImageIcon("src/PacManLeft.png");
        this.pacManDownImg = new ImageIcon("src/PacManDown.png");
        this.pacManClosedImg = new ImageIcon("src/PacManClosed.png");
        this.pelletImg = new ImageIcon("src/Pellet.png");
        this.scaledBombImage = bombImg.getImage().getScaledInstance(jTable.getColumnModel().getColumn(0).getWidth(), jTable.getRowHeight(), Image.SCALE_SMOOTH);
        this.scaledBoostImage = boostImg.getImage().getScaledInstance(jTable.getColumnModel().getColumn(0).getWidth(), jTable.getRowHeight(), Image.SCALE_SMOOTH);
        this.scaledPelletImage = pelletImg.getImage().getScaledInstance(jTable.getColumnModel().getColumn(0).getWidth()-5, jTable.getRowHeight()/2, Image.SCALE_SMOOTH);
        this.scaledGhostImage = ghostImg.getImage().getScaledInstance(jTable.getColumnModel().getColumn(0).getWidth()+5, jTable.getRowHeight()-20, Image.SCALE_SMOOTH);
        this.scaledPacImage = pacManImg.getImage().getScaledInstance(jTable.getColumnModel().getColumn(0).getWidth()-23, jTable.getRowHeight()-23, Image.SCALE_SMOOTH);
        this.scaledPacRightImage = pacManImg.getImage().getScaledInstance(jTable.getColumnModel().getColumn(0).getWidth()-23, jTable.getRowHeight()-23, Image.SCALE_SMOOTH);
        this.scaledPacUpImage = pacManUpImg.getImage().getScaledInstance(jTable.getColumnModel().getColumn(0).getWidth()-23, jTable.getRowHeight()-23, Image.SCALE_SMOOTH);
        this.scaledPacLeftImage = pacManLeftImg.getImage().getScaledInstance(jTable.getColumnModel().getColumn(0).getWidth()-23, jTable.getRowHeight()-23, Image.SCALE_SMOOTH);
        this.scaledPacDownImage = pacManDownImg.getImage().getScaledInstance(jTable.getColumnModel().getColumn(0).getWidth()-23, jTable.getRowHeight()-23, Image.SCALE_SMOOTH);
        this.scaledPacClosedImage = pacManClosedImg.getImage().getScaledInstance(jTable.getColumnModel().getColumn(0).getWidth()-23, jTable.getRowHeight()-23, Image.SCALE_SMOOTH);
    }

    public ListSelectionModel createSelectionModel() {
        ListSelectionModel myModel = new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1) {

            }
        };
        return myModel;
    }
}




