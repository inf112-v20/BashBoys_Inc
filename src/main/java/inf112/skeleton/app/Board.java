package inf112.skeleton.app;

import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.enums.LeftRight;
import inf112.skeleton.app.object.Robot;

import java.util.ArrayList;

public class Board {

    public int width;
    public int height;
    private IMapObject[][] board;

    /**
     * Constructs an empty board
     * 
     * @param width
     * @param height
     */
    Board(int width, int height) {
        this.width = width;
        this.height = height;
        board = new IMapObject[width][height];

        //this.addItem(new Robot(), 5, 5);
    }

    /**
     * Add item to board at (x,y) and set item pos = (x,y)
     * 
     * @param item
     * @param x
     * @param y
     */
    public void addItem(IMapObject item, int x, int y) {
        item.setX(x);
        item.setY(y);
        board[x][y] = item;
    }

    /**
     * Remove item form board
     * 
     * @param item
     */
    public void removeItem(IMapObject item) {
        board[item.getX()][item.getY()] = null;
    }

    /**
     * Moves Robot relative to current position (Robot needs move function to use)
     * @param item
     * @param amount
     */
    public void moveItem(Robot item, int amount) {
        if (!clearPath(item, amount))
            throw new IllegalArgumentException("Path not clear");
        removeItem(item);
        item.move(amount);
        board[item.getX()][item.getY()] = item;
    }

    /**
     * Checks if path is clear (all tiles == null for now) in front of given robot
     * @param item
     * @param amount
     * @return boolean if path is clear
     */
    private boolean clearPath(Robot item, int amount) {
        int x = item.getX();
        int y = item.getY();
        Direction dir = item.getDir();
        if (amount == -1) {
            dir = Direction.turn(LeftRight.LEFT, dir);
            dir = Direction.turn(LeftRight.LEFT, dir);
            amount = -amount;
        }
            
        switch (dir) {
        case NORTH:
            while (++y <= item.getY() + amount) {
                if (board[item.getX()][y] != null)
                    return false;
            }
            break;
        case SOUTH:
            while (--y >= item.getY() - amount) {
                if (board[item.getX()][y] != null)
                    return false;
            }
            break;
        case EAST:
            while (++x <= item.getX() + amount) {
                if (board[x][item.getY()] != null)
                    return false;
            }
            break;
        case WEST:
            while (--x >= item.getX() - amount) {
                if (board[x][item.getY()] != null)
                    return false;
            }
            break;
        }
        return true;
    }
    

    /**
     * Get item at (x,y)
     * 
     * @param x
     * @param y
     * @return MapObject item
     */
    public IMapObject getItem(int x, int y) {
        return board[x][y];
    }

    /**
     * Return all MapObjects on the board
     * @return ArrayList<IMapObject>
     */
    public ArrayList<IMapObject> getObjects() {
        ArrayList<IMapObject> mapObjects = new ArrayList<>();

        for (int x = 0; x < this.board.length; x++) {
            for (int y = 0; y < this.board.length; y++) {
                if (this.board[x][y] != null) {
                    mapObjects.add(this.board[x][y]);
                }
            }
        }
        return mapObjects;
    }
}
