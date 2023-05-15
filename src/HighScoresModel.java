import javax.swing.*;
import java.io.*;
import java.util.Vector;

public class HighScoresModel extends AbstractListModel implements Serializable {

    private Vector<PacMan> names;

    public HighScoresModel(Vector<PacMan> names) {
        this.names = names;
    }

    @Override
    public int getSize() {
        return names.size();
    }

    @Override
    public Object getElementAt(int index) {
        return names.elementAt(index);
    }

    public Vector<PacMan> getNames() {
        return names;
    }

    public void setNames(Vector<PacMan> names) {
        this.names = names;
    }
    public void writeToFile() throws IOException {
        File file = new File("HighScores.txt");
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            for (int i = 0; i < names.size(); i++)
                outputStream.writeObject(names.get(i));
        }
    }
}
