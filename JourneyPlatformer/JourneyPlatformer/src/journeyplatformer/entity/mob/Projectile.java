package journeyplatformer.entity.mob;

import java.util.ArrayList;
import journeyplatformer.JourneyPlatformer;
import journeyplatformer.graphics.Screen;
import journeyplatformer.graphics.Sprite;
import journeyplatformer.level.tile.Tile;

/**
 *
 * @author Raphael
 */
public class Projectile extends Mob {
    
    private double x, y;
    private double dx, dy;
    protected boolean remove = false;
    public static ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
    
    public Projectile(Sprite sprite, double dx, double dy, int x, int y) {
        this.sprite = sprite;
        
        // this part is to make sure your projectile moves fast enough
        // if it's slow, makes it faster. If it doesn't move, remove it
        if (dx < 1 && dx > .2)  dx = 1;
        if (dx > -1 && dx < -.2) dx = -1;
        if (dy < 1 && dy > .2)  dy = 1;
        if (dy > -1 && dy < -.2) dy = -1;
        if (Math.abs(dx) <= .2 && Math.abs(dy) <= .2) remove = true;
        
        this.dx = dx;
        this.dy = dy;
        this.x = x;
        this.y = y;
        //System.out.println("New projectile " + "(" + x + ", " + y + ")!");
    }
    
    @Override
    public void render(Screen screen) {
        screen.renderSprite((int) x, (int) y, sprite);
    }
    
    /**
     * Renders all of the projectiles
     * Don't worry about offscreen projectiles, they get removed in updateProjectiles()
     * @param screen the screen to be rendered on
     */
    public static void renderProjectiles(Screen screen) {
        for(Projectile proj : projectiles) {
            proj.render(screen);
        }
    }
    
    /**
     * updates (moves and checks for collisions of) projectiles
     * removes them if they collided with something
     */
    public static void updateProjectiles() {
        for(int i = 0; i < projectiles.size(); i++) {
            projectiles.get(i).update();
            if (projectiles.get(i).remove) {
                projectiles.remove(i);
                System.out.println("Projectile removed");
            }
        }
    }
    
    protected void move() {
        x += dx;
        y += dy;
    }
    
    @Override 
    public void update() {
        if (collision() || distanceFrom(JourneyPlatformer.player) > 2000) {
            double d = distanceFrom(JourneyPlatformer.player);
            if(d > 2000) System.out.println("x: " + x + "y: " + y + "player x: " + JourneyPlatformer.player.x + "player y: " + JourneyPlatformer.player.y + d);
            
            remove = true;
        } else {
            move();
        }
    }
    
    private boolean collision() {
        if (x < Screen.xOffset - Screen.width - sprite.SIZE) return true;
        if (y < Screen.yOffset - Screen.height - sprite.SIZE) return true;
        if (x > Screen.xOffset + Screen.width + sprite.SIZE) return true;
        if (y > Screen.yOffset + Screen.height + sprite.SIZE) return true;
        return false;
    }
    
}
