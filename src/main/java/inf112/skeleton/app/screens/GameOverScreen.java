package inf112.skeleton.app.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import inf112.skeleton.app.Setting;

public class GameOverScreen implements Screen {
    private Stage stage;
    private GameView g;
    private Setting set;

    public GameOverScreen(GameView g, Setting set) {
        this.g = g;
        this.set = set;
    }

    @Override
    public void show() {
        stage = new Stage();

        Texture imgTexture = new Texture(Gdx.files.internal("assets/Background.png"));
        Image img = new Image(imgTexture);
        img.setPosition(0, Gdx.graphics.getHeight() - img.getHeight());
        stage.addActor(img);

        Skin skin = new Skin(Gdx.files.internal("assets/gui/skin/CustomSkin.json"));

        Label name = new Label("You lost", skin);
        name.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        name.setColor(Color.RED);
        stage.addActor(name);

        TextButton start = new TextButton("Play", skin);
        start.setPosition(150, 550);
        start.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float xx, float yy) {
                set.spectator = true;
                ((Game) Gdx.app.getApplicationListener()).setScreen(g);
            }
        });
        stage.addActor(start);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // Nothing
    }

    @Override
    public void pause() {
        // Implemented method
    }

    @Override
    public void resume() {
        // Implemented method
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }
}
