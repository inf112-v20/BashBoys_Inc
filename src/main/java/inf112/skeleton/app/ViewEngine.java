package inf112.skeleton.app;

import inf112.skeleton.app.screens.Menu;

public class ViewEngine extends com.badlogic.gdx.Game {
    private GameClass g;
    public ViewEngine(GameClass g) {
        this.g = g;
    }

    @Override
    public void create(){
        setScreen(new Menu(g));        
    }

}
