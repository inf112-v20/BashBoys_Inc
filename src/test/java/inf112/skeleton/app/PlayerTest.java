package inf112.skeleton.app;

import inf112.skeleton.app.object.MoveCard;
import inf112.skeleton.app.object.iCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    private Player player;
    private iCard move1;
    private iCard move2;

    @BeforeEach
    public void init() {
        player = new Player("one");
        move1 = new MoveCard(1, 1, "move1");
        move2 = new MoveCard(2, 1, "move2");
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
        assertTrue(player.getHand().size() == 0);
    }

    @Test
    public void addOneCardToProgramSheetTest() {
        player.addCardToSheet(move1, 1);
        assertTrue(player.getProgramSheet().size() == 1);
    }

    @Test
    public void addOneCardFromHandToProgramSheetTest() {
        player.giveCard(move1);
        iCard cardInHand = player.getHand().get(0);
        player.addCardToSheet(cardInHand, 1);
        assertEquals(player.getProgramSheet().get(0), move1);
        assertTrue(player.getHand().size() == 0);
    }

    @Test
    public void getCardFromEmptyProgramSheetTest() {
        assertNull(player.getCardFromSheet(1));
    }

    @Test
    public void addCardToExistingProgramSheetIndexTest() {
        player.addCardToSheet(move1, 0);
        player.addCardToSheet(move2, 0);
        assertEquals(1, player.getProgramSheet().size());
        assertEquals(move2, player.getCardFromSheet(0));
    }

    @Test
    public void addCardToProgramSheetWithIndexOutOfRangeThrowsExceptionTest() {
        boolean thrown = false;

        try {
            player.addCardToSheet(move1, 5);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }

        assertTrue(thrown);
    }

    @Test
    public void swapCardFromSheetToHandTest() {
        player.giveCard(move1);
        player.giveCard(move2);
        player.addCardToSheet(player.getHand().get(0), 0);
        player.addCardToSheet(player.getHand().get(0), 0);
        assertEquals(1, player.getHand().size());
        assertEquals(1, player.getProgramSheet().size());
        assertEquals(move1, player.getHand().get(0));
        assertEquals(move2, player.getProgramSheet().get(0));

    }

    @Test
    public void moveCardFromSheetToHandTest() {
        player.addCardToSheet(move1, 0);
        player.moveCardFromSheetToHand(move1);
        assertEquals(0, player.getProgramSheet().size());
        assertEquals(1, player.getHand().size());
    }

    @Test
    public void clearSheetTest() {
        player.addCardToSheet(move1, 0);
        player.clearSheet();
        assertEquals(0, player.getProgramSheet().size());
    }

    @Test
    public void moveCardFromSheetToEmptySheetSpotTest() {
        player.addCardToSheet(move1, 0);
        player.addCardToSheet(move1, 1);
        assertEquals(1, player.getProgramSheet().size());
    }

    @Test
    public void moveCardFromSheetToOccupiedSheetSpotTest() {
        player.addCardToSheet(move1, 0);
        player.addCardToSheet(move2, 1);
        player.addCardToSheet(move1, 1);
        assertEquals(2, player.getProgramSheet().size());
        assertEquals(move1, player.getCardFromSheet(1));
        assertEquals(move2, player.getCardFromSheet(0));
    }
}
