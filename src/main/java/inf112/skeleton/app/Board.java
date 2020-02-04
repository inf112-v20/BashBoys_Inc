package inf112.skeleton.app;

public class Board {

    public int width;
    public int height;
    private MapObject[][] board;

    /**
     * Constructs an empty board
     * 
     * @param width
     * @param height
     */
    Board(int width, int height) {
        this.width = width;
        this.height = height;
        board = new MapObject[width][height];
    }

    /**
     * Add item to board at (x,y) and set item pos = (x,y)
     * 
     * @param item
     * @param x
     * @param y
     */
    public void addItem(MapObject item, int x, int y) {
        item.setX(x);
        item.setY(y);
        board[x][y] = item;
    }

    /**
     * Remove item form board
     * 
     * @param item
     */
    public void removeItem(MapObject item) {
        board[item.getX()][item.getY()] = null;
    }

    /**
     * Move item to (x,y)
     * 
     * @param item
     * @param x
     * @param y
     */
    public void moveItem(MapObject item, int x, int y) {
        removeItem(item);
        board[x][y] = item;
    }

    /**
     * Get Item on board
     * 
     * @param item
     * @return MapObject item
     */
    public MapObject getItem(MapObject item) {
        return board[item.getX()][item.getY()];
    }

    /**
     * Get item at (x,y)
     * 
     * @param x
     * @param y
     * @return MapObject item
     */
    public MapObject getItem(int x, int y) {
        return board[x][y];
    }

}
