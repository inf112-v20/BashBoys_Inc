package inf112.skeleton.app.gui;


import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import inf112.skeleton.app.GameClass;
import inf112.skeleton.app.Player;
import inf112.skeleton.app.object.Robot;

public class GuiHud implements IGuiElement {

    private Player player;
    private Robot robot;
    private ImageButton panel;
    private float divRes;
    //private Stage stage;

    @Override
    public void initialize(Stage stage, GameClass game, int player){
        this.player = game.players().get(player);
        robot = this.player.getRobot();
        //this.stage = stage;
        addPlayerHud(stage);
    }

    @Override
    public void update(Stage stage, GameClass game){
        for(Actor a : stage.getActors()){
            if(a.getName() != null){
                if(a.getName().equals("hp")){
                    if(player.getRobot().getHp()==0){
                        ((Text) a).setText("YOU DIED");
                    } else {
                        ((Text) a).setText(player.getRobot().getHp()+" hp");
                    }
                } else if(a.getName().equals("flags")){
                    ((Text)a).setText(player.getFlags().size() + "");
                }
            }
        }
    }

    @Override
    public void setDivRes(float div) {
        this.divRes = div;
    }

    /**
     * Set Panel for ui elements dependent on the panel
     * @param panel - Panel form GuiPanel
     */
    public void setPanel(ImageButton panel){
        this.panel = panel;
    }

    /**
     * Adds hud elements to gui
     * @param stage - UiStage to add hud to
     */
    private void addPlayerHud(Stage stage){
        float x = stage.getWidth()/10;

        Text name = new Text(""+player.getName()+" : ");
        Text robotName = new Text(""+robot.getName());
        Text hp = new Text(robot.getHp()+" hp : ");
        hp.setName("hp");

        name.setPosition(
                panel.getX() + panel.getWidth()/2 - (name.getWidth()+robotName.getWidth()+hp.getWidth())/2,
                panel.getY() - panel.getHeight()/50);

        robotName.setPosition(
                name.getX() + name.getWidth(),
                panel.getY() - panel.getHeight()/50);

        hp.setPosition(
                robotName.getX() + robotName.getWidth(),
                panel.getY() - panel.getHeight()/50);

        Text flags = new Text(player.getFlags().size()+"");
        flags.setName("flags");
        flags.setPosition(
                panel.getX() + (panel.getWidth()/8f)*6.15f,
                panel.getY() + (panel.getHeight()/16f)*12.5f);
        flags.getStyle().font.getData().setScale(2);

        stage.addActor(name);
        stage.addActor(robotName);
        stage.addActor(hp);
        stage.addActor(flags);
    }

}
