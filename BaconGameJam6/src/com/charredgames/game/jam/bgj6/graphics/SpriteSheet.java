package com.charredgames.game.jam.bgj6.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	private String sheetPath;
	public final int size;
	public int[] pixels;
	
	public static SpriteSheet sprites = new SpriteSheet("/textures/sprites.png", 256);
	public static SpriteSheet mobs = new SpriteSheet("/textures/mobs.png", 256);
	
	public SpriteSheet(String path, int size){
		this.sheetPath = path;
		this.size = size;
		pixels = new int[size * size];
		load();
	}
	
	private void load(){
		try{
			BufferedImage img = ImageIO.read(SpriteSheet.class.getResource(sheetPath));
			int width = img.getWidth();
			int height = img.getHeight();
			img.getRGB(0, 0, width, height, pixels, 0, width);
		}catch(IOException e){ e.printStackTrace(); }
	}
	
}
