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
			
			System.out.println("Hello and Welcome to ultimate Snek!");
			System.out.println();
			System.out.print("Please enter a name:");
			String name = input.readLine();
			
			boolean isHost = false;
			boolean gameIsRunning = false;
			String option;

			
			while(true) {
				System.out.println("Type \"Host\" if you want to host a game,");
				System.out.println("type \"Join\" if you want to join a game.");
				System.out.println("Or type \"Exit\" to close the game");
				option = input.readLine();
				if(option.equals("Host")) {
					//isHost = true;
					isHost=true;
					
					break;
				}
				else if(option.equals("Join")) {
					break;
				}
				else if(option.equals("Exit")) {
					break;
				}
				else {
					System.out.println("Uknown comand!");
				}
			}
			System.out.print("Write IP address or enter for default IP: ");
			option = input.readLine();
			if(option.isEmpty()){
				IP = InetAddress.getLocalHost().getHostAddress();
			}
			else if (option.equals("j")){
				IP="10.209.118.64";	
			}
			String uri = "tcp://"+IP+":9002/queue?keep";
			if(isHost) {
				new Thread(new Host("tcp://"+IP+":9002/?keep")).start();
				Thread.sleep(200);
			}
			
			
			// Set the URI of the chat space
			// Default value
		
			// Connect to the remote chat space 
			System.out.println("Connecting to chat space " + uri + "...");
			RemoteSpace chat = new RemoteSpace(uri);

			chat.put("join", name,1);
			// Keep sending whatever the user types
			new Thread(new ReadChat(chat,name)).start();
			new Thread(new WriteChat(chat,name,isHost)).start();
			System.out.println("Start chatting...");
			
			while(true) {
				
				
				Object[] b = chat.queryp(new ActualField("begin"));
				if(b!=null) {
					System.out.println("Game is starting...");
					break;
				}
				
				
			}	
			
			String uriM = "tcp://"+IP+":9002/"+name+"_movement?keep";
			String uriP = "tcp://"+IP+":9002/"+name+"_positions?keep";
			RemoteSpace position = new RemoteSpace(uriP);
			RemoteSpace movement = new RemoteSpace(uriM);
			System.out.println("Connecting to chat space " + uriM + "...");
			System.out.println("Connecting to chat space " + uriP + "...");
			
			
			
			
			//System.out.println(movement.query(new FormalField(String.class))[0]);
			//change to numplayers
			new Thread(new PlayerInGame((int) chat.query(new FormalField(Integer.class))[0],name,position,movement)).start();
			
			new Thread(new ReadChat(chat,name)).start();
			new Thread(new WriteChat(chat,name,isHost)).start();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}