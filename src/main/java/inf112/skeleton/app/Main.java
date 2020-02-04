package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "roborally";
        cfg.width = 768;
        cfg.height = 768;

		cfg.title = "hello-world";
		cfg.width = width;
		cfg.height = height;

        new LwjglApplication(new Map(), cfg);

    }
}
