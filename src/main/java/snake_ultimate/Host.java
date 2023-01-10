package snake_ultimate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.SequentialSpace;
import org.jspace.SpaceRepository;

public class Host implements Runnable{

	private ArrayList<PlayerInfo> players;
	private String uri;
	Host(String uri){
		this.uri=uri;
		this.players=new ArrayList<>();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
try {
			
		
			// Create a repository 
			SpaceRepository repository = new SpaceRepository();
			 
			SequentialSpace queue = new SequentialSpace();

			repository.add("queue", queue);
		
			URI myUri = new URI(uri);
			String gateUri = "tcp://" + myUri.getHost() + ":" + myUri.getPort() +  "?keep" ;
			System.out.println("Opening repository gate at " + gateUri + "...");
			repository.addGate(gateUri);

			// queuing players 
			while (queue.query(new ActualField("start"))==null) {
				Object[] p = queue.getp(new ActualField("join"),new FormalField(String.class));
				if(p!=null) {
					players.add(new PlayerInfo((String)p[1]));
				}
			}
			
			//creating tupleSpaces
			for(PlayerInfo p:players) {
				repository.add(p+"_positions", p.posistion);
				repository.add(p+"_movement", p.movement);
			}
			
			//GameLoop
			while(true) {
				//update player position
				for(PlayerInfo p:players) {
					char m;
					Object[] t = p.movement.query(new FormalField(char.class));
					if(t!=null) {
						m=(char) t[0];
						if(m=='a') {
							p.increaseAngle();
						}
						else {
							p.decreaseAngle();
						}
					}
					//move player
					p.move();
					
				}
				checkCollision(players);
				
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	//check player collision
	public void checkCollision(ArrayList<PlayerInfo> players2) {
		for(PlayerInfo p: players) {
			
		}
	}
	
	
	public class PlayerInfo{
		double angle;
		String name;
		SequentialSpace posistion;
		SequentialSpace movement;
		double forceX = 1;
		double forceY =1;
		double force = 5;
		int x;
		int y;
		
		PlayerInfo(String name){
			this.name=name;
			this.angle=0;
			this.posistion = new SequentialSpace();
			this.movement = new SequentialSpace();
		}
		public void decreaseAngle() {
			// TODO Auto-generated method stub
			angle-=12;
		}
		public void increaseAngle() {
			// TODO Auto-generated method stub
			angle+=12;
		}
		public void move() {
			forceX=Math.cos(Math.toRadians(angle));
			forceY=Math.sin(Math.toRadians(angle));
			y+=forceY*force;
			x+=forceX*force;
			
		}
		
	}

	

}