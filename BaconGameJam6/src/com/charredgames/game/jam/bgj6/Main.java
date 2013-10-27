package com.charredgames.game.jam.bgj6;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.charredgames.game.jam.bgj6.Mob.Mob;
import com.charredgames.game.jam.bgj6.Mob.Player;
import com.charredgames.game.jam.bgj6.Mob.Powerup;
import com.charredgames.game.jam.bgj6.Mob.Powerups;
import com.charredgames.game.jam.bgj6.graphics.Screen;
import com.charredgames.game.jam.bgj6.graphics.Sprite;
import com.charredgames.game.jam.bgj6.input.Keyboard;
import com.charredgames.game.jam.bgj6.input.Mouse;

public class Main extends Canvas implements Runnable{

	private static final long serialVersionUID = 1L;
	public static final int _WIDTH = 350;
	public static final int _HEIGHT = _WIDTH / 16 * 9;
	public static final int _SCALE = 3;
	public static final int _DESIREDTPS = 60;
	private boolean isRunning = false;
	private String title = "CharredGames: Leprechaun Simulator 2014 (BGJ 6) ";
	public boolean paused = false, loading = true;
	public int loadingTicks = 0;
	
	private Thread mainThread;
	private static JFrame window;
	private BufferStrategy buffer;
	private Graphics g;
	private BufferedImage image = new BufferedImage(_WIDTH, _HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	private BufferedImage logo;
	
	public static final int rainbowStrandWidth = 32;
	private Keyboard keyboard;
	private Screen screen;
	private Player player;
	private Random rand = new Random();
	
	private void tick(){
		if(loading){
			loadingTicks++;
			if(loadingTicks/_DESIREDTPS >= 3) loading = false;
			return;
		}
		if(paused || player.getHealth() == 0 || Controller.gameWon) return;
		Controller.update();
		keyboard.update();
		player.update();
		Controller.updateMobs();
		Controller.tickCount++;
		generateCloud();
		generateCoin();
		generateMob();
		generateMob();
		generateMob();
		generateMob();
		generatePowerup();
	}
	
	private void render(){
		buffer = getBufferStrategy();
		if(buffer==null){
			createBufferStrategy(3);
			return;
		}
		g = buffer.getDrawGraphics();
		
		if(loading){
			g.setColor(Color.gray);
			g.fillRect(0, 0, window.getWidth(), window.getHeight());
			g.drawImage(logo, (window.getWidth()-logo.getWidth())/2, (window.getHeight()-logo.getHeight())/2, logo.getWidth(), logo.getHeight(), null);
			g.dispose();
			buffer.show();
			return;
		}

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
		g.drawString("Health: " + player.getHealth(), 5, 45);
		
		//Draw power-up timers
		int timerDrawY = 150;
		g.setColor(Color.WHITE);
		g.drawString("Powerup Time Remaining:", 5, timerDrawY);
		timerDrawY+= 15;
		g.drawString("Speed: " + Controller.powerups.get(Powerups.SPEED), 5, timerDrawY);
		timerDrawY += 15;
		g.drawString("Magnet: " + Controller.powerups.get(Powerups.MAGNET), 5, timerDrawY);
		timerDrawY += 15;
		g.drawString("Invincible: " + Controller.powerups.get(Powerups.INVINCIBLE), 5, timerDrawY);
		
		//Logo & title
		g.setColor(Color.GREEN);
		g.drawString("Leprechaun Simulator 2014", window.getWidth()-g.getFontMetrics().stringWidth("Leprechaun Simulator 2014")-10, 15);
		
		g.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
		g.setColor(new Color(0xFFFF8800));
		g.drawString("Charred", window.getWidth()-g.getFontMetrics().stringWidth("CharredGames")-15, 40);
		g.setColor(new Color(0xFF00AFC9));
		g.drawString("Games", window.getWidth()-g.getFontMetrics().stringWidth("Charred"), 40);
		
		//Death Message && restart button
		int restartStartX = 0, restartEndX = 0, restartStartY = 0, restartEndY = 0;
		if(player.getHealth() == 0){
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 40));
			g.setColor(new Color(0xFF00AFC9));
			g.drawString("You Lose!", (window.getWidth()-g.getFontMetrics().stringWidth("You Lose!"))/2, (window.getHeight()-g.getFontMetrics().getHeight())/2);
			g.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
			String line = "Score: " + Controller.getScore() + ". Time: " + Controller.getTime();
			g.drawString(line, (window.getWidth()-g.getFontMetrics().stringWidth(line))/2, ((window.getHeight())/2)+5);
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 40));
			g.setColor(Color.BLACK);
			restartStartX = (window.getWidth()-g.getFontMetrics().stringWidth("Restart"))/2;
			restartEndX = restartStartX + g.getFontMetrics().stringWidth("Restart");
			restartStartY = ((window.getHeight()/2))+45;
			restartEndY = restartStartY - g.getFontMetrics().getHeight();
			g.drawString("Restart", (window.getWidth()-g.getFontMetrics().stringWidth("Restart"))/2, ((window.getHeight()/2))+45);
		}
		
		//Winner Message && restart button
		if(Controller.gameWon){
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 40));
			g.setColor(new Color(0xFF00AFC9));
			g.drawString("You Win!", (window.getWidth()-g.getFontMetrics().stringWidth("You Win!"))/2, (window.getHeight()-g.getFontMetrics().getHeight())/2);
			g.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
			String line = "Score: " + Controller.getScore() + ". Time: " + Controller.getTime();
			g.drawString(line, (window.getWidth()-g.getFontMetrics().stringWidth(line))/2, ((window.getHeight())/2)+5);
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 40));
			g.setColor(Color.BLACK);
			restartStartX = (window.getWidth()-g.getFontMetrics().stringWidth("Restart"))/2;
			restartEndX = restartStartX + g.getFontMetrics().stringWidth("Restart");
			restartStartY = ((window.getHeight()/2))+45;
			restartEndY = restartStartY - g.getFontMetrics().getHeight();
			g.drawString("Restart", (window.getWidth()-g.getFontMetrics().stringWidth("Restart"))/2, ((window.getHeight()/2))+45);
		}
		
		//Pause button && popup
		g.setColor(Color.BLACK);
		g.setFont(new Font(Font.DIALOG, Font.PLAIN, 16));
		if(paused){
			g.drawString("Resume", 5, 65);
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 40));
			g.setColor(new Color(0xFF00AFC9));
			g.drawString("PAUSED", (window.getWidth()-g.getFontMetrics().stringWidth("PAUSED"))/2, (window.getHeight()-g.getFontMetrics().getHeight())/2);
		}
		else{
			g.setFont(new Font(Font.DIALOG, Font.PLAIN, 16));
			g.drawString("Pause", 5, 65);
		}
		g.setFont(new Font(Font.DIALOG, Font.PLAIN, 16));
		
		g.setColor(Color.BLACK);
		if(Controller.soundOn) g.drawString("Sound: On", 5, 82);
		else g.drawString("Sound: Off", 5, 82);
		
		if(Mouse.getMouseButton() == 1){
			int mX = Mouse.getX(), mY = Mouse.getY();
			int pauseStartX = 5, pauseEndX = g.getFontMetrics().stringWidth("Resume") + pauseStartX;
			int pauseStartY = 65, pauseEndY = pauseStartY - g.getFontMetrics().getHeight();
			if((mX >= pauseStartX && mX <= pauseEndX) && (mY <= pauseStartY && mY >= pauseEndY)){
				if(paused) paused = false;
				else paused = true;
			}
			
			int soundStartX = 5, soundEndX = g.getFontMetrics().stringWidth("Sound: On") + soundStartX;
			int soundStartY = 82, soundEndY =  soundStartY - g.getFontMetrics().getHeight();
			if((mX >= soundStartX && mX <= soundEndX) && (mY <= soundStartY && mY >= soundEndY)){
				if(Controller.soundOn) Controller.soundOn = false;
				else Controller.soundOn = true;
			}
			
			if((player.getHealth() == 0 || Controller.gameWon) && (mX >= restartStartX && mX <= restartEndX) && (mY <= restartStartY && mY >= restartEndY)){
				reset();
			}

			Mouse.reset();
		}
		
		g.dispose();
		buffer.show();
	}
	
	private void reset(){
		Controller.reset();
		player.reset();
		player.setPosition(_WIDTH/2, _HEIGHT);
	}
	
	private void generateMob(){
		if(Controller.seconds >= 300 || rand.nextInt(300-Controller.seconds) < 5){
			int xPos = Math.abs(rand.nextInt(getRainbowRightEdge() - getRainbowLeftEdge()));
			xPos += (getRainbowLeftEdge() - 8);
			int mobType = rand.nextInt(3);
			if(mobType==0) new Mob(xPos, -100, -2, Sprite.mob_santa);
			else if(mobType==1) new Mob(xPos, -100, -3, Sprite.mob_rabbit);
			else if(mobType==2) new Mob(xPos, -100, -3, Sprite.mob_cat);
		}
	}
	
	private void generatePowerup(){
		if(rand.nextInt(300) == 1){
			int xPos = Math.abs(rand.nextInt(getRainbowRightEdge() - getRainbowLeftEdge()));
			xPos += (getRainbowLeftEdge() + 16);
			new Powerup(xPos, -100, 5, Powerups.SPEED);
		}
		else if(rand.nextInt(1250) == 1){
			int xPos = Math.abs(rand.nextInt(getRainbowRightEdge() - getRainbowLeftEdge()));
			xPos += (getRainbowLeftEdge() + 16);
			new Powerup(xPos, -100, 5, Powerups.MAGNET);
		}
		else if(rand.nextInt(5000) == 1){
			int xPos = Math.abs(rand.nextInt(getRainbowRightEdge() - getRainbowLeftEdge()));
			xPos += (getRainbowLeftEdge() + 16);
			new Powerup(xPos, -100, 5, Powerups.INVINCIBLE);
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
		int shouldSpawn = rand.nextInt(2000);
		if(shouldSpawn < 6){
			int side = rand.nextInt(2);
			if(side==1) new Mob(10,-100, -3, Sprite.cloud);
			else new Mob(_WIDTH - 50, -100, -3, Sprite.cloud);
		}
	}
	
	//Doesn't need to be a separate function, but useful to keep main rendering method clean.
	private void loadRainbow(){
		int currentSectorX = (int) ((_WIDTH/2)-(rainbowStrandWidth*(3.5)));
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
		try {
			logo = ImageIO.read(Main.class.getResource("/textures/logo.png"));
		} catch (IOException e) {e.printStackTrace();}
		keyboard = new Keyboard();
		Mouse mouse = new Mouse();
		addKeyListener(keyboard);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		screen = new Screen(_WIDTH, _HEIGHT);
		player = new Player(keyboard);
		reset();
		Controller.powerups.put(Powerups.MAGNET, 0);
		Controller.powerups.put(Powerups.INVINCIBLE,0);
		Controller.powerups.put(Powerups.SPEED, 0);
	}
	
	public static void main(String[] args){
		Main main = new Main();
		
		window.add(main);
		window.pack();
		window.setTitle(main.title);
		window.setLocationRelativeTo(null);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		
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
