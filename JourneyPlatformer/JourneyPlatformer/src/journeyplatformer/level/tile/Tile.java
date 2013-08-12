package journeyplatformer.level.tile;

import journeyplatformer.graphics.Screen;
import journeyplatformer.graphics.Sprite;

public class Tile {

    public Sprite sprite;
    public static Tile GrassTile = new Tile(Sprite.grass);
    public static Tile BrickTile = new Tile(Sprite.brick);
    public static Tile VoidTile = new Tile(Sprite.voidSprite);

    public Tile(Sprite sprite) {
        this.sprite = sprite;
    }

    public void render(int x, int y, Screen screen) {
        screen.renderTile(x << 4, y << 4, this);
    }
    
    //true if you can't move through it, false if you can
    public boolean isSolid() {
        return false;
    }
    
    public boolean isVoidTile() {
        return false;
    }
}
