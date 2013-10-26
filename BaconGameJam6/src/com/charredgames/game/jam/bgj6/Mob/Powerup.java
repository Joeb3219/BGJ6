package com.charredgames.game.jam.bgj6.Mob;

import com.charredgames.game.jam.bgj6.graphics.Sprite;

public class Powerup extends Mob{
	
	public Powerups type;
	
	public Powerup(int x, int y, int points, Powerups type) {
		super(x, y, points, type.getSprite());
		this.type = type;
	}
	
	public Powerups getType(){
		return type;
	}

}
