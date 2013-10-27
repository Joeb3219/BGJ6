package com.charredgames.game.jam.bgj6.Mob;

import java.util.Map.Entry;

import com.charredgames.game.jam.bgj6.Controller;
import com.charredgames.game.jam.bgj6.Main;
import com.charredgames.game.jam.bgj6.Sound;
import com.charredgames.game.jam.bgj6.graphics.Screen;
import com.charredgames.game.jam.bgj6.graphics.Sprite;
import com.charredgames.game.jam.bgj6.input.Keyboard;

public class Player extends Mob{

	private static int speed = 1, health = 30;
	private static boolean magnet, invincible = false;
	
	//protected Sprite sprite = Sprite.player;
	
	public static void reset(){
		speed = 1;
		magnet = false;
		invincible = false;
		health = 30;
	}
	
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
		if(magnet) checkNearbyCoins();
		resetPowerups();
	}
	
	private void damage(){
		health--;
		Sound.damage.playSound();
	}
	
	private void checkNearbyCoins(){
		int checkDistance = Controller.magnetDistance;
		for(Mob mob : Controller.mobs){
			if(mob.getPoints()<0 || mob.removed()) continue;
			if((mob.getX() >= this.x && mob.getX() <= this.x+checkDistance)&&(mob.getY() >= this.y && mob.getY() <= this.y+checkDistance)){
				mob.remove();
				Controller.score += mob.getPoints();
				checkPositive(mob);
			}
			else if((mob.getX()+10 >= this.x && mob.getX()+checkDistance <= this.x+checkDistance)&&(mob.getY()+10 >= this.y && mob.getY()+checkDistance <= this.y+checkDistance)){
				mob.remove();
				Controller.score += mob.getPoints();
				checkPositive(mob);
			}
			else if((mob.getX() >= this.x && mob.getX() <= this.x+checkDistance)&&(mob.getY()+checkDistance >= this.y && mob.getY()+checkDistance <= this.y+checkDistance)){
				mob.remove();
				Controller.score += mob.getPoints();
				checkPositive(mob);
			}
			else if((mob.getX()+checkDistance >= this.x && mob.getX()+checkDistance <= this.x+checkDistance)&&(mob.getY() >= this.y && mob.getY() <= this.y+checkDistance)){
				mob.remove();
				Controller.score += mob.getPoints();
				checkPositive(mob);
			}
		}
	}
	
	private void resetPowerups(){
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
			Sound.powerup.playSound();
		}
		else if(mob.getPoints() > 0) Sound.coin.playSound();
	}
	
	private void checkCollision(){
		for(Mob mob : Controller.mobs){
			if(mob.removed()) continue;
			if((mob.getX() >= this.x && mob.getX() <= this.x+10)&&(mob.getY() >= this.y && mob.getY() <= this.y+10)){
				mob.remove();
				if(!invincible && mob.getPoints()<0) {
					Controller.score += mob.getPoints();
					damage();
				}
				if(mob.getPoints()>=0) Controller.score += mob.getPoints();
				checkPositive(mob);
			}
			else if((mob.getX()+10 >= this.x && mob.getX()+10 <= this.x+10)&&(mob.getY()+10 >= this.y && mob.getY()+10 <= this.y+10)){
				mob.remove();
				if(!invincible && mob.getPoints()<0) {
					Controller.score += mob.getPoints();
					damage();
				}
				if(mob.getPoints()>=0) Controller.score += mob.getPoints();
				checkPositive(mob);
			}
			else if((mob.getX() >= this.x && mob.getX() <= this.x+10)&&(mob.getY()+10 >= this.y && mob.getY()+10 <= this.y+10)){
				mob.remove();
				if(!invincible && mob.getPoints()<0) {
					Controller.score += mob.getPoints();
					damage();
				}
				if(mob.getPoints()>=0) Controller.score += mob.getPoints();
				checkPositive(mob);
			}
			else if((mob.getX()+10 >= this.x && mob.getX()+10 <= this.x+10)&&(mob.getY() >= this.y && mob.getY() <= this.y+10)){
				mob.remove();
				if(!invincible && mob.getPoints()<0) {
					Controller.score += mob.getPoints();
					damage();
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
	
	public int getHealth(){
		return health;
	}
	
}
