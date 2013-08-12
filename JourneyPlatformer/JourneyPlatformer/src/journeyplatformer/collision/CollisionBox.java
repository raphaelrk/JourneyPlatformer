/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package journeyplatformer.collision;

import java.awt.Rectangle;
import journeyplatformer.entity.mob.Mob;
import journeyplatformer.graphics.Sprite;
import journeyplatformer.level.Level;
import journeyplatformer.level.tile.GroundTile;
import journeyplatformer.level.tile.Tile;
import journeyplatformer.level.tile.VoidTile;

/**
 *
 * @author Raphael
 */
public class CollisionBox {
    
    protected int width, height, leftX, rightX, topY, bottomY; // bottomY is the greater y since Y's increase as they go down
    protected Rectangle rect;
    
    public CollisionBox(int left, int right, int top, int bottom) {
        leftX = left;
        rightX = right;
        topY = top;
        bottomY = bottom;
        width = rightX - leftX;
        height = bottomY - topY;
        rect = new Rectangle(leftX, topY, width, height);
    }
    
    public void updateBox(int left, int top) {
        leftX = left;
        rightX = leftX + width;
        topY = top;
        bottomY = top + height;
        rect = new Rectangle(leftX, topY, width, height);
    }
    
    public boolean collidesWith(Mob mob) {
        if (rect.intersects(mob.cb.rect)) {
            return true;
        }
        
        return false;
    }
    
    public boolean collidesWithLevel(Level level) {
        //CollisionBox[] cbArray = level.getCollisionBoxes(leftX, topY, width, height);
        
        int arrayWidth = width / 16;
        int arrayHeight = height / 16;
        
        for(int arrayY = 0; arrayY < arrayHeight; arrayY++) {
            for(int arrayX = 0; arrayX < arrayWidth; arrayX++) {
                int tileX = leftX / 16 + arrayX;
                int tileY = topY / 16 + arrayY;
                
                Tile tile = level.getTile(tileX, tileY);
                
                if(tile.isSolid()) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
}
