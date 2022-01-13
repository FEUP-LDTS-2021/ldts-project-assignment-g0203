import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    private int width;
    private int height;
    private int points = 0;
    private Snake baby;
    private List<Wall> walls;
    private List<Apple> apples = new ArrayList<>();
    private BoardMenu boardMenu;
    public boolean available = true;
    public Position snake[] = new Position[10000];
    public int size;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        baby = new Snake(width/2,height/2);
        snake[0] = baby.position;
        this.walls = createWalls();
        this.apples = createApples();
        this.size = 1;
    }

    private List<Wall> createWalls() {
        walls = new ArrayList<>();

        for (int w = 0; w < width; w++) {
            walls.add(new Wall(w, 0));
            walls.add(new Wall(w, height - 1));
        }
        for (int i = 1; i < height - 1; i++) {
            walls.add(new Wall(0, i));
            walls.add(new Wall(width - 1, i));
        }
        return walls;
    }

    public void processKey(KeyStroke key) throws IOException {
        switch (key.getKeyType()) {
            case ArrowUp -> moveSnake(baby.moveUp());
            case ArrowDown -> moveSnake(baby.moveDown());
            case ArrowLeft -> moveSnake(baby.moveLeft());
            case ArrowRight -> moveSnake(baby.moveRight());
            case Escape -> {
                boardMenu = new BoardMenu();
                boardMenu.run();
            }
        }
    }

    public void moveSnake(Position position) throws IOException {
        if (canSnakeMove(position)) {
            baby.setPosition(position);
        }
        else {
            BoardMenu gameOver = new BoardMenu();
            gameOver.gameOvermenu();
            System.out.println("You went out of boundaries!");
        }
        retrieveApples(position);
        if(apples.isEmpty()) createApples();
    }

    private boolean canSnakeMove(Position position) {
        if (position.getX() < 0 || position.getX() > width) return false;
        else if (position.getY() < 0 || position.getY() > height) return false;
        for ( Wall wall : walls) {
            if (wall.getPosition().equals(position)) {
                return false;
            }
        }
        for (int i = size - 1; i > 0; i--) {
            snake[i] = snake[i - 1];
        }
        for (int i = 1; i < size; i++) {
            if (snake[0] == snake [i]) return false;
        }
        return true;
    }

    private List<Apple> createApples() {
        Random random = new Random();
        apples.add(new Apple(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1));
        return apples;
    }

    private void retrieveApples(Position position) {
        for(Apple apple : apples)
            if(apple.getPosition().equals(baby.getPosition())) {
                apples.remove(apple);
                points++;
                drawSnake(baby.position);
                break;
            }
    }

    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#000000"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
        graphics.setForegroundColor(TextColor.Factory.fromString("#FFFF33"));
        baby.draw(graphics);

        for (Wall wall : walls) {
            wall.draw(graphics);
        }
        for(Apple apple : apples)
            apple.draw(graphics);
    }

    private void drawSnake(Position position) {

        size++;
        Snake body = new Snake(position.getX(), position.getY() - 1);
        snake[size - 1] = body.position;


    }
}