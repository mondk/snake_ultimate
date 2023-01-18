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

			repository.add("queue", queue);//aka lobby rep
		
			URI myUri = new URI(uri);
			String gateUri = "tcp://" + myUri.getHost() + ":" + myUri.getPort() +  "?keep" ;
			System.out.println("Opening repository gate at " + gateUri + "...");
			repository.addGate(gateUri);

			// queuing players 
			while(true) {
			while (queue.queryp(new ActualField("start"))==null&&players.size()<4) { //cannot start with more than 4 players, get start from host to start
				Object[] beksed = queue.getp(new FormalField(String.class),new FormalField(String.class),new ActualField("lol"));
				
				if(beksed!=null){
					for(PlayerInfo p: players) {//repeat message received to all other players so they cant print
						queue.put(beksed[0],beksed[1],p.name);
					}
				}

				Object[] p = queue.getp(new ActualField("join"),new FormalField(String.class),new FormalField(Integer.class));
				if(p!=null) {//a new player connecting automatically writes join in chat

					players.add(new PlayerInfo((String)p[1],(byte) (players.size()+1)));//adds a playerInfo for that new player

					System.out.println("Joined");
				}
			}
			queue.get(new ActualField("start"));
			
			//creating tupleSpaces for all players
			for(PlayerInfo p:players) {
				repository.add(p.name+"_positions", p.posistion);
				repository.add(p.name+"_movement", p.movement);
				queue.put("begin"); //send a message with triggers all joined players to begin their ingame threads
				queue.put(players.size());
				
			}
			//players.get(0).movement.put("a");
			

			//String we ="e";
			//GameLoop
			
			byte playersAlive = (byte) players.size();
			map = new byte[1000][1000];
			for(PlayerInfo p: players) {
				p.posistion.get(new ActualField("Ready"));
			}
			for(PlayerInfo p: players) {
				queue.put("Server", "All players have connected", p.name);
				queue.put("Server","Game starting in 3",p.name);
			}
			Thread.sleep(1000);
			for(PlayerInfo p: players) {
				queue.put("Server","Game starting in 2",p.name);
			}
			Thread.sleep(1000);
			for(PlayerInfo p: players) {
				queue.put("Server","Game starting in 1",p.name);
			}
			Thread.sleep(1000);
			for(PlayerInfo p: players) {
				queue.put("Server","GO!!!",p.name);
			}
			
			while(playersAlive > 0) {
				Thread.sleep(30); //slow down game
				//update player position
				for(PlayerInfo p:players) {
					if(p.isAlive) { //if this player is still alive
						String input;
						while(true) { //mutual exclusion on p.movement from input thread by setting lock1
							p.movement.put("Lock1");
							if(p.movement.queryp(new ActualField("Lock2")) == null) {
								break;
							}
							p.movement.get(new ActualField("Lock1"));
						}
						Object[] t = p.movement.query(new FormalField(String.class)); //fetch input from player input thread
						p.movement.get(new ActualField("Lock1"));
						if(t!=null) {
							input=(String) t[0]; //move according to input
							if(input.equals("d")) {
								p.increaseAngle();
							}
							else if(input.equals("a")){
								p.decreaseAngle();
							}
						}
						
						p.formerx = p.x;//save currentpos before update
						p.formery = p.y;
						
						//move player
						p.move(); //move player in playerinfo class
						
						//check boundary collision
						if(p.x < 0 + p.thickness || p.x > 999 - p.thickness || p.y < 0 + p.thickness || p.y > 999 - p.thickness) {
							for(PlayerInfo q: players) {//if dead, announce to all
								queue.put("Server", p.name + " has been elimanted by going out of bounds!",q.name);
							}
							p.isAlive=false;
							playersAlive --;
						}
						else {
							//collision check with all tails and heads of the players circle
							for(int m = -p.thickness; m <= p.thickness; m++) {
								for(int n = -p.thickness; n <= p.thickness; n++) {
									if(Math.ceil(Math.sqrt(m*m + n*n)) <= p.thickness) {//a circle
										byte currentTile = map[p.x + m][p.y + n];
										if(currentTile != 0 && currentTile != p.playernumber) {//if 0 nothing was here, if playerNumber, own head was here
											for(PlayerInfo q: players) {
												queue.put("Server", p.name + " has been elimanted by touching another tail!",q.name);
											}
											p.isAlive=false;
											m = p.thickness + 1;//break loop
											n = p.thickness + 1;
											playersAlive --;
										}
									}
								}
							}
							for(int m = -p.thickness; m <= p.thickness; m++) {//sets old position to be tail
								for(int n = -p.thickness; n <= p.thickness; n++) {
									if(Math.ceil(Math.sqrt(m*m + n*n)) <= p.thickness) {//circle around position
										map[p.formerx + m][p.formery + n] = 5;
									}
								}
							}
							
							for(int m = -p.thickness; m <= p.thickness; m++) {//sets current position to be ok (since a snake movement is less than 1 head in length)
								for(int n = -p.thickness; n <= p.thickness; n++) {
									if(Math.ceil(Math.sqrt(m*m + n*n)) <= p.thickness) {//circle around position
										map[p.x + m][p.y + n] = p.playernumber;
									}
								}
							}
						}


					}
				}
				//after all players movement has been updated and checked
				for(PlayerInfo p: players) {//then send all players new position to all players
					for(PlayerInfo q: players) {
						p.posistion.put(q.x,q.y);
					}
				}
			}
			
			for(PlayerInfo p: players) {//if in game loop ends (too few players alive)
				p.posistion.put(-1,0); //ends draw thread
				Thread.sleep(50);
				if(p.isAlive) {
					for(PlayerInfo q: players) {
						queue.put("Server", "Congratulations to " + p.name + " for being the last man standing!",q.name);
					}
				}
				else {
					p.isAlive = true; //reset game
				}
				
				if(p.playernumber == 1) { //start angle depending on player and spawn location
					p.angle= 45;
					p.x = 100;
					p.y = 100;
				}
				else if(p.playernumber == 2) {
					p.angle = 135;
					p.x = 900;
					p.y = 100;
				}
				else if(p.playernumber == 3) {
					p.angle = -45;
					p.x = 100;
					p.y = 700;
				}
				else if(p.playernumber == 4){
					p.angle = -135;
					p.x = 900;
					p.y = 700;
				}
				
				p.movement.getp(new FormalField(String.class)); //empty movement
				p.posistion.get(new ActualField("Close"));//wait for threat to close before removing repos
				repository.remove(p.name+"_positions");
				repository.remove(p.name+"_movement");
				queue.put("GameEnd");
			}
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	public class PlayerInfo{
		double angle;
		byte playernumber;
		String name;
		SequentialSpace posistion;
		SequentialSpace movement;
		SequentialSpace chat;
		double forceX;
		double forceY;
		double force = 5;
		int thickness;
		int x;
		int y;
		int formerx;
		int formery;
		boolean isAlive;
		
		PlayerInfo(String name, byte playerNumber){
			this.playernumber = playerNumber;
			this.isAlive = true;

			this.name=name;
			if(playerNumber == 1) { //start angle depending on player and spawn location
				this.angle= 45;
			}
			else if(playerNumber == 2) {
				this.angle = 135;
			}
			else if(playerNumber == 3) {
				this.angle = -45;
			}
			else if(playerNumber == 4){
				this.angle = -135;
			}
			this.posistion = new SequentialSpace();
			this.movement = new SequentialSpace();
			this.chat = new SequentialSpace();
			this.thickness=5;
			if(playerNumber == 1) {//set start position for all players
				this.x=100;
				this.y=100;
			}
			else if(playerNumber == 2) {
				this.x=900;
				this.y=100;
			}
			else if(playerNumber == 3) {
				this.x=100;
				this.y=700;
			}
			else if(playerNumber == 4) {
				this.x=900;
				this.y=700;
			}
		}
		public void decreaseAngle() {
			// TODO Auto-generated method stub
			angle-=12;
		}
		public void increaseAngle() {
			// TODO Auto-generated method stub
			angle+=12;
		}
		public void move() { //calculates movement based on angle
			forceX=Math.cos(Math.toRadians(angle));
			forceY=Math.sin(Math.toRadians(angle));
			y+=forceY*force;
			x+=forceX*force;
			
		}
		
	}

	

}