package inf112.skeleton.app;

import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import inf112.skeleton.app.enums.LeftRight;
import inf112.skeleton.app.object.Robot;

public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();

        cfg.title = "RoboRally";
        cfg.width = 768;
        cfg.height = 768;
        
        Game game = new Game(2, cfg.width, cfg.height);
        game.play(cfg);
        
    }
}
