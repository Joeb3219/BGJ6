package com.charredgames.game.jam.bgj6;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Random;

import javax.swing.JFrame;

import com.charredgames.game.jam.bgj6.Mob.Mob;
import com.charredgames.game.jam.bgj6.Mob.Player;
import com.charredgames.game.jam.bgj6.graphics.Screen;
import com.charredgames.game.jam.bgj6.graphics.Sprite;
import com.charredgames.game.jam.bgj6.graphics.Tile;
import com.charredgames.game.jam.bgj6.input.Keyboard;
import com.charredgames.game.jam.bgj6.input.Mouse;

public class Main extends Canvas implements Runnable{

	public static final int _WIDTH = 350;
	public static final int _HEIGHT = _WIDTH / 16 * 9;
	public static final int _SCALE = 3;
	public static final int _DESIREDTPS = 60;
	private boolean isRunning = false;
	private String title = "CharredGames: Leprechaun Simulator 2014 (BGJ 6) ";
	public boolean paused = false;
	
	private Thread mainThread;
	private static JFrame window;
	private BufferStrategy buffer;
	private Graphics g;
	private BufferedImage image = new BufferedImage(_WIDTH, _HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	public static final int rainbowStrandWidth = 32;
	private Keyboard keyboard;
	private Screen screen;
	private Player player;
	private Random rand = new Random();
	
	private void tick(){
		if(paused) return;
		Controller.update();
		keyboard.update();
		player.update();
		Controller.updateMobs();
		Controller.tickCount++;
		generateCloud();
		generateCoin();
		generateMob();
		generateMob();
	}
	
	private void render(){
		buffer = getBufferStrategy();
		if(buffer==null){
			createBufferStrategy(3);
			return;
		}
		g = buffer.getDrawGraphics();

		screen.clear();
		
		loadRainbow();
		Controller.renderMobs(screen);
		player.render(screen);
		
		for( int i = 0; i < pixels.length; i++){
			pixels[i] = screen.pixels[i];
		}
		
		g.drawImage(image, 0, 0, window.getWidth(), window.getHeight(), null);
		
		//Score & Time
		g.setColor(Color.WHITE);
		g.drawString("Time: " + Controller.getTime(), 5, 15);
		g.drawString("Score " + Controller.getScore(), 5, 30);
		
		//Logo & title
		g.setColor(Color.GREEN);
		g.drawString("Leprechaun Simulator 2014", window.getWidth()-g.getFontMetrics().stringWidth("Leprechaun Simulator 2014")-10, 15);
		
		g.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
		g.setColor(new Color(0xFFFF8800));
		g.drawString("Charred", window.getWidth()-g.getFontMetrics().stringWidth("CharredGames")-15, 40);
		g.setColor(new Color(0xFF00AFC9));
		g.drawString("Games", window.getWidth()-g.getFontMetrics().stringWidth("Charred"), 40);
		
		g.dispose();
		buffer.show();
	}
	
	private void generateMob(){
		if(Controller.seconds >= 300 || rand.nextInt(300-Controller.seconds) < 5){
			int xPos = Math.abs(rand.nextInt(getRainbowRightEdge() - getRainbowLeftEdge()));
			xPos += (getRainbowLeftEdge() - 8);
			int mobType = rand.nextInt(3);
			if(mobType==0) new Mob(xPos, -100, -2, Sprite.mob_santa);
			else if(mobType==1) new Mob(xPos, -100, -3, Sprite.mob_rabbit);
			else if(mobType==2) new Mob(xPos, -100, -3, Sprite.mob_cat);
			Sound.test.playSound();
		}
	}
	
	private void generateCoin(){
		if(rand.nextInt(100) == 1){
			int xPos = Math.abs(rand.nextInt(getRainbowRightEdge() - getRainbowLeftEdge()));
			xPos += (getRainbowLeftEdge() + 16);
			new Mob(xPos, -100, 5, Sprite.coin);
		}
	}
	
	//Doesn't need to be a separate function, but reduces clutter.
	private void generateCloud(){
		int shouldSpawn = rand.nextInt(4000);
		if(shouldSpawn < 6){
			int side = rand.nextInt(2);
			if(side==1) new Mob(10,-100, 0, Sprite.cloud);
			else new Mob(_WIDTH - 50, -100, 0, Sprite.cloud);
		}
	}
	
	//Doesn't need to be a separate function, but useful to keep main rendering method clean.
	private void loadRainbow(){
		int currentSectorX = (int) ((_WIDTH/2)-(rainbowStrandWidth*(3.5)));
		//int endingSectorX = (window.getWidth()/2)+(rainbowStrandWidth*3);
		/*for(int strand = 0; strand < 7; strand++){
			g.setColor(Controller.rainbowColours.get(strand));
			g.fillRect(currentSectorX, 0, rainbowStrandWidth, window.getHeight());
			currentSectorX+=rainbowStrandWidth;
		}*/
		for(int strand = 0; strand < 7; strand++){
			Sprite.rainbowSprite.changeColor(Controller.rainbowColours.get(strand));
			for(int y = 0; y < window.getHeight(); y += rainbowStrandWidth){
				screen.renderTile(currentSectorX, y, Sprite.rainbowSprite);
			}
			
			currentSectorX += rainbowStrandWidth;
		}
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
		player = new Player(keyboard);
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
	
	public static int getWindowHeight(){
		return window.getHeight();
	}
	
	public static int getRainbowLeftEdge(){
		return (int) ((_WIDTH/2)-(rainbowStrandWidth*(3.5)));
	}
	
	public static int getRainbowRightEdge(){
		return getRainbowLeftEdge() + (rainbowStrandWidth * 7);
	}
	
}
