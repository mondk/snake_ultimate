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
	public static RemoteSpace position;
	public static RemoteSpace movement;
	private int numOfPlayers;
	public String newInput;
	public String lastInput;
	
	PlayerInGame(int numberOfPlayers,String name,RemoteSpace position,RemoteSpace movement){
		this.name=name;
		this.position=position;
		this.movement=movement;
		this.numOfPlayers=numberOfPlayers;
		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//new Thread(new sendInput(movement)).start();
		//new Thread(new DrawUpdate(numOfPlayers, this.position)).start();
		JFrame frame = new JFrame("Test");
		//frame.add(new DrawUpdate(numOfPlayers, movement));
		frame.setTitle("CurveTest");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.addKeyListener(new Control(movement));
		frame.setVisible(true);
		frame.pack();
		frame.setLocationRelativeTo(null);
		
		
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
	


	class DrawUpdate extends JPanel implements Runnable {
		int formerPosX[];
		int formerPosY[];
		int numPlayers = 2;

		private RemoteSpace position;

		

		Graphics graphics;
		public Image image;
		
	    public DrawUpdate(int numPlayers,RemoteSpace position) {
	    	this.numPlayers = numPlayers;
	    	this.position=position;
	    	
	    	this.formerPosX = new int[numPlayers];
	    	this.formerPosY = new int[numPlayers];
	    	
	        setPreferredSize(new Dimension(1000, 1000));
	        setBackground(Color.white);
	    	
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
						//get from sequential Space of player i which is pushed from host in that order
						//playerInfo[i] = get.nextTuple(int);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	    		for(int i = 0; i < numPlayers; i++){
	    			
	    			//get from sequential Space of player i which is pushed from host in that order
	    			//playerInfo[i] = get.nextTuple(int);
	    			drawCircle(graphics,playerPosx[i],playerPosy[i],5);
	    		}
	    		
	    		
	    	}

	    		//draw lines from formerPos to newPos for all players
	    		//delete and update circles to newPos
	    		//formerPos = newPos
	    }

	}

	
	
    public void drawCircle(Graphics cg, int xCenter, int yCenter, int r) {
        cg.drawOval(xCenter-r, yCenter-r, 2*r, 2*r);
    }//end drawCircle
}





