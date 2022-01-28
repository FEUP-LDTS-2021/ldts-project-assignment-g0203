package snake;

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

/**
 * Menus that are accessed while playing the game
 */
public class BoardMenu {

    Screen screen;
    TextGraphics textGraphics;
    SoundEffect sound = new SoundEffect();

    /**
     * Constructor of the class
     */
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

    /**
     * Draws pause menu
     */
    private void pauseMenu() throws IOException {
        screen.clear();
        sound.inputSound("mixkit-unlock-game-notification-253.wav");
        textGraphics.setBackgroundColor(TextColor.Factory.fromString("#000000"));
        textGraphics.putString(1, 1, "Are you sure you wanna exit your game?");
        textGraphics.putString(17, 3, "Yes");
        textGraphics.putString(17, 5, "No");
        //textGraphics.drawRectangle(new TerminalPosition(0, 0), new TerminalSize(40, 10), '*');
        screen.refresh();
        processInputGameOver(screen.readInput());
        screen.refresh();
    }

    /**
     * Processes user's input
     * @param key User's input
     */
    private void processInput(KeyStroke key) throws IOException {
        switch (key.getCharacter()) {
            case 'y' : {
                System.exit(0);
                break;
            }
            case 'n' : {
                screen.close();
                break;
            }
            default : {
                System.out.println("Unknown error");
                break;
            }
        }
    }

    /**
     * Initializes class
     */
    public void run() throws IOException{
        screen.clear();
        pauseMenu();
        screen.refresh();
        processInput(screen.readInput());
        screen.refresh();
    }

    /**
     * Draws game over menu when snake dies
     * @param points Number of apples the snake ate
     */
    public void gameOverMenu(int points) throws IOException {
        screen.clear();
        textGraphics.setBackgroundColor(TextColor.Factory.fromString("#008000"));
        textGraphics.putString(10, 1, "snake.Game Over");
        textGraphics.putString(1, 3, "Points: " + points);
        textGraphics.drawRectangle(new TerminalPosition(0, 0), new TerminalSize(40, 10), '*');
        textGraphics.putString(1, 5, "Press any key to exit.");
        screen.refresh();
        processInputGameOver(screen.readInput());
        screen.refresh();
    }

    /**
     * Exits game after dying
     * @param key User's input
     */
    private void processInputGameOver(KeyStroke key) {
        if (key.getKeyType() == KeyType.Escape || key.getKeyType() == KeyType.ArrowUp || key.getKeyType() == KeyType.ArrowDown || key.getKeyType() == KeyType.ArrowLeft || key.getKeyType() == KeyType.ArrowRight) System.exit(0);
    }
}
