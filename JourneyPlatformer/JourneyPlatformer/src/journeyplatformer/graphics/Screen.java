/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package journeyplatformer.graphics;

import journeyplatformer.level.tile.Tile;

/**
 *
 * @author Raphael
 */
public class Screen {

    public static int width, height;
    public int[] pixels;
    public static int xOffset = 0, yOffset = 0;

    //instantiate Screen object with width and height and pixels[]
    public Screen(int width, int height) {
        Screen.width = width;
        Screen.height = height;
        pixels = new int[Screen.width * Screen.height];
    }

    //set pixels[] to the voidTile
    
    public void clear() {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0xabcdef;
        }
    }
    
    public void clearWithVoidTile() {
        //take the offset, and add/subtract it by mod 16 for the corner pins
        int x0 = xOffset - ((xOffset * 27 / 48) % 16) - 16;
        int x1 = xOffset + width + ((xOffset * 27 / 48) % 16) + 16;
        int y0 = yOffset - ((yOffset * 27 / 48) % 16) - 16;
        int y1 = height +  yOffset + ((yOffset * 27 / 48) % 16) + 16;


        for (int y = y0; y <= y1; y += 16) {
            for (int x = x0; x <= x1; x += 16) {
                renderTile(x, y, new Tile(Sprite.voidSprite));
            }
        }
    }

    //render pixels[]
    public void render(int xOffset, int yOffset) {
        for (int x = 0; x < width; x += 16) {
            int xp = xOffset + x;
            for (int y = 0; y < height; y += 16) {
                int yp = yOffset + y;
                renderTile(xp, yp, new Tile(Sprite.grass));
            }
        }
    }

    //renders a tile at the specified offset
    public void renderTile(int xPos, int yPos, Tile t) {
        xPos -= xOffset;
        yPos -= yOffset;

        for (int y = 0; y < t.sprite.SIZE; y++) {
            int yp = yPos + y; //y pixel
            //if (yp < 0 || yp >= HEIGHT) continue;
            for (int x = 0; x < t.sprite.SIZE; x++) {
                int xp = xPos + x;
                //if (xp < 0 || xp >= WIDTH) continue;
                if (xp < 0 - t.sprite.SIZE || xp >= width || yp < 0 || yp >= height) {
                    break;
                }
                if (xp < 0) {
                    xp = 0;
                }
                int pixel = t.sprite.pixels[x + (y * t.sprite.SIZE)];
                if(pixel != 0xffff00ff) {
                    pixels[xp + (yp * width)] = pixel;
                }
            }
        }
    }
    public void renderSprite(int xPos, int yPos, Sprite s) {
        xPos -= xOffset;
        yPos -= yOffset;
        
        for (int y = 0; y < s.SIZE; y++) {
            
            int yp = yPos + y; 
            
            for (int x = 0; x < s.SIZE; x++) {
                int xp = xPos + x;
                
                if (xp < 0 - s.SIZE || xp >= width || yp < 0 || yp >= height) break;
                if (xp < 0) xp = 0;
                
                int color = s.pixels[x + y * s.SIZE];
                
                if(color != 0xffff00ff) pixels[xp + yp * width] = color;
            }
        }
    }

    //sets the Offsets for the tiles
    public void setOffset(int xOffset, int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public void renderPlayer(int xp, int yp, Sprite sprite) {
        xp -= xOffset;
        yp -= yOffset;
        
        for(int y = 0; y < sprite.SIZE; y++) {
            int ya = y + yp;
            for(int x = 0; x < sprite.SIZE; x++) {
                int xa = x + xp;
                if(xa < -sprite.SIZE || xa >= width || ya < 0 || ya >= height) break;
                if (xa < 0) xa = 0;
                int color = sprite.pixels[x + y * sprite.SIZE];
                if(color != 0xffff00ff) pixels[xa + ya * width] = color;
                
            }
        }
    }

    public void renderMainMenu() {
        
    }
}