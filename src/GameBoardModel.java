import javax.swing.*;
import javax.swing.table.AbstractTableModel;


public class GameBoardModel extends AbstractTableModel {


    private int width;
    private int length;
    protected Object[][] board;

    public GameBoardModel(int width, int length) {
        this.width = width;
        this.length = length;
        this.board = new Object[width][length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = 0;
                int rand = (int)(Math.random()*3);
                if (rand == 1)
                    board[i][j] = 1;
                if (i % 2 == 0)
                    board[i][j] = 1;
            }
        }
    }

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

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        this.board[rowIndex][columnIndex] = aValue;
        fireTableCellUpdated(rowIndex, columnIndex);
    }

}
