package inf112.skeleton.app;

import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.interfaces.ICheckPoint;
import inf112.skeleton.app.interfaces.IDirectionalObject;
import inf112.skeleton.app.interfaces.IMapObject;
import inf112.skeleton.app.object.Gear;
import inf112.skeleton.app.object.HealDraw;
import inf112.skeleton.app.object.Hole;
import inf112.skeleton.app.object.Laser;
import inf112.skeleton.app.object.Pusher;
import inf112.skeleton.app.object.Robot;
import inf112.skeleton.app.object.Wall;
import inf112.skeleton.app.object.belts.Belt;

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
        board = new ArrayList[height][width];

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
    public void addItem(IMapObject item, int x, int y){
        item.setX(x);
        item.setY(y);
        board[y][x].add(item);
    }

    /**
     * Remove item from board
     * 
     * @param item - item to remove
     */
    public void removeItem(IMapObject item){
        board[item.getY()][item.getX()].remove(item);
    }

    /**
     * Attempts to move robot in the robots direction
     * 
     * @param item   - robot to move
     * @param amount - amount to move
     * @return true if move is completed
     */
    public boolean moveItem(Robot item, int amount){
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
    private boolean moveItem(Robot item, int amount, Direction dir){
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
            if (obj instanceof Wall && ((Wall) obj).getDir() == dir) {
                return false;
            }
        }

        // Changes X/Y to next position
        x = nextPos(dir, x, y)[0];
        y = nextPos(dir, x, y)[1];

        if (x < 0 || x >= width || y < 0 || y >= height) {
            ded(item);
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
            } else if (obj instanceof Hole) {
                ded(item);
                return true;
            } else if (obj instanceof Pusher) {
                return false;
            }
        }

        // If there was a robot to push
        if (push != null && !pushRobot(push, dir)) {
            // Tries to move robot, if it doesn't move it's blocked
            return false;
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
    private boolean pushRobot(Robot robot, Direction dir){
        return moveItem(robot, 1, dir);
    }

    /**
     * Fires lasers from all walls and robots
     */
    public void fireLasers(){
        fireWalls();
        fireRobots();
    }

    /**
     * Fires lasers from all walls inwards
     */
    public ArrayList<Laser> fireWalls(){
        ArrayList<Laser> arr = new ArrayList<>();
        ArrayList<IDirectionalObject> all = getDirectionals(); // All objects
        for (IDirectionalObject obj : all) {
            int x = obj.getX(); // x
            int y = obj.getY(); // x
            if (obj instanceof Wall) {
                int dmg = ((Wall) obj).getDmg(); // wall dmg
                if (dmg > 0) { // If dmg
                    Direction dir = Direction.uTurn(obj.getDir()); // Walls fires reversed
                    for (int[] ar : shoot(dir, x, y, dmg)) {
                        arr.add(new Laser(ar[0], ar[1], dir, ((Wall) obj).getDmg()));
                    }
                }
            }
        }
        return arr;
    }

    /**
     * Fires lasers from all robot lasers move forward from the front
     */
    public void fireRobots(){
        for (Robot obj : getRobots()) {
            int x = obj.getX(); // x
            int y = obj.getY(); // x
            Direction dir = obj.getDir();
            int[] n = nextPos(dir, x, y);
            if (!(n[0] < 0 || n[0] >= width || n[1] < 0 || n[1] >= height)) {
                shoot(dir, n[0], n[1], 1);
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
    private ArrayList<int[]> shoot(Direction dir, int x, int y, int dmg){
        ArrayList<int[]> ar = new ArrayList<>();
        boolean wall = false; // Hits wall
        ArrayList<IMapObject> tile = getItems(x, y); // Items
        ar.add(new int[] { x, y });
        for (IMapObject obj : tile) {
            if (obj instanceof Robot) { // Hits robot
                ((Robot) obj).dmg(dmg); // Damages robot
                if (((Robot) obj).getHp() == 0) { // Dead robot
                    ded((Robot) obj);
                }
                return ar;
            } else if (obj instanceof Wall) { // At wall
                if (((Wall) obj).getDir() == dir) { // If it block from keeping going
                    wall = true; // Stops after tile
                }
            } else if (obj instanceof Pusher) {
                wall = true;
            }
        }

        x = nextPos(dir, x, y)[0];
        y = nextPos(dir, x, y)[1];

        if (x < 0 || y < 0 || x >= width || y >= height) { // Out of board
            return ar;
        } else {
            for (IMapObject obj : getItems(x, y)) { // Items at next tile
                if (obj instanceof Wall && ((Wall) obj).getDir() == Direction.uTurn(dir)) {
                    wall = true;
                }
            }
        }

        if (!wall) // If not blocked
            ar.addAll(shoot(dir, x, y, dmg));
        return ar;
    }

    /**
     * Returns next (x,y) in direction
     * 
     * @param dir - Direction of next coordinate
     * @param x   - x coordinate
     * @param y   - y coordinate
     * @return Array with 2 integers index 0 is x and index 1 is y
     */
    private int[] nextPos(Direction dir, int x, int y){
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
        default:
            break;
        }
        ret[0] = x;
        ret[1] = y;
        return ret;
    }

    /**
     * 
     * @param x   - x position from
     * @param y   - y position from
     * @param dir - the direction too check in
     * @return false if way is blocked
     */
    private boolean posibleMove(int x, int y, Direction dir){
        for (IMapObject obj : getItems(x, y)) {
            if (obj instanceof Wall && ((Wall) obj).getDir() == dir) {
                return false;
            }
        }

        int[] nX = nextPos(dir, x, y);
        for (IMapObject obj : getItems(nX[0], nX[1])) {
            if (obj instanceof Wall) {
                // If there is wall there checks if it blocks entrance to tile
                if (((Wall) obj).getDir() == Direction.uTurn(dir)) {
                    return false;
                }
            } else if (obj instanceof Robot) {
                if (!posibleMove(nX[0], nX[1], dir)) {
                    return false;
                }
            } else if (obj instanceof Pusher) {
                return false;
            }
        }

        return true;
    }

    /**
     * Moves all robots on belts First all robots on double belts Then all robots on
     * any belt
     */
    public void moveBelts(){
        moveBelt(1);
        moveBelt(2);
    }

    /**
     * Calls the moving of robots
     * 
     * @param i - 1 if only double should push 2 else 1
     */
    private void moveBelt(int i){
        for (Robot ob : getRobots()) {
            int x = ob.getX();
            int y = ob.getY();
            for (IMapObject obj : getItems(x, y)) {
                if (obj instanceof Belt) {
                    int[] next = nextPos(((Belt) obj).getDir(), x, y);

                    Belt nextBelt = getBelt(next[0], next[1]);

                    if (i == 1) {
                        if (((Belt) obj).getStrength() != 2)
                            continue;
                        beltMove(((Belt) obj), nextBelt, ob);
                    } else {
                        beltMove(((Belt) obj), nextBelt, ob);
                    }
                }
            }
        }
    }

    /**
     * 
     * @param oldBelt - Belt robot stood on
     * @param newBelt - Belt robot gets pushed on or null
     * @param r       - Robot to be pushed
     */
    private void beltMove(Belt oldBelt, Belt newBelt, Robot r){
        Direction oldDir = oldBelt.getDir();

        if (newBelt == null) {
            pushRobot(r, oldDir);
            return;
        }

        if (!posibleMove(r.getX(), r.getY(), oldDir))
            return;

        Direction newDir = newBelt.getDir();

        pushRobot(r, oldDir);
        if (oldDir.equals(newDir) || Direction.uTurn(oldDir).equals(newDir))
            return;
        else if (!oldDir.equals(newDir)) {
            r.turn(Direction.relation(oldDir, newDir));
        }

    }

    /**
     * @return - all directionals (Robots, Walls, Belts)
     */
    public ArrayList<IDirectionalObject> getDirectionals(){
        ArrayList<IDirectionalObject> ret = new ArrayList<>();

        for (IMapObject obj : getObjects()) {
            if (obj instanceof IDirectionalObject) {
                ret.add((IDirectionalObject) obj);
            }
        }

        return ret;
    }

    /**
     * @return - All robots on board
     */
    public ArrayList<Robot> getRobots(){
        ArrayList<Robot> ret = new ArrayList<>();

        for (IMapObject obj : getObjects()) {
            if (obj instanceof Robot) {
                ret.add((Robot) obj);
            }
        }

        return ret;
    }

    /**
     * 
     * @param x - x to get belt at
     * @param y - y to get belt at
     * @return - Belt or null if no belt
     */
    private Belt getBelt(int x, int y){
        if (getItems(x, y) == null)
            return null;
        for (IMapObject ob : getItems(x, y)) {
            if (ob instanceof Belt)
                return (Belt) ob;
        }
        return null;
    }

    /**
     * kills robot sets robot hp to 0 and removes from board
     * 
     * @param r - robot to kill
     */
    private void ded(Robot r){
        System.out.println(r.getName() + " is ded");
        r.setHp(0);
        removeItem(r);
    }

    /**
     * Return all MapObjects on the board
     * 
     * @return ArrayList<IMapObject>
     */
    public ArrayList<IMapObject> getObjects(){
        ArrayList<IMapObject> mapObjects = new ArrayList<>();

        for (ArrayList<IMapObject>[] arrayLists : this.board) {
            for (int y = 0; y < width; y++) {
                if (arrayLists[y] != null) {
                    mapObjects.addAll(arrayLists[y]);
                }
            }
        }
        return mapObjects;
    }

    /**
     * Get item at (x,y)
     * 
     * @param x - x position
     * @param y - y position
     * @return ArrayList<MapObject> that contains all object on the tile
     */
    public ArrayList<IMapObject> getItems(int x, int y){
        if (x >= width || x < 0 || y < 0 || y >= height)
            return null;
        return board[y][x];
    }

    /**
     * Aktivates all pusher in current phase
     * 
     * @param i - phase currently in
     */
    public void pushAll(int i){

        ArrayList<Pusher> pushers = new ArrayList<>();

        for (IDirectionalObject o : getDirectionals()) {
            if (o instanceof Pusher) {
                Pusher p = (Pusher) o;
                switch (i) {
                case 1:
                    if (p.getPhase()) {
                        pushers.add(p);
                    }
                    break;
                case 3:
                    if (p.getPhase()) {
                        pushers.add(p);
                    }
                    break;
                case 5:
                    if (p.getPhase()) {
                        pushers.add(p);
                    }
                    break;
                case 2:
                    if (!p.getPhase()) {
                        pushers.add(p);
                    }
                    break;
                case 4:
                    if (!p.getPhase()) {
                        pushers.add(p);
                    }
                    break;
                default:
                    break;
                }
            }
        }

        ArrayList<Robot> rs = new ArrayList<>();
        ArrayList<Direction> dirs = new ArrayList<>();

        for (Pusher push : pushers) {
            int[] next = nextPos(push.getDir(), push.getX(), push.getY());
            for (IMapObject obj : getItems(next[0], next[1])) {
                if (obj instanceof Robot) {
                    rs.add((Robot) obj);
                    dirs.add(push.getDir());
                }
            }
        }

        for (int j = 0; j < rs.size(); j++) {
            pushRobot(rs.get(j), dirs.get(j));
        }
    }

    /**
     * Turns all robots on gears
     */
    public void gearsDo(){
        for (IMapObject obj : getObjects()) {
            if (obj instanceof Gear) {
                for (IMapObject ob : getItems(obj.getX(), obj.getY())) {
                    if (ob instanceof Robot) {
                        ((Robot) ob).turn(((Gear) obj).getLR());
                    }
                }
            }
        }
    }

    /**
     * Heals all robots on wrenches
     */
    public void healDo(){
        for (IMapObject obj : getObjects()) {
            if (obj instanceof HealDraw) {
                for (IMapObject ob : getItems(obj.getX(), obj.getY())) {
                    if (ob instanceof Robot) {
                        /*
                        if (((HealDraw) obj).draw()) {
                            // draw card
                        }
                        */
                        ((Robot) ob).heal(1);
                    }
                }
            }
        }
    }

    /**
     * All automated turn events in order
     * 
     * @param i - What phase we are on
     */
    public void turnStuff(int i){
        moveBelts();
        pushAll(i);
        gearsDo();
        fireLasers();
        healDo();
        flagCheck();
        // energy();
        // checkpoint();
    }

    /**
     * gets laser objects containing position and direction of all tiles lasers
     * shoot over for displaying mostly
     * 
     * @return ArrayList with laser objects
     */
    public ArrayList<Laser> getLasers(){
        ArrayList<Laser> arr = new ArrayList<>();
        ArrayList<IDirectionalObject> all = getDirectionals(); // All objects
        for (IDirectionalObject obj : all) {

            if (obj instanceof Wall) {
                int dmg = ((Wall) obj).getDmg(); // wall dmg
                if (dmg > 0) { // If dmg
                    int x = obj.getX(); // x
                    int y = obj.getY(); // y
                    Direction dir = Direction.uTurn(((Wall) obj).getDir());
                    boolean stop = false;
                    boolean add = true;
                    while (x < width && x >= 0 && y >= 0 && y < height && !stop) {
                        for (IMapObject item : getItems(x, y)) {
                            if (item instanceof Wall) {
                                if (((Wall) item).getDir() == dir) {
                                    stop = true;
                                    break;
                                }
                            } else if (item instanceof Pusher) {
                                stop = true;
                                add = false;
                                break;
                            }
                        }
                        if (add)
                            arr.add(new Laser(x, y, dir, dmg));
                        x = nextPos(dir, x, y)[0];
                        y = nextPos(dir, x, y)[1];
                        if (x < width && x >= 0 && y >= 0 && y < height) {
                            for (IMapObject item : getItems(x, y)) {
                                if (item instanceof Wall && Direction.uTurn(((Wall) item).getDir()) == dir) {
                                    stop = true;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return arr;
    }

    private void flagCheck(){
        for (IMapObject itm : getObjects()) {
            if (itm instanceof ICheckPoint) {
                for (IMapObject obj : getItems(itm.getX(), itm.getY())) {
                    if (obj instanceof Robot) {
                        ((Robot) obj).getPlayer().setSpawn((ICheckPoint) itm);
                    }
                }
            }
        }
    }

}
