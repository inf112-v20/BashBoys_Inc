package inf112.skeleton.app.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import inf112.skeleton.app.GameClass;
import inf112.skeleton.app.Player;
import inf112.skeleton.app.Setting;
import inf112.skeleton.app.object.Robot;

public class Menu implements Screen {
    private Stage stage;
    private TextField name;

    private GameClass g;
    private Menu m;
    private Setting set = new Setting();
    
    public Menu(GameClass g) {
        this.g = g;
        m = this;
    }
    
    @Override
    public void show(){
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        BitmapFont font = new BitmapFont();
        Skin skin = new Skin(Gdx.files.internal("assets/gui/skin/uiskin.json"));
        TextureAtlas atlas = new TextureAtlas("assets/gui/skin/uiskin.atlas");
        skin.addRegions(atlas);
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font;

        style.up = skin.getDrawable("apptheme_btn_radio_on_holo_light");
        style.down = skin.getDrawable("apptheme_btn_radio_on_focused_holo_light");

        TextButton playButton = new TextButton("Play",style);
        playButton.setWidth(100);
        playButton.setHeight(100);
        playButton.setPosition(150,100);
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float xx, float yy){
                Robot r = new Robot(1,2,"robot 1");
                g.getBoard().addItem(r, 1, 2);
                g.players().add(new Player(name.getText(),"127.0.0.1",7777,r,0));
                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameView(g,0,set));
            }
        });
        stage.addActor(playButton);
        
        TextButton settingsButton = new TextButton("Settings",style);
        settingsButton.setWidth(100);
        settingsButton.setHeight(100);
        settingsButton.setPosition(150,250);
        settingsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float xx, float yy){
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Settings(m,set));
            }
        });
        stage.addActor(settingsButton);
        
        TextButton hostButton = new TextButton("Host",style);
        hostButton.setWidth(100);
        hostButton.setHeight(100);
        hostButton.setPosition(150,400);
        hostButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float xx, float yy){
                g.players().add(new Player(name.getText(),"84.215.102.111",25565,new Robot(0,0,"robot 1"),0));
                ((Game) Gdx.app.getApplicationListener()).setScreen(new HostScreen(g,set));
            }
        });
        stage.addActor(hostButton);
        
        TextButton joinButton = new TextButton("Join",style);
        joinButton.setWidth(100);
        joinButton.setHeight(100);
        joinButton.setPosition(150,550);
        joinButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float xx, float yy){
                ((Game) Gdx.app.getApplicationListener()).setScreen(new JoinScreen(g,set));
            }
        });
        stage.addActor(joinButton);
        
        name = new TextField("abc",skin);
        name.setPosition(400, 400);
        name.setSize(300,60);
        stage.addActor(name);
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
