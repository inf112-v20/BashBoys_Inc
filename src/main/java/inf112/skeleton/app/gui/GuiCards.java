package inf112.skeleton.app.gui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import inf112.skeleton.app.Board;
import inf112.skeleton.app.GameClass;
import inf112.skeleton.app.Player;
import inf112.skeleton.app.cards.MoveCard;
import inf112.skeleton.app.cards.Nothing;
import inf112.skeleton.app.cards.ShutDown;
import inf112.skeleton.app.interfaces.ICard;

import java.awt.*;
import java.util.ArrayList;

public class GuiCards {

    private static int margin = 50;
    private ArrayList<Register> registers = new ArrayList<>();
    private ArrayList<ButtonCard> cards = new ArrayList<>();
    private ArrayList<ICard> hand;
    private ImageButton powerDown;
    private ImageButton lockIn;
    private boolean finished = false;

    /**
     * Start the card gui, adds card and register and end Turn button
     * 
     * @param stage gui stage to be added to
     * @param cards amount of cards present
     */
    public void startCardGui(Stage stage, GameClass g, Board b, int player){
        addRegisters(stage);
        addCards(g.players().get(player), stage);
        addEndTurn(stage, g, b, player);
        addPowerDownButton(stage, g, b, player);
    }

    /**
     * Adds card to gui
     * 
     * @param n     - Amount of cards
     * @param stage - Stage to change
     */
    private void addCards(Player p, Stage stage){
        int n = p.getHand().size();
        hand = (ArrayList<ICard>) p.getHand().clone();
        for (int i = 0; i < n; i++) {
            ButtonCard SpecCard = GuiFactory.createCard(0, 0, new MoveCard(1, 1, "Move 1", null));
            int x = (int) (i * SpecCard.getWidth() + stage.getWidth() / 2 - SpecCard.getWidth() * (n / 2));
            int y = 0;
            if (n % 2 != 0) {
                x = (int) (x - SpecCard.getWidth() / 2);
            }

            ButtonCard temp = GuiFactory.createCard(x, y, p.getHand().get(i));
            temp.setOriginPoint(new Point(x, y)); // Set reset point for card

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
    }

    private void unRegisterCard(Register register){
        ButtonCard card = register.getCard();
        if (card == null)
            return;
        card.setPosition(card.getOriginX(), card.getOriginY());
        register.setStatus(false);
        card.register = null;
        register.setCard(null);
    }

    /**
     * Add registers to gui-stage, default is 5 registers
     * 
     * @param stage - Gui-Stage
     */
    private void addRegisters(Stage stage){
        for (int i = 0; i < 5; i++) {
            Register SpecRegister = GuiFactory.createRegister(0, 0);
            int x = (int) (i * SpecRegister.getWidth() + stage.getWidth() / 2 - SpecRegister.getWidth() * 2
                    - SpecRegister.getWidth() / 2);
            int y = 100 + margin;

            Register temp = GuiFactory.createRegister(x, y);

            stage.addActor(temp);
            registers.add(temp);
        }

    }

    /**
     * Adds a end-turn button to gui-stage
     * 
     * @param stage - Gui-stage to add to
     */
    private void addEndTurn(Stage stage, GameClass g, Board b, int player){
        lockIn = new ImageButton(GuiFactory.getTexture("assets/gui/Signs/LockIn.png"),
                GuiFactory.getTexture("assets/gui/Signs/LockInPushed.png"));

        lockIn.setWidth(100);
        lockIn.setHeight(100);

        Register tempRegister = registers.get(registers.size() - 1);
        float x = tempRegister.getX() + tempRegister.getWidth();
        float y = tempRegister.getY() + tempRegister.getHeight() / 2 - lockIn.getHeight() / 2;

        lockIn.setPosition(x, y);

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
                            g.players().get(player).addCardToSheet(register.getCard().getType()); // temp
                        } else {
                            g.players().get(player).addCardToSheet(new Nothing(g.players().get(player)));
                            System.out.print("null" + " - ");
                        }
                    }
                    g.players().get(player).setReady(true); // temp
                    System.out.println("");
                }
                if (player != 0) {
                    g.sendHand();
                }
            }
        });
        stage.addActor(lockIn);
    }

    private void addPowerDownButton(Stage stage, GameClass g, Board b, int player){
        powerDown = new ImageButton(GuiFactory.getTexture("assets/gui/Signs/PowerDown.png"),
                GuiFactory.getTexture("assets/gui/Signs/PowerDownPushed.png"));

        powerDown.setWidth(100);
        powerDown.setHeight(100);

        Register tempRegister = registers.get(0);
        float x = tempRegister.getX() - powerDown.getWidth();
        float y = tempRegister.getY() + tempRegister.getHeight() / 2 - powerDown.getHeight() / 2;

        powerDown.setPosition(x, y);
        powerDown.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float xx, float yy){
                if (event.getType() == InputEvent.Type.touchUp) {
                    g.players().get(player).setShutDow(2);
                    for (Register register : registers) {
                        g.players().get(player).addCardToSheet(new ShutDown(g.players().get(player))); // temp
                    }
                    g.players().get(player).setReady(true);
                    System.out.println(g.players().get(player).getName() + ": Shutdown");
                    if (player != 0) {
                        g.sendHand();
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

    public void update(Player p, Stage s){
        if (!p.getHand().equals(hand)) {
            for (ButtonCard bc : cards) {
                bc.remove();
            }
            for (Register r : registers) {
                unRegisterCard(r);
            }
            addCards(p, s);
        }
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
                            } else if (temp.register != null) { // Register is not empty, then swap registers and card
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
                        }
                    }
                }

                click = false;
                super.touchUp(event, x, y, pointer, button);
            }
        });
    }
}
