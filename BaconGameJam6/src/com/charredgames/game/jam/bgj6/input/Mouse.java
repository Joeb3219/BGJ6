package com.charredgames.game.jam.bgj6.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseMotionListener, MouseListener{
	
	private int x, y, mouseButton = -1;
	
	public void mouseClicked(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		mouseButton = e.getButton();
	}

	public void mouseReleased(MouseEvent e) {
		mouseButton = -1;
	}

	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getMouseButton(){
		return mouseButton;
	}

}
