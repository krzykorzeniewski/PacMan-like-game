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
                if (i == 0 || i == board.length-1 || j == 0 || j == board[0].length-1)
                    board[i][j] = 0;
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

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        this.board[rowIndex][columnIndex] = aValue;
        fireTableCellUpdated(rowIndex, columnIndex);
    }

}
