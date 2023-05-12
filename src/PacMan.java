import java.util.Vector;

public class PacMan {
    private String username;
    protected int x;
    protected int y;
    private static Vector<PacMan> usernames = new Vector<>();
    private int points;

    public PacMan() {
        this.points = 0;
        usernames.add(this);
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
}

