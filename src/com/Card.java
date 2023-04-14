/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Card extends JLabel implements MouseListener{

	Main main;
	String name;
	boolean on;
	boolean canClick=false;
	boolean clicked=false;
	public Card(Main m,String name,boolean on){
		this.main=m;
		this.name=name;
		this.on=on;
	    if(this.on)
	    	this.turnFront();
	    else {
			this.turnRear();
		}
		this.setSize(100, 135);
		this.setVisible(true);
		this.addMouseListener(this);
	}
	
	public void turnFront() {
		this.setIcon(new ImageIcon("image/"+ name +".jpg"));
		this.on = true;
	}
	
	public void turnRear() {
		this.setIcon(new ImageIcon("image/0.jpg"));
		this.on = false;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(canClick)
		{
			Point from=this.getLocation();
			int step; 
			if(clicked)
				step=-20;
			else {
				step=20;
			}
			clicked=!clicked; 
			
			Common.move(this,from,new Point(from.x,from.y-step));
		}
		
		
	}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}

}

