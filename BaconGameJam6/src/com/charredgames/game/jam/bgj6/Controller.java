package com.charredgames.game.jam.bgj6;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.charredgames.game.jam.bgj6.Mob.Mob;
import com.charredgames.game.jam.bgj6.graphics.Screen;
import com.charredgames.game.jam.bgj6.graphics.Tile;

public class Controller {

	public static Map<Integer, Tile> tileColours = new HashMap<Integer, Tile>();
	public static ArrayList<Mob> mobs = new ArrayList<Mob>();
	public static ArrayList<Integer> rainbowColours = new ArrayList<Integer>(Arrays.asList(
			0xFFFF0000, 0xFFff7f00, 0xFFffff00, 0xFF00ff00,
			0xFF0000ff, 0xFF4b0082, 0xFF8f00ff
			));
	public static int tickCount = 0;
	public static int seconds = 275;
	public static int score = 0;
	
	public static String getScore(){
		return String.format("%07d", score);
	}
	
	public static String getTime(){
		int minutes = 0;
		int sec = seconds;
		if(sec/60>=1){
			minutes += (Math.floor(sec/60));
			sec -= (Math.floor(sec/60) * 60);
		}
		return String.format("%02d", minutes) + ":" + String.format("%02d", sec);
	}
	
	public static void update(){
		if(tickCount % Main._DESIREDTPS == 0) seconds++;
 	}
	
	public static void addTile(int identifier, Tile tile){
		tileColours.put(identifier, tile);
	}
	
	public static void addMob(Mob mob){
		mobs.add(mob);
	}
	
	public static void updateMobs(){
		for(Mob mob : mobs){
			if(!mob.removed()) mob.update();
		}
	}
	
	public static void renderMobs(Screen screen){
		for(Mob mob : mobs){
			if(!mob.removed()) mob.render(screen);
		}
	}
	
}
