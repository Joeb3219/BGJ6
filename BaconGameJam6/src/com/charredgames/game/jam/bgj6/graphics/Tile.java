package com.charredgames.game.jam.bgj6.graphics;

import com.charredgames.game.jam.bgj6.Controller;

public class Tile {

	//private int x, y;
	private Sprite sprite;
	private boolean solid;
	
	public static Tile cloud = new Tile(0xFF8f00ff, Sprite.cloud, false);
	public static Tile rainbow = new Tile(0xFF000000, Sprite.rainbowSprite, false);
	
	public Tile(int identifier, Sprite sprite, boolean solid){
		this.sprite = sprite;
		this.solid = solid;
		Controller.addTile(identifier, this);
	}
	
	public void render(int x, int y, Screen screen){
		screen.renderTile(x, y, this.sprite);
	}
	
	public boolean isSolid(){
		return solid;
	}
	
	public Sprite getSprite(){
		return sprite;
	}
	
}
