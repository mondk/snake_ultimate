package snake_ultimate;


import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.RemoteSpace;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Queue;

public class Client {
	static String IP;
	public static void main(String[] args) throws InterruptedException {

		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.println("Hello and Welcome to ultimate Snek!"); //introduction
			System.out.println();
			System.out.print("Please enter a name:");
			String name = input.readLine(); //untested if some names might trigger something in code
			//be specially minded, the game cant handle 2 people with the same name
			
			boolean isHost = false;
			boolean gameIsRunning = false;
			String option;

			
			while(true) {
				System.out.println("Type \"Host\" if you want to host a game,");
				System.out.println("type \"Join\" if you want to join a game.");
				System.out.println("Or type \"Exit\" to close the game");
				option = input.readLine(); //host executes host() then join()
				if(option.equals("Host")) {
					isHost=true; //will set up host server and allow to start game
					
					break;
				}
				else if(option.equals("Join")) {
					break;
				}
				else if(option.equals("Exit")) { //does not exit yet (might remove)
					break;
				}
				else {
					System.out.println("Uknown comand!");
				}
			}
			System.out.print("Write IP address or enter for default IP: ");
			option = input.readLine(); //ask for Ip
			if(option.isEmpty()){
				IP = InetAddress.getLocalHost().getHostAddress();
			}
			else if (option.equals("j")){ //jaspers IP
				IP="10.209.118.64";	
			}
			String uri = "tcp://"+IP+":9002/queue?keep";//sets up uri for lobby given IP
			if(isHost) {
				new Thread(new Host("tcp://"+IP+":9002/?keep")).start(); //starts thread on the givenuri
			//	Thread.sleep(200);
			}
			
			
			// Set the URI of the chat space
			// Default value
		
			// Connect to the remote chat space 
			System.out.println("Connecting to chat space " + uri + "..."); //connects to the gicen uri
			RemoteSpace chat = new RemoteSpace(uri);

			chat.put("join", name,1); //types to server join to create a playerInfo object.
			// Keep sending whatever the user types
			//Thread.sleep(50);
			new Thread(new WriteChat(chat,name,isHost)).start(); //allows to chat (and start game for host)
			//Thread.sleep(50);
			new Thread(new ReadChat(chat,name)).start(); //allows to print chat
			
			System.out.println("Start chatting...");
			
			while(true) {
				
				
			chat.get(new ActualField("begin")); //waits until host types "start" where server will write "begin" to all.
				
				
				
			
			String uriM = "tcp://"+IP+":9002/"+name+"_movement?keep"; //creates game channels for when game starts
			String uriP = "tcp://"+IP+":9002/"+name+"_positions?keep";
			RemoteSpace position = new RemoteSpace(uriP);
			RemoteSpace movement = new RemoteSpace(uriM);
			System.out.println("Connecting to chat space " + uriM + "...");
			System.out.println("Connecting to chat space " + uriP + "...");
			
			
			
			
			//System.out.println(movement.query(new FormalField(String.class))[0]);
			//change to numplayers
			new Thread(new PlayerInGame((int) chat.query(new FormalField(Integer.class))[0],name,position,movement)).start(); //starts the inGame class
			
			chat.get(new ActualField("GameEnd"));
			System.out.println("Returning to Lobby");
			}

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}