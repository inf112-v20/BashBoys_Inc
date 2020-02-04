package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewEngine extends ApplicationAdapter {
    private Game game;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    private HashMap<String, TiledMapTile> tiles = new HashMap<>();
    private TiledMapTileLayer.Cell playerNormalCell;
    private TiledMapTileLayer boardLayer;
    private TiledMapTileLayer objectLayer;
    private TiledMapTileLayer robotLayer;

    private TiledMapTileSet tiledMapTileSet;

    public ViewEngine(Game game) {
        this.game = game;
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
        tiledMapTileSet = map.getTileSets().getTileSet(0);
        for (TiledMapTile tile:tiledMapTileSet) {
            Object property = tile.getProperties().get("name");
            if (property != null) {
                tiles.put((String) property, tile);
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

        ArrayList<MapObject> gameObjects = game.getObjects();

        for (MapObject gameObject : gameObjects) {
            if (gameObject != null) {
                int x = gameObject.getX();
                int y = gameObject.getY();
                TiledMapTileLayer.Cell cell = robotLayer.getCell(x, y);
                if (cell == null) {
                    cell = new TiledMapTileLayer.Cell();
                    robotLayer.setCell(x, y, cell);
                }
                TiledMapTile tile = getTileByName(gameObject.getName());
                cell.setTile(tile);
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

    private TiledMapTile getTileByName(String name) {
        return tiles.get(name);
    }
}
