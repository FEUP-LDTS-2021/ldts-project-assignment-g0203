import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.io.IOException;

public class Board {
    private int width;
    private int height;
    private Snake baby;
    public boolean available = true;
    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        baby = new Snake(10,10);
    }

    private void configureScreen (TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#000000"));
        graphics.fillRectangle(new TerminalPosition(0,0), new TerminalSize(width, height), ' ');
    }

    public void draw (TextGraphics graphics) throws IOException {
        configureScreen(graphics);
        baby.draw(graphics);
    }

    public void processKey(KeyStroke key) throws IOException{
        // if else statements to move my character
        if (key.getKeyType() == KeyType.ArrowDown) {
            moveHero(baby.moveUp());
        } else if (key.getKeyType() == KeyType.ArrowUp) {
            moveHero(baby.moveDown());
        } else if (key.getKeyType() == KeyType.ArrowLeft) {
            moveHero(baby.moveLeft());
        } else if (key.getKeyType() == KeyType.ArrowRight) {
            moveHero(baby.moveRight());
        } else if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'q') {
            available = false;
        } else if (key.getKeyType() == KeyType.EOF) {
            available = false;
        }
    }

    public void moveHero(Position position) {
        if(position.getX() > 0 || position.getX() < width || position.getY() > 0 || position.getY() < height) {
            baby.setPosition(position);
        }
        else {
            System.out.println("You went out of boundaries!");
        }
    }
}
