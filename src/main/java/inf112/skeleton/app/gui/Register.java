package inf112.skeleton.app.gui;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Register than extends ImageButton and add some custom features
 */
public class Register extends ImageButton {

    private boolean status = false;
    private ButtonCard card = null;
    public boolean disabled = false;

    public Register(TextureRegionDrawable texture){
        super(texture);
    }

    /**
     * Get status of register
     * @return if register is full/not
     */
    public boolean getStatus() {
        return status;
    }

    /**
     * Set status of register
     * @param status - true=full / false=empty
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * Get Card register to this register
     * @return - Registered ButtonCard
     */
    public ButtonCard getCard() {
        return card;
    }

    /**
     * Set card registered to this register
     * @param card - ButtonCard to be reignited
     */
    public void setCard(ButtonCard card) {
        this.card = card;
    }

    /**
     * If (x,y) location is contain within this register parameters
     * @param x - X location
     * @param y - Y Location
     * @return boolean if (x,y) is within this register
     */
    public boolean contains(float x, float y){
        // Buffer for how exact the check is
        float buffer = 0;
        if(disabled) return false;
        return (x > this.getX()-buffer && y > this.getY()-buffer && x < this.getX()+this.getWidth()+buffer && y < this.getY()+this.getHeight()+buffer);
    }


}
