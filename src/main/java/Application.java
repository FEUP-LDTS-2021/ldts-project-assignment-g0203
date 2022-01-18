import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        try {
           Menu menu = new Menu(60, 30);
           menu.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}