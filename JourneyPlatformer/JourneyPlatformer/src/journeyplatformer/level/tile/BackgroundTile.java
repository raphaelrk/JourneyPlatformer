/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package journeyplatformer.level.tile;

import journeyplatformer.graphics.Screen;
import journeyplatformer.graphics.Sprite;

/**
 *
 * @author RAPHAEL
 */
public class BackgroundTile extends Tile {
    public BackgroundTile(Sprite sprite) {
        super(sprite);
    }

    @Override
    public void render(int x, int y, Screen screen) {
        screen.renderTile(x << 4, y << 4, this);
    }

    @Override
    public String toString() {
        return "BackGroundTile";
    }
}
