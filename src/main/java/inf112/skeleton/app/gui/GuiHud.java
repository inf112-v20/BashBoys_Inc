package inf112.skeleton.app.gui;


import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import inf112.skeleton.app.GameClass;
import inf112.skeleton.app.Player;
import inf112.skeleton.app.object.Robot;

public class GuiHud implements IGuiElement {

    private Player player;
    private Robot robot;
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
                    ((Text)a).setText(player.getFlags().size() + " flags");
                }
            }
        }
    }

    private void addPlayerHud(Stage stage){
        float x = stage.getWidth()/10;

        Text name = new Text(""+player.getName());
        name.setPosition(x,stage.getHeight()/2);

        Text robotName = new Text(""+robot.getName());
        robotName.setPosition(x,name.getY()-name.getHeight());

        Text hp = new Text(robot.getHp()+" hp");
        hp.setName("hp");
        hp.setPosition(x,robotName.getY()-robotName.getHeight());

        Text flags = new Text(player.getFlags().size() + " flags");
        flags.setName("flags");
        flags.setPosition(x,hp.getY()-hp.getHeight());

        stage.addActor(name);
        stage.addActor(robotName);
        stage.addActor(hp);
        stage.addActor(flags);
    }

}
