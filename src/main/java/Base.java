import com.googlecode.lanterna.graphics.TextGraphics;

abstract class Base {
    protected Position position;

    public Base (int i, int j) {
        position = new Position(i,j);
    }

    public Position getPosition () {
        return position;
    }

    public void setPosition (Position pos) {
        position.setX(pos.getX());
        position.setY(pos.getY());
    }
    abstract void draw(TextGraphics graphics);
}