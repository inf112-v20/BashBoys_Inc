package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import inf112.skeleton.app.object.Robot;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewEngine extends com.badlogic.gdx.Game {
    private Game game;
    private TiledMap map;

    private Stage stage;

    private Texture myTexture;
    private TextureRegion myTextureRegion;
    private TextureRegionDrawable myTexRegionDrawable;
    private ImageButton button;

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

        //myTexture = new Texture(Gdx.files.internal("assets/exampleCard.png"));
        //myTextureRegion = new TextureRegion(myTexture);
        //myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        //button = new ImageButton(myTexRegionDrawable);

        stage = new Stage(new ScreenViewport());

        //button.setWidth(150);
        //button.setHeight(150);

        Card card1 = new Card(150, 0);
        Card card2 = new Card(300, 0);
        Card card3 = new Card(0, 0);
        Card card4 = new Card(450, 0);
        Card card5 = new Card(600, 0);


        stage.addActor(card1.getActor());
        stage.addActor(card2.getActor());
        stage.addActor(card3.getActor());
        stage.addActor(card4.getActor());
        stage.addActor(card5.getActor());
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
