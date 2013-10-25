package com.charredgames.game.jam.bgj6;

import java.util.HashMap;
import java.util.Map;

import com.charredgames.game.jam.bgj6.graphics.Tile;

public class Controller {

	public static Map<Integer, Tile> tileColours = new HashMap<Integer, Tile>();
	
	public static void addTile(int identifier, Tile tile){
		tileColours.put(identifier, tile);
	}
	
}
