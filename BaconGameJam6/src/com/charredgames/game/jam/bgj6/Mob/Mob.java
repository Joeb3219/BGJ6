package com.charredgames.game.jam.bgj6.Mob;

import com.charredgames.game.jam.bgj6.Controller;
import com.charredgames.game.jam.bgj6.Main;
import com.charredgames.game.jam.bgj6.graphics.Screen;
import com.charredgames.game.jam.bgj6.graphics.Sprite;
import com.charredgames.game.jam.bgj6.input.Keyboard;

public class Mob {

	protected int x, y;
	protected Sprite sprite;
	protected Keyboard input;
	protected boolean removed = false;
	protected int direction = 0;
	public int points = 0;
	
	public Mob(int x, int y, int points, Sprite sprite){
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		this.points = points;
		Controller.addMob(this);
	}
	
	public Mob(Keyboard input){
		this.input = input;
	}
	
	public void update(){
		this.y++;
		if(y > Main.getWindowHeight()) removed = true;
	}
	
	public void render(Screen screen){
		screen.renderTile(this.x, this.y, this.sprite);
	}
	
	public boolean removed(){
		return removed;
	}
	
	public void remove(){
		removed = true;
	}
	
}
