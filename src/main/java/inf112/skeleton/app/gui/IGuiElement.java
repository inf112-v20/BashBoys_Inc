package inf112.skeleton.app.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Stage;
import inf112.skeleton.app.GameClass;

public interface IGuiElement {

    /**
     * Start the card gui, adds card and register and end Turn button
     * @param stage gui stage to be added to
     * @param game amount of cards present
     */
    void initialize(Stage stage , GameClass game, int player);

    void update(Stage stage, GameClass game);

}
