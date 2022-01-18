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
import java.io.*;

public class Menu {
    int width, height;
    private Screen screen;
    private Game game;
    private Board board;
    TextGraphics textGraphics;
    private boolean val;
    private char choice;

    public Menu(int width, int height) {
        this.width = width;
        this.height = height;
        try {
            Terminal terminal = new DefaultTerminalFactory().createTerminal();
            screen = new TerminalScreen(terminal);
            screen.setCursorPosition(null);
            screen.startScreen();
            screen.doResizeIfNecessary();

            textGraphics = screen.newTextGraphics();

        } catch (IOException e) {
            e.printStackTrace();
        }
        board = new Board(width, height);
    }

    private void printMenu() {
        screen.clear();
        textGraphics.setBackgroundColor(TextColor.Factory.fromString("#008000"));
        textGraphics.putString(6, 1, "Main Menu");
        textGraphics.putString(2, 3, "Play Game");
        textGraphics.putString(2, 5, "Instructions");
        textGraphics.putString(2, 7, "Exit");
        textGraphics.putString(21, 3, "1");
        textGraphics.putString(21, 5, "2");
        textGraphics.putString(21, 7, "0");
        textGraphics.drawRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), '*');
        textGraphics.putString(0, 12, "Enter your choice: ");
    }

    private char getInput() {
        int pos = 14;
        while (true) {
            try {
                KeyStroke key = screen.readInput();

                if (key.getKeyType() == KeyType.EOF) return '*';
                else if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'q') screen.close();

                else if (key.getCharacter() == '0' || key.getCharacter() == '1' || key.getCharacter() == '2') {
                    choice = key.getCharacter();
                    return choice;
                }
                textGraphics.putString(0, pos, "Input invalid. Please try again.");
                screen.refresh();

            } catch (IOException e) {
                e.printStackTrace();
            }
            pos += 1;
        }
    }

    private void doInput(int choice) throws IOException {
        switch (choice) {
            case '1' -> {
                game = new Game(screen);
                screen = game.screen;
                game.run();
            }
            case '2' -> {
                printInstructions();
                val = true;
            }
            case '0' -> {
                val = true;
                screen.close();
            }
            default -> System.out.println("Unknown error");
        }
    }

    private void draw() throws IOException {
        screen.clear();
        game.board.draw(screen.newTextGraphics());
        screen.refresh();
    }

    public void run() throws IOException {
        while (!val) {
            screen.clear();
            printMenu();
            screen.refresh();
            int choice = getInput();
            doInput(choice);
        }
    }

    private void printInstructions() throws IOException {
        int pos = 0;

        screen.clear();
        try {
            File f = new File("Instructions.txt");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                textGraphics.putString(0, pos, line);
                screen.refresh();
                pos += 1;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        pos += 1;

        while (true) {
            textGraphics.putString(0, pos, "Press Enter to go back to Menu or Esc to finish: ");
            pos += 1;
            screen.refresh();
            KeyStroke key = screen.readInput();
            if (key.getKeyType() == KeyType.Enter) run();
            else if (key.getKeyType() == KeyType.Escape) System.exit(0);
            else textGraphics.putString(0, pos, "Input invalid. Please try again.");
            pos += 1;
        }
    }
}