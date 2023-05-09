import javax.swing.*;
import java.util.Vector;

public class HighScoresModel extends AbstractListModel {

    private Vector<String> names;

    public HighScoresModel(Vector<String> names) {
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

    public Vector<String> getNames() {
        return names;
    }

    public void setNames(Vector<String> names) {
        this.names = names;
    }
}
