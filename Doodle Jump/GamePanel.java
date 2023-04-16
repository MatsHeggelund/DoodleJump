import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Arrays;
import javax.swing.JPanel;
import java.lang.Math;

public class GamePanel extends JPanel implements Runnable {
    final int screenWidth = 600;
    final int screenHeight = 1200;
    final int FPS = 60;

    Thread gameThread;
    KeyHandler keyH = new KeyHandler();
    Player player = new Player(this, this.screenWidth/2 - 40, this.screenHeight/2 - 40);
    Platform[] platforms = {};

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        initialize();
    }

    public Platform[] AppendPlatforms(Platform[] array, Platform element){
        array = Arrays.copyOf(array, array.length + 1);
        array[array.length-1] = element;
        return array;
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        //Define time variables
        double drawInterval = 10e8/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >= 1){
                //Update function
                update();
                //Draw function
                repaint();
                delta--;
            }
        }
    }

    public void initialize(){
        int NumberOfPlatforms = 14;
        for(int i = 0; i < NumberOfPlatforms; i++){
            int Min = 0;
            int Max = this.screenWidth - 100;
            int Randint = Min + (int)(Math.random() * ((Max - Min) + 1));
            platforms = AppendPlatforms(platforms, new Platform(this, Randint, this.screenHeight/NumberOfPlatforms * i + (int)(Math.random() * 40)-20));
        }
    }

    //update
    public void update(){
        player.update();

        for(int i = 0; i < platforms.length; i++){
            platforms[i].update();
        }
    }

    //draw
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        for(int i = 0; i < platforms.length; i++){
            platforms[i].draw(g2);
        }

        player.draw(g2);

        g2.dispose();
    }
}
