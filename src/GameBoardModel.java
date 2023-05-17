import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collections;

public class GameBoardModel extends AbstractTableModel {


    private int width;
    private int length;
    protected Object[][] board;

    public GameBoardModel(int width, int length) {
        this.width = width;
        this.length = length;
        this.board = new Object[width][length];
        generateMap();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (i == board.length/2) {
                    board[i][j] = 1;
                }
                if (i == 1 || i == board.length-2 || j == 1 || j == board[0].length-2)
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
    public void generateMap() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = 0;
            }
        }

        board[1][1] = 1;

        ArrayList<int[]> neighbours = new ArrayList<>();
        ArrayList<int[]> temp = new ArrayList<>();
        temp.add(new int[]{1, 1});

        while (temp.size() != 0) {
            int[] currentCell = temp.get(temp.size()-1);
            int row = currentCell[0];
            int col = currentCell[1];
            if (col > 2 && board[row][col - 2].equals(0)) {
                neighbours.add(new int[]{row, col - 2});
            }
            if (row > 2 && board[row - 2][col].equals(0)) {
                neighbours.add(new int[]{row - 2, col});
            }
            if (col < board[0].length - 2 && board[row][col + 2].equals(0)) {
                neighbours.add(new int[]{row, col + 2});
            }
            if (row < board.length - 2 && board[row + 2][col].equals(0)) {
                neighbours.add(new int[]{row + 2, col});
            }
            Collections.shuffle(neighbours);
            if (!neighbours.isEmpty()) {
                int[] nextCell = neighbours.get(0);
                int nextRow = nextCell[0];
                int nextCol = nextCell[1];
                board[nextRow][nextCol] = 1;
                board[(row + nextRow) / 2][(col + nextCol) / 2] = 1;
                temp.add(nextCell);
            } else {
                temp.remove(temp.size()-1);
            }
            neighbours.clear();
        }
    }
}




