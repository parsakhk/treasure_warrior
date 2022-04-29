package main;

import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.sql.Time;

public class GamePanel extends JPanel implements Runnable {

    //SCREEN SETTINGS
    final int originalTileSize = 16; // 16 x 16
    public final int tileSize = originalTileSize * 3;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;
    //Set player default pos
    int PlayerX = 100;
    int PlayerY = 100;
    int PlayerSpeed = 4;

    //FPS
    int FPS = 60;

    Thread gameThread;
    KeyHandler keyH = new KeyHandler();
    Player player = new Player(this, keyH);
    TileManager tileManager = new TileManager(this);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    public void StartGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    };
    @Override
    public void run() {
        double drawIntervar = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long Timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawIntervar;
            Timer += (currentTime - lastTime);
            lastTime =currentTime;
            if(delta >= 1) {
                Update();
                repaint();
                delta--;
                drawCount++;
            }
            if(Timer >= 1000000000) {
                drawCount = 0;
                Timer = 0;
            }
        }
        this.requestFocusInWindow();
    }
    public  void Update() {
    player.update();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g; tileManager.Draw(g2);player.draw(g2);
        g2.dispose();
    }
}
