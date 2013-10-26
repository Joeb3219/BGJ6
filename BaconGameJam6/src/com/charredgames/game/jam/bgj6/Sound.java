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
	
	public static Sound test = new Sound("/sound/pickup.wav");
	
	public Sound(String path){
			this.clip = Applet.newAudioClip(Sound.class.getResource(path));
	}

	public synchronized void playSound() {
		clip.play();
	}
}
