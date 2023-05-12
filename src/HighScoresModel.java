import javax.swing.*;
import java.util.Vector;

public class HighScoresModel extends AbstractListModel {

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
}
