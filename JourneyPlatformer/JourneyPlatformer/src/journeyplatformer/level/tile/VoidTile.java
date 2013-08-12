package journeyplatformer.level.tile;

import journeyplatformer.graphics.Screen;
import journeyplatformer.graphics.Sprite;

/**
 *
 * @author RAPHAEL
 */
public class VoidTile extends Tile {
    
    public VoidTile(Sprite s) {
        super(s);
    }
    
    public void render(int x, int y, Screen screen) {
        //screen.renderTile(x << 4, y << 4, this);
    }
    
    public String toString() {
        return "VoidTile";
    }
    
    public boolean isVoidTile() {
        return true;
    }
}


