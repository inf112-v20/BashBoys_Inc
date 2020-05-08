package inf112.skeleton.app.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import inf112.skeleton.app.GameClass;
import inf112.skeleton.app.enums.Metrics;

import java.util.ArrayList;

public class GuiPanel implements IGuiElement {

    public ImageButton panel;
    public ArrayList<Image> tokens = new ArrayList<>();
    public ArrayList<Image> lifetokens = new ArrayList<>();

    private int player;

    public int default_hp;
    private int start_hp = 10;
    private int life;
    private float divRes;

    @Override
    public void initialize(Stage stage, GameClass game, int player){
        ImageButton panel = new ImageButton(GuiFactoryUtil.getTexture("assets/gui/Hud.png"));

        this.player = player;
        float imgWidth = panel.getWidth();
        float imgHeight = panel.getHeight();
        float ratio = imgHeight / imgWidth;

        panel.setWidth(Gdx.graphics.getWidth() - (game.getBoard().width * Metrics.TILE.width) / divRes);
        panel.setHeight(panel.getWidth() * ratio);
        panel.setPosition((game.getBoard().width * Metrics.TILE.width) / divRes,
                Gdx.graphics.getHeight() - panel.getHeight());
        panel.setZIndex(0);

        this.panel = panel;
        stage.addActor(panel);
        addDmgTokens(stage, game);
        addLifeTokens(stage, game);
    }

    @Override
    public void update(Stage stage, GameClass game){
        if (game.getPlayer().getRobot().getHp() != default_hp) {
            for(Image i : tokens) i.remove();
            tokens.clear();
            addDmgTokens(stage,game);
        }
        if (life != game.getPlayer().getLifes()) {
            for(Image i : lifetokens) i.remove();
            lifetokens.clear();
            addLifeTokens(stage, game);
        }

    }

    @Override
    public void setDivRes(float div) {
        this.divRes = div;
    }

    /**
     * Adds Damage tokens to panel
     * @param stage - UiStage to add tokens to
     * @param game - GameClass
     */
    public void addDmgTokens(Stage stage, GameClass game){
        int hp = game.players().get(player).getRobot().getHp();

        float marginY = (panel.getHeight() / 100);
        float marginX = (panel.getWidth() / 35);

        for (int i = start_hp-1; i >= hp ; i--) {
            Image token;
            if (i == 0) {
                token = new Image(GuiFactoryUtil.getTexture("assets/gui/Signs/DamageSignRed.png"));

            } else {
                token = new Image(GuiFactoryUtil.getTexture("assets/gui/Signs/DamageSign.png"));
            }
            token.setWidth(panel.getWidth() / 11.8f);
            token.setHeight(panel.getHeight() / 8);

            token.setPosition(
                    game.getBoard().width * (Metrics.TILE.width/divRes) + marginX
                            + i * (token.getWidth() + panel.getWidth() / 95),
                    panel.getY() + panel.getHeight() / 2 + marginY);

            stage.addActor(token);
            tokens.add(token);
        }
    }

    /**
     * Adds life tokens to panel
     * @param stage - UiStage to add tokens to
     * @param game - GameClass
     */
    public void addLifeTokens(Stage stage, GameClass game){
        life = game.getPlayer().getLifes();
        for (int i = 0; i < 3; i++) {
            Image token;
            if(i<life)
                token = new Image(GuiFactoryUtil.getTexture("assets/gui/Signs/LifeTokenOn.png"));
            else
                token = new Image(GuiFactoryUtil.getTexture("assets/gui/Signs/LifeTokenOff.png"));

            token.setWidth(panel.getWidth() / 10);
            token.setHeight(panel.getWidth() / 10);

            token.setPosition(
                    panel.getX() + panel.getWidth() / 24 * 7.8f + i * (token.getWidth() + panel.getWidth() / 45),
                    panel.getY() + panel.getHeight() / 12 * 9);

            stage.addActor(token);
            lifetokens.add(token);
        }
    }

}
