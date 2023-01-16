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
			System.out.println();
			boolean isHost = false;
			
			System.out.print("Write IP address or enter for default IP: ");
			String option=input.readLine();
			if(option.isEmpty()){
				IP = InetAddress.getLocalHost().getHostAddress();
			}
			else if (option.equals("j")){
				IP="10.209.118.64";	
			}
		
				
				

			String uri = "tcp://"+IP+":9002/queue?keep";
			while(true) {
				System.out.println("Type \"Host\" if you want to host a game,");
				System.out.println("type \"Join\" if you want to join a game.");
				System.out.println("Or type \"Exit\" to close the game");
				option = input.readLine();
				if(option.equals("Host")) {
				
					new Thread(new Host("tcp://"+IP+":9002/?keep")).start();
					//isHost = true;
					isHost=true;
					Thread.sleep(200);
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
			
			// Set the URI of the chat space
			// Default value
		
			// Connect to the remote chat space 
			System.out.println("Connecting to chat space " + uri + "...");
			RemoteSpace chat = new RemoteSpace(uri);

			chat.put("join", name,1);
			// Keep sending whatever the user types
			System.out.println("Start chatting...");
			while(true) {
				String message = input.readLine();
				if(message.equals("start")&&isHost) {
					chat.put("start");
				}
				chat.put(name, message);
				Object[] b = chat.queryp(new ActualField("begin"));
				if(b!=null) {
					System.out.println("Game is starting...");
					break;
				}
				Object[] t = chat.query(new FormalField(String.class),new FormalField(String.class));
				System.out.println(t[0] + ": " + t[1]);
				
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
			
			

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}