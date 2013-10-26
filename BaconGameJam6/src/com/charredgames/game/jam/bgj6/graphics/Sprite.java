package com.charredgames.game.jam.bgj6.graphics;

import com.charredgames.game.jam.bgj6.Main;

public class Sprite {

	public final int size;
	private int x, y;
	private SpriteSheet spriteSheet;
	public int[] pixels;
	
	public static Sprite cloud = new Sprite(64, 0, 1, SpriteSheet.sprites);
	public static Sprite coin = new Sprite(16, 1, 0, SpriteSheet.sprites);
	public static Sprite rainbowSprite = new Sprite(Main.rainbowStrandWidth, 0xFFFF0000);
	
	public static Sprite player_forward = new Sprite(16, 1, 0, SpriteSheet.mobs);
	public static Sprite player_backward = new Sprite(16, 0, 0, SpriteSheet.mobs);
	public static Sprite player_left = new Sprite(16, 2, 0, SpriteSheet.mobs);
	public static Sprite player_right = new Sprite(16, 3, 0, SpriteSheet.mobs);
	
	public static Sprite mob_santa = new Sprite(16, 0, 1, SpriteSheet.mobs);
	public static Sprite mob_rabbit = new Sprite(16, 1, 1, SpriteSheet.mobs);
	public static Sprite mob_cat = new Sprite(16, 0, 2, SpriteSheet.mobs);
	
	public static Sprite powerup_invincible = new Sprite(16, 1, 1, SpriteSheet.sprites);
	public static Sprite powerup_magnet = new Sprite(16, 1, 2, SpriteSheet.sprites);
	public static Sprite powerup_speed = new Sprite(16, 1, 3, SpriteSheet.sprites);

	public Sprite(int size, int x, int y, SpriteSheet spriteSheet){
		this.size = size;
		this.x = x * size;
		this.y = y * size;
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

	public void changeColor(int newColour){
		for(int y = 0; y < size; y ++){
			for (int x = 0; x < size; x++){
				pixels[x + y * size] = newColour;
			}
		}
	}
	
}
