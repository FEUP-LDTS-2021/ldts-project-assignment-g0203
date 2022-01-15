import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardTest {
    Board board;

    @BeforeEach
    public void createBoard() {
        board = new Board(40,10);
    }

    @Test
    public void testDrawSnake() {
        int ogSize = board.size;
        board.drawSnake(new Position(0, 0));
        assertEquals(ogSize + 1, board.size);
    }
}