package snake_ultimate;

<<<<<<< HEAD
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
=======
import java.awt.*;
import javax.swing.JPanel;

public class PlayerInGame {
>>>>>>> 5cc3a9d1aaeddd52c90b928a0a9a9d264e6e252c

import javax.imageio.IIOException;

import org.jspace.RemoteSpace;
import org.jspace.SequentialSpace;
import org.jspace.*;

public class PlayerInGame {
	
	private static String name;
	private static RemoteSpace position;
	private static RemoteSpace movement;
	private static RemoteSpace queue;
	private static String IP;
	public static boolean isHost=false;
	
	PlayerInGame(String name,String IP){
		this.name=name;
		this.IP=IP;
		
	}

	public static void main(String[] args) throws InterruptedException, IOException {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		connectToHost();
		
		//chat before game
		while(true) {
			
			Object[] t = queue.query(new FormalField(String.class),new FormalField(String.class));
			System.out.println(t[0] + ": " + t[1]);
			String message = input.readLine();
			if(message.equals("start")&&isHost) {
				queue.put("start");
			}
			queue.put(name,message);
			
		}

	}
	
	public static void connectToHost() throws InterruptedException {
		try {
			queue = new RemoteSpace("tcp://"+IP+":9001/queue?keep");
			System.out.println("connected");
			queue.put("join", "name");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	public static void connectToChannels(){
		
		try {
			
			//uri to the two communication channels
			String uriM = "tcp://"+IP+":9001/"+name+"_movement?keep";
			String uriP ="tcp://"+IP+":9001/"+name+"_position?keep";
			
			position = new RemoteSpace(uriP);
			movement=new RemoteSpace(uriM);
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		int numPlayers;

	    public DrawUpdate(int numPlayers) {
	    	numPlayers = numPlayers;
	    	
	    	formerPosX = new int[numPlayers];
	    	formerPosY = new int[numPlayers];
	    	
	    	for(int i = 0; i < numPlayers; i++) {
	  //  		formerPosX[i] = startPosX[i];
	  //   		formerPosY[i] = startPosY[i];
	    	}
	    	
	  //draw Start Pos
	    	
	    }

	    public void run() {
	    	int playerInfo[] = new int[numPlayers];
			
	    	while(true) {
	    		for(int i = 0; i < numPlayers; i++){
	    			//get from sequential Space of player i which is pushed from host in that order
	    			//playerInfo[i] = get.nextTuple(int);
	    		}
	    		//draw lines from formerPos to newPos for all players
	    		//delete and update circles to newPos
	    		//formerPos = newPos
	    	}

	       }

	   }
	}




