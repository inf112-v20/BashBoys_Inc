package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.object.Robot;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewEngine extends ApplicationAdapter {
    private Board board;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    private HashMap<String, TiledMapTile> mapTiles = new HashMap<>();
    private HashMap<String, TiledMapTile> robotTiles = new HashMap<>();
    private TiledMapTileLayer boardLayer;
    private TiledMapTileLayer objectLayer;
    private TiledMapTileLayer robotLayer;


    public ViewEngine(Board board) {
        this.board = board;
    }

    @Override
    public void create() {
        map = new TmxMapLoader().load("assets/maps/roborallyCleanBoard.tmx");
        boardLayer = (TiledMapTileLayer) map.getLayers().get("Board");

        objectLayer = (TiledMapTileLayer) map.getLayers().get("Objects");
        robotLayer = (TiledMapTileLayer) map.getLayers().get("Robots");

        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();

        // Add textures
        TiledMapTileSet mapTileSet = map.getTileSets().getTileSet("MapTileSet");
        for (TiledMapTile tile : mapTileSet) {
            Object property = tile.getProperties().get("name");
            if (property != null) {
                mapTiles.put((String) property, tile);
            }
        }

        TiledMapTileSet robotTileSet = map.getTileSets().getTileSet("RobotTileSet");
        for (TiledMapTile tile : robotTileSet) {
            Object property = tile.getProperties().get("robot 1");
            if (property != null) {
                robotTiles.put((String) property, tile);
            }
        }
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        ArrayList<IMapObject> gameObjects = board.getObjects();

        for (IMapObject gameObject : gameObjects) {
            if (gameObject != null) {
                int x = gameObject.getX();
                int y = gameObject.getY();

                if (gameObject instanceof Robot) {
                    // GameObject is a robot, get tile from robotTileSet and add to robotLayer
                    TiledMapTileLayer.Cell cell = robotLayer.getCell(x, y);
                    if (cell == null) {
                        cell = new TiledMapTileLayer.Cell();
                        robotLayer.setCell(x, y, cell);
                    }

                    TiledMapTile tile = getRobotTileByName(getStrRobot(((Robot) gameObject).getDir()));
                    cell.setTile(tile);
                } else {
                    // GameObject is not a robot do something else
                    TiledMapTileLayer.Cell cell = boardLayer.getCell(x, y);
                    if (cell == null) {
                        cell = new TiledMapTileLayer.Cell();
                        boardLayer.setCell(x, y, cell);
                    }

                    TiledMapTile tile = getMapTileByName(gameObject.getName());
                    cell.setTile(tile);
                }
            }
        }

        renderer.setView(camera);
        renderer.render();
    }

    @Override
    public void resize(int width, int height) {
        camera.position.set((width / 2), (height / 2), 0);
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    private TiledMapTile getMapTileByName(String name) {
        return mapTiles.get(name);
    }

    private TiledMapTile getRobotTileByName(String name) {
        return robotTiles.get(name);
    }
    
    private String getStrRobot(Direction dir) {
        switch(dir) {
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