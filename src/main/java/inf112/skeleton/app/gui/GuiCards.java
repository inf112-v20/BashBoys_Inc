package inf112.skeleton.app.gui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import inf112.skeleton.app.Board;
import inf112.skeleton.app.Game;
import inf112.skeleton.app.cards.Deck;
import inf112.skeleton.app.cards.MoveCard;

import java.awt.*;
import java.util.ArrayList;

public class GuiCards implements IGuiElement{

    private ArrayList<Register> registers = new ArrayList<>();
    private ArrayList<ButtonCard> cards = new ArrayList<>();
    private ArrayList<ICard> hand;
    ImageButton powerDown;
    Stage uiStage;
    GameClass game;
    Player player;
    ImageButton panel;
    private boolean finished = false;

    @Override
    public void initialize(Stage stage,GameClass game, int player){
        this.game = game;

    /**
     * Start the card gui, adds card and register and end Turn button
     * @param cards amount of cards present
     */
    public void startCardGui(Stage stage, int cards,Game game , Board b){
        addRegisters(stage);
        addCards(cards,stage);
        addEndTurn(stage,game);
    }

    /**
     * Adds card to gui
     * @param p - Amount of cards
     * @param stage - Stage to change
     */
    private void addCards(int n, Stage stage){
    private void addCards(Player p, Stage stage){
        int n = p.getHand().size();
        hand = (ArrayList<ICard>) p.getHand().clone();


        int ii = 0;
        for(int i = 0; i < n; i++){

            if(n % 2 != 0){
                x = (int) (x - SpecCard.getWidth()/2);
            float width = (panel.getWidth()/9);
            }

            ButtonCard temp = GuiFactory.createCard(x,y,deck.getCard());
            float x = panel.getX() + i*width;

            ButtonCard temp = GuiFactory.createCard(x,y,width,height,p.getHand().get(i));

            // Adds a drag listener to actor
            temp.addListener(new DragListener(){

                // OffsetX,Y of mouse click to button position
                float offsetX = 0;
                float offsetY = 0;

                // Boolean for single click or drag
                boolean click = false;

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    // Changes Z-index so that dragged items are on the top of the stack
                    int index = 0;
                    for(ButtonCard c : cards){
                        if(c.getZIndex()+2 > index){
                            index = c.getZIndex();
                        }
                    }
                    temp.setZIndex(index+1);

                    offsetX = x;
                    offsetY = y;
                    click = true;

                    return super.touchDown(event, x, y, pointer, button);
                }

                @Override
                public void drag(InputEvent event, float x, float y, int pointer) {
                    temp.moveBy(x-offsetX,y-offsetY);
                    Register specRegister = GuiFactory.createRegister(0,0);
                    if(temp.getWidth() < specRegister.getWidth()){
                        temp.setWidth(temp.getWidth()+10);
                    }
                    if(temp.getHeight() < specRegister.getHeight()){
                        temp.setHeight(temp.getHeight()+10);
                    }
                    click = false;
                    super.drag(event, x, y, pointer);
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    offsetX = x;
                    offsetY = y;

                    // If it's a simple click
                    if(click){
                        doClick(temp);
                    } else {
                        doDrag(temp,offsetX,offsetY);
                    }
                    click = false;
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
        card.reSize(register.getWidth(),register.getHeight(),this.uiStage);
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
        register.setStatus(false);
        card.register = null;
        register.setCard(null);
        card.reSize(card.start_width,card.start_height,this.uiStage);

    }
    
        ButtonCard card = register.getCard();
        card.setPosition(card.getOriginX(),card.getOriginY());
        register.setStatus(false);
        card.register = null;
    }

    /**
     * Add registers to gui-stage, default is 5 registers
     * @param stage - Gui-Stage
     */
    private void addRegisters(Stage stage){
        for(int i = 0; i < 5;i++){
            float height = (panel.getHeight()/3)+((panel.getHeight()/3)/10*2);

            float x = (panel.getX() + panel.getWidth()/47) + i*(width+(panel.getWidth()/50)*2);
            float y = (panel.getY() + panel.getHeight()/25);

            Register temp = GuiFactory.createRegister(x,y,width,height);

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
    private void addEndTurn(Stage stage,GameClass g, int player){
                GuiFactory.getTexture("assets/gui/Signs/LockIn.png"),
                GuiFactory.getTexture("assets/gui/Signs/LockInPushed.png"));

        lockIn.setWidth(panel.getHeight()/3.2f);
        lockIn.setHeight(panel.getHeight()/3.2f);


        float x = (panel.getX()+(panel.getWidth()/5)*4)+panel.getWidth()/200;
        float y = (panel.getY() + panel.getHeight() - lockIn.getHeight())-panel.getWidth()/200;

        lockIn.setPosition(x,y);

            @Override
            public void clicked(InputEvent event, float xx, float yy){
                if(event.getType() == InputEvent.Type.touchUp) {
                    finished = true;
                    System.out.println("");
                    int i = 0;
                    for(Register register : registers){
                        if(register.getStatus()){
                            System.out.print(register.getCard().getType().getName()+" - ");
                            game.players().get(0).addCardToSheet(register.getCard().getType()); //temp
                        } else {
                            System.out.print("null"+" - ");
                        }
                    i++;
                    }
                    game.players().get(0).setReady(true); //temp
                    System.out.println("");
                    game.nextPlayer();
                }
            }
        });
        stage.addActor(lockIn);
    }


        powerDown = new ImageButton(
                GuiFactory.getTexture("assets/gui/Signs/PowerDown.png"),
                GuiFactory.getTexture("assets/gui/Signs/PowerDownPushed.png"));

        powerDown.setWidth(panel.getHeight()/3);

        float x = panel.getX();
        float y = panel.getY() + panel.getHeight() - powerDown.getHeight();

        button.addListener(new ClickListener(){
        powerDown.setPosition(x,y);
            @Override
            public void clicked(InputEvent event, float xx, float yy){
                if(event.getType() == InputEvent.Type.touchUp){
                    powerDown.setChecked(true);
                    System.out.println(game.players().get(0).getName()+": Shutdown");
                }
            }
        });
        stage.addActor(powerDown);
    }

    /**
     * Get if this gui-stage is finished
     * @return - boolean if gui-stage is finished
     */
    public boolean isFinished(){
        return finished;
    }

    public void setPanel(ImageButton panel){
        this.panel = panel;
    }

}
