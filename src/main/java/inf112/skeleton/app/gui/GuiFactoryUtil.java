package inf112.skeleton.app.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import inf112.skeleton.app.Board;
import inf112.skeleton.app.enums.Metrics;
import inf112.skeleton.app.interfaces.ICard;

/**
 * @class Card
 * Creates a Actor (ImageButton) representing a Card.
 */
public class GuiFactoryUtil {

    public static float ratio = (389/4f)/(596/4f);
    public static float HEIGHT = Gdx.graphics.getHeight()/5f;
    public static float WIDTH = HEIGHT*ratio;


    /**
     * Get texture for param path
     * @param path - Path of img for texture
     * @return texture from img path
     */
    public static TextureRegionDrawable getTexture(String path){
        FileHandle fileHandle = Gdx.files.internal(path);
        Texture tex = new Texture(fileHandle);
        TextureRegion texRegion = new TextureRegion(tex);
        return  new TextureRegionDrawable(texRegion);
    }

    /**
     * Creates a card/button
     * @param x - x position
     * @param y - y position
     * @return ButtonCard
     */
    static ButtonCard createCard(float x, float y, float width, float height, ICard cardType) {
        final ButtonCard button = new ButtonCard(getTexture(GuiFactoryUtil.getCardPath(cardType.getName())),cardType);
        button.setWidth(width);
        button.setHeight(height);

        button.setPosition(x, y);
        return button;
    }

    /**
     * Create register/button at (x,y)
     * @param x - x position
     * @param y - y position
     * @return Register
     */
    static Register createRegister(float x, float y, float width, float height){
        final Register button = new Register(getTexture("assets/gui/invholder.png"));
        button.setWidth(width);
        button.setHeight(height);
        button.setPosition(x,y);

        return button;
    }

    /**
     * Get the img-path associated with the ICard name
     * @param type - Type of card/Name
     * @return String path of img
     */
    private static String getCardPath(String type){
        String location = "assets/gui/cards/";
        String fileFormat = ".png";
        switch (type){
            case "U-turn":
                location += "rotate";
                break;
            case "Rotate Right":
                location += "rotateRight";
                break;
            case "Rotate Left":
                location += "rotateLeft";
                break;
            case "Move 1":
                location += "move1";
                break;
            case "Move 2":
                location += "move2";
                break;
            case "Move 3":
                location += "move3";
                break;
            default:
                location += "backUp";
                break;
        }
        return location+fileFormat;
    }

    /**
     * Get Text Style
     * @param path - path of Text-style
     * @return TextButton.TextButtonStyle Style
     */
    public static TextButton.TextButtonStyle getTextStyle(String path) {
        TextureAtlas atlas = new TextureAtlas("assets/gui/skin/CustomSkin.atlas");
        BitmapFont font = new BitmapFont(Gdx.files.internal("assets/gui/skin/CustomFont20.fnt"), atlas.findRegion("CustomFont20"));
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font;
        return style;
    }

    /**
     * Get Base Width which each Gui element is based on
     *
     * @return Base Width
     */
    public static float getWidth() {
        return WIDTH;
    }

    /**
     * Get Base Height which each Gui element is based on
     *
     * @return Base Height
     */
    public static float getHeight() {
        return HEIGHT;
    }

    public static float getScaleConstant(Board b) {
        float scale = 1f;
        if (Gdx.graphics.getHeight() < (Metrics.TILE.height * b.height)) {
            scale = 1.3f;
        }
        return scale;
    }

}
