package inf112.skeleton.app.gui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class Text extends TextButton {


    public Text(String text) {
        super(text, GuiFactoryUtil.getTextStyle("assets/gui/skin/CustomSkin.atlas"));
        BitmapFont font = new BitmapFont();
        TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font;
    }

}
