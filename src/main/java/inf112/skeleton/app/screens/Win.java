package inf112.skeleton.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import inf112.skeleton.app.gui.Text;

public class Win implements Screen {
    private Stage stage;
    private Text name;
    String winner;

    
    
    public Win(String s) {
        winner = s;
    }
    
    @Override
    public void show(){
        stage = new Stage();
        name = new Text(winner+" won!!!");
        name.setPosition(500, 500);
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
