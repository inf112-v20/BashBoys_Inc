package inf112.skeleton.app.screens;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import inf112.skeleton.app.GameClass;
import inf112.skeleton.app.Player;
import inf112.skeleton.app.Setting;
import inf112.skeleton.app.object.Robot;

public class JoinScreen implements Screen{

    Stage stage;
    int port;
    Protocol protocol;
    Socket socket;
    TextArea name;
    TextArea ip;
    TextArea ownIp;
    TextButton button;
    Thread t1;
    GameClass g;
    Skin skin;
    boolean swap = false;
    int pp = 0;
    int player;
    ServerSocket server;
    Setting set;
    boolean t = true;
    
    public JoinScreen(GameClass g,Setting set){
        this.set = set;
        this.g = g;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        BitmapFont font = new BitmapFont();
        skin = new Skin(Gdx.files.internal("assets/gui/skin/uiskin.json"));
        TextureAtlas atlas = new TextureAtlas("assets/gui/skin/uiskin.atlas");
        skin.addRegions(atlas);
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font;

        style.up = skin.getDrawable("apptheme_btn_radio_on_holo_light");
        style.down = skin.getDrawable("apptheme_btn_radio_on_focused_holo_light");
        
        port = 7777;
        protocol = Protocol.TCP;
        
        ip = new TextArea("84.215.102.111",skin);
        ownIp = new TextArea("192.168.0.13",skin);
        name = new TextArea("Name",skin);
        button = new TextButton("Join",skin);
        
        
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String send = "";
                SocketHints sh = new SocketHints();
                sh.connectTimeout = 10000;
                socket = Gdx.net.newClientSocket(protocol, ip.getText(), 25565, sh);
                try {
                    send = name.getText()+","+ownIp.getText()+",7777\n";
                    socket.getOutputStream().write(send.getBytes());
                }catch(IOException e) {
                    e.printStackTrace();
                }
            }
        });
        ip.setPosition(200, 300);
        ownIp.setPosition(400, 300);
        name.setPosition(200, 400);
        button.setPosition(200, 500);
        stage.addActor(ip);
        stage.addActor(ownIp);
        stage.addActor(name);
        stage.addActor(button);
        
        t1 = new Thread(new Runnable() {
            @Override
            public void run(){
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
                        System.out.println(ss[0]);
                        int ps = ss.length/3;
                        g.setPlayer(Integer.parseInt(ss[0]));
                        player = Integer.parseInt(ss[0]);
                        int id = 0;
                        for(int i = 2; i <ps*3+2;i=i+3) {
                            Robot r = new Robot(pp,pp++,"robot "+(id+1));
                            g.players().add(new Player(ss[i],ss[i+1],Integer.parseInt(ss[i+2]),r,id++));
                            g.getBoard().addItem(r, r.getX(), r.getY());
                        }
                        g.setMap(ss[1]);
                        t=false;
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
    public void show(){
        
    }

    @Override
    public void render(float delta){
        if(swap)swap();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        
    }

    @Override
    public void resize(int width, int height){
        // TODO Auto-generated method stub
        
    }

    @Override
    public void pause(){
        // TODO Auto-generated method stub
        
    }

    @Override
    public void resume(){
        // TODO Auto-generated method stub
        
    }

    @Override
    public void hide(){
        // TODO Auto-generated method stub
        
    }

    @Override
    public void dispose(){
        // TODO Auto-generated method stub
        
    }

    private void swap() {
        ((Game) Gdx.app.getApplicationListener()).setScreen(new GameView(g,player,set));
    }
}
