package inf112.skeleton.app;

import inf112.skeleton.app.cards.MoveCard;
import inf112.skeleton.app.interfaces.ICard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class PlayerTest {

    private Player player;
    private ICard move1;

    @BeforeEach
    public void init() {
        player = new Player("one");
        move1 = new MoveCard(1, 1, "move1",null);
    }

    @Test
    public void addOneCardToHandTest() {
        player.giveCard(move1);
        assertTrue(player.getHand().size() == 1);
    }

    @Test
    public void removeCardFromHandTest() {
        player.giveCard(move1);
        player.removeCardFromHand(move1);
        assertTrue(player.getHand().isEmpty());
    }

    @Test
    public void addOneCardToProgramSheetTest() {
        player.addCardToSheet(move1);
        assertTrue(player.getProgramSheet().size() == 1);
    }

    @Test
    public void getCardFromEmptyProgramSheetTest() {
        assertNull(player.getCardFromSheet(1));
    }

    @Test
    public void getOneCardFromSheetTest() {
        player.addCardToSheet(move1);
        assertEquals(move1, player.getCardFromSheet(0));
    }

    @Test
    public void getAllCardsFromFullSheetTest() {
        ICard move2 = new MoveCard(2, 1, "move2",null);
        ICard move3 = new MoveCard(2, 1, "move3",null);
        ICard move4 = new MoveCard(2, 1, "move4",null);
        ICard move5 = new MoveCard(2, 1, "move5",null);
        player.addCardToSheet(move1);
        player.addCardToSheet(move2);
        player.addCardToSheet(move3);
        player.addCardToSheet(move4);
        player.addCardToSheet(move5);
        assertEquals(move1, player.getCardFromSheet(0));
        assertEquals(move2, player.getCardFromSheet(1));
        assertEquals(move3, player.getCardFromSheet(2));
        assertEquals(move4, player.getCardFromSheet(3));
        assertEquals(move5, player.getCardFromSheet(4));
    }

    @Test
    public void clearSheetTest() {
        player.addCardToSheet(move1);
        player.clearSheet();
        assertEquals(0, player.getProgramSheet().size());
    }
}