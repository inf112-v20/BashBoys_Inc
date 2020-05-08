package inf112.skeleton.app.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import inf112.skeleton.app.Setting;

/**
 * Settings Screen
 */
public class Settings implements Screen {

    private Stage stage;
    private Menu m;
    private Setting set;
    private CheckBox devBox, musicBox;

    public Settings(Menu m, Setting set) {
        this.m = m;
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

        TextButton button = new TextButton("Back", skin);
        button.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                set.music = musicBox.isChecked();
                set.devMode = devBox.isChecked();
                ((Game) Gdx.app.getApplicationListener()).setScreen(m);
            }
        });

        stage.addActor(button);

        // Adds Music setting check box
        musicBox = new CheckBox("Music", skin);
        musicBox.setPosition(400, 350);
        if (set.music)
            musicBox.toggle();
        stage.addActor(musicBox);

        // Add devMode Settings check box
        devBox = new CheckBox("Developer Mode", skin);
        if (set.devMode)
            devBox.toggle();
        devBox.setPosition(400, 500);
        stage.addActor(devBox);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // Auto-generated method stub

    }

    @Override
    public void pause() {
        // Auto-generated method stub

    }

    @Override
    public void resume() {
        // Auto-generated method stub

    }

    @Override
    public void hide() {
        // Auto-generated method stub

    }

    @Override
    public void dispose() {
        // Auto-generated method stub

    }

}
