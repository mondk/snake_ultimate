package snake_ultimate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.awt.*;
import javax.swing.*;
import javax.imageio.IIOException;
import org.jspace.*;

public class PlayerInGame implements Runnable{
	
	private static String name;
	private static RemoteSpace position;
	private static RemoteSpace movement;
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
		//new Thread(new sendInput(movement)).start();
		new Thread(new DrawUpdate(numOfPlayers, position)).start();
		
	}


	

	
	class sendInput implements Runnable {
		char lastInput;
		char newInput;
		private RemoteSpace movement;
		public sendInput(RemoteSpace movement) {
			this.lastInput = ' ';
			this.movement=movement;
		}
		
		public void run() {
			//send nothing
			try {
			while(true) {
				//newInput = det du holder inde nu, hvis ikke a eller d: så ' '
				if(lastInput == ' ') {
					if(newInput == 'a') {
						//put.channel('a')
						movement.put('a');
					}
					else if(newInput == 'd') {
						//put.channel('d')
					}
					else {
						//nothing
					}
				}
				else if(lastInput == 'a') {
					if(newInput == 'a') {
						//nothing
					}
					else if(newInput == 'd') {
						//put.channel('d')
						//get.channel('a')
					}
					else {
						//get.channel('a')
					}
				}
				else { //lastInput == 'd'
					if(newInput == 'a') {
						//put.channel('a')
						//get.channel('d')
					}
					else if(newInput == 'd') {
						//nothing
					}
					else {
						//get.channel('d')
					}
				}
			}
			
			
			
		}catch (InterruptedException e) {
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
	




