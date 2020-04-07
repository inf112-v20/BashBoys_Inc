package inf112.skeleton.app.gui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import inf112.skeleton.app.cards.ICard;
import inf112.skeleton.app.cards.MoveCard;
import inf112.skeleton.app.cards.RotateCard;
import inf112.skeleton.app.enums.LeftRight;

import java.awt.*;

/**
 * ButtonCard that extends ImageButton with some new features.
 */
public class ButtonCard extends ImageButton {

    private Point originPoint = null;
    private ICard type;
    public Register register = null;

    ButtonCard(TextureRegionDrawable texture, ICard type) {
        super(texture);
        this.type = type;
        priority = new Text(type.getPriority()+"");
    }
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

    /**
     * Reset size of card to default size
     */
    public void resetSize(){
        this.setWidth(GuiFactory.getWidth()/4);
        this.setHeight(GuiFactory.getHeight()/4);
    }

    public void reSize(float width, float height, Stage stage){

        float reScale = (width/start_width);
        System.out.println(reScale+"");
        if(reScale==0){reScale=1;}
        this.setFontScale(reScale,stage);

        this.setWidth(width);
        this.setHeight(height);
        this.updatePriorityPos();
    }

    private void setFontScale(float scale, Stage stage){
        displayPriority().remove();
        this.priority = new Text(type.getPriority()+"");
        this.updatePriorityPos();
        this.priority.getStyle().font.getData().setScale(scale);
        stage.addActor(priority);
    }

}
