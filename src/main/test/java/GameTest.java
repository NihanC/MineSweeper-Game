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
    public void testWorldGeneration() {
        Board easyBoard = new Board(Level.EASY);
        assertEquals(8, easyBoard.getRowNum());
        assertEquals(8, easyBoard.getColNum());

        Board mediumBoard = new Board(Level.MEDIUM);
        assertEquals(16, mediumBoard.getRowNum());
        assertEquals(16, mediumBoard.getColNum());

        Board hardBoard = new Board(Level.HARD);
        assertEquals(16, hardBoard.getRowNum());
        assertEquals(32, hardBoard.getColNum());
    }

}
