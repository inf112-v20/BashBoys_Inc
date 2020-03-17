package inf112.skeleton.app.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import inf112.skeleton.app.Board;
import inf112.skeleton.app.Game;
import inf112.skeleton.app.cards.CardFactory;
import inf112.skeleton.app.cards.Deck;
import inf112.skeleton.app.cards.MoveCard;

import java.awt.*;
import java.util.ArrayList;

public class GuiCards {

    private static int margin = 50;
    private ArrayList<Register> registers = new ArrayList<>();
    private ArrayList<ButtonCard> cards = new ArrayList<>();
    private Deck deck = new Deck();
    private boolean finished = false;

    /**
     * Start the card gui, adds card and register and end Turn button
     * @param stage gui stage to be added to
     * @param cards amount of cards present
     */
    public void startCardGui(Stage stage, int cards /* Change to player later*/,Game g , Board b){
        addRegisters(stage);
        addCards(cards,stage);
        addEndTurn(stage,g,b);
        addPowerDownButton(stage,g,b);
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
            int y = 0;
            if(n % 2 != 0){
                x = (int) (x - SpecCard.getWidth()/2);
            }


            ButtonCard temp = GuiFactory.createCard(x,y,deck.getCard());
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
                    // Changes Z-index so that dragged items are on the top of the stack
                    int index = 0;
                    for(ButtonCard c : cards){
                        if(c.getZIndex() > index){
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
                    click = false;
                    super.drag(event, x, y, pointer);
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    offsetX = x;
                    offsetY = y;

                    // If it's a simple click
                    if(click){
                        if(temp.register != null){
                            unRegisterCard(temp,temp.register);
                        } else {
                            // Find fist open register
                            for(Register register : registers){
                                if(!register.getStatus() && !register.disabled){
                                    registerCard(temp,register);
                                    break;
                                }
                            }
                        }
                    } else {
                        boolean hit = false;
                        for(Register register : registers){
                            // Register is hit by dragging motion
                            if(register.contains(temp.getX()+offsetX,temp.getY()+offsetY)){
                                hit = true;
                                if(!register.getStatus()){ // Register is empty
                                    if(temp.register == null){
                                        registerCard(temp,register);
                                        break;
                                    } else {
                                        unRegisterCard(temp,temp.register);
                                        registerCard(temp,register);
                                        break;
                                    }
                                } else if(temp.register != null) { // Register is not empty, then swap registers and card
                                    if (temp.register == register){
                                        unRegisterCard(temp,register);
                                        break;
                                    } else {
                                        swapRegister(temp, temp.register, register);
                                        break;
                                    }
                                } else { // // Reset to origin point
                                    temp.setPosition(temp.getOriginX(),temp.getOriginY());
                                }
                            }
                        }
                        if(!hit){ // If dragging was a miss, then unregister/return to origin point
                            if(temp.register == null){
                                temp.setPosition(temp.getOriginX(),temp.getOriginY());
                            } else {
                                unRegisterCard(temp,temp.register);
                            }
                        }
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
     * Registers a card in a register visually
     * @param card - Card to register
     * @param register - Register to register
     */
    private void registerCard(ButtonCard card, Register register){
        card.register = register;
        register.setCard(card);
        register.setStatus(true);
        card.setPosition(register.getX(),register.getY());
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
    }

    /**
     * Add registers to gui-stage, default is 5 registers
     * @param stage - Gui-Stage
     */
    private void addRegisters(Stage stage){
        for(int i = 0; i < 5;i++){
            Register SpecRegister = GuiFactory.createRegister(0,0);
            int x = (int) (i*SpecRegister.getWidth() + stage.getWidth()/2 - SpecRegister.getWidth()*2 - SpecRegister.getWidth()/2);
            int y = 100+margin;

            Register temp = GuiFactory.createRegister(x,y);

            stage.addActor(temp);
            registers.add(temp);
        }

    }

    /**
     * Adds a end-turn button to gui-stage
     * @param stage - Gui-stage to add to
     */
    private void addEndTurn(Stage stage,Game g,Board b){
        BitmapFont font = new BitmapFont();
        Skin skin = new Skin();
        TextureAtlas atlas = new TextureAtlas("assets/gui/skin/uiskin.atlas");
        skin.addRegions(atlas);
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font;

        style.up = skin.getDrawable("apptheme_btn_radio_on_focused_holo_light");
        style.down = skin.getDrawable("apptheme_btn_radio_on_holo_light");

        TextButton button = new TextButton("Lock",style);

        button.setWidth(100);
        button.setHeight(100);

        Register tempRegister = registers.get(registers.size()-1);
        float x = tempRegister.getX() + tempRegister.getWidth();
        float y = tempRegister.getY() + tempRegister.getHeight()/2 - button.getHeight()/2;

        button.setPosition(x,y);

        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float xx, float yy){
                if(event.getType() == InputEvent.Type.touchUp) {
                    finished = true;
                    System.out.println("");
                    int i = 0;
                    for(Register register : registers){
                        if(register.getStatus()){
                            System.out.print(register.getCard().getType().getName()+" - ");
                            g.players().get(0).addCardToSheet(register.getCard().getType()); //temp
                        } else {
                            System.out.print("null"+" - ");
                        }
                    i++;
                    }
                    g.players().get(0).setReady(true); //temp
                    System.out.println("");
                    g.nextPlayer();
                }
            }
        });
        stage.addActor(button);
    }


    private void addPowerDownButton(Stage stage,Game g, Board b){
        BitmapFont font = new BitmapFont();
        Skin skin = new Skin();
        TextureAtlas atlas = new TextureAtlas("assets/gui/skin/uiskin.atlas");
        skin.addRegions(atlas);
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font;

        style.up = skin.getDrawable("apptheme_btn_radio_on_holo_light");
        style.down = skin.getDrawable("apptheme_btn_radio_on_focused_holo_light");

        TextButton button = new TextButton("PowerDown",style);

        button.setWidth(100);
        button.setHeight(100);

        Register tempRegister = registers.get(0);
        float x = tempRegister.getX() - button.getWidth();
        float y = tempRegister.getY() + tempRegister.getHeight()/2 - button.getHeight()/2;

        button.setPosition(x,y);
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float xx, float yy){
                if(event.getType() == InputEvent.Type.touchUp){
                    System.out.println(g.players().get(0).getName()+": Shutdown");
                }
            }
        });
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
