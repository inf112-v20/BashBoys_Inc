package inf112.skeleton.app.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import inf112.skeleton.app.cards.ICard;

/**
 * @class Card
 * Creates a Actor (ImageButton) representing a Card.
 */
public class GuiFactory {

    /**
     * Get texture for param path
     * @param path - Path of img for texture
     * @return texture from img path
     */
    static public TextureRegionDrawable getTexture(String path){
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
    static public ButtonCard createCard(int x, int y, ICard cardType) {
        final ButtonCard button = new ButtonCard(getTexture(GuiFactory.getCardPath(cardType.getName())),cardType);

        button.setWidth(75);
        button.setHeight(100);
        button.setPosition(x, y);
        return button;
    }

    /**
     * Create register/button at (x,y)
     * @param x - x position
     * @param y - y position
     * @return Register
     */
    static public Register createRegister(int x, int y){
        final Register button = new Register(getTexture("assets/gui/holder.png"));
        button.setWidth(75);
        button.setHeight(100);
        button.setPosition(x,y);

        return button;
    }

    /**
     * Get the img-path associated with the ICard name
     * @param type - Type of card/Name
     * @return String path of img
     */
    static public String getCardPath(String type){
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
            case "Back Up":
                location += "backUp";
                break;
        }

        return location+fileFormat;
    }
}
