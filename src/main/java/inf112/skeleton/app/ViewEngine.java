package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import inf112.skeleton.app.object.Robot;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewEngine extends com.badlogic.gdx.Game {
    private Game game;
    private TiledMap map;

    private Stage uiStage;

    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    private HashMap<String, TiledMapTile> mapTiles = new HashMap<>();
    private HashMap<String, TiledMapTile> robotTiles = new HashMap<>();
    private TiledMapTileLayer boardLayer;
    private TiledMapTileLayer objectLayer;
    private TiledMapTileLayer robotLayer;


    public ViewEngine(Game game) {
        this.game = game;
    }

    @Override
    public void create() {

        uiStage = new Stage(new ScreenViewport());

        uiStage.addActor(CardFactory.create(0, 0));
        uiStage.addActor(CardFactory.create(150, 0));
        uiStage.addActor(CardFactory.create(300, 0));
        uiStage.addActor(CardFactory.create(450, 0));
        uiStage.addActor(CardFactory.create(600, 0));

        Gdx.input.setInputProcessor(uiStage);


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
        uiStage.dispose();
    }

    @Override
    public void render() {
        this.clearLayer(robotLayer);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //uiStage.act(Gdx.graphics.getDeltaTime());


        ArrayList<IMapObject> gameObjects = game.getObjects();

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

                    TiledMapTile tile = getRobotTileByName(gameObject.getName());
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
        uiStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        uiStage.getViewport().update(width, height, false);
        camera.position.set(384, 100, 0);
        ExtendViewport cameraViewport = new ExtendViewport(width * 2, height * 2, camera);
        cameraViewport.update(width, height, false);
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

    public void clearLayer(TiledMapTileLayer layer) {
        int height = layer.getHeight();
        int width = layer.getWidth();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                TiledMapTile clearTile = getMapTileByName("clear");
                layer.getCell(x, y).setTile(clearTile);
            }
        }
    }
}

