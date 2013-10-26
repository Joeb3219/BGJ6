package com.charredgames.game.jam.bgj6;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.MalformedURLException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

	public AudioClip clip;
	
	public static Sound damage = new Sound("/sound/hurt1.wav");
	public static Sound powerup = new Sound("/sound/powerup.wav");
	public static Sound coin = new Sound("/sound/coin.wav");
	
	public Sound(String path){
			this.clip = Applet.newAudioClip(Sound.class.getResource(path));
	}

	public synchronized void playSound() {
		if(Controller.soundOn) clip.play();
	}
}
