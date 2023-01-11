package snake_ultimate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.SequentialSpace;
import org.jspace.SpaceRepository;

public class Host implements Runnable{

	private ArrayList<PlayerInfo> players;
	private String uri;
	byte map[][];
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
			while (queue.queryp(new ActualField("start"))==null) {
			
				
				Object[] p = queue.getp(new ActualField("join"),new FormalField(String.class),new FormalField(Integer.class));
				if(p!=null) {
					players.add(new PlayerInfo((String)p[1]));
					System.out.println("Joined");
				}
			}
			
			//creating tupleSpaces
			for(PlayerInfo p:players) {
				queue.put("begin");
				repository.add(p+"_positions", p.posistion);
				repository.add(p+"_movement", p.movement);
			}
			
			map = new byte[1000][1000];
			for(int m = 0; m < 1000; m++) {
				for(int n = 0; n < 1000; n++) {
					map[m][n] = 0;
				}
			}
			
			//GameLoop
			while(true) {
				//update player position
				for(PlayerInfo p:players) {
					char input;
					Object[] t = p.movement.query(new FormalField(char.class));
					if(t!=null) {
						input=(char) t[0];
						if(input=='a') {
							p.increaseAngle();
						}
						else {
							p.decreaseAngle();
						}
					}
					//move player
					p.move();
					
					//tranfer position to players
					p.posistion.put("new_position",p.x,p.y);
					
					//update map
					for(int m = -p.thickness/2; m <= p.thickness/2; m++) {
						for(int n = -p.thickness; n <= p.thickness/2; n++) {
							if(Math.ceil(Math.sqrt(m*m + n*n)) == p.thickness) {
								map[p.x + m][p.y + n] = p.playernumber;
							}
						}
					}
				}
				
				
				
				
				//collision is checked for all players
				checkCollision(players);
				
				for(PlayerInfo p: players) {
					for(PlayerInfo q: players) {
						p.posistion.put(q.x,q.y);
					}
				}
				Thread.sleep(1000);
				
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	//check player collision
	public void checkCollision(ArrayList<PlayerInfo> players) {
		for(PlayerInfo p: players) {
			for(int m = -p.thickness/2; m <= p.thickness/2; m++) {
				for(int n = -p.thickness; n <= p.thickness/2; n++) {
					if(Math.ceil(Math.sqrt(m*m + n*n)) == p.thickness) {
						if(map[p.x + m][p.y + n] != 0 && map[p.x +m][p.y + n] != p.playernumber) {
							//dead;
						}
						//cannot colide with itself
					}
				}
			}
		}
	}
	
	
	public class PlayerInfo{
		double angle;
		byte playernumber;
		String name;
		SequentialSpace posistion;
		SequentialSpace movement;
		double forceX;
		double forceY;
		double force = 5;
		int thickness;
		int x;
		int y;
		
		PlayerInfo(String name){
			this.name=name;
			this.angle=0;
			this.posistion = new SequentialSpace();
			this.movement = new SequentialSpace();
			this.forceX = 1;
			this.forceY=1;
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
