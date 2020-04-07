package inf112.skeleton.app.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import inf112.skeleton.app.GameClass;
import inf112.skeleton.app.enums.Metrics;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GuiPanel implements IGuiElement {

    public ImageButton panel;
    public ArrayList<Image> tokens = new ArrayList<>();
    public ArrayList<Image> lifetokens = new ArrayList<>();

    private int player;

    public int default_hp;

    @Override
    public void initialize(Stage stage, GameClass game, int player) {
        ImageButton panel = new ImageButton(GuiFactory.getTexture("assets/gui/Hud.png"));

        this.player = player;
        float imgWidth = panel.getWidth();
        float imgHeight = panel.getHeight();
        float ratio = imgHeight/imgWidth;

        panel.setWidth(Gdx.graphics.getWidth()-(game.getBoard().width*Metrics.TILE.width));
        panel.setHeight(panel.getWidth()*ratio);
        panel.setPosition(game.getBoard().width*Metrics.TILE.width,Gdx.graphics.getHeight()-panel.getHeight());
        panel.setZIndex(0);

        this.panel = panel;
        stage.addActor(panel);
        addDmgTokens(stage,game);
        addLifeTokens(stage,game);
    }

    @Override
    public void update(Stage stage, GameClass game) {
        if(game.players().get(player).getRobot().isDead()){
            for(Image i : tokens){
                i.remove();
            }
            tokens.clear();
        } else {
            if(default_hp > game.players().get(player).getRobot().getHp()){
                int hp = game.players().get(player).getRobot().getHp();
                int life_lost = default_hp-hp;
                ArrayList<Image> toBeDeleted = new ArrayList<>();

                for(int i = 0; i < life_lost; i++){
                    toBeDeleted.add(tokens.get(tokens.size()-1-i));
                }

                tokens.removeAll(toBeDeleted);
                for(Image i : toBeDeleted){
                    i.remove();
                }
                this.default_hp = hp;
                toBeDeleted.clear();
            } else if (default_hp < game.players().get(player).getRobot().getHp()){
                for(Image i : tokens){
                    i.remove();
                }
                tokens.clear();
                addDmgTokens(stage,game);
            }
        }




    }

    public void addDmgTokens(Stage stage, GameClass game){
        int hp = game.players().get(player).getRobot().getHp();
        this.default_hp = hp;

        float marginY = (panel.getHeight()/100);
        float marginX = (panel.getWidth()/35);

        for(int i = 0; i <= hp; i++){
            Image token;
            if (i==0){
                token = new Image(GuiFactory.getTexture("assets/gui/Signs/DamageSignRed.png"));

            } else {
                token = new Image(GuiFactory.getTexture("assets/gui/Signs/DamageSign.png"));

            }
            token.setWidth(panel.getWidth()/11.8f);
            token.setHeight(panel.getHeight()/8);

            token.setPosition(
                    game.getBoard().width*Metrics.TILE.width + marginX + i*(token.getWidth()+panel.getWidth()/95),
                    panel.getY()+panel.getHeight()/2 + marginY);

            stage.addActor(token);
            tokens.add(token);
        }
    }

    public void addLifeTokens(Stage stage, GameClass game){
        int life = 3; // Temp, lives not added yet
        for(int i = 0; i < life; i++){
            Image token = new Image(GuiFactory.getTexture("assets/gui/Signs/LifeTokenOn.png"));

            token.setWidth(panel.getWidth()/10);
            token.setHeight(panel.getWidth()/10);

            token.setPosition(
                    panel.getX()+panel.getWidth()/24*7.8f + i*(token.getWidth()+panel.getWidth()/45),
                    panel.getY()+panel.getHeight()/12*9);

            stage.addActor(token);
            lifetokens.add(token);
        }
    }

}
