package inf112.skeleton.app;

import inf112.skeleton.app.screens.Menu;

public class ViewEngine extends com.badlogic.gdx.Game {
    GameClass g;
    Board board;
    public ViewEngine(GameClass g) {
        this.g = g;
        this.board = g.getBoard();
    }

    @Override
    public void create(){
        setScreen(new Menu(g));        
    }

}
