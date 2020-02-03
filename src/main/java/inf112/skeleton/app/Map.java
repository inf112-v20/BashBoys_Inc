package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Map implements ApplicationListener {
    private SpriteBatch batch;
    private ShapeRenderer shape;

    public int width;
    public int height;
    private Object[][] board;

    /**
     * Constructs a empty board
     * @param width
     * @param height
     */
    Map(int width, int height){
        this.width = width;
        this.height = height;
        board = new Object[width][height];
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        shape = new ShapeRenderer();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(Color.GRAY.r, Color.GRAY.g, Color.GRAY.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.end();

        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(Color.BLACK);
        for (int i = 0; i < height / 32; i++) {
            shape.line(0, i * 32, width, i * 32);
        }
        for (int i = 0; i < width / 32; i++) {
            shape.line(i * 32, 0, i * 32, height);
        }
        shape.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        shape.dispose();
    }
}
