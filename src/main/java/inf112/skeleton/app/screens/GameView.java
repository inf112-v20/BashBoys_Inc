package inf112.skeleton.app.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import inf112.skeleton.app.Board;
import inf112.skeleton.app.GameClass;
import inf112.skeleton.app.Player;
import inf112.skeleton.app.Setting;
import inf112.skeleton.app.cards.MoveCard;
import inf112.skeleton.app.cards.Nothing;
import inf112.skeleton.app.cards.RotateCard;
import inf112.skeleton.app.cards.ShutDown;
import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.enums.LeftRight;
import inf112.skeleton.app.enums.Metrics;
import inf112.skeleton.app.gui.GuiCards;
import inf112.skeleton.app.gui.GuiHud;
import inf112.skeleton.app.gui.GuiPanel;
import inf112.skeleton.app.interfaces.ICard;
import inf112.skeleton.app.interfaces.IMapObject;
import inf112.skeleton.app.object.Laser;
import inf112.skeleton.app.object.Robot;
import inf112.skeleton.app.object.Wall;

import javax.naming.NameNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class GameView implements Screen {
    private final Board board;
    private TiledMap map;

    private Stage uiStage;

    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    private HashMap<String, TiledMapTile> mapTiles = new HashMap<>();
    private HashMap<String, HashMap<String, TiledMapTile>> robotTiles = new HashMap<>();

    // private TiledMapTileLayer boardLayer;
    private TiledMapTileLayer objectLayer;
    private TiledMapTileLayer wallLayer;
    private TiledMapTileLayer wallLayer2;
    private TiledMapTileLayer robotLayer;
    private TiledMapTileLayer laserLayer;
    private TiledMapTileLayer emiterLayer;

    private GuiCards guiCards = new GuiCards();
    private GuiHud guiHud = new GuiHud();
    private GuiPanel guiPanel = new GuiPanel();
    private float divRes = 1f;


    private GameClass g;
    private int player;
    private Thread t1;
    private Music sound;
    private Setting set;
    private float d = 0;

    public GameView(GameClass g, int player, Setting set) {
        this.player = player;
        this.g = g;
        this.board = g.getBoard();
        this.set = set;

        sound = Gdx.audio.newMusic(Gdx.files.internal("assets/music.mp3"));

        if (player == 0) {
            t1 = new Thread(new Runnable() {
                @Override
                public void run(){
                    ServerSocketHints ssh = new ServerSocketHints();
                    ssh.acceptTimeout = 0;
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ServerSocket server = Gdx.net.newServerSocket(Protocol.TCP, 25565, ssh);
                    while (true) {
                        Socket s = server.accept(null);
                        BufferedReader buffer = new BufferedReader(new InputStreamReader(s.getInputStream()));

                        try {
                            String st = buffer.readLine();
                            System.out.println(st);
                            String[] ss = st.split(",");
                            Player p = g.players().get(Integer.parseInt(ss[0]));
                            for (int i = 1; i < 16; i = i + 3) {
                                p.addCardToSheet(card(Integer.parseInt(ss[i]), Integer.parseInt(ss[i + 1]),
                                        Integer.parseInt(ss[i + 2]), p));
                            }
                            System.out.println(p);
                            System.out.println(p.getHand().size());
                            p.setReady(true);

                        } catch (IOException e) {
                            // Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            });
            t1.start();
        } else {
            t1 = new Thread(new Runnable() {
                @Override
                public void run(){
                    ServerSocketHints ssh = new ServerSocketHints();
                    ssh.acceptTimeout = 0;
                    ServerSocket server = Gdx.net.newServerSocket(Protocol.TCP, 25565, ssh);
                    while (true) {
                        Socket s = server.accept(null);
                        BufferedReader buffer = new BufferedReader(new InputStreamReader(s.getInputStream()));

                        try {
                            String st = buffer.readLine();
                            System.out.println(st);
                            String[] ss = st.split(",");
                            for (Player p : g.players()) {
                                p.clearSheet();
                            }
                            for (int i = 0; i < 4 * g.players().size() * 5; i = i + 4) {
                                Player p = g.players().get(Integer.parseInt(ss[i]));
                                p.addCardToSheet(card(Integer.parseInt(ss[i + 1]), Integer.parseInt(ss[i + 2]),
                                        Integer.parseInt(ss[i + 3]), p));
                            }
                            for (Player p : g.players()) {
                                p.setReady(true);
                            }
                        } catch (IOException e) {
                            // Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            });
            t1.start();
        }
    }

    @Override
    public void show(){
        if (set.music) {
            sound.setVolume(0.25f);
            sound.setLooping(true);
            sound.play();
        }

        g.t();
        uiStage = new Stage(new ScreenViewport());
        ui();
        Gdx.input.setInputProcessor(uiStage);

        map = new TmxMapLoader().load("assets/maps/roborallyCleanBoard.tmx");

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
                mapTiles.put("wall", tile);
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
        renderLasers();
    }

    private void renderLasers(){
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
        }

    }

    @Override
    public void dispose(){
        map.dispose();
        renderer.dispose();
        uiStage.dispose();
        sound.dispose();
    }

    @Override
    public void render(float delta){
        if (g.won()) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new Win(g.winner()));
        }
        if (g.getPlayer().getLifes() <= 0 && g.getPlayer().getRobot().getHp() == 0 && !set.spectator) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameOverScreen(this, set));
        }
        keyCheck(delta);

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
                        if (((Wall) gameObject).getDmg() > 0) {
                            int dmg = ((Wall) gameObject).getDmg();
                            TiledMapTile etile = getMapTileByName(dmg + "emitter" + ((Wall) gameObject).getDir());
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
            renderLasers();
        }

        renderer.setView(camera);
        renderer.render();
        guiHud.update(uiStage, g);
        guiCards.update(uiStage, g);
        guiPanel.update(uiStage, g);

        // Should update guiCards and guiHud later
        uiStage.draw();
    }

    private void keyCheck(float delta){
        if (set.devMode) {
            double timer = .25;
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && d > timer) {
                new RotateCard(LeftRight.LEFT, 0, false, "", g.getPlayer()).doStuff(board);
                d = 0;
            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && d > timer) {
                new RotateCard(LeftRight.RIGHT, 0, false, "", g.getPlayer()).doStuff(board);
                d = 0;
            } else if (Gdx.input.isKeyPressed(Input.Keys.UP) && d > timer) {
                new MoveCard(1, 0, "", g.getPlayer()).doStuff(board);
                d = 0;
            } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && d > timer) {
                new MoveCard(-1, 0, "", g.getPlayer()).doStuff(board);
                d = 0;
            } else if (Gdx.input.isKeyPressed(Input.Keys.ENTER) && d > timer) {
                board.turnStuff(1);
                board.pushAll(2);
                d = 0;
            } else if (Gdx.input.isKeyPressed(Input.Keys.J) && d > timer) {
                ArrayList<ICard> hand = g.getPlayer().getHand();
                if (hand.size() - 1 >= 0)
                    g.getPlayer().removeCardFromHand(hand.get(hand.size() - 1));
                d = 0;
            } else if (Gdx.input.isKeyPressed(Input.Keys.K) && d > timer) {
                g.getPlayer().giveCard(g.getDeck().getCard());
                d = 0;
            } else if (Gdx.input.isKeyPressed(Input.Keys.R) && d > timer) {
                g.respawn(g.getPlayer());
                d = 0;
            } else if (Gdx.input.isKeyPressed(Input.Keys.N) && d > timer) {
                g.getPlayer().takeLife();
                if(g.getPlayer().getLifes() == 0)board.ded(g.getPlayer().getRobot());
                d = 0;
            } else if (Gdx.input.isKeyPressed(Input.Keys.M) && d > timer) {
                g.getPlayer().giveLife();
                if(g.getPlayer().getLifes() == 1) {
                    g.getPlayer().getRobot().setHp(10);
                    board.addItem(g.getPlayer().getRobot(), g.getPlayer().getRobot().getX(), g.getPlayer().getRobot().getY());
                }
                d = 0;
            } else {
                d += delta;
            }
        }
    }

    @Override
    public void resize(int width, int height){
        uiStage.getViewport().update(width, height, true);
        camera.position.set((Metrics.TILE.width * board.width), (Metrics.TILE.height * board.height), 0);

        ExtendViewport cameraViewport = new ExtendViewport(width * divRes, height * divRes, camera);

        cameraViewport.update(width, height, true);
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

    @Override
    public void hide(){
        // Auto-generated method stub
    }

    /**
     * generates new card from parameters
     * 
     * @param priority - priority of card
     * @param type     - card type
     * @param amount   - amount to do type for
     * @param p        - player card belongs to
     * @return new Card with properties
     */
    private ICard card(int priority, int type, int amount, Player p){
        switch (type) {
        case 1:
            if (amount == 4) {
                return new MoveCard(-1, priority, "Back Up", p);
            } else {
                return new MoveCard(amount, priority, "Move " + amount, p);
            }
        case 2:
            if (amount == 2) {
                return new RotateCard(LeftRight.LEFT, priority, false, "Rotate Left", p);
            } else if (amount == 1) {
                return new RotateCard(LeftRight.RIGHT, priority, false, "Rotate Right", p);
            } else {
                return new RotateCard(LeftRight.LEFT, priority, true, "U-turn", p);
            }
        case 3:
            if (p.getShutdown() == 0)
                p.setShutDow(2);
            return new ShutDown(p);
        case 4:
            return new Nothing(p);
        default:
            return null;
        }
    }

    /**
     * Sets ui stuff
     */
    public void ui(){

        // Finds the best res/size for the board
        if ((64*15)<Gdx.graphics.getHeight()){
            while(((64*15)/divRes)<=Gdx.graphics.getHeight()){
                this.divRes -= 0.01f;
            }
        } else {
            while(((64*15)/divRes)>Gdx.graphics.getHeight()){
                this.divRes += 0.01f;
            }
        }

        Texture imgTexture = new Texture(Gdx.files.internal("assets/Background.png"));
        Image img = new Image(imgTexture);
        img.setPosition((64*12)/divRes, Gdx.graphics.getHeight() - img.getHeight());
        img.setZIndex(0);
        uiStage.addActor(img);

        guiPanel.setDivRes(divRes);
        guiPanel.initialize(uiStage, g, player);

        guiCards.setDivRes(divRes);
        guiCards.setPanel(guiPanel.panel);
        guiCards.initialize(uiStage, g, player);

        guiHud.setDivRes(divRes);
        guiHud.setPanel(guiPanel.panel);
        guiHud.initialize(uiStage, g, player);
    }
}
