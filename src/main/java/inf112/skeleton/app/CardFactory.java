package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * @class Card
 * Creates a Actor (ImageButton) representing a Card.
 */
public class CardFactory {

    /**
     * @param width
     * @param height
     */
    static public Actor create(int width, int height) {
        Texture myTexture = new Texture(Gdx.files.internal("assets/exampleCard.png"));
        TextureRegion myTextureRegion = new TextureRegion(myTexture);
        TextureRegionDrawable myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        final ImageButton button = new ImageButton(myTexRegionDrawable);
        button.setWidth(150);
        button.setHeight(150);
        button.setPosition(width, height);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                button.remove();
            }
        });
        return button;
    }
}
