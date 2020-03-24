package inf112.skeleton.app.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import inf112.skeleton.app.cards.ICard;

/**
 * @class Card
 * Creates a Actor (ImageButton) representing a Card.
 */
public class GuiFactory {

    private static float WIDTH = 389;
    private static float HEIGHT = 596;

    /**
     * Get texture for param path
     * @param path - Path of img for texture
     * @return texture from img path
     */
    static TextureRegionDrawable getTexture(String path){
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
    static ButtonCard createCard(int x, int y, ICard cardType) {
        final ButtonCard button = new ButtonCard(getTexture(GuiFactory.getCardPath(cardType.getName())),cardType);

        button.setWidth(WIDTH/4);
        button.setHeight(HEIGHT/4);
        button.setPosition(x, y);
        return button;
    }

    /**
     * Create register/button at (x,y)
     * @param x - x position
     * @param y - y position
     * @return Register
     */
    static Register createRegister(int x, int y){
        final Register button = new Register(getTexture("assets/gui/holder.png"));
        button.setWidth(WIDTH/5);
        button.setHeight(HEIGHT/5);
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
     static TextButton.TextButtonStyle getTextStyle(String path){
        BitmapFont font = new BitmapFont();
        Skin skin = new Skin();
        TextureAtlas atlas = new TextureAtlas(path);
        skin.addRegions(atlas);
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font;
        return style;
    }

    static float getWidth(){
        return WIDTH;
    }

    static float getHeight(){
        return HEIGHT;
    }

}
