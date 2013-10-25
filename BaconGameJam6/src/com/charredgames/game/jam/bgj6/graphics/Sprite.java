package com.charredgames.game.jam.bgj6.graphics;

public class Sprite {

	public final int size;
	private int x, y;
	private SpriteSheet spriteSheet;
	public int[] pixels;
	
	public Sprite(int size, int x, int y, SpriteSheet spriteSheet){
		this.size = size;
		this.x = x;
		this.y = y;
		this.spriteSheet = spriteSheet;
		pixels = new int[size * size];
		load();
	}
	
	public Sprite(int size, int colour){
		this.size = size;
		pixels = new int[size * size];
		for(int y = 0; y < size; y ++){
			for (int x = 0; x < size; x++){
				pixels[x + y * size] = colour;
			}
		}
	}
	
	private void load(){
		for(int y = 0; y < size; y++){
			for(int x = 0; x < size; x++){
				pixels[x + y * size] = spriteSheet.pixels[(x + this.x) + (y + this.y) * spriteSheet.size];
				
			}
		}
	}
	
}
