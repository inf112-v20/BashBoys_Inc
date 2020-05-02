package inf112.skeleton.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Win implements Screen {
    private Stage stage;
    private String winner;

    
    
    public Win(String s) {
        winner = s;
    }
    
    @Override
    public void show(){
        stage = new Stage();
        Texture imgTexture = new Texture(Gdx.files.internal("assets/Background.png"));
        Image img = new Image(imgTexture);
        img.setPosition(0, Gdx.graphics.getHeight() - img.getHeight());
        stage.addActor(img);
        Skin skin = new Skin(Gdx.files.internal("assets/gui/skin/CustomSkin.json"));
        Label name = new Label(winner + " won!!!", skin);
        name.setPosition(500, 500);
        name.setColor(Color.BLACK);
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
