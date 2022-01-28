package snaketest;

import com.googlecode.lanterna.input.KeyStroke;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import snake.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardTest {
    Board test;
    Snake baby;
    List<Wall> walls;
    List<Apple> juice;
    int width = 40, height = 20, speed = 0;

    @BeforeEach
    public void createBoard() {
        test = new Board(width, height, speed);
        baby = new Snake(Direction.UP);
    }

    @Test
    public void checkValues() {
        assertEquals(width, test.getWidth());
        assertEquals(height, test.getHeight());
        assertEquals(speed, test.getSpeed());
    }

    @Test
    public void assureWalls() {
        assert(walls == null);
        walls = test.createWalls();
        assert(walls != null);
    }

    @Test
    public void assureApples() {
        assert(juice == null);
        juice =test.createApples();
        assert(juice != null);
    }

    @Test
    public void assureRetrieve() {
        juice = test.createApples();
        assert(juice != null);
        //assert(baby.getHead().equals(juice.getFirst()));
    }

    @Test
    public void eatApple() {

    }
}
