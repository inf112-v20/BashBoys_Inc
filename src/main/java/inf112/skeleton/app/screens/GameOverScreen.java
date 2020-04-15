package inf112.skeleton.app.screens;

import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import inf112.skeleton.app.GameClass;
import inf112.skeleton.app.Player;
import inf112.skeleton.app.Setting;
import inf112.skeleton.app.gui.Text;

public class GameOverScreen implements Screen {
    private Stage stage;
    private GameView g;
    private Setting set;
    
    public GameOverScreen(GameView g,Setting set) {
        this.g = g;
        this.set = set;
    }
    
    @Override
    public void show(){
        stage = new Stage();
        
        Text name = new Text("You lost");
        name.setPosition(500, 500);
        name.setColor(Color.BLACK);
        stage.addActor(name);
        
        BitmapFont font = new BitmapFont();
        Skin skin = new Skin(Gdx.files.internal("assets/gui/skin/uiskin.json"));
        TextureAtlas atlas = new TextureAtlas("assets/gui/skin/uiskin.atlas");
        skin.addRegions(atlas);
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font;

        style.up = skin.getDrawable("apptheme_btn_radio_on_holo_light");
        style.down = skin.getDrawable("apptheme_btn_radio_on_focused_holo_light");
        
        TextButton start = new TextButton("Play", style);
        start.setWidth(100);
        start.setHeight(100);
        start.setPosition(150, 100);
        start.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float xx, float yy){
                set.spectator = true;
                ((Game) Gdx.app.getApplicationListener()).setScreen(g);
            }
        });
        stage.addActor(start);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void dispose(){
        stage.dispose();
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height){
        // Nothing
    }

    @Override
    public void pause(){
        // Implemented method
    }

    @Override
    public void resume(){
        // Implemented method
    }

    @Override
    public void hide(){
        // TODO Auto-generated method stub
        
    }
}
