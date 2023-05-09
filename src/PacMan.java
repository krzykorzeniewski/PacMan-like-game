import java.util.Vector;

public class PacMan {
    private String username;
    private static Vector<String> usernames;

    public PacMan(String username) {
        this.username = username;
        this.usernames = new Vector<>();
        usernames.add(this.username);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static Vector<String> getUsernames() {
        return usernames;
    }
}
