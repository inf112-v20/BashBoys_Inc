package inf112.skeleton.app.gui;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import inf112.skeleton.app.cards.ICard;

import java.awt.*;

/**
 * ButtonCard that extends ImageButton with some new features.
 */
public class ButtonCard extends ImageButton {

    private Point originPoint = null;
    private ICard type;
    public Register register = null;
    private Text priority;

    ButtonCard(TextureRegionDrawable texture, ICard type) {
        super(texture);
        this.type = type;
        priority = new Text(type.getPriority()+"");
    }

    @Override
    public void moveBy(float x, float y) {
        priority.moveBy(x,y);
        super.moveBy(x, y);
    }

    @Override
    public boolean remove() {
        priority.remove();
        return super.remove();
    }

    @Override
    public void setPosition(float x, float y) {
        priority.setPosition(x+this.getWidth()-this.getWidth()/2.3f,y+this.getHeight()-this.getHeight()/5);
        super.setPosition(x, y);
    }

    /**
     * Get the Priority element of the card
     * @return Text - Priority
     */
    public Text displayPriority(){
        return priority;
    }

    /**
     * Get the origin X point of this card
     * @return (float) X location of this card
     */
    public float getOriginX() {
        return (float) originPoint.getX();
    }

    /**
     * Get the origin Y point of this card
     * @return (float) Y location of this card
     */
    public float getOriginY() {
        return (float) originPoint.getY();
    }

    /**
     * Set the origin point of this card
     * @param originPoint - Point to be set as origin
     */
    public void setOriginPoint(Point originPoint) {
        this.originPoint = originPoint;
    }

    /**
     * Return the type of card
     * @return ICard card for this Button
     */
    public ICard getType(){
        return type;
    }



}
