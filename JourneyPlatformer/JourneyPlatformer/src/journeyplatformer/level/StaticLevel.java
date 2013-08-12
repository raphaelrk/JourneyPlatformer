/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package journeyplatformer.level;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 *
 * @author RAPHAEL
 */
public class StaticLevel extends Level {
    
    String path;
    
    public StaticLevel(String path) {
        this.path = path;
        loadLevel(path);
        importLevel();
    }
    
    protected void importLevel() {
        for(int i = 0; i < tiles.length; i++) {
            if      (tiles[i] == 0xff00ff00) tiles[i] = 0; // 0xff0000 is grass
            else if (tiles[i] == 0xffc8c800) tiles[i] = 1; // 0x00ff00 is rock
            else if (tiles[i] == 0xffc8ff00) tiles[i] = 2; // 0x0000ff is flower
            else if (tiles[i] == 0xffc86400) tiles[i] = 3; // 0xffff00 is brick
            else if (tiles[i] == 0xff00ffff) tiles[i] = 4; // 0xffd700 is voidSprite
            else if (tiles[i] == 0xffff00ff) tiles[i] = 5; // 0xffd700 is emptySprite
            else if (tiles[i] == 0xff966400) tiles[i] = 6; // 0x966400 is brickbg
            else if (tiles[i] == 0xff808080) tiles[i] = 7; // 0x808080 is cement
            else if (tiles[i] == 0xff0080ff) tiles[i] = 8; // 0x0080ff is water
            else if (tiles[i] == 0xff800000) tiles[i] = 9; // 0x800000 is bridgeMiddle
            else if (tiles[i] == 0xff808000) tiles[i] = 10; // 0x808000 is bridgeLeftEnd
            else if (tiles[i] == 0xff080888) tiles[i] = 11; // 0x080888 is bridgeRightEnd
            else if (tiles[i] == 0xff804000) tiles[i] = 12; // 0x804000 is dirt
            else if (tiles[i] == 0xff408000) tiles[i] = 13; // 0x408000 is sideviewGrass
            else if (tiles[i] == 0xffC105ED) tiles[i] = 14; // 0xC105ED is a solid emptySprite
            else if (tiles[i] == 0xff654321) tiles[i] = 15; // 0x654321 is bgDirt
        }
    }
    
    protected void loadLevel(String path) {
        try {
            BufferedImage image = ImageIO.read(this.getClass().getClassLoader().getResource(path));
            levelwidth = image.getWidth();
            levelheight = image.getHeight();
            tiles = new int[levelwidth * levelheight];
            image.getRGB(0, 0, levelwidth, levelheight, tiles, 0, levelwidth);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Could not load level");
        }
    }
    
}
