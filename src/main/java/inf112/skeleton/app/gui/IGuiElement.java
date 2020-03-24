package inf112.skeleton.app.gui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import inf112.skeleton.app.Game;

public interface IGuiElement {

    /**
     * Initializes the gui-elements
     * @param stage - Stage to add Gui to
     * @param game - Current game
     */
    void initialize(Stage stage, Game game);

    /**
     * Updates current GUI
     * @param stage - Stage to update
     * @param game - Current game
     */
    void update(Stage stage, Game game);
}
