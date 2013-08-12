package journeyplatformer.entity.mob;

import java.awt.Rectangle;
import java.util.ArrayList;
import journeyplatformer.JourneyPlatformer;
import journeyplatformer.collision.CollisionBox;
import journeyplatformer.graphics.Screen;
import journeyplatformer.graphics.Sprite;
import journeyplatformer.input.Keyboard;
import journeyplatformer.input.Mouse;
import journeyplatformer.level.Level;

/**
 *
 * @author RAPHAEL
 */
public class Player extends Mob {

    private Keyboard input;
    private Mouse mouse;
    private int anim = 0;
    private double accel = .2, maxspeed = 4;
    
    /**
     * 
     * @param input the keyboard to take input from
     * @param mouse the mouse to take input from
     * @param level the level the player is on, for collision
     */
    public Player(Keyboard input, Mouse mouse, Level level) {
        this.input = input;
        this.mouse = mouse;
        this.level = level;
        sprite = Sprite.player_forward;
        x = 40;
        y = 40;
        cb = new CollisionBox((int) x - 9, (int) y - 14, (int) x + 9, (int) y + 30);
    }
    
    /**
     * 
     * @param x the x position for the center of the player to spawn in
     * @param y the y position for the center of the player to spawn in
     * @param input the keyboard to take input from
     * @param mouse the mouse to take input from
     * @param level the level the player is on, for collision
     */
    public Player(int x, int y, Keyboard input, Mouse mouse, Level level) {
        this.input = input;
        this.mouse = mouse;
        this.level = level;
        sprite = Sprite.player_forward;
        this.x = x;
        this.y = y;
        cb = new CollisionBox((int) x - 9, (int) y - 14, (int) x + 9, (int) y + 30);
    }
    
    /**
     * update player movement and projectiles
     */
    public void update() {
        updateMovement();
        cb.updateBox((int) x - 9, (int) y - 14);
        
        if (mouse.mouseHeld && anim % 10 == 0) {
            Sprite projsprite = Sprite.snowball;
            
            double projdx = mouse.xPos + dx - Screen.width * JourneyPlatformer.scale / 2;
            double projdy = mouse.yPos + dy - Screen.height * JourneyPlatformer.scale  / 2;
            projdx /= 100;
            projdy /= 100;
            
            System.out.println("mouseX: " + mouse.xPos + " mouseY: " + mouse.yPos);
            System.out.println("dx: " + projdx + " dy: " + projdy);
            
            int projX = (int) (x - sprite.SIZE / 4);
            int projY = (int) (y - sprite.SIZE / 4);
            Projectile.projectiles.add(new Projectile(projsprite, projdx, projdy, projX, projY));
            //mouse.mouseClicked = false;
        }
    }
    
    /**
     * moves player if there is no collision
     */
    private void updateMovement() {

        //animation value
        if (anim < 7500) anim++;
        else anim = 0;
        
        // dy setting
        if (input.up && dy == 0) dy -= 4;
        if (input.down && dy != 0) dy += .2;
        dy += .1;
        if(dy > 8) dy = 8;
        if (collision(dy, 2)) dy = 0;
        
        // dx setting
        if (input.left) dx -= accel;
        else if (input.right) dx += accel;
        else dx = 0;
        if (input.left && input.right) dx = 0;
        
        if (dx > maxspeed)  dx = maxspeed;
        if (dx < -maxspeed) dx = -maxspeed;
        
        // final movement
        if (dx != 0 || dy != 0) {
            move(dx, dy);
            moving = true;
        } else {
            moving = false;
        }
    }
    
    /**
     * Renders the player to the screen after deciding the correct sprite to use
     * @param screen the screen to render the player on
     */
    @Override
    public void render(Screen screen) {
        if (dir == 0) {
            sprite = Sprite.player_forward;
            if (moving) {
                if (anim % 30 > 20) {
                    sprite = Sprite.player_forward_1;
                } else if (anim % 30 > 10) {
                    sprite = Sprite.player_forward_2;
                }
            }
        }
        if (dir == 1) {
            sprite = Sprite.player_left;
            if (moving) {
                if (anim % 30 > 20) {
                    sprite = Sprite.player_left_1;
                } else if (anim % 30 > 10) {
                    sprite = Sprite.player_left_2;
                }
            }
        }
        if (dir == 2) {
            sprite = Sprite.player_back;
            if (moving) {
                if (anim % 30 > 20) {
                    sprite = Sprite.player_back_1;
                } else if (anim % 30 > 10) {
                    sprite = Sprite.player_back_2;
                }
            }
        }
        if (dir == 3) {
            sprite = Sprite.player_right;
            if (moving) {
                if (anim % 30 > 20) {
                    sprite = Sprite.player_right_1;
                } else if (anim % 30 > 10) {
                    sprite = Sprite.player_right_2;
                }
            }
        }
        int halfSize = 16;
        screen.renderPlayer((int) (x - halfSize), (int) (y - halfSize), sprite);
        //screen.renderSprite((int) (x - halfSize), (int) (y - halfSize), sprite); works too
    }
    
    /**
     * Checks for collision at the specified change in the specified direction
     * Also "collides" if you're going to go out of the map
     * @param change how much the player should move
     * @param dir the direction the player should move
     * @return 
     */
    @Override
    protected boolean collision(double change, int dir) {
        
        // side to side
        if (dir == 1 || dir == 3) {
            // check outer boundaries
            int leftx = (int) (x + change - sprite.SIZE / 2 + 9);
            int rightx = (int) (x + change + sprite.SIZE / 2 - 9);
            if (leftx < 0 || rightx > level.levelwidth << 4) {
                return true;
            }
            
            //check level
            int levelx;
            if (dir == 1) {levelx = rightx / 16;}
            else          {levelx = leftx / 16;}
            
            int topy = (int) (y - sprite.SIZE / 2 + 2);
            int bottomy = (int) (y + sprite.SIZE / 2) + 16;
            for(int levely = (topy + 1) / 16; levely < (bottomy - 1)/ 16; levely++) {
                //if(levelx >= 0 && levely >= 0 && levelx < level.levelwidth && levely < level.levelheight) level.tiles[levelx + levely * level.levelwidth] = 5;
                if(level.getTile(levelx, levely).isSolid()) {
                    return true;
                }
            }
        }
        // vertical
        if (dir == 0 || dir == 2) {
            // check outer boundaries
            int topy = (int) (y + change - sprite.SIZE / 2 + 2);
            int bottomy = (int) (y + change + sprite.SIZE / 2);
            if (bottomy > level.levelheight << 4) {
                //dy = 0;
                return true;
            }
            
            // check level
            int levely;
            if (dir == 0) {levely = (topy + 1) / 16;}
            else          {levely = (bottomy - 1) / 16;}
            
            int leftx = (int) (x - sprite.SIZE / 2 + 9);
            int rightx = (int) (x + sprite.SIZE / 2 + 7);
            
            for(int levelx = leftx / 16; levelx < rightx / 16; levelx++) {
                //if(levelx >= 0 && levely >= 0 && levelx < level.levelwidth && levely < level.levelheight) level.tiles[levelx + levely * level.levelwidth] = 5;
                if(level.getTile(levelx, levely).isSolid()) {
                    if(dir == 0) dy = -.09 ;
                    return true;
                }
            }
        }
        return false;
    }
    
}
