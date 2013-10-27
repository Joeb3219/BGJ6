package com.charredgames.game.jam.bgj6;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {

	public AudioClip clip;
	
	public static Sound damage = new Sound("/sound/hurt.wav");
	public static Sound powerup = new Sound("/sound/pickup.wav");
	public static Sound coin = new Sound("/sound/pickup.wav");
	
	public Sound(String path){
			this.clip = Applet.newAudioClip(Sound.class.getResource(path));
	}

	public void playSound() {
		if(Controller.soundOn) clip.play();
	}
}
