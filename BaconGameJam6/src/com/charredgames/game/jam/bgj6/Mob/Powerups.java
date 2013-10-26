package com.charredgames.game.jam.bgj6.Mob;

import com.charredgames.game.jam.bgj6.graphics.Sprite;

public enum Powerups {
	
	SPEED(Sprite.powerup_speed), INVINCIBLE(Sprite.powerup_invincible), MAGNET(Sprite.powerup_magnet);
	
	private Sprite sprite;
	
	private Powerups(Sprite sprite){
		this.sprite = sprite;
	}
	
	public Sprite getSprite(){
		return sprite;
	}
	
}
