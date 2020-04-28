package inf112.skeleton.app.gui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import inf112.skeleton.app.GameClass;
import inf112.skeleton.app.Player;
import inf112.skeleton.app.cards.Nothing;
import inf112.skeleton.app.cards.ShutDown;
import inf112.skeleton.app.enums.Metrics;
import inf112.skeleton.app.interfaces.ICard;

import java.awt.*;
import java.util.ArrayList;

public class GuiCards implements IGuiElement {

    private ArrayList<Register> registers = new ArrayList<>();
    private ArrayList<ButtonCard> cards = new ArrayList<>();
    private ArrayList<ICard> hand;
    private ImageButton powerDown;
    private ImageButton lockIn;
    private Stage uiStage;
    private Player player;
    private ImageButton panel;
    private boolean finished = false;
    private float divRes;


    @Override
    public void initialize(Stage stage, GameClass game, int player){
        this.uiStage = stage;
        this.player = game.players().get(player);

        addRegisters(stage);
        addCards(game.players().get(player), stage);
        addEndTurn(stage, game, player);
        addPowerDownButton(stage, game, player);
    }

    @Override
    public void update(Stage stage, GameClass game){
        if(!player.getHand().equals(hand)) {
            for (Register r : registers) {
                unRegisterCard(r);
                r.disabled = registers.indexOf(r) >= (player.getRobot().getHp() - 1);
            }
            for(ButtonCard bc : cards) {
                bc.remove();
            }
            addCards(player,stage);
        }
    }

    @Override
    public void setDivRes(float div) {
        this.divRes = div;
    }

    /**
     * Adds card to gui
     * 
     * @param player - Amount of cards
     * @param stage - Stage to change
     */
    private void addCards(Player player, Stage stage){
        int n = player.getHand().size();
        hand = (ArrayList<ICard>) player.getHand().clone();

        for (int i = 0; i < n; i++) {

            float width = (panel.getWidth()/9);
            float height = (panel.getWidth()/9)/GuiFactoryUtil.ratio;



            float x = panel.getX() + i*width;
            float y = ((Metrics.SCREEN.height)/divRes)/24;



            ButtonCard temp = GuiFactoryUtil.createCard(x,y,width,height,player.getHand().get(i));
            temp.start_height = height;
            temp.start_width = width;
            temp.setOriginPoint(new Point((int) x, (int) y)); // Set reset point for card

            addListener(temp);

            stage.addActor(temp);
            stage.addActor(temp.displayPriority());
            cards.add(temp);
        }
    }

    /**
     * Registers a card in a register visually
     * 
     * @param card     - Card to register
     * @param register - Register to register
     */
    private void registerCard(ButtonCard card, Register register){
        card.register = register;
        register.setCard(card);
        register.setStatus(true);
        card.setPosition(register.getX(), register.getY());
        card.reSize(register.getWidth(),register.getHeight(),this.uiStage);

    }

    /**
     * Swap card from register reg1 to register reg2
     * 
     * @param card - Card to swap
     * @param from - From register
     * @param to   - To register
     */
    private void swapRegister(ButtonCard card, Register from, Register to){
        unRegisterCard(card, from);
        registerCard(to.getCard(), from);
        registerCard(card, to);
    }

    /**
     * Un register a card from a register
     * 
     * @param card     - Card to un register
     * @param register - Register to be un registed
     */
    private void unRegisterCard(ButtonCard card, Register register){
        card.setPosition(card.getOriginX(), card.getOriginY());
        register.setStatus(false);
        card.register = null;
        register.setCard(null);
        card.reSize(card.start_width,card.start_height,this.uiStage);
    }

    private void unRegisterCard(Register register){
        ButtonCard card = register.getCard();
        if (card == null)
            return;
        card.setPosition(card.getOriginX(), card.getOriginY());
        register.setStatus(false);
        card.register = null;
        register.setCard(null);
        card.reSize(card.start_width,card.start_height,this.uiStage);
    }

    /**
     * Add registers to gui-stage, default is 5 registers
     * 
     * @param stage - Gui-Stage
     */
    private void addRegisters(Stage stage){
        for (int i = 0; i < 5; i++) {
            float width = (panel.getWidth()/5)-(panel.getWidth()/50)*2;
            float height = (panel.getHeight()/3)+((panel.getHeight()/3)/10*2);

            float x = (panel.getX() + panel.getWidth()/47) + i*(width+(panel.getWidth()/50)*2);
            float y = (panel.getY() + panel.getHeight()/25);


            Register temp = GuiFactoryUtil.createRegister(x,y,width,height);
            if(i >= player.getRobot().getHp()){
                temp.disabled = true;
            }

            stage.addActor(temp);
            registers.add(temp);
        }

    }

    /**
     * Adds a end-turn button to gui-stage
     * 
     * @param stage - Gui-stage to add to
     */
    private void addEndTurn(Stage stage, GameClass game, int player){
        lockIn = new ImageButton(
                GuiFactoryUtil.getTexture("assets/gui/Signs/LockIn.png"),
                GuiFactoryUtil.getTexture("assets/gui/Signs/LockInPushed.png"));

        lockIn.setWidth(panel.getHeight()/4);
        lockIn.setHeight(panel.getHeight()/4);


        float x = panel.getX() + (panel.getWidth()/40)*33f;
        float y = panel.getY() + panel.getHeight() - lockIn.getHeight() - panel.getHeight()/24;

        lockIn.setPosition(x,y);

        lockIn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float xx, float yy){
                if (event.getType() == InputEvent.Type.touchUp) {
                    finished = true;
                    powerDown.setDisabled(true);
                    lockIn.setDisabled(true);
                    System.out.println("");
                    for (Register register : registers) {
                        if (register.getStatus()) {
                            System.out.print(register.getCard().getType().getName() + " - ");
                            game.players().get(player).addCardToSheet(register.getCard().getType()); // temp
                        } else {
                            game.players().get(player).addCardToSheet(new Nothing(game.players().get(player)));
                            System.out.print("Nothing" + " - ");
                        }
                    }
                    game.players().get(player).setReady(true); // temp
                    System.out.println("");
                }
                if (player != 0) {
                    game.sendHand();
                }
            }
        });
        stage.addActor(lockIn);
    }

    private void addPowerDownButton(Stage stage, GameClass game, int player){
        powerDown = new ImageButton(
                GuiFactoryUtil.getTexture("assets/gui/Signs/PowerDown.png"),
                GuiFactoryUtil.getTexture("assets/gui/Signs/PowerDownPushed.png"));


        powerDown.setWidth(panel.getHeight()/4);
        powerDown.setHeight(panel.getHeight()/4);

        float x = panel.getX() + panel.getWidth()/40;
        float y = panel.getY() + panel.getHeight() - powerDown.getHeight() - panel.getHeight()/24;

        powerDown.setPosition(x, y);
        powerDown.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float xx, float yy){
                if (event.getType() == InputEvent.Type.touchUp) {
                    game.players().get(player).setShutDow(2);
                    for (int i = 0; i < registers.size();i++) {
                        game.players().get(player).addCardToSheet(new ShutDown(game.players().get(player))); // temp
                    }
                    game.players().get(player).setReady(true);
                    System.out.println(game.players().get(player).getName() + ": Shutdown");
                    if (player != 0) {
                        game.sendHand();
                    }
                }
            }
        });
        stage.addActor(powerDown);
    }

    /**
     * Get if this gui-stage is finished
     * 
     * @return - boolean if gui-stage is finished
     */
    public boolean isFinished(){
        return finished;
    }

    public void setPanel(ImageButton panel){
        this.panel = panel;
    }


    private void addListener(ButtonCard temp){
        // Adds a drag listener to actor
        temp.addListener(new DragListener() {

            // OffsetX,Y of mouse click to button position
            float offsetX = 0;
            float offsetY = 0;

            // Boolean for single click or drag
            boolean click = false;

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                // Changes Z-index so that dragged items are on the top of the stack
                int index = 0;
                for (ButtonCard c : cards) {
                    if (c.getZIndex() > index) {
                        index = c.getZIndex();
                    }
                }
                temp.setZIndex(index + 1);
                temp.displayPriority().setZIndex(index + 2);

                offsetX = x;
                offsetY = y;
                click = true;

                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void drag(InputEvent event, float x, float y, int pointer){
                temp.moveBy(x - offsetX, y - offsetY);
                click = false;
                super.drag(event, x, y, pointer);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                offsetX = x;
                offsetY = y;

                // If it's a simple click
                if (click) {
                    if (temp.register != null) {
                        unRegisterCard(temp, temp.register);
                    } else {
                        // Find fist open register
                        for (Register register : registers) {
                            if (!register.getStatus() && !register.disabled) {
                                registerCard(temp, register);
                                break;
                            }
                        }
                    }
                } else {
                    boolean hit = false;
                    for (Register register : registers) {
                        // Register is hit by dragging motion
                        if (register.contains(temp.getX() + offsetX, temp.getY() + offsetY)) {
                            hit = true;
                            if (!register.getStatus()) { // Register is empty
                                if (temp.register == null) {
                                    registerCard(temp, register);
                                    break;
                                } else {
                                    unRegisterCard(temp, temp.register);
                                    registerCard(temp, register);
                                    break;
                                }
                            } else if (temp.register != null) { // Register is not empty, then swap registers and temp
                                if (temp.register == register) {
                                    unRegisterCard(temp, register);
                                    break;
                                } else {
                                    swapRegister(temp, temp.register, register);
                                    break;
                                }
                            } else { // // Reset to origin point
                                temp.setPosition(temp.getOriginX(), temp.getOriginY());
                            }
                        }
                    }
                    if (!hit) { // If dragging was a miss, then unregister/return to origin point
                        if (temp.register == null) {
                            temp.setPosition(temp.getOriginX(), temp.getOriginY());
                        } else {
                            unRegisterCard(temp, temp.register);
                            temp.reSize(temp.start_width,temp.start_height,uiStage);

                        }
                    }
                }

                click = false;
                super.touchUp(event, x, y, pointer, button);
            }
        });
    }
}
