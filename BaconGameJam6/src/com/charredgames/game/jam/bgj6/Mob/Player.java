package com.charredgames.game.jam.bgj6.Mob;

import com.charredgames.game.jam.bgj6.Main;
import com.charredgames.game.jam.bgj6.graphics.Screen;
import com.charredgames.game.jam.bgj6.graphics.Sprite;
import com.charredgames.game.jam.bgj6.input.Keyboard;

public class Player extends Mob{

	//protected Sprite sprite = Sprite.player;
	
	public Player(Keyboard input){
		super(input);
	}
	
	public void update(){
		if(input.up) {
			direction = 2;
			this.y --;
		}
		if(input.down) {
			direction = 0;
			this.y ++;
		}
		if(input.left) {
			direction = 1;
			this.x --;
		}
		if(input.right) {
			direction = 3;
			this.x ++; 
		}
		if(this.x > Main.getRainbowRightEdge() - 10) this.x = Main.getRainbowRightEdge() - 10;
		if(this.x < Main.getRainbowLeftEdge() - 6) this.x = Main.getRainbowLeftEdge() - 6;
		if(this.y < 0) this.y = 0;
		if(this.y > Main.getWindowHeight()/3 - 16) this.y = Main.getWindowHeight()/3 - 16;
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
