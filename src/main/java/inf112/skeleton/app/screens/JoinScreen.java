package inf112.skeleton.app.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import inf112.skeleton.app.GameClass;
import inf112.skeleton.app.Player;
import inf112.skeleton.app.Setting;
import inf112.skeleton.app.object.Robot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JoinScreen implements Screen {

    private Menu m;
    private Stage stage;
    private int port;
    private Protocol protocol;
    private TextField name;
    private TextField ip;
    private TextField ownIp;
    private GameClass g;
    private boolean swap = false;
    private int pp = 0;
    private int player;
    private Setting set;
    private boolean t = true;

    public JoinScreen(Menu m, GameClass g, Setting set) {
        this.set = set;
        this.g = g;
        this.m = m;
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

        port = 25565;
        protocol = Protocol.TCP;

        ip = new TextField("84.215.102.111", skin);
        ownIp = new TextField("192.168.0.13", skin);
        name = new TextField("Name", skin);
        TextButton button = new TextButton("Join", skin);


        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String send = "";
                SocketHints sh = new SocketHints();
                sh.connectTimeout = 10000;
                Socket socket = Gdx.net.newClientSocket(protocol, ip.getText(), port, sh);
                try {
                    send = name.getText() + "," + ownIp.getText() + ",25565\n";
                    socket.getOutputStream().write(send.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Label joinIpStr = new Label("IP Address", skin);
        joinIpStr.setPosition(710, 585);
        stage.addActor(joinIpStr);

        ip.setPosition(700, 525);
        ip.setSize(300, 60);

        Label ownIpStr = new Label("Your IP", skin);
        ownIpStr.setPosition(710, 435);
        stage.addActor(ownIpStr);

        ownIp.setPosition(700, 375);
        ownIp.setSize(300, 60);

        Label nameStr = new Label("Your Name", skin);
        nameStr.setPosition(710, 285);
        stage.addActor(nameStr);

        name.setPosition(700, 225);
        name.setSize(300, 60);

        button.setPosition(150, 550);
        stage.addActor(ip);
        stage.addActor(ownIp);
        stage.addActor(name);
        stage.addActor(button);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                ServerSocketHints ssh = new ServerSocketHints();
                ssh.acceptTimeout = 0;
                ServerSocket server = Gdx.net.newServerSocket(protocol, port, ssh);
                while (t) {
                    Socket s = server.accept(null);
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(s.getInputStream()));

                    try {
                        String st = buffer.readLine();
                        System.out.println(st);
                        String[] ss = st.split(",");
                        System.out.println(ss[0]);
                        int ps = ss.length / 3;
                        g.setPlayer(Integer.parseInt(ss[0]));
                        player = Integer.parseInt(ss[0]);
                        int id = 0;
                        for (int i = 2; i < ps * 3 + 2; i = i + 3) {
                            Robot r = new Robot(pp, pp++, "robot " + (id + 1));
                            g.players().add(new Player(ss[i], ss[i + 1], Integer.parseInt(ss[i + 2]), r, id++));
                            g.getBoard().addItem(r, r.getX(), r.getY());
                        }
                        g.setMap(ss[1]);
                        t = false;
                        swap = true;
                        server.dispose();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
        t1.start();
    }

    @Override
    public void show() {
        // Nothing
    }

    @Override
    public void render(float delta) {
        if (swap) swap();
        Gdx.gl.glClearColor(1, 1, 1, 1);
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

    private void swap() {
        ((Game) Gdx.app.getApplicationListener()).setScreen(new GameView(g, player, set));
    }
}
