/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package journeyplatformer.level;

/**
 *
 * @author RAPHAEL
 */
public class RandomLevel extends Level {

    public RandomLevel(int width, int height) {
        levelwidth = width;
        levelheight = height;
        tiles = new int[width * height];
        generateLevel();
    }
    
    //creates a new level of random tiles
    private void generateLevel() {
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = rand.nextInt(4);
            System.out.println(tiles[i]);
        }
    }
}
