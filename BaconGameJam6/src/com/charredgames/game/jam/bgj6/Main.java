package com.charredgames.game.jam.bgj6;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.charredgames.game.jam.bgj6.graphics.Screen;
import com.charredgames.game.jam.bgj6.input.Keyboard;
import com.charredgames.game.jam.bgj6.input.Mouse;

public class Main extends Canvas implements Runnable{

	public static final int _WIDTH = 350;
	public static final int _HEIGHT = _WIDTH / 16 * 9;
	public static final int _SCALE = 3;
	public static final int _DESIREDTPS = 60;
	private boolean isRunning = false;
	private String title = "Bacon Game Jam 6 : Rainbows";
	
	private Thread mainThread;
	private JFrame window;
	private BufferStrategy buffer;
	private Graphics g;
	private BufferedImage image = new BufferedImage(_WIDTH, _HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	private Keyboard keyboard;
	private Screen screen;
	
	private void tick(){
		keyboard.update();
	}
	
	private void render(){
		buffer = getBufferStrategy();
		if(buffer==null){
			createBufferStrategy(3);
			return;
		}
		g = buffer.getDrawGraphics();
		
		screen.clear();
		
		for(int i = 0; i < pixels.length; i++){
			pixels[i] = screen.pixels[i];
		}
		
		g.drawImage(image, 0, 0, window.getWidth(), window.getHeight(), null);
		
		g.setColor(Color.GRAY);
		//g.fillRect(0, 0, window.getWidth(), window.getHeight());
				
		
		
		g.dispose();
		buffer.show();
	}
	
	public void run(){
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double nanoSeconds = 1000000000.0 / _DESIREDTPS;
		double delta = 0;
		int frames = 0, ticks = 0;
		requestFocus();
		while(isRunning){
			long now = System.nanoTime();
			delta += (now-lastTime) / nanoSeconds;
			lastTime = now;
			while(delta >= 1){
				tick();
				ticks++;
				delta--;
			}
			render();
			frames++;
			if((System.currentTimeMillis() - timer) > 1000){
				timer += 1000;
				window.setTitle(title + " (" + ticks + " TPS, " + frames + " FPS)");
				ticks = 0;
				frames = 0;
			}
		}
		stop();
	}
	
	private void start(){
		isRunning = true;
		mainThread = new Thread(this, "MainThread");
		mainThread.start();
	}
	
	private void stop(){
		try {mainThread.join();} catch (InterruptedException e) {e.printStackTrace();}
	}
	
	public Main(){
		Dimension wSize = new Dimension(_WIDTH * _SCALE, _HEIGHT * _SCALE);
		setPreferredSize(wSize);
		window = new JFrame();
		keyboard = new Keyboard();
		Mouse mouse = new Mouse();
		addKeyListener(keyboard);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		screen = new Screen(_WIDTH, _HEIGHT);
	}
	
	public static void main(String[] args){
		Main main = new Main();
		
		main.window.add(main);
		main.window.pack();
		main.window.setTitle(main.title);
		main.window.setLocationRelativeTo(null);
		main.window.setResizable(false);
		main.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.window.setVisible(true);
		
		main.start();
	}
	
}
