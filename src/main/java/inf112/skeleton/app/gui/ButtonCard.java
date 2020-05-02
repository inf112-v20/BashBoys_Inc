package inf112.skeleton.app.gui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import inf112.skeleton.app.interfaces.ICard;

import java.awt.*;

/**
 * ButtonCard that extends ImageButton with some new features.
 */
public class ButtonCard extends ImageButton {

    private Point originPoint = null;
    private ICard type;
    public Register register = null;
    private Text priority;
    public float start_width;
    public float start_height;
    private float boostX = 0;
    private float boostY = 0;

    ButtonCard(TextureRegionDrawable texture, ICard type) {
        super(texture);
        this.type = type;
        priority = new Text(type.getPriority() + "");
        priority.getStyle().font.getData().setScale(0.65f);
        this.updatePriorityPos();
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
        super.setPosition(x, y);
        this.updatePriorityPos();
    }

    public void updatePriorityPos(){
        priority.setPosition(
                this.getX() + boostX + (this.getWidth() / 10) * 5.2f,
                this.getY() + boostY + (this.getHeight() / 12) * 9.63f);
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

    public void reSize(float width, float height, Stage stage) {
        if (this.register == null) {
            this.setFontScale(0.65f, stage);
            this.boostX = 0;
            this.boostY = 0;
        } else {
            //float reScale = ((width - 20) / start_width);
            this.setFontScale(1, stage);
            this.boostX = 6;
            this.boostY = 4;
        }

        this.updatePriorityPos();
        this.setWidth(width);
        this.setHeight(height);
        this.updatePriorityPos();

    }

    private void setFontScale(float scale, Stage stage){
        this.priority.remove();
        this.priority = new Text(type.getPriority()+"");
        this.updatePriorityPos();
        this.priority.getStyle().font.getData().setScale(scale);
        stage.addActor(priority);
    }

}
