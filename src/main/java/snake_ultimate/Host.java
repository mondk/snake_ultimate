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
		map = new byte[1000][1000];
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
			while (queue.queryp(new ActualField("start"))==null&&players.size()<4) {
				Object[] beksed = queue.getp(new FormalField(String.class),new FormalField(String.class),new FormalField(Integer.class));
				
				if(beksed!=null){
					for(PlayerInfo p: players) {
						queue.put(beksed[0],beksed[1],p.name);
					}
				}

				Object[] p = queue.getp(new ActualField("join"),new FormalField(String.class),new FormalField(Integer.class));
				if(p!=null) {

					players.add(new PlayerInfo((String)p[1],(byte) (players.size()+1)));

					System.out.println("Joined");
				}
			}
			
			//creating tupleSpaces
			for(PlayerInfo p:players) {
				repository.add(p.name+"_positions", p.posistion);
				repository.add(p.name+"_movement", p.movement);
				
			}
			queue.put("begin");
			queue.put(players.size());

			//players.get(0).movement.put("a");
			

			//String we ="e";
			//GameLoop
			
			byte playersAlive = (byte) players.size();
			
			while(playersAlive > 0) {
				Thread.sleep(30); //slow down game
				//update player position
				for(PlayerInfo p:players) {
					
					if(p.isAlive) {
						String input;
						Object[] t = p.movement.query(new FormalField(String.class));
						if(t!=null) {
							input=(String) t[0];
							if(input.equals("d")) {
								p.increaseAngle();
							}
							else if(input.equals("a")){
								p.decreaseAngle();
							}
						}
						
						p.formerx = p.x;
						p.formery = p.y;
						
						//move player
						p.move();
						
						//check boundary collision
						if(p.x < 0 + p.thickness || p.x > 999 - p.thickness || p.y < 0 + p.thickness || p.y > 999 - p.thickness) {
							System.out.println("Boundary Collision :"+p.playernumber);
							p.force = 0;
							p.isAlive=false;
							playersAlive --;
							p.posistion.put(-p.playernumber,0);
						}
						else {
			//		System.out.println(p.x+" "+p.y);							
							for(int m = -p.thickness; m <= p.thickness; m++) {//collision check
								for(int n = -p.thickness; n <= p.thickness; n++) {
									if(Math.ceil(Math.sqrt(m*m + n*n)) <= p.thickness) {
										byte currentTile = map[p.x + m][p.y + n];
										if(currentTile != 0 && currentTile != p.playernumber) {
											System.out.println("Collision :"+p.playernumber);//possible manually draw the circle instead of this automated shit
											p.force = 0;
											p.isAlive=false;
											m = p.thickness + 1;//break loop
											n = p.thickness + 1;
											playersAlive --;
											p.posistion.put(-p.playernumber,0);
										}
									}
								}
							}
							for(int m = -p.thickness; m <= p.thickness; m++) {//sets old position to be tail
								for(int n = -p.thickness; n <= p.thickness; n++) {
									if(Math.ceil(Math.sqrt(m*m + n*n)) <= p.thickness) {
										map[p.formerx + m][p.formery + n] = 5;
									}
								}
							}
							
							for(int m = -p.thickness; m <= p.thickness; m++) {//sets current position to be ok (since a snake movement is less than 1 head in length)
								for(int n = -p.thickness; n <= p.thickness; n++) {
									if(Math.ceil(Math.sqrt(m*m + n*n)) <= p.thickness) {
										map[p.x + m][p.y + n] = p.playernumber;
									}
								}
							}
						}


					}
				//collision is checked for all players

				//checkCollision(players);
		//			System.out.println(p.x+" "+p.y);
					//update map
				}
				
//				for(PlayerInfo p:players) {
//					//update map
//					for(int m = -p.thickness; m <= p.thickness; m++) {
//						for(int n = -p.thickness; n <= p.thickness; n++) {
//							if(Math.ceil(Math.sqrt(m*m + n*n)) == p.thickness) {
//								if(map[p.x + m][p.y + n] != 0 && map[p.x + m][p.y + n] != p.playernumber) {
//									System.out.println("Collision"); //possible manually draw the circle instead of this automated shit
//								}
//								map[p.x + m][p.y+n] = 5;
//							}
//						}
//					}
//				}
				
				
				//collision is checked for all players

				for(PlayerInfo p: players) {
					for(PlayerInfo q: players) {
						p.posistion.put(q.x,q.y);
					}
				}
			}
			
			for(PlayerInfo p: players) {
				p.posistion.put(-5,0);
				if(p.isAlive) {
					System.out.println("Congratulations to " + p.name + " for being last mans standing!");
				}
				else {
					System.out.println(p.name + " was eliminated!");

				}
			}
			

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	//check player collision
//	public void checkCollision(ArrayList<PlayerInfo> players) {
//		for(PlayerInfo p: players) {
//			for(int m = -p.thickness; m <= p.thickness; m++) {
//				for(int n = -p.thickness; n <= p.thickness; n++) {
//					if(Math.ceil(Math.sqrt(m*m + n*n)) == p.thickness) {
//						if(map[p.x + m][p.y + n] != 0 && map[p.x +m][p.y + n] != p.playernumber) {
//							//dead;
//						}
//						//cannot colide with itself
//					}
//				}
//			}
//		}
//	}
	
	
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
			this.angle=0;
			this.posistion = new SequentialSpace();
			this.movement = new SequentialSpace();
			this.chat = new SequentialSpace();
			this.forceX = 1;
			this.forceY=1;
			this.thickness=5;
			if(playerNumber == 1) {
				this.x=100;
				this.y=100;
			}
			else if(playerNumber == 2) {
				this.x=900;
				this.y=100;
			}
			else if(playerNumber == 3) {
				this.x=100;
				this.y=900;
			}
			else if(playerNumber == 4) {
				this.x=900;
				this.y=900;
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
		public void move() {
			forceX=Math.cos(Math.toRadians(angle));
			forceY=Math.sin(Math.toRadians(angle));
			y+=forceY*force;
			x+=forceX*force;
			
		}
		
	}

	

}