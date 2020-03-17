package inf112.skeleton.app.gui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class Text extends TextButton {

    private BitmapFont font;
    private TextButtonStyle style;

    public Text(String text) {
        super(text, GuiFactory.getTextStyle("assets/gui/skin/uiskin.atlas"));
        this.font = new BitmapFont();
        style = new TextButton.TextButtonStyle();
        style.font = font;
    }

}
