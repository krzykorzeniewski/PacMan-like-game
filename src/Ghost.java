import java.awt.*;

public class Ghost {

    private int movementSpeed;
    private Color color;
    private String[] bonuses = {"Power Pellet", "Speed Boost", "Shield", "Double Points", "Super Jump"};

    public Ghost(int movementSpeed, Color color) {
        this.movementSpeed = movementSpeed;
        this.color = color;
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

    public Color getColor() {
        return color;
    }

    public String[] getBonuses() {
        return bonuses;
    }
}
