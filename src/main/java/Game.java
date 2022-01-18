import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Game {
    Screen screen;
    TextGraphics graphics;
    Board board;
    Snake baby;
    Position position;

    public Game(Screen screen) {
        try {
            this.screen = screen;
            // configuring the screen
            screen.refresh();

            graphics = screen.newTextGraphics();
            board = new Board(40,20);
            baby = new Snake(10,10);
            position = baby.position;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processKey(KeyStroke key) throws IOException {
        board.processKey(key);
    }

    public void draw() throws IOException {
        screen.clear();
        board.draw(graphics);
        screen.refresh();
    }

    public void run() throws IOException {
        while(board.available) {
            draw();
            KeyStroke key = screen.readInput();
            processKey(key);
        }
        screen.close();
    }

    public void close() throws IOException {

    }
}
