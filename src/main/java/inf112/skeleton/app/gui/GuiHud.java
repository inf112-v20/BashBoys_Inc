package inf112.skeleton.app.gui;


import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import inf112.skeleton.app.Game;
import inf112.skeleton.app.Player;
import inf112.skeleton.app.object.Robot;
import org.lwjgl.Sys;

public class GuiHud implements IGuiElement {

    private Player player;
    private Robot robot;

    @Override
    public void initialize(Stage stage,GameClass game, int player){
        this.player = game.players().get(player);
        this.robot = this.player.getRobot();
        //this.stage = stage;
        addPlayerHud(stage);
    }

    /**
     * Add HUD for player
     * @param stage - Stage to add HUD to
     */
    private void addPlayerHud(Stage stage){
        float x = stage.getWidth()/10;

        Text name = new Text(""+player.getName());
        name.setPosition(x,stage.getHeight()/2);

        Text robotName = new Text(""+robot.getName());
        robotName.setPosition(x,name.getY()-name.getHeight());
        
        Text hp = new Text(robot.getHp()+" hp");
        hp.setName("hp");
        hp.setPosition(x,robotName.getY()-robotName.getHeight());

        Text flags = new Text("0 flags"); // Not implemented flags yet
        flags.setName("flags");
        flags.setPosition(x,hp.getY()-hp.getHeight());

        stage.addActor(name);
        stage.addActor(robotName);
        stage.addActor(hp);
        stage.addActor(flags);
    }

    @Override
    public void update(Stage stage,GameClass g){
        for(Actor a : stage.getActors()){
            if(a.getName() != null){
                if(a.getName().equals("hp")){
                    if(game.players().get(0).getRobot().getHp()==0){
                        ((Text) a).setText("YOU DIED");
                    } else {
                        ((Text) a).setText(game.players().get(0).getRobot().getHp()+" hp");
                    }
                } else if(a.getName().equals("flags")){
                    // Not yet implemented
                }
            }
        }
    }
}
