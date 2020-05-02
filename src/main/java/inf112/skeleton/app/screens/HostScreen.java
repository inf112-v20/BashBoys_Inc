package inf112.skeleton.app.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import inf112.skeleton.app.GameClass;
import inf112.skeleton.app.Player;
import inf112.skeleton.app.Setting;
import inf112.skeleton.app.object.Robot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class HostScreen implements Screen {

    private Stage stage;
    private int port;
    private Protocol protocol;
    private GameClass g;
    private Menu m;
    private String pl;
    private Label p;
    private int pp = 1;
    private ServerSocket server;
    private Setting set;
    private boolean t = true;

    public HostScreen(Menu m, GameClass g, Setting set) {
        //visual stuff
        this.set = set;
        this.g = g;
        this.m = m;
        g.setPlayer(0);
        stage = new Stage();

        Texture imgTexture = new Texture(Gdx.files.internal("assets/Background.png"));
        Image img = new Image(imgTexture);
        img.setPosition(0, Gdx.graphics.getHeight() - img.getHeight());
        stage.addActor(img);

        Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin(Gdx.files.internal("assets/gui/skin/CustomSkin.json"));

        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(m);
            }
        });
        stage.addActor(backButton);

        //port to host on
        port = 25565;
        protocol = Protocol.TCP;

        //Play solo button
        TextButton start = new TextButton("Play", skin);
        start.setPosition(150, 550);
        start.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float xx, float yy) {
                t = false;
                server.dispose();
                g.setMap(set.map);

                //Sends player info to all players
                String[] sends = new String[g.players().size() - 1];
                String send = "";
                for (Player p : g.players()) {
                    send += p.getName() + "," + p.getIP() + "," + p.getPort() + ",";
                }
                send = send.substring(0, send.length() - 1); //Removes last comma
                send += "\n";
                SocketHints sh = new SocketHints();
                sh.connectTimeout = 10000;
                ArrayList<Socket> socks = new ArrayList<>();
                for (int i = 1; i < g.players().size(); i++) {
                    socks.add(Gdx.net.newClientSocket(protocol, g.players().get(i).getIP(),
                            g.players().get(i).getPort(), sh));
                    sends[i - 1] = (i) + "," + set.map + "," + send;
                }
                for (int i = 0; i < socks.size(); i++) {
                    try {
                        socks.get(i).getOutputStream().write(sends[i].getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameView(g, 0, set));
            }
        });
        stage.addActor(start);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                ServerSocketHints ssh = new ServerSocketHints();
                ssh.acceptTimeout = 0;
                server = Gdx.net.newServerSocket(protocol, port, ssh);
                while (t) {
                    Socket s = server.accept(null);
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(s.getInputStream()));

                    try {
                        String st = buffer.readLine();
                        System.out.println(st);
                        String[] ss = st.split(",");
                        System.out.println(ss[0] + " " + ss[1] + " " + ss[2]);
                        g.players().add(new Player(ss[0], ss[1], Integer.parseInt(ss[2]),
                                new Robot(pp, pp, "robot " + (pp + 1)), pp++));
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t1.start();

    }

    @Override
    public void show() {
        Skin skin = new Skin(Gdx.files.internal("assets/gui/skin/CustomSkin.json"));
        TextureAtlas atlas = new TextureAtlas("assets/gui/skin/CustomSkin.atlas");
        skin.addRegions(atlas);
        pl = "";
        for (Player p : g.players()) {
            pl += p.getName() + "      ";
        }
        p = new Label(pl, skin);
        p.setPosition(500, 575);
        stage.addActor(p);
    }

    @Override
    public void render(float delta) {

        if (g.players().size() == 8) {
            server.dispose();
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameView(g, 0, set));
        }

        pl = "";
        for (Player pla : g.players()) {
            pl += pla.getName() + "      ";
        }
        p.setText(pl);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

}
