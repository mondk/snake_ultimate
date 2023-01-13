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
			this.movement=movement;
		}
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if(e.getKeyCode()==KeyEvent.VK_LEFT) {
				input("a");
			}
			else if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
				input("d");
			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			if(e.getKeyCode()==KeyEvent.VK_LEFT) {
				input(" ");
			}
			
			else if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
				input(" ");
			
		}
			
		
		
	}
		public void input(String i) {
			try {
				this.movement.getp(new FormalField(String.class));
				this.movement.put(i);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
	}

	/*class DrawThread implements Runnable {
		int formerPosX[];
		int formerPosY[];
		int numPlayers = 2;

		private RemoteSpace position;

		
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
		int numPlayers = 2;
		int x [] = {100, 266, 633, 900}; //start positions
		int y [] = {100, 400, 400, 100};
		Color color[] = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};
		private RemoteSpace position;
		private RemoteSpace movement;
		
	    public DrawThread(int numPlayers,RemoteSpace position,RemoteSpace movement) {
	    	this.numPlayers = numPlayers;
	    	this.position=position;
	    	this.movement=movement;
	    	
	    	
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
			for(int i = 0; i < 4; i++) {
				g.setColor(color[i]);
				drawCircle(g,x[i],y[i],5);
			}
			
		}
		
		 public void drawCircle(Graphics cg, int xCenter, int yCenter, int r) {
		     cg.drawOval(xCenter-r, yCenter-r, 2*r, 2*r);
		     cg.fillOval(xCenter-r, yCenter-r, 2*r, 2*r);
		     Toolkit.getDefaultToolkit().sync();
		 }//end drawCircle
		
		public void run(){
			try {
				while(true){
					
					repaint();
					for(int i = 0; i < numPlayers; i++) {
						Object[] t;

						t = position.get(new FormalField(Integer.class),new FormalField(Integer.class));
						x[i] = (int) t[0];
						y[i] = (int) t[1];
						
					}
				}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	
	}
}
	




