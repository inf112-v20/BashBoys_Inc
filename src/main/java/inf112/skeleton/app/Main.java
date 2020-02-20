package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();

        cfg.title = "RoboRally";
        cfg.width = 768;
        cfg.height = 768;

        Game game = new Game(2, cfg.width, cfg.height);
        LwjglApplication app = new LwjglApplication(new ViewEngine(game), cfg);
    }
}
