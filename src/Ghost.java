import java.awt.*;

public class Ghost {

    private int movementSpeed;
    private int x;
    private int y;
    private int direction;
    private boolean isAlive;
    private String[] bonuses = {"Power Pellet", "Speed Boost", "Shield", "Double Points", "Super Jump"};

    public Ghost(int x, int y) {
        this.x = x;
        this.y = y;
        this.isAlive = true;
    }

    public void generateBonus() {
        boolean temp = true;
        while (temp) {
            double rand = Math.random()*1;
            if (rand <= 0.25 && rand >= 0) {

            }
            else if (rand <= 0.50 && rand > 0.25) {

            }
            else if (rand > 0.50 && rand <= 0.75) {

            }
            else {

            }
        }
    }

    public int getMovementSpeed() {
        return movementSpeed;
    }

    public String[] getBonuses() {
        return bonuses;
    }

    public void setMovementSpeed(int movementSpeed) {
        this.movementSpeed = movementSpeed;
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

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setBonuses(String[] bonuses) {
        this.bonuses = bonuses;
    }
}
