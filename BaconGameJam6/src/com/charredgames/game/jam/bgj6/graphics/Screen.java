package com.charredgames.game.jam.bgj6.graphics;

public class Screen {

	private static int width, height;
	public int[] pixels;
	private final int tileSize = 16;
	public int[] tiles = new int[tileSize * tileSize];
	
	public Screen(int width, int height){
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}
	
	public void clear(){
		for(int i = 0; i < pixels.length; i++){
			pixels[i] = 0xFF0973B5;
		}
	}
	
	public void renderTile(int xPos, int yPos, Sprite sprite){
		for(int y = 0; y < sprite.size; y++){
			int yAbsolute = y + yPos;
			for(int x = 0; x < sprite.size; x++){
				int xAbsolute = x + xPos;
				if(xAbsolute < -sprite.size) xAbsolute = -1;
				if(xAbsolute >= width || yAbsolute < 0 || yAbsolute >= height) break;
				if(xAbsolute < 0) xAbsolute = 0;
				int grabColour = sprite.pixels[x + y * sprite.size];
				if(grabColour != 0xFFFF5978) pixels[xAbsolute + yAbsolute * width] = grabColour;
			}
		}
	}
	
}
