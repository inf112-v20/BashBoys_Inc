package inf112.skeleton.app.enums;

import com.badlogic.gdx.Gdx;

public enum Metrics {

    SCREEN(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()),
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
