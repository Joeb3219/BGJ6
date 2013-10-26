package com.charredgames.game.jam.bgj6.Mob;

import java.util.Map.Entry;

import com.charredgames.game.jam.bgj6.Controller;
import com.charredgames.game.jam.bgj6.Main;
import com.charredgames.game.jam.bgj6.graphics.Screen;
import com.charredgames.game.jam.bgj6.graphics.Sprite;
import com.charredgames.game.jam.bgj6.input.Keyboard;

public class Player extends Mob{

	private int speed = 1, health = 30;
	private boolean magnet, invincible = false;
	
	//protected Sprite sprite = Sprite.player;
	
	public Player(Keyboard input){
		super(input);
	}
	
	public void update(){
		if(input.up) {
			direction = 2;
			this.y -= speed;
		}
		if(input.down) {
			direction = 0;
			this.y += speed;
		}
		if(input.left) {
			direction = 1;
			this.x -= speed;
		}
		if(input.right) {
			direction = 3;
			this.x += speed; 
		}
		if(this.x > Main.getRainbowRightEdge() - 10) this.x = Main.getRainbowRightEdge() - 10;
		if(this.x < Main.getRainbowLeftEdge() - 6) this.x = Main.getRainbowLeftEdge() - 6;
		if(this.y < 0) this.y = 0;
		if(this.y > Main.getWindowHeight()/3 - 16) this.y = Main.getWindowHeight()/3 - 16;
		
		checkCollision();
		
		resetPowerups();
	}
	
	private void resetPowerups(){
		System.out.println(Controller.powerups.get(Powerups.INVINCIBLE));
		if(Controller.powerups.get(Powerups.INVINCIBLE) == 0) invincible = false;
		if(Controller.powerups.get(Powerups.MAGNET) == 0) magnet = false;
		if(Controller.powerups.get(Powerups.SPEED) == 0) speed = 1;
	}
	
	private void checkPositive(Mob mob){
		if(mob instanceof Powerup){
			Powerups pType = ((Powerup) mob).getType();
			Controller.incrementPowerup(pType);
			if(pType==Powerups.SPEED) speed=2;
			else if(pType == Powerups.INVINCIBLE) invincible = true;
			else if(pType == Powerups.MAGNET) magnet = true;
		}
	}
	
	private void checkCollision(){
		for(Mob mob : Controller.mobs){
			if(!mob.removed() && (mob.getX() >= this.x && mob.getX() <= this.x+10)&&(mob.getY() >= this.y && mob.getY() <= this.y+10)){
				mob.remove();
				if(!invincible && mob.getPoints()<0) {
					Controller.score += mob.getPoints();
					health--;
				}
				if(mob.getPoints()>=0) Controller.score += mob.getPoints();
				checkPositive(mob);
			}
			else if(!mob.removed() && (mob.getX()+10 >= this.x && mob.getX()+10 <= this.x+16)&&(mob.getY()+10 >= this.y && mob.getY()+10 <= this.y+16)){
				mob.remove();
				if(!invincible && mob.getPoints()<0) {
					Controller.score += mob.getPoints();
					health--;
				}
				if(mob.getPoints()>=0) Controller.score += mob.getPoints();
				checkPositive(mob);
			}
			else if(!mob.removed() && (mob.getX() >= this.x && mob.getX() <= this.x+10)&&(mob.getY()+10 >= this.y && mob.getY()+10 <= this.y+10)){
				mob.remove();
				if(!invincible && mob.getPoints()<0) {
					Controller.score += mob.getPoints();
					health--;
				}
				if(mob.getPoints()>=0) Controller.score += mob.getPoints();
				checkPositive(mob);
			}
			else if(!mob.removed() && (mob.getX()+10 >= this.x && mob.getX()+10 <= this.x+10)&&(mob.getY() >= this.y && mob.getY() <= this.y+10)){
				mob.remove();
				if(!invincible && mob.getPoints()<0) {
					Controller.score += mob.getPoints();
					health--;
				}
				if(mob.getPoints()>=0) Controller.score += mob.getPoints();
				checkPositive(mob);
			}
		}

	}
	
	public void render(Screen screen){
		Sprite realSprite = Sprite.player_forward;
		if(direction==0) realSprite = Sprite.player_forward;
		else if(direction==1) realSprite = Sprite.player_left;
		else if(direction==2) realSprite = Sprite.player_backward;
		else if(direction==3) realSprite = Sprite.player_right;
		screen.renderTile(this.x, this.y, realSprite);
	}
	
	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
	}
	
}
