/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package journeyplatformer.entity.mob;

import java.util.ArrayList;
import journeyplatformer.collision.CollisionBox;
import journeyplatformer.entity.Entity;
import journeyplatformer.graphics.Screen;
import journeyplatformer.graphics.Sprite;

/**
 *
 * @author RAPHAEL
 */
public abstract class Mob extends Entity{
    
    protected Sprite sprite;
    public CollisionBox cb;
    protected int dir = -1;
    protected boolean moving = false;
    protected double dy = 0, dx = 0;
    protected static ArrayList<Mob> mobs = new ArrayList<Mob>();
    public boolean remove = false;
    
    public static void updateMobs() {
        for(int i = 0; i < mobs.size(); i++) {
            mobs.get(i).update();
            if (mobs.get(i).remove) {
                mobs.remove(i);
            }
        }
    }
    
    public static void renderMobs(Screen screen) {
        for(Mob mob : mobs) mob.render(screen);
    }
    
    /**
     * moves entity's x and y values
     * @param dx is the direction to move in x
     * @param dy is the direction to move in y
     */
    public void move(double dx, double dy) {
        if (dy > 0) dir = 2;
        if (dy < 0) dir = 0;
        if (dx > 0) dir = 1;
        if (dx < 0) dir = 3;
        
        if(dx > 0 && !collision(dx, 1))      x += dx; // right
        else if(dx < 0 && !collision(dx, 3)) x += dx; // left
        
        if(dy > 0 && !collision(dy, 2))      y += dy; // down
        else if(dy < 0 && !collision(dy, 0)) y += dy; // up
        
    }
    
    public void update() {
    }
    
    protected boolean collision(double change, int dir) {
        
        return false;
    }
    
    public double distanceFrom(Mob mob) {
        double distance = Math.sqrt((this.x - mob.x)*(this.x - mob.x) + (this.y - mob.y)*(this.y - mob.y));
        return distance;
    }
}
