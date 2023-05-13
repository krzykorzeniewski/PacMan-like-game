import java.util.Vector;

public class PacMan {
    private String username;
    private int x;
    private int y;
    private int direction; // 0-prawo, 1-dol, 2-lewo, 3-gora
    private boolean isAlive;
    private static Vector<PacMan> usernames = new Vector<>();
    private int points;

    public PacMan(int x, int y) {
        this.points = 0;
        this.isAlive = true;
        this.x = x;
        this.y = y;
        usernames.add(this);
        this.direction = 0;
    }

    public void addUsername(PacMan pac) {
        this.usernames.add(pac);
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static Vector<PacMan> getUsernames() {
        return usernames;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return "Username: "+username+" "+" High score: "+points;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
    public int getDirection() {
        return direction;
    }

    public boolean isAlive() {
        return isAlive;
    }
    public void addPoint() {
        points++;
    }
}

