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
<<<<<<< Updated upstream
		// TODO Auto-generated method stub
		
=======
>>>>>>> Stashed changes
		new Thread(new DrawThread(numOfPlayers, position,movement)).start();
		
	}


	

	
	public class Control implements KeyListener{

		RemoteSpace movement;
		
		Control(RemoteSpace movement){
<<<<<<< Updated upstream
			this.movement=movement;
=======
			this.movement=movement; //init remotespace for input from player to server
			try {
				this.movement.put(" "); //put an initial value (straight ahead) or game will freeze until all have pressed one
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
>>>>>>> Stashed changes
		}
		@Override
		public void keyTyped(KeyEvent e) {
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
<<<<<<< Updated upstream
			// TODO Auto-generated method stub
			if(e.getKeyCode()==KeyEvent.VK_LEFT) {
=======
			if(e.getKeyCode()==KeyEvent.VK_LEFT) {//read from keyboard
>>>>>>> Stashed changes
				input("a");
			}
			else if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
				input("d");
			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
<<<<<<< Updated upstream
			// TODO Auto-generated method stub
			if(e.getKeyCode()==KeyEvent.VK_LEFT) {
=======
			if(e.getKeyCode()==KeyEvent.VK_LEFT) {//read from keyboard
>>>>>>> Stashed changes
				input(" ");
			}
			
			else if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
				input(" ");
			
		}
			
		
		
	}
		public void input(String i) {
			try {
<<<<<<< Updated upstream
				this.movement.getp(new FormalField(String.class));
=======
				while(true) {//mutual exclusion on p.movement from host thread by setting lock2
					this.movement.put("Lock2");
					if(this.movement.queryp(new ActualField("Lock1")) == null) {
						break;
					}
					this.movement.get(new ActualField("Lock2"));
				}
				this.movement.getp(new FormalField(String.class)); //updates channel based on keyevents
>>>>>>> Stashed changes
				this.movement.put(i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
	}
	}

	/*class DrawThread implements Runnable {
		int formerPosX[];
		int formerPosY[];
		int numPlayers = 2;

		private RemoteSpace position;

		
<<<<<<< Updated upstream
	    public DrawThread(int numPlayers,RemoteSpace position) {
	    	this.numPlayers = numPlayers;
	    	this.position=position;
	    	
	    	this.formerPosX = new int[numPlayers];
	    	this.formerPosY = new int[numPlayers];
	    	for(int i = 0; i < numPlayers; i++) {
	  //  		formerPosX[i] = startPosX[i];
	  //   		formerPosY[i] = startPosY[i];
	    	}
	    	
	  //draw Start Pos
	    	
	    }

	    public void run() {
	    	int playerPosx[] = new int[numPlayers];
	    	int playerPosy[] = new int[numPlayers];
			
	    	while(true) {

	    		try {
					for(int i = 0; i < numPlayers; i++){
						
						Object[] t = position.get(new FormalField(Integer.class),new FormalField(Integer.class));
						
						playerPosx[i]=(int) t[0];
						playerPosy[i]=(int) t[1];
							
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	    		for(int i = 0; i < numPlayers; i++){

	    		}
	    		
	    		
	    	}

	    		//draw lines from formerPos to newPos for all players
	    		//delete and update circles to newPos
	    		//formerPos = newPos
	    }

	}
	*/
	class DrawThread extends JPanel implements Runnable{

		int formerPosX[];
		int formerPosY[];
		int numPlayers = 1;
		int x [] = {100, 266, 633, 900}; //start positions
		int y [] = {100, 400, 400, 100};
		Color color[] = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};
		private RemoteSpace position;
		private RemoteSpace movement;
=======
		int numPlayers; //used to draw each player in game
		int x [] = {100, 900, 100, 900}; //start positions for all 4 whether they join or not
		int y [] = {100, 100, 700, 700};
		Color color[] = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW}; //color for each player
		private RemoteSpace position;
>>>>>>> Stashed changes
		
	    public DrawThread(int numPlayers,RemoteSpace position,RemoteSpace movement) {
	    	this.numPlayers = numPlayers;
	    	this.position=position;
<<<<<<< Updated upstream
	    	this.movement=movement;
	    	
=======
>>>>>>> Stashed changes
	    	
	    	//Defining the parameters of the gameboard 
			JFrame j = new JFrame();
			j.setBackground(Color.WHITE);
			j.setSize(1000,1000);
			j.add(this);
			j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			j.setResizable(false);
			j.setVisible(true);
			j.addKeyListener(new Control(movement));
		}
		

				
		protected void paintComponent(Graphics g){
<<<<<<< Updated upstream
			for(int i = 0; i < 4; i++) {
				g.setColor(color[i]);
				drawCircle(g,x[i],y[i],5);
=======
			for(int i = 0; i < numPlayers; i++) {//for all players in Game
				g.setColor(color[i]);//set their color
				drawCircle(g,x[i],y[i],5); //draw their current position
>>>>>>> Stashed changes
			}
			
		}
		
		 public void drawCircle(Graphics cg, int xCenter, int yCenter, int r) {
		     cg.drawOval(xCenter-r, yCenter-r, 2*r, 2*r);
		     cg.fillOval(xCenter-r, yCenter-r, 2*r, 2*r);
		 }//end drawCircle
		
		public void run(){
			try {
				while(true){
					Thread.sleep(300);
					repaint();
					for(int i = 0; i < numPlayers; i++) {
						Object[] t;

						t = position.get(new FormalField(Integer.class),new FormalField(Integer.class));
						x[i] = (int) t[0];
						y[i] = (int) t[1];
						
<<<<<<< Updated upstream
=======
						if((int) t[0] < 0) { //if fetched a negative number, then game has concluded
							gameInProgress = false;
							break;
						}
						else {
							x[i] = (int) t[0];
							y[i] = (int) t[1];
						}
>>>>>>> Stashed changes
					}
				}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}

	
	}
}
	



