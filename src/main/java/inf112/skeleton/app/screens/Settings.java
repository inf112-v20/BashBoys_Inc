package inf112.skeleton.app.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import inf112.skeleton.app.Setting;

public class Settings implements Screen {

    private Stage stage;
    private Menu m;
    private Setting set;
    private CheckBox devBox,musicBox;
    
    public Settings(Menu m,Setting set) {
        this.m = m;
        this.set = set;
    }
    
    @Override
    public void show(){
        stage = new Stage();
        BitmapFont font = new BitmapFont();
        Skin skin = new Skin(Gdx.files.internal("assets/gui/skin/uiskin.json"));
        TextureAtlas atlas = new TextureAtlas("assets/gui/skin/uiskin.atlas");
        skin.addRegions(atlas);
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font;

        style.up = skin.getDrawable("apptheme_btn_radio_on_holo_light");
        style.down = skin.getDrawable("apptheme_btn_radio_on_focused_holo_light");
        
        TextButton button = new TextButton("Back", style);
        button.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                set.music = musicBox.isChecked();
                set.devMode = devBox.isChecked();
                ((Game)Gdx.app.getApplicationListener()).setScreen(m);
            }
        });
        
        stage.addActor(button);
        musicBox = new CheckBox("Music",skin);
        musicBox.setPosition(400, 400);
        if(set.music)
            musicBox.toggle();
        stage.addActor(musicBox);
        
        devBox = new CheckBox("Delevoper Mode",skin);
        if(set.devMode)
            devBox.toggle();
        devBox.setPosition(400, 500);
        stage.addActor(devBox);
        
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
        
    }

    @Override
    public void resize(int width, int height){
        // TODO Auto-generated method stub
        
    }

    @Override
    public void pause(){
        // TODO Auto-generated method stub
        
    }

    @Override
    public void resume(){
        // TODO Auto-generated method stub
        
    }

    @Override
    public void hide(){
        // TODO Auto-generated method stub
        
    }

    @Override
    public void dispose(){
        // TODO Auto-generated method stub
        
    }

}
