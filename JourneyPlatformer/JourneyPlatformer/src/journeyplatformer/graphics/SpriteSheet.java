package journeyplatformer.graphics;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import journeyplatformer.graphics.SpriteSheet;

public class SpriteSheet {
	
	private String path;
	public final int SIZE;
	public int[] pixels;
	
	public static SpriteSheet tiles = new SpriteSheet("resources/SpriteSheet.png", 256);
	public static SpriteSheet mainMenu = new SpriteSheet("resources/SpriteSheet.png", 256);
	
	public SpriteSheet(String path, int size) {
		this.path = path;
		SIZE = size;
		pixels = new int[SIZE * SIZE];
		load();
	}
        
	
	private void load() {
		try {
			//BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			BufferedImage image = ImageIO.read(new FileInputStream("resources/SpriteSheet.png"));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
