package com.charredgames.game.jam.bgj6.Mob;

import com.charredgames.game.jam.bgj6.Controller;
import com.charredgames.game.jam.bgj6.graphics.Screen;
import com.charredgames.game.jam.bgj6.graphics.Sprite;
import com.charredgames.game.jam.bgj6.input.Keyboard;

public class Mob {

	protected int x, y;
	protected Sprite sprite;
	protected Keyboard input;
	
	
	public Mob(int x, int y, Sprite sprite){
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		Controller.addMob(this);
	}
	
	public Mob(Keyboard input){
		this.input = input;
	}
	
	public void update(){
		
	}
	
	public void render(Screen screen){
		screen.renderTile(this.x, this.y, this.sprite);
	}
	
}
