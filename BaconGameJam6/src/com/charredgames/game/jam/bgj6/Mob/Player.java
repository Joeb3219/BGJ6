package com.charredgames.game.jam.bgj6.Mob;

import com.charredgames.game.jam.bgj6.graphics.Screen;
import com.charredgames.game.jam.bgj6.graphics.Sprite;
import com.charredgames.game.jam.bgj6.input.Keyboard;

public class Player extends Mob{

	//protected Sprite sprite = Sprite.player;
	
	public Player(Keyboard input){
		super(input);
	}
	
	public void update(){
		
	}
	
	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
	}
	
}
