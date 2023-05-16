import java.awt.*;

public class Ghost {
    private int x;
    private int y;
    private int direction;
    private String[] bonuses = {"Ghost Speed Boosted", "Ghost Speed Reduced", "Plus 50 Points", "Another Ghost Spawns", "Eliminate all ghosts"};

    public Ghost(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
