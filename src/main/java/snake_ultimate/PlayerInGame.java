package snake_ultimate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
import javax.imageio.IIOException;
import org.jspace.*;

public class PlayerInGame implements Runnable{
	
	private static String name;
	private  RemoteSpace position;
	private RemoteSpace movement;
	private int numOfPlayers;
	
	PlayerInGame(int numberOfPlayers,String name,RemoteSpace position,RemoteSpace movement){
		this.name=name;
		this.position=position;
		this.movement=movement;
		this.numOfPlayers=numberOfPlayers;
		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		new Thread(new DrawThread(numOfPlayers, position,movement)).start();
		
	}
	
	public class Control implements KeyListener{

		RemoteSpace movement; 
		
		Control(RemoteSpace movement){
			this.movement=movement; //init remotespace for input from player to server
			try {
				this.movement.put(" "); //put an initial value (straight ahead) or game will freeze until all have pressed one
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if(e.getKeyCode()==KeyEvent.VK_LEFT) {//read from keyboard
				input("a");
			}
			else if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
				input("d");
			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			if(e.getKeyCode()==KeyEvent.VK_LEFT) {//read from keyboard
				input(" ");
			}
			
			else if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
				input(" ");
			
		}
			
		
		
	}
		public void input(String i) {
			try {
				while(true) {//mutual exclusion on p.movement from host thread by setting lock2
					this.movement.put("Lock2");
					if(this.movement.queryp(new ActualField("Lock1")) == null) {
						break;
					}
					this.movement.get(new ActualField("Lock2"));
				}
				this.movement.getp(new FormalField(String.class)); //updates channel based on keyevents
				this.movement.put(i);
				this.movement.get(new ActualField("Lock2"));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	class DrawThread extends JPanel implements Runnable{
		
		int numPlayers; //used to draw each player in game
		int x [] = {100, 900, 100, 900}; //start positions for all 4 wether they join or not
		int y [] = {100, 100, 900, 900};
		Color color[] = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW}; //colour for each player
		private RemoteSpace position;
	//	private RemoteSpace movement;
		
	    public DrawThread(int numPlayers,RemoteSpace position,RemoteSpace movement) {
	    	this.numPlayers = numPlayers;
	    	this.position=position;
	  //  	this.movement=movement;
	    	
	    	
			JFrame j = new JFrame();
			j.setBackground(Color.WHITE);
			j.setSize(1000,1000);
			j.add(this);
			j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			j.setResizable(false);
			j.setVisible(true);
			j.addKeyListener(new Control(movement)); //Remote space for keylistener
		}
		

				
		protected void paintComponent(Graphics g){
			for(int i = 0; i < numPlayers; i++) {//for all players in Game
				g.setColor(color[i]);//set their colour
				drawCircle(g,x[i],y[i],5); //draw their current position
			}
			
		}
		
		 public void drawCircle(Graphics cg, int xCenter, int yCenter, int r) {
		     cg.drawOval(xCenter-r, yCenter-r, 2*r, 2*r); //draw and fill a circle
		     cg.fillOval(xCenter-r, yCenter-r, 2*r, 2*r);
		     Toolkit.getDefaultToolkit().sync();
		 }//end drawCircle
		
		public void run(){ //drawThread thread
			try {
				boolean gameInProgress = true;
				position.put("Ready");
				while(gameInProgress){
					
					repaint(); //paints everything
					for(int i = 0; i < numPlayers; i++) {
						Object[] t;
						t = position.get(new FormalField(Integer.class),new FormalField(Integer.class)); //fetches all player positions
						
						if((int) t[0] < 0) { //if fetched a neg number, then game has concluded
							gameInProgress = false;
							break;
						}
						else {
							x[i] = (int) t[0];
							y[i] = (int) t[1];
						}
					}
				}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	
	}
}
	




