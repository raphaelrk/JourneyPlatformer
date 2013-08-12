package journeyplatformer.level;

import java.util.Random;
import journeyplatformer.collision.CollisionBox;

import journeyplatformer.graphics.Screen;
import journeyplatformer.graphics.Sprite;
import journeyplatformer.level.tile.BackgroundTile;
import journeyplatformer.level.tile.GroundTile;
import journeyplatformer.level.tile.Tile;
import journeyplatformer.level.tile.VoidTile;
import journeyplatformer.level.tile.WallTile;

/**
 * 
 * @author RAPHAEL
 */
public abstract class Level {

    public int levelwidth, levelheight;
    public static int[] tiles;
    Random rand = new Random();
    
    
    //renders the tiles from the top-left tile to bottom-right of the screen
    public void render(int xScroll, int yScroll, Screen screen) {
        screen.setOffset(xScroll, yScroll);
        
        //corner pins
        int x0 = xScroll >> 4;
        int x1 = (xScroll + screen.width) >> 4;
        int y0 =  yScroll >> 4;
        int y1 = (yScroll + screen.height) >> 4;

        //System.out.println("xScroll: " + xScroll + "  yScroll: " + yScroll + " x0: " + x0 + "  x1: " + x1 + " y0: " + y0 + " y1: " + y1);

        //from the top-left to bottom-right tiles, get and render the tile
        for (int y = y0; y <= y1; y++) {
            for (int x = x0; x <= x1; x++) {
                getTile(x, y).render(x, y, screen);
            }
        }
    }
    
    //returns tile at position (x, y) in tiles[]
    //returns VoidTile if out of bounds
    public Tile getTile(int x, int y) {
        if (x < 0 || y < 0 || x >= levelwidth || y >= levelheight) {
            return new VoidTile(Sprite.voidSprite);
        }
        int tile = tiles[x + y * levelwidth];
        
        if (tile == 0) return new GroundTile(Sprite.grass);
        if (tile == 1) return new GroundTile(Sprite.rock);
        if (tile == 2) return new GroundTile(Sprite.flower);
        if (tile == 3) return new WallTile(Sprite.brick);
        if (tile == 4) return new VoidTile(Sprite.voidSprite);
        if (tile == 5) return new VoidTile(Sprite.emptySprite);
        if (tile == 6) return new BackgroundTile(Sprite.brickbg);
        if (tile == 7) return new GroundTile(Sprite.cement);
        if (tile == 8) {
            if(System.currentTimeMillis() % 1000 < 500) return new BackgroundTile(Sprite.water_1);
            return new BackgroundTile(Sprite.water_2);
        }
        if (tile == 9) return new GroundTile(Sprite.bridgeMiddle);
        if (tile == 10) return new BackgroundTile(Sprite.bridgeLeftEnd);
        if (tile == 11) return new BackgroundTile(Sprite.bridgeRightEnd);
        if (tile == 12) return new GroundTile(Sprite.dirt);
        if (tile == 13) return new GroundTile(Sprite.sideviewGrass);
        if (tile == 14) return new WallTile(Sprite.emptySprite);
        if (tile == 15) return new BackgroundTile(Sprite.bgDirt);
        
        return new VoidTile(Sprite.voidSprite);
    }
    
    public Tile[] getTiles(int x, int y, int width, int height) {
        int arrayWidth = x * (width + 16) / 16;
        int arrayHeight = y * (height + 16) / 16;
        
        Tile[] tileArray = new Tile[arrayWidth * arrayHeight];
        
        for(int arrayY = 0; arrayY < arrayHeight; arrayY++) {
            for(int arrayX = 0; arrayX < arrayWidth; arrayX++) {
                Tile tileToAdd = getTile(x >> 4 + arrayX, y >> 4 + arrayY);
                tileArray[x + y * arrayWidth] = tileToAdd;
            }
        }
        
        return tileArray;
    }
    
    public CollisionBox[] getCollisionBoxes(int x, int y, int width, int height) {
        
        int arrayWidth = (width + 16) / 16;
        int arrayHeight = (height + 16) / 16;
        
        CollisionBox[] cbArray = new CollisionBox[arrayWidth * arrayHeight];
        
        for(int i = 0; i < cbArray.length; i++) {
            cbArray[i] = new CollisionBox(0, 0, 0, 0);
        }
        
        for(int arrayY = 0; arrayY < arrayHeight; arrayY++) {
            for(int arrayX = 0; arrayX < arrayWidth; arrayX++) {
                
                Tile tileToAdd = getTile(x >> 4 + arrayX, y >> 4 + arrayY);
                System.out.println("Tile: " + tileToAdd);
                if(tileToAdd.isSolid()) {
                    CollisionBox cbToAdd = new CollisionBox(arrayX * 16 + x, arrayY * 16 + y, 16, 16);
                    cbArray[arrayX + arrayY * arrayWidth] = cbToAdd;
                }
                else {
                    cbArray[arrayX + arrayY * arrayWidth] = null;
                }
            }
        }
        
        return cbArray;
    }
}
