package inf112.skeleton.app.gui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class Text extends TextButton {

    /**
     * A TextButton which displays a text on screen
     * @param text - String to be displayed
     */
    public Text(String text) {
        super(text, GuiFactory.getTextStyle("assets/gui/skin/uiskin.atlas")); // Random Skin Found online, temp
        BitmapFont font = new BitmapFont();
        TextButtonStyle style = new TextButtonStyle();
        style.font = font;
    }

}
