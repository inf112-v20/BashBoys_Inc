package inf112.skeleton.app.gui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import inf112.skeleton.app.GameClass;

public interface IGuiElement {

    /**
     * Start the card gui, adds card and register and end Turn button
     * @param stage - gui stage to be added to
     * @param game - GameClass
     * @param player - player which gui is for
     */
    void initialize(Stage stage, GameClass game, int player);

    /**
     * Updates the GuiElement
     * @param stage - gui stage to be added to
     * @param game - amount of cards present
     */
    void update(Stage stage, GameClass game);

    /**
     * Sets a Dividing resolution const for gui element depended on size of screen
     * @param div - Div const
     */
    void setDivRes(float div);

}
