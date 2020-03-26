package inf112.skeleton.app.gui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import inf112.skeleton.app.Game;
import inf112.skeleton.app.cards.Deck;
import inf112.skeleton.app.cards.MoveCard;

import java.awt.*;
import java.util.ArrayList;

public class GuiCards implements IGuiElement {

    private Game game;
    private ArrayList<Register> registers = new ArrayList<>();
    private ArrayList<ButtonCard> cards = new ArrayList<>();
    private ImageButton shutdown;
    private ImageButton lock_in;
    private boolean finished = false;


    @Override
    public void initialize(Stage stage,Game game){
        this.game = game;
        addRegisters(stage);
        addCards(game.players().get(0).getRobot().getHp(),stage);
        addEndTurn(stage,game);
        addPowerDownButton(stage,game);
    }

    @Override
    public void update(Stage stage,Game game) {
        // Disable all Gui Elements when waiting for all moves to be done
        if (!game.all_moves_done) {
            shutdown.setDisabled(true);
            lock_in.setDisabled(true);
            for(ButtonCard actor : cards){
                if(actor.register == null){
                    actor.remove();
                }
            }
        } else {
            for (ButtonCard actor : cards) {
                if (actor.register != null) {
                    unRegisterCard(actor, actor.register);
                }
                actor.remove();
            }
            cards.clear();

            // Makes new deck each time, essentially reshuffling and re-adding, temp
            game.deck = new Deck();
            addCards(game.players().get(0).getRobot().getHp(), stage);
            finished = false;
        }
    }

    /**
     * Adds card to gui
     * @param n - Amount of cards
     * @param stage - Stage to change
     */
    private void addCards(int n, Stage stage){
        for(int i = 0; i < n; i++){
            ButtonCard SpecCard = GuiFactory.createCard(0,0,new MoveCard(1,1,"Move 1"));
            int x = (int) (i*SpecCard.getWidth() + stage.getWidth()/2 - SpecCard.getWidth()*(n/2)) ;
            int y = 40;

            if(n % 2 != 0){
                x = (int) (x - SpecCard.getWidth()/2);
            }

            ButtonCard temp = GuiFactory.createCard(x,y,game.deck.getCard());
            temp.setOriginPoint(new Point(x,y)); // Set reset point for card

            // Adds a drag listener to actor
            temp.addListener(new DragListener(){

                // OffsetX,Y of mouse click to button position
                float offsetX = 0;
                float offsetY = 0;

                // Boolean for single click or drag
                boolean click = false;

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if(!isFinished()) {
                        // Changes Z-index so that dragged items are on the top of the stack
                        int index = 0;
                        for (ButtonCard c : cards) {
                            if (c.getZIndex() + 2 > index) {
                                index = c.getZIndex();
                            }
                        }
                        temp.setZIndex(index + 1);

                        offsetX = x;
                        offsetY = y;
                        click = true;
                    }
                    return super.touchDown(event, x, y, pointer, button);
                }

                @Override
                public void drag(InputEvent event, float x, float y, int pointer) {
                    if(!isFinished()){
                        temp.moveBy(x-offsetX,y-offsetY);
                        Register specRegister = GuiFactory.createRegister(0,0);
                        if(temp.getWidth() > specRegister.getWidth()){
                            temp.setWidth(temp.getWidth()-5);
                        }
                        if(temp.getHeight() > specRegister.getHeight()){
                            temp.setHeight(temp.getHeight()-5);
                        }
                        click = false;
                        super.drag(event, x, y, pointer);
                    }
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    if(!isFinished()) {
                        offsetX = x;
                        offsetY = y;

                        // If it's a simple click
                        if (click) {
                            doClick(temp);
                        } else {
                            doDrag(temp, offsetX, offsetY);
                        }
                        click = false;
                    }
                    super.touchUp(event, x, y, pointer, button);
                }
            });

            stage.addActor(temp);
            cards.add(temp);
        }
    }

    /**
     * Does click on card
     * @param card - card to be clicked
     */
    private void doClick(ButtonCard card){
        if(card.register != null){
            unRegisterCard(card,card.register);
        } else {
            // Find fist open register
            for(Register register : registers){
                if(!register.getStatus() && !register.disabled){
                    registerCard(card,register);
                    break;
                }
            }
        }
    }

    /**
     * Does drag on card
     * @param card - card to be dragged
     * @param offsetX - offset x for dragging motion
     * @param offsetY - offset y for dragging motion
     */
    private void doDrag(ButtonCard card, float offsetX, float offsetY){
        boolean hit = false;
        for(Register register : registers){
            // Register is hit by dragging motion
            if(register.contains(card.getX()+offsetX,card.getY()+offsetY)){
                hit = true;
                if(!register.getStatus()){ // Register is empty
                    if(card.register == null){
                        registerCard(card,register);
                        break;
                    } else {
                        unRegisterCard(card,card.register);
                        registerCard(card,register);
                        break;
                    }
                } else if(card.register != null) { // Register is not empty, then swap registers and card
                    if (card.register == register){
                        unRegisterCard(card,register);
                        break;
                    } else {
                        swapRegister(card, card.register, register);
                        break;
                    }
                } else { // // Reset to origin point
                    card.setPosition(card.getOriginX(),card.getOriginY());
                    card.resetSize();
                }
            }
        }
        if(!hit){ // If dragging was a miss, then unregister/return to origin point
            if(card.register == null){
                card.setPosition(card.getOriginX(),card.getOriginY());
                card.resetSize();
            } else {
                unRegisterCard(card,card.register);
            }
        }
    }

    /**
     * Registers a card in a register visually
     * @param card - Card to register
     * @param register - Register to register
     */
    private void registerCard(ButtonCard card, Register register){
        card.register = register;
        register.setCard(card);
        register.setStatus(true);
        card.setPosition(register.getX(),register.getY());
        card.setWidth(GuiFactory.getWidth()/5);
        card.setHeight(GuiFactory.getHeight()/5);
    }

    /**
     * Swap card from register reg1 to register reg2
     * @param card - Card to swap
     * @param from - From register
     * @param to - To register
     */
    private void swapRegister(ButtonCard card, Register from, Register to){
        unRegisterCard(card,from);
        registerCard(to.getCard(),from);
        registerCard(card,to);
    }

    /**
     * Un register a card from a register
     * @param card - Card to un register
     * @param register - Register to be un registed
     */
    private void unRegisterCard(ButtonCard card, Register register){
        card.setPosition(card.getOriginX(),card.getOriginY());
        card.resetSize();
        register.setStatus(false);
        card.register = null;
        register.setCard(null);
    }

    /**
     * Add registers to gui-stage, default is 5 registers
     * @param stage - Gui-Stage
     */
    private void addRegisters(Stage stage){
        for(int i = 0; i < 5;i++){
            Register SpecRegister = GuiFactory.createRegister(0,0);
            int x = (int) (i*SpecRegister.getWidth() + stage.getWidth()/2 - SpecRegister.getWidth()*2 - SpecRegister.getWidth()/2);
            int y = 250;

            Register temp = GuiFactory.createRegister(x,y);
            temp.setZIndex(1);
            stage.addActor(temp);
            registers.add(temp);
        }

    }

    /**
     * Adds a end-turn button to gui-stage
     * @param stage - Gui-stage to add to
     * @param game - Current game
     */
    private void addEndTurn(Stage stage,Game game){
        ImageButton button = new ImageButton(
                GuiFactory.getTexture("assets/gui/Signs/LockIn.png"),
                GuiFactory.getTexture("assets/gui/Signs/LockInPushed.png"));

        button.setWidth(100);
        button.setHeight(100);

        Register tempRegister = registers.get(registers.size()-1);
        float x = tempRegister.getX() + tempRegister.getWidth();
        float y = tempRegister.getY() + tempRegister.getHeight()/2 - button.getHeight()/2;

        button.setPosition(x,y);

        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float xx, float yy){
                if(event.getType() == InputEvent.Type.touchUp && !isFinished()) {

                    System.out.println("");
                    int i = 0;
                    for(Register register : registers){
                        if(register.getStatus()){
                            game.players().get(0).addCardToSheet(register.getCard().getType()); //temp
                        }
                    i++;
                    }
                    game.players().get(0).setReady(true); //temp
                    game.nextPlayer();
                    finished = true;
                }
            }
        });
        lock_in = button;
        stage.addActor(button);
    }

    /**
     * Adds a PowerDownButton to GUI
     * @param stage - UI-stage to add button to
     * @param game - Current game
     */
    private void addPowerDownButton(Stage stage,Game game){
        ImageButton button = new ImageButton(
                GuiFactory.getTexture("assets/gui/Signs/PowerDown.png"),
                GuiFactory.getTexture("assets/gui/Signs/PowerDownPushed.png"));

        button.setWidth(100);
        button.setHeight(100);

        Register tempRegister = registers.get(0);
        float x = tempRegister.getX() - button.getWidth();
        float y = tempRegister.getY() + tempRegister.getHeight()/2 - button.getHeight()/2;

        button.setPosition(x,y);
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float xx, float yy){
                if(event.getType() == InputEvent.Type.touchUp && !isFinished()){
                    button.setChecked(true);
                    System.out.println(game.players().get(0).getName()+": Shutdown");
                }
            }
        });
        shutdown = button;
        stage.addActor(button);
    }


    /**
     * Get if this gui-stage is finished
     * @return - boolean if gui-stage is finished
     */
    public boolean isFinished(){
        return finished;
    }

}
