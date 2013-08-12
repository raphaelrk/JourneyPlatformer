package journeyplatformer;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import journeyplatformer.entity.mob.LeftRightMob;
import journeyplatformer.entity.mob.Mob;
import journeyplatformer.entity.mob.Player;
import journeyplatformer.entity.mob.Projectile;

import journeyplatformer.level.Level;

import journeyplatformer.input.Keyboard;
import journeyplatformer.input.Mouse;

import journeyplatformer.graphics.Screen;
import journeyplatformer.graphics.Sprite;
import journeyplatformer.level.RandomLevel;
import journeyplatformer.level.StaticLevel;

/**
 *
 * @author Raphael
 */
public class JourneyPlatformer extends Canvas implements Runnable {

    private static final long serialVersionUID = 1L;
    JFrame frame;
    static final int WIDTH = 300;
    static final int HEIGHT = WIDTH * 9 / 16; 
    public static int scale = 3;
    public boolean running;
    private Thread daThread;
    private Keyboard key;
    private Mouse mouse;
    public static Level level;
    public static Player player;
    private static String title = "JourneyPlatformer";
    private static String version = "Alpha 2";
    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    private static Screen screen;
    private boolean mainMenu = true;
    private boolean gameStarted = false;
    private boolean paused = false;
    private boolean gameWonScreen = false;
 
    public static void main(String[] args) {
        JourneyPlatformer game = new JourneyPlatformer();

        game.frame.setResizable(false);
        game.frame.setVisible(true);
        game.frame.setTitle(title + version);
        game.frame.add(game);
        game.frame.pack();
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.frame.setFocusable(true);
        game.frame.setLocationRelativeTo(null);

        game.start();
    }

    public JourneyPlatformer() {
        Dimension size = new Dimension(WIDTH * scale, HEIGHT * scale);
        setPreferredSize(size);

        screen = new Screen(WIDTH, HEIGHT);
        frame = new JFrame();
        key = new Keyboard();
        mouse = new Mouse();
        
        startGame();
        gameStarted = true;
        
        addKeyListener(key);
        addMouseListener(mouse);
        addMouseMotionListener(mouse);

        System.out.println("Game Object instantiated");
    }
    
    public void startGame() {
        //level = new RandomLevel(16, 16);
        level = new StaticLevel("CavesAndMountains.png");
        player = new Player(160, 160, key, mouse, level);
        
        Sprite[] mobLeft = {Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2};
        Sprite[] mobRight = {Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2};
        
        Random random = new Random();
        for(int i = 0; i < 1000; i++) {
            int mobx = random.nextInt(level.levelwidth * 16);
            int moby = random.nextInt(level.levelheight * 16);
            LeftRightMob mobToAdd = new LeftRightMob(mobx, moby, random.nextDouble() * 4 - 2, level, 9, 9, 14, 16);
            while(mobToAdd.collision(0, 0) || mobToAdd.collision(0, 1)) {
                mobToAdd.x = random.nextInt(level.levelwidth * 16);
                mobToAdd.y = random.nextInt(level.levelheight * 16);
            }
//            mobs.add(mobToAdd);
        }
    }
    
    private void levelTwoStart() {
        level = new StaticLevel("Factory.png");
        player = new Player(16, 160, key, mouse, level);
        
        Sprite[] mobLeft = {Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2};
        Sprite[] mobRight = {Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2};
        
        Random random = new Random();
        for(int i = 0; i < 1000; i++) {
            int mobx = random.nextInt(level.levelwidth * 16);
            int moby = random.nextInt(level.levelheight * 16);
            LeftRightMob mobToAdd = new LeftRightMob(mobx, moby, random.nextDouble() * 4 - 2, level, 9, 9, 14, 16 );
            while(mobToAdd.collision(0, 0) || mobToAdd.collision(0, 1)) {
                mobToAdd.x = random.nextInt(level.levelwidth * 16);
                mobToAdd.y = random.nextInt(level.levelheight * 16);
            }
//            mobs.add(mobToAdd);
        }
    }
    
    public void start() {
        running = true;
        daThread = new Thread(this, title + "" + version);
        daThread.start();
    }

    public void stop() {
        running = false;
        try {
            daThread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        long time = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60.0;
        double delta = 0;
        int frames = 0;
        int updates = 0;
        requestFocus();

        while (running) {
            long now = System.nanoTime();
            delta += (now - time) / ns;
            time = now;
            while (delta >= 1) {
                update();
                updates++;
                delta--;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;

                frame.setTitle(title + " " +  version + " | FPS: " + frames + " UPS: " + updates);
                updates = frames = 0;
            }
        }
        stop();
    }

    public void update() {
        if (mainMenu) {
            if(mouse.mouseClicked) {
                mainMenu = false;
            }
        } else if (paused) {
            
        } else if (gameWonScreen) {
            
        } else {
            key.update();
            player.update();
            Projectile.updateProjectiles();
            Mob.updateMobs();
            System.out.println(LeftRightMob.leftRightMobAmount);
            if (LeftRightMob.leftRightMobAmount == 0) {
                gameWonScreen = true;
            }
        }
        
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3); // screen, to be displayed, to be displayed after to be displayed
            return;
        }
        
        if (mainMenu) {
            screen.clear();
            screen.renderMainMenu();
            System.arraycopy(screen.pixels, 0, pixels, 0, pixels.length);

            Graphics g = bs.getDrawGraphics();
            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
            
            g.setColor(Color.WHITE);
            g.setFont(new Font("Verdana", 0, 100));
            g.drawString("Click to begin", 0, 100);
            
            g.dispose();
            bs.show();
        } else if (paused) {
            System.arraycopy(screen.pixels, 0, pixels, 0, pixels.length);

            Graphics g = bs.getDrawGraphics();
            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
            
            g.setColor(Color.WHITE);
            g.setFont(new Font("Verdana", 0, 100));
            g.drawString("Paused", 450, 500);
            
            g.dispose();
            bs.show();
        } else if (gameWonScreen) {
            System.arraycopy(screen.pixels, 0, pixels, 0, pixels.length);

            Graphics g = bs.getDrawGraphics();
            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
            
            g.setColor(Color.WHITE);
            g.setFont(new Font("Verdana", 0, 100));
            g.drawString("You won!", 150, 150);
            
            g.dispose();
            bs.show();  
        } else {
            screen.clearWithVoidTile();
            int xScroll = (int) (player.x - screen.width / 2);
            int yScroll = (int) (player.y - screen.height / 2);
            level.render(xScroll, yScroll, screen);
            Mob.renderMobs(screen);
            player.render(screen);
            Projectile.renderProjectiles(screen);
            
            System.arraycopy(screen.pixels, 0, pixels, 0, pixels.length);

            Graphics g = bs.getDrawGraphics();
            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
            
            g.setColor(Color.BLACK);
            g.setFont(new Font("Verdana", 0, 50));
            g.drawString("Enemies left:" + LeftRightMob.leftRightMobAmount, 450, 500);
            
            g.dispose();
            bs.show();
        }
        
    }
}
