import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

public class GameBoardModel extends AbstractTableModel {

    private int width;
    private int length;

    protected Image ghost = new ImageIcon("Ghost.png").getImage();
    protected Image pacMan = new ImageIcon("PacMan.png").getImage();
    private int y;
    private Object[][] board = new Object[10][10]; //default size

    @Override
    public int getRowCount() {
        return board.length;
    }

    @Override
    public int getColumnCount() {
        return board[0].length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return board[rowIndex][columnIndex];
    }

    public Object[][] getBoard() {
        return board;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setBoardDimensions (int x, int y) {
        this.board = new Object[x][y];
    }

    public void setBoard(Object[][] board) {
        this.board = board;
    }
}
