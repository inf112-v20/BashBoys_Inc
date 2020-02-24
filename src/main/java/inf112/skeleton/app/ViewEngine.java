package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import inf112.skeleton.app.object.Robot;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewEngine extends com.badlogic.gdx.Game {
    private Game game;
    private TiledMap map;

    private Stage stage;

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

        stage = new Stage(new ScreenViewport());

        Actor card1 = CardFactory.create(0, 0);
        Actor card2 = CardFactory.create(150, 0);
        Actor card3 = CardFactory.create(300, 0);
        Actor card4 = CardFactory.create(450, 0);
        Actor card5 = CardFactory.create(600, 0);

        stage.addActor(card1);
        stage.addActor(card2);
        stage.addActor(card3);
        stage.addActor(card4);
        stage.addActor(card5);
        Gdx.input.setInputProcessor(stage);

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
        stage.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());


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
        stage.draw();
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
}
