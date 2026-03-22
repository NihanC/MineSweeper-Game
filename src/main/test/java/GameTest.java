import org.junit.jupiter.api.Test;
import org.myteam.minesweeper.*;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {

    @Test
    public void testMines() {
        testMinesForLevel(Level.EASY);
        testMinesForLevel(Level.MEDIUM);
        testMinesForLevel(Level.HARD);
    }

    public void testMinesForLevel(Level level) {
        Random rand = new Random();
        for (int attempt = 0; attempt < 100; attempt++) {
            Board board = new Board(level);
            int rows = board.getRowNum();
            int cols = board.getColNum();
            int firstRow = rand.nextInt(board.getRowNum());
            int firstCol = rand.nextInt(board.getColNum());
            board.click(false,firstRow,firstCol,new Game(level));
            int mineCount = 0;
            for (int i = 0; i < rows; i++)
                for (int j = 0; j < cols; j++)
                    if (board.getGrid()[i][j] instanceof Mine)
                        mineCount++;

            assertEquals(board.getNrOfMines(), mineCount);
        }
    }

    @Test
    public void testRows() {
        testRowsForLevel(Level.EASY);
        testRowsForLevel(Level.MEDIUM);
        testRowsForLevel(Level.HARD);
    }

    public void testRowsForLevel(Level level) {
        Random rand = new Random();
        for (int attempt = 0; attempt < 100; attempt++) {
            Board board = new Board(Level.EASY);
            int rows = board.getRowNum();
            int firstRow = rand.nextInt(board.getRowNum());
            int firstCol = rand.nextInt(board.getColNum());
            board.click(false, firstRow, firstCol, new Game(Level.EASY));
            int rowCount = 0;
            for (int i = 0; i < rows; i++) {
                rowCount++;
            }
            assertEquals(board.getRowNum(), rowCount);
        }
    }

    @Test
    public void testColumns() {
        testColumnsForLevel(Level.EASY);
        testColumnsForLevel(Level.MEDIUM);
        testColumnsForLevel(Level.HARD);
    }

    public void testColumnsForLevel(Level level) {
        Random rand = new Random();
        for (int attempt = 0; attempt < 100; attempt++) {
            Board board = new Board(Level.EASY);
            int columns = board.getColNum();
            int firstRow = rand.nextInt(board.getRowNum());
            int firstCol = rand.nextInt(board.getColNum());
            board.click(false, firstRow, firstCol, new Game(Level.EASY));
            int columnCount = 0;
            for (int i = 0; i < columns; i++) {
                columnCount++;
            }
            assertEquals(board.getRowNum(), columnCount);
        }
    }

    @Test
    public void testWorldAccToDifficulty() {

    }
}
