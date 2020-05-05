package inf112.skeleton.app.screens;

import java.io.IOException;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import inf112.skeleton.app.GameClass;
import inf112.skeleton.app.Player;
import inf112.skeleton.app.Setting;
import inf112.skeleton.app.object.Robot;

public class Menu implements Screen {
    private Stage stage;
    private TextField name, ipBox;

    private GameClass g;
    private Menu m;
    private Setting set = new Setting();

    public Menu(GameClass g) {
        this.g = g;
        m = this;
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Texture imgTexture = new Texture(Gdx.files.internal("assets/Background.png"));
        Image img = new Image(imgTexture);
        img.setPosition(0, Gdx.graphics.getHeight() - img.getHeight());
        stage.addActor(img);

        Skin skin = new Skin(Gdx.files.internal("assets/gui/skin/CustomSkin.json"));

        TextButton playButton = new TextButton("Play", skin);
        playButton.setPosition(150, 900);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float xx, float yy) {
                Robot r = new Robot(1, 2, "robot 1");
                g.getBoard().addItem(r, 1, 2);
                g.players().add(new Player(name.getText(), "127.0.0.1", 7777, r, 0));
                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameView(g, 0, set));
            }
        });
        stage.addActor(playButton);

        TextButton hostButton = new TextButton("Host", skin);
        hostButton.setPosition(150, 750);
        hostButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float xx, float yy) {
                g.players().add(new Player(name.getText(), ipBox.getText(), 25565, new Robot(0, 0, "robot 1"), 0));
                ((Game) Gdx.app.getApplicationListener()).setScreen(new HostScreen(m, g, set));
            }
        });
        stage.addActor(hostButton);

        TextButton joinButton = new TextButton("Join", skin);
        joinButton.setPosition(150, 600);
        joinButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float xx, float yy) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new JoinScreen(m, g, set));
            }
        });
        stage.addActor(joinButton);
        
        TextButton settingsButton = new TextButton("Settings", skin);
        settingsButton.setPosition(150, 450);
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float xx, float yy) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Settings(m, set));
            }
        });
        stage.addActor(settingsButton);
        
        TextButton devNotes = new TextButton("Dev Notes", skin);
        devNotes.setPosition(150, 300);
        devNotes.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float xx, float yy) {
                notepad("Devmode");
            }
        });
        stage.addActor(devNotes);
        
        TextButton credits = new TextButton("Credits", skin);
        credits.setPosition(150, 150);
        credits.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float xx, float yy) {
                notepad("Credits");
            }
        });
        stage.addActor(credits);
        

        Label nameStr = new Label("Name", skin);
        nameStr.setPosition(710, 510);
        stage.addActor(nameStr);

        name = new TextField("George", skin);
        name.setPosition(700, 450);
        name.setSize(300, 60);
        stage.addActor(name);

        Label ipStr = new Label("IP Address", skin);
        ipStr.setPosition(710, 360);
        stage.addActor(ipStr);

        ipBox = new TextField("192.168", skin);
        ipBox.setPosition(700, 300);
        ipBox.setSize(300, 60);
        stage.addActor(ipBox);
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
    
    //Temp notepad opener, opens from notes in assets folder
    static private void notepad(String file) {
        Runtime rs = Runtime.getRuntime();
        try {
            rs.exec("notepad ./assets/notes/"+file+".txt");
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
}
