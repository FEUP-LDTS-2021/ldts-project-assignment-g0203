package snake;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents the board where the user plays
 */
public class Board extends JFrame implements KeyListener {
    public BoardMenu boardMenu;
    public Snake baby;
    private List<Wall> walls;
    private List<Apple> apples = new ArrayList<>();
    public SoundEffect sound = new SoundEffect();
    public boolean available = true;
    public boolean retrieved = false;
    private final int width;
    private final int height;
    public int speed;
    public int size;
    public int points = 0;

    /**
     * Constructor of the class
     * @param width Size in the X axis
     * @param height Size in the Y axis
     * @param speed Speed the snake moves
     */
    public Board(int width, int height, int speed) {
        this.width = width;
        this.height = height;
        this.walls = createWalls();
        this.apples = createApples();
        this.speed = speed;
        baby = new Snake(Direction.UP);
        this.size = 3;
        this.addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
    }

    /**
     * Creates wall around the board
     * @return List with all walls
     */
    public List<Wall> createWalls() {
        walls = new ArrayList<>();
        for (int c = 0; c < width; c++) {
            walls.add(new Wall(c, 0));
            walls.add(new Wall(c, height - 1));
        }
        for (int r = 1; r < height - 1; r++) {
            walls.add(new Wall(0, r));
            walls.add(new Wall(width - 1, r));
        }
        return walls;
    }

    /**
     * Places apple on board
     * @return List with all apples
     */
    public List<Apple> createApples() {
        while (apples.size() < 3) {
            Random random = new Random();
            apples.add(new Apple(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1));
        }
        return apples;
    }

    /**
     * Checks if snake eats apple and, if so, removes it from board and plays sound
     */
    public void retrieveApples() {
        for(Apple apple : apples)
            if(apple.getPosition().equals(baby.getHead())) {
                apples.remove(apple);
                baby.increase();
                points++;
                retrieved = true;
                sound.inputSound("The_Office_US_Kevin_is_Cookie_Monster_Trim (online-audio-converter.com).wav");
                break;
            }
    }

    /**
     * Moves the snake, and, if possible, plays sound
     * @return Boolean dependant on whether it's possible to move to the position in question
     */
    public boolean moveSnake() {
        if (canSnakeMove(baby.getHead())) {
            baby.move();
            retrieved = false;
            retrieveApples();
            createApples();
            return true;
        }
        else {
            sound.inputSound("mixkit-retro-arcade-game-over-470.wav");
            retrieved = false;
            retrieveApples();
            if(apples.isEmpty()) createApples();
            return false;
        }
    }

    /**
     * Verifies if it's possible to move the snake to the wanted position
     * @param position Where the snake is going to
     * @return Boolean dependant on whether it's possible to move to the position in question
     */
    private boolean canSnakeMove(Position position) {
        for(int i = 0; i < baby.getBody().size() - 1; i++)
            if (baby.getBody().get(i).equals(baby.getHead()))
                return false;
        for (Wall wall : walls) {
            if (wall.getPosition().equals(baby.getHead()))
                return false;
        }
        if (position.getX() <= 0 || position.getX() >= width)
            return false;
        else return position.getY() > 0 || position.getY() < height;
    }

    /**
     * Draws board
     */
    public void drawBoardGraphics(TextGraphics graphics) {
        setVisible(true);
        graphics.setBackgroundColor(TextColor.Factory.fromString("#000000"));
        graphics.setForegroundColor(TextColor.Factory.fromString("#45733C"));
        baby.drawSnake(graphics);

        for (Wall wall : walls) {
            wall.draw(graphics);
        }
        for(Apple apple : apples)
            apple.draw(graphics);
    }

    /**
     * Getter for speed
     * @return Speed
     */
    public int getSpeed() {
        return speed;
    }


    // jframe in another class is better :)

    /**
     * Getter for width, overriden from JFrame
     * @return Width
     */
    @Override
    public int getWidth() {
        return width;
    }

    /**
     * Getter for height, overriden from JFrame
     * @return Height
     */
    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    /**
     * Makes the snake change direction based on the input
     * @param e User input
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP: {
                baby.setDirection(Direction.UP);
                break;
            }
            case KeyEvent.VK_DOWN: {
                baby.setDirection(Direction.DOWN);
                break;
            }
            case KeyEvent.VK_LEFT: {
                baby.setDirection(Direction.LEFT);
                break;
            }
            case KeyEvent.VK_RIGHT: {
                baby.setDirection(Direction.RIGHT);
                break;
            }
            case KeyEvent.VK_ESCAPE: {
                boardMenu = new BoardMenu();
                try {
                    boardMenu.run();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}