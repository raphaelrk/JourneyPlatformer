package journeyplatformer.graphics;

import journeyplatformer.graphics.SpriteSheet;

/**
 * @author Raphael
 */
public class Sprite {

    public final int SIZE;
    public int[] pixels;
    public int xInSheet, yInSheet;
    public SpriteSheet sheet;
    public static Sprite grass = new Sprite(16, 0, 0, SpriteSheet.tiles);
    public static Sprite flower = new Sprite(16, 1, 0, SpriteSheet.tiles);
    public static Sprite rock = new Sprite(16, 2, 0, SpriteSheet.tiles);
    public static Sprite dirt = new Sprite(16, 3, 0, SpriteSheet.tiles);
    public static Sprite bgDirt = new Sprite(16, 5, 0, SpriteSheet.tiles);
    public static Sprite sideviewGrass = new Sprite(16, 4, 0, SpriteSheet.tiles);
    public static Sprite brick = new Sprite(16, 0, 1, SpriteSheet.tiles);
    public static Sprite brickbg = new Sprite(16, 1, 1, SpriteSheet.tiles);
    public static Sprite water_1 = new Sprite(16, 0, 5, SpriteSheet.tiles);
    public static Sprite water_2 = new Sprite(16, 1, 5, SpriteSheet.tiles);
    public static Sprite cement = new Sprite(16, 2, 1, SpriteSheet.tiles);
    public static Sprite snowball = new Sprite(16, 0, 3, SpriteSheet.tiles);
    public static Sprite bridgeLeftEnd = new Sprite(16, 1, 6, SpriteSheet.tiles);
    public static Sprite bridgeRightEnd = new Sprite(bridgeLeftEnd);
    public static Sprite bridgeMiddle = new Sprite(16, 0, 6, SpriteSheet.tiles);
    public static Sprite emptySprite = new Sprite(16, 0, 4, SpriteSheet.tiles);
    public static Sprite voidSprite = new Sprite(16, 0, 2, SpriteSheet.tiles);
    
    public static Sprite player_forward = new Sprite(32, 0, 5, SpriteSheet.tiles);
    public static Sprite player_back = new Sprite(32, 2, 5, SpriteSheet.tiles);
    public static Sprite player_left = new Sprite(32, 1, 5, SpriteSheet.tiles);
    public static Sprite player_right = new Sprite(player_left);
    
    public static Sprite player_forward_1 = new Sprite(32, 0, 6, SpriteSheet.tiles);
    public static Sprite player_forward_2 = new Sprite(32, 0, 7, SpriteSheet.tiles);
    
    public static Sprite player_back_1 = new Sprite(32, 2, 6, SpriteSheet.tiles);
    public static Sprite player_back_2 = new Sprite(32, 2, 7, SpriteSheet.tiles);
    
    public static Sprite player_left_1 = new Sprite(32, 1, 6, SpriteSheet.tiles);
    public static Sprite player_left_2 = new Sprite(32, 1, 7, SpriteSheet.tiles);
    
    public static Sprite player_right_1 = new Sprite(player_left_1);
    public static Sprite player_right_2 = new Sprite(player_left_2);
    
    public Sprite(int size, int x, int y, SpriteSheet sheet) {
        SIZE = size;
        xInSheet = x * size;
        yInSheet = y * size;
        this.sheet = sheet;
        pixels = new int[size * size];
        load();
    }

    public Sprite(int size, int color) {
        SIZE = size;
        pixels = new int[SIZE * SIZE];
        setColor(color);
    }
    
    /**
     * Loads the mirror of a sprite
     * @param sprite the sprite to mirror
     */
    public Sprite(Sprite sprite) {
        SIZE = sprite.SIZE;
        xInSheet = sprite.xInSheet;
        yInSheet = sprite.yInSheet;
        this.sheet = sprite.sheet;
        pixels = new int[SIZE * SIZE];
        loadReverseSprite();
    }
    
    private void loadReverseSprite() {
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                pixels[(SIZE - 1 - x) + y * SIZE] = sheet.pixels[(x + xInSheet) + (y + yInSheet) * sheet.SIZE];
            }
        }
    }

    private void setColor(int color) {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = color;
        }
    }

    private void load() {
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                pixels[x + y * SIZE] = sheet.pixels[(x + xInSheet) + (y + yInSheet) * sheet.SIZE];
            }
        }
    }
    
    @Override
    public String toString() {
        return "Sprite x:" + this.xInSheet + "y: " + this.yInSheet;
    }
}
