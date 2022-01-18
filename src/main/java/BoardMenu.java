import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class BoardMenu {
    Screen screen;
    TextGraphics textGraphics;
    char value;

    public BoardMenu() {
        try {
            // configuring the terminal
            TerminalSize terminalSize = new TerminalSize(40, 10);
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
            Terminal terminal = terminalFactory.createTerminal();

            // configuring the screen
            screen = new TerminalScreen(terminal);
            screen.setCursorPosition(null);
            screen.startScreen();
            screen.doResizeIfNecessary();

            textGraphics = screen.newTextGraphics();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printMenu() {
        screen.clear();
        textGraphics.setBackgroundColor(TextColor.Factory.fromString("#008000"));
        textGraphics.putString(1, 1, "Are you sure you wanna exit the game?");
        textGraphics.putString(2, 2, "Yes.");
        textGraphics.putString(6, 2, "No.");
        textGraphics.drawRectangle(new TerminalPosition(0, 0), new TerminalSize(40, 10), '*');
        textGraphics.putString(1, 8, "Enter your choice: ");
    }

    private void doInput(KeyStroke key) throws IOException {
        switch (key.getCharacter()) {
            case 'y' -> {
                System.exit(0);
            }
            case 'n' -> {
                screen.close();
            }
            default -> System.out.println("Unknown error");
        }
    }

    public void run() throws IOException{
        screen.clear();
        printMenu();
        screen.refresh();
        doInput(screen.readInput());
        screen.refresh();
    }
}
