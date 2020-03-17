package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.gui.GuiCards;
import inf112.skeleton.app.gui.GuiHud;
import inf112.skeleton.app.interfaces.IMapObject;
import inf112.skeleton.app.object.Gear;
import inf112.skeleton.app.object.Laser;
import inf112.skeleton.app.object.Robot;
import inf112.skeleton.app.object.Wall;

import javax.naming.NameNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ViewEngine extends com.badlogic.gdx.Game {
    private final Board board;
    private TiledMap map;

    private Stage uiStage;

    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    private HashMap<String, TiledMapTile> mapTiles = new HashMap<>();
    private HashMap<String, HashMap<String, TiledMapTile>> robotTiles = new HashMap<>();

    private TiledMapTileLayer boardLayer;
    private TiledMapTileLayer objectLayer;
    private TiledMapTileLayer wallLayer;
    private TiledMapTileLayer wallLayer2;
    private TiledMapTileLayer robotLayer;
    private TiledMapTileLayer laserLayer;
    private TiledMapTileLayer emiterLayer;

    private GuiCards guiCards = new GuiCards();
    private GuiHud guiHud = new GuiHud();
    private Game g;

    public ViewEngine(Game g) {
        this.g = g;
        this.board = g.getBoard();
    }

    @Override
    public void create(){

        uiStage = new Stage(new ScreenViewport());
        guiCards.startCardGui(uiStage, 8, g, board);
        guiHud.startHud(uiStage,g);
        Gdx.input.setInputProcessor(uiStage);

        map = new TmxMapLoader().load("assets/maps/roborallyCleanBoard.tmx");
        boardLayer = (TiledMapTileLayer) map.getLayers().get("Board");

        objectLayer = (TiledMapTileLayer) map.getLayers().get("Objects");
        robotLayer = (TiledMapTileLayer) map.getLayers().get("Robots");
        wallLayer = (TiledMapTileLayer) map.getLayers().get("Wall");
        wallLayer2 = (TiledMapTileLayer) map.getLayers().get("Wall2");
        laserLayer = (TiledMapTileLayer) map.getLayers().get("Lasers");
        emiterLayer = (TiledMapTileLayer) map.getLayers().get("Emiters");

        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();

        // Add textures
        TiledMapTileSet mapTileSet = map.getTileSets().getTileSet("MapTileSet");
        for (TiledMapTile tile : mapTileSet) {
            MapProperties properties = tile.getProperties();

            Iterator<String> keys = tile.getProperties().getKeys();
            if (keys.hasNext()) {
                String name = keys.next();
                Object val = properties.get(name);
                mapTiles.put((String) val, tile);
            }
            if (properties != null) {
                mapTiles.put((String) "wall", tile);
            }
        }

        TiledMapTileSet robotTileSet = map.getTileSets().getTileSet("RobotTileSet");
        for (TiledMapTile tile : robotTileSet) {
            MapProperties properties = tile.getProperties();
            Iterator<String> keys = properties.getKeys();
            if (keys.hasNext()) {
                String player = keys.next();
                Object direction = properties.get(player);
                if (!robotTiles.containsKey(player)) {
                    robotTiles.put(player, new HashMap<>());
                }

                robotTiles.get(player).put((String) direction, tile);
            }
        }
        for (Laser l : board.getLasers()) {
            int x = l.getX();
            int y = l.getY();
            TiledMapTileLayer.Cell cell = laserLayer.getCell(x, y);
            if (cell == null) {
                cell = new TiledMapTileLayer.Cell();
                laserLayer.setCell(x, y, cell);
            }
            try {
                TiledMapTile tile = getMapTileByName(l.getName());
                cell.setTile(tile);
            } catch (NameNotFoundException error) {
                System.out.println("Could not find the mapTile with name " + l.getName());
            }
        }System.out.println(board.getItems(11, 5));
    }

    @Override
    public void dispose(){
        map.dispose();
        renderer.dispose();
        uiStage.dispose();
    }

    @Override
    public void render(){
        this.clearLayer(robotLayer);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        ArrayList<IMapObject> gameObjects = board.getObjects();

        for (IMapObject gameObject : gameObjects) {
            if (gameObject != null) {
                int x = gameObject.getX();
                int y = gameObject.getY();

                if (gameObject instanceof Robot) {
                    TiledMapTileLayer.Cell cell = robotLayer.getCell(x, y);
                    // GameObject is a robot, get tile from robotTileSet and add to robotLayer
                    try {
                        TiledMapTile tile = getRobotTileByName(gameObject.getName(),
                                getStrRobot(((Robot) gameObject).getDir()));
                        if (cell == null) {
                            cell = new TiledMapTileLayer.Cell();
                            robotLayer.setCell(x, y, cell);
                        }
                        cell.setTile(tile);
                    } catch (NameNotFoundException error) {
                        // Name or direction of robot was not found
                        System.out.println("Name or direction of robot was not found. " + gameObject.getName() + " - "
                                + ((Robot) gameObject).getDir());
                    }
                } else if (gameObject instanceof Wall) {
                    // GameObject is not a robot do something else
                    TiledMapTileLayer.Cell cell = wallLayer.getCell(x, y);
                    TiledMapTileLayer.Cell cell2 = wallLayer2.getCell(x, y);
                    TiledMapTileLayer.Cell ecell = emiterLayer.getCell(x, y);
                    if (cell == null) {
                        cell = new TiledMapTileLayer.Cell();
                        wallLayer.setCell(x, y, cell);
                    }
                    if (cell2 == null) {
                        cell2 = new TiledMapTileLayer.Cell();
                        wallLayer2.setCell(x, y, cell2);
                    }
                    if (ecell == null) {
                        ecell = new TiledMapTileLayer.Cell();
                        emiterLayer.setCell(x, y, ecell);
                    }
                    try {
                        TiledMapTile tile = getMapTileByName(gameObject.getName());
                        TiledMapTile n = getMapTileByName(new Wall(Direction.NORTH).getName());
                        TiledMapTile s = getMapTileByName(new Wall(Direction.SOUTH).getName());
                        TiledMapTile e = getMapTileByName(new Wall(Direction.EAST).getName());
                        TiledMapTile w = getMapTileByName(new Wall(Direction.WEST).getName());

                        if (cell.getTile() == n || cell.getTile() == s || cell.getTile() == e || cell.getTile() == w) {
                            if (cell.getTile() != tile) {
                                cell2.setTile(tile);
                            } else {
                                cell.setTile(tile);
                            }
                        } else {
                            cell.setTile(tile);
                        }
                        if(((Wall)gameObject).getDmg() > 0) {
                            int dmg =((Wall)gameObject).getDmg();
                            TiledMapTile etile = getMapTileByName(dmg+"emitterNORTH");
                            ecell.setTile(etile);
                        }
                    } catch (NameNotFoundException error) {
                        System.out.println("Could not find the mapTile with name " + gameObject.getName());
                    }
                } else {
                    // GameObject is not a robot do something else
                    TiledMapTileLayer.Cell cell = objectLayer.getCell(x, y);
                    if (cell == null) {
                        cell = new TiledMapTileLayer.Cell();
                        objectLayer.setCell(x, y, cell);

                    }
                    try {
                        TiledMapTile tile = getMapTileByName(gameObject.getName());
                        cell.setTile(tile);
                    } catch (NameNotFoundException error) {
                        System.out.println("Could not find the mapTile with name " + gameObject.getName());
                    }
                }
            }
        }

        renderer.setView(camera);
        renderer.render();
        guiHud.updateHud(uiStage,g);
        // Should update guiCards and guiHud later
        uiStage.draw();
    }

    @Override
    public void resize(int width, int height){
        uiStage.getViewport().update(width, height, false);
        camera.position.set(384, 100, 0);
        ExtendViewport cameraViewport = new ExtendViewport(width * 2, height * 2, camera);
        cameraViewport.update(width, height, false);
    }

    @Override
    public void pause(){
        // Implemented method
    }

    @Override
    public void resume(){
        // Implemented method
    }

    /**
     * Get map tile
     * 
     * @param name name of tile
     * @return map tile
     */
    private TiledMapTile getMapTileByName(String name) throws NameNotFoundException{
        try {
            return mapTiles.get(name);
        } catch (Exception error) {
            throw new NameNotFoundException();
        }
    }

    /**
     * Get robot tile by name
     * 
     * @param name      - name of robot. (robot 1, robot 2 etc)
     * @param direction - Direction of robot. (RobotFaceNorth)
     * @return robot tile
     */
    private TiledMapTile getRobotTileByName(String name, String direction) throws NameNotFoundException{
        try {
            return robotTiles.get(name).get(direction);
        } catch (Exception error) {
            throw new NameNotFoundException();
        }
    }

    /**
     * Clear layer
     * 
     * @param layer - layer to clear
     */
    private void clearLayer(TiledMapTileLayer layer){
        int height = layer.getHeight();
        int width = layer.getWidth();
        try {
            TiledMapTile clearTile = getMapTileByName("clear");
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // Not sure which method is best set cell to null or set Tile to clearTile:
                    // layer.setCell(x, y, null);
                    TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                    if (cell == null) {
                        layer.setCell(x, y, new TiledMapTileLayer.Cell());
                        cell = layer.getCell(x, y);
                    }
                    cell.setTile(clearTile);
                }
            }
        } catch (NameNotFoundException error) {
            System.out.println("Could not find the clear tile in mapTiles.");
        }

    }

    /**
     * Get string name of robot
     * 
     * @param dir - direction of robot
     * @return name of robot
     */
    private String getStrRobot(Direction dir){
        switch (dir) {
        case NORTH:
            return "RobotFaceNorth";
        case SOUTH:
            return "RobotFaceSouth";
        case EAST:
            return "RobotFaceEast";
        case WEST:
            return "RobotFaceWest";
        default:
            return "Error direction not found";
        }
    }
}
