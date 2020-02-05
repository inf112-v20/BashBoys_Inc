package inf112.skeleton.app;

import java.util.ArrayList;

import inf112.skeleton.app.object.Robot;
import inf112.skeleton.app.object.TestRobot;

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

        this.addItem(new Robot(), 5, 5);
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
     * Move item to (x,y)
     * 
     * @param item
     * @param x
     * @param y
     */
    public void moveItem(IMapObject item, int x, int y) {
        removeItem(item);
        board[x][y] = item;
    }

    public void moveItem(TestRobot item, int amount) {
        if (!clearPath(item, amount))
            throw new IllegalArgumentException("Path not clear");
        removeItem(item);
        item.move(amount);
        board[item.getX()][item.getY()] = item;
    }

    private boolean clearPath(IDirectionalObject item, int amount) {
        int p;
        switch (item.getDir()) {
        case NORTH:
            p = item.getY();
            while (++p <= item.getY() + amount) {
                if (board[item.getX()][p] != null)
                    return false;
            }
            break;
        case SOUTH:
            p = item.getY();
            while (--p >= item.getY() - amount) {
                if (board[item.getX()][p] != null)
                    return false;
            }
            break;
        case EAST:
            p = item.getX();
            while (++p <= item.getX() + amount) {
                if (board[item.getX()][p] != null)
                    return false;
            }
            break;
        case WEST:
            p = item.getX();
            while (--p >= item.getX() - amount) {
                if (board[item.getX()][p] != null)
                    return false;
            }
            break;

        }
        return true;
    }

    /**
     * Get Item on board
     * 
     * @param item
     * @return MapObject item
     */
    public IMapObject getItem(IMapObject item) {
        return board[item.getX()][item.getY()];
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
