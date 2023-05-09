import javax.swing.table.AbstractTableModel;

public class GameBoardModel extends AbstractTableModel {

    private int x;
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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setBoardDimensions (int x, int y) {
        this.board = new Object[x][y];
    }
}
