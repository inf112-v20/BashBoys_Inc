package inf112.skeleton.app.enums;

import com.badlogic.gdx.Gdx;

public enum Metrics {

    // width and height of gdx screen
    SCREEN(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()),

    // width and height of tiles
    TILE(64,64);

    public final float width;
    public final float height;

    /**
     * Some Metrics for gui-elements
     * @param width - Width of element
     * @param height - Height of element
     */
    Metrics(float width, float height) {
        this.width = width;
        this.height = height;
    }



}
