package inf112.skeleton.app;

import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.object.IMapObject;
import inf112.skeleton.app.object.Robot;
import inf112.skeleton.app.object.Wall;

import java.util.ArrayList;

public class Board {

    public int width;
    public int height;
    private ArrayList<IMapObject>[][] board;

    /**
     * Constructs an empty board
     * 
     * @param width  - width of board
     * @param height - height of board
     */
    @SuppressWarnings("unchecked")
    Board(int width, int height) {
        this.width = width;
        this.height = height;
        board = new ArrayList[width][height];

        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                board[j][i] = new ArrayList<IMapObject>();
            }
        }
    }

    /**
     * Add item to board at (x,y) and set item pos = (x,y)
     * 
     * @param item - item to add
     * @param x    - x position
     * @param y    - y position
     */
    public void addItem(IMapObject item, int x, int y) {
        item.setX(x);
        item.setY(y);
        board[x][y].add(item);
    }

    /**
     * Remove item from board
     * 
     * @param item - item to remove
     */
    public void removeItem(IMapObject item) {
        board[item.getX()][item.getY()].remove(item);
    }

    /**
     * Attempts to move robot in the robots direction
     * 
     * @param item   - robot to move
     * @param amount - amount to move
     * @return true if move is completed
     */
    public boolean moveItem(Robot item, int amount) {
        return moveItem(item, amount, item.getDir());
    }

    /**
     * Attempts to move robot in given direction. Will move step by step till
     * stopped or done
     * 
     * @param item   - robot to move
     * @param amount - amount to move
     * @param dir    - direction to move in
     * @return true if move was completed till end
     */
    private boolean moveItem(Robot item, int amount, Direction dir) {
        if (!getObjects().contains(item))
            return false;
        if (amount == 0)
            return true;// moved all the way
        int x = item.getX(); // Start X/Y
        int y = item.getY();
        if (amount < 0) { // If it's a "reverse"
            dir = Direction.uTurn(dir); // Flip direction and make moves positive
            amount = -amount;
        }

        // Checks if there is a wall on current tile that blocks path
        for (IMapObject obj : getItems(x, y)) {
            if (obj instanceof Wall) {
                if (((Wall) obj).getDir() == dir) {
                    return false;
                }
            }
        }

        // Changes X/Y to next position
        x = nextPos(dir, x, y)[0];
        y = nextPos(dir, x, y)[1];

        if (x < 0 || x >= width || y < 0 || y >= height) {
            removeItem(item);
            return true;
        }

        Robot push = null;// To hold robot if there is a robot that needs to be pushed
        // Checks stuff on next tile to see if it can move there
        for (IMapObject obj : getItems(x, y)) {
            if (obj instanceof Wall) {
                // If there is wall there checks if it blocks entrance to tile
                if (((Wall) obj).getDir() == Direction.uTurn(dir)) {
                    return false;
                }
            } else if (obj instanceof Robot) { // If there is a robot there try to push it
                push = (Robot) obj; // This way to avoid ConcurrentModificationException
            }
        }

        // If there was a robot to push
        if (push != null) {
            // Tries to move robot, if it doesn't move it's blocked
            if (!pushRobot(push, dir)) {
                return false;
            }
        }

        // Nothing blocking and everything moved so we can move one step
        removeItem(item);
        item.move(1, dir);
        addItem(item, item.getX(), item.getY());
        // calls self till blocked or done
        moveItem(item, amount - 1, dir);
        return true;
    }

    /**
     * Push a robot 1 tile in a direction
     * 
     * @param robot - robot to push
     * @param dir   - direction to push
     * @return boolean if move is done
     */
    private boolean pushRobot(Robot robot, Direction dir) {
        return moveItem(robot, 1, dir);
    }

    /**
     * Get item at (x,y)
     * 
     * @param x - x position
     * @param y - y position
     * @return ArrayList<MapObject> that contains all object on the tile
     */
    public ArrayList<IMapObject> getItems(int x, int y) {
        return board[x][y];
    }

    /**
     * Return all MapObjects on the board
     * 
     * @return ArrayList<IMapObject>
     */
    public ArrayList<IMapObject> getObjects() {
        ArrayList<IMapObject> mapObjects = new ArrayList<>();

        for (ArrayList<IMapObject>[] arrayLists : this.board) {
            for (int y = 0; y < this.board.length; y++) {
                if (arrayLists[y] != null) {
                    mapObjects.addAll(arrayLists[y]);
                }
            }
        }
        return mapObjects;
    }

    /**
     * Fires lasers from all walls and robots
     */
    public void fireLasers() {
        ArrayList<IMapObject> all = getObjects(); // All objects
        for (IMapObject obj : all) {
            int x = obj.getX(); // x
            int y = obj.getY(); // x
            if (obj instanceof Wall) {
                int dmg = ((Wall) obj).getDmg(); // wall dmg
                if (dmg > 0) { // If dmg
                    Direction dir = Direction.uTurn(((Wall) obj).getDir()); // Walls fires reversed
                    shoot(dir, x, y, dmg); // Shoots from spot
                }
            } else if (obj instanceof Robot) {
                Direction dir = ((Robot) obj).getDir();
                int[] n = nextPos(dir, x, y);
                if (!(n[0] < 0 || n[0] >= width || n[1] < 0 || n[1] >= height)) {
                    shoot(dir, n[0], n[1], 1);
                }

            }
        }
    }

    /**
     * Shoots a laser in given direction from given point
     * 
     * @param dir - Direction laser fires
     * @param x   - x position of laser
     * @param y   - y position of laser
     * @param dmg - damage laser deals to robots
     */
    private void shoot(Direction dir, int x, int y, int dmg) {
        boolean wall = false; // Hits wall
        ArrayList<IMapObject> tile = getItems(x, y); // Items

        ArrayList<IMapObject> ded = new ArrayList<>();
        for (IMapObject obj : tile) {
            if (obj instanceof Robot) { // Hits robot
                ((Robot) obj).dmg(dmg); // Damages robot
                if (((Robot) obj).getHp() == 0) { // Dead robot
                    ded.add(obj);
                }
            } else if (obj instanceof Wall) { // At wall
                if (((Wall) obj).getDir() == dir) { // If it block from keeping going
                    wall = true; // Stops after tile
                }
            }
        }
        for (IMapObject obj : ded) {
            System.out.println(obj.getName() + " is ded");
            removeItem(obj);
        }

        x = nextPos(dir, x, y)[0];
        y = nextPos(dir, x, y)[1];

        if (x < 0 || y < 0 || x >= width || y >= height) { // Out of board
            return;
        } else {
            for (IMapObject obj : getItems(x, y)) { // Items at next tile
                if (obj instanceof Wall) {
                    if (((Wall) obj).getDir() == Direction.uTurn(dir)) { // Checks if there is a wall blocking entrance
                        wall = true;
                    }
                }
            }
        }

        if (!wall) // If not blocked
            shoot(dir, x, y, dmg);

    }

    /**
     * Returns next (x,y) in direction
     * 
     * @param dir - Direction of next coordinate
     * @param x   - x coordinate
     * @param y   - y coordinate
     * @return Array with 2 integers index 0 is x and index 1 is y
     */
    private int[] nextPos(Direction dir, int x, int y) {
        int[] ret = new int[2];
        switch (dir) {
        case NORTH:
            y++;
            break;
        case SOUTH:
            y--;
            break;
        case EAST:
            x++;
            break;
        case WEST:
            x--;
            break;
        }
        ret[0] = x;
        ret[1] = y;
        return ret;
    }
}
