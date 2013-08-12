package journeyplatformer.entity;

import java.util.Random;
import journeyplatformer.graphics.Screen;
import journeyplatformer.level.Level;

/**
 *
 * @author RAPHAEL
 */
public abstract class Entity {
    public double x, y;
    private boolean removed = false;
    protected Level level;
    protected final Random random = new Random();
    
    public void update() {
    }
    
    public void render(Screen screen) {
    }
    
    public void remove() {
        //remove from level
        removed = true;
    }
    
    public boolean isRemoved() {
        return removed;
    }
}
