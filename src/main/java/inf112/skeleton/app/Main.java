package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;


public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();

        cfg.title = "RoboRally";
        cfg.width = 1920;
        cfg.height = 1080;

        Game game = new Game();
        game.play(cfg);
        
    }
}
