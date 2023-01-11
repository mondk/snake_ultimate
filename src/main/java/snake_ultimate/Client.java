package snake_ultimate;


import org.jspace.RemoteSpace;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

public class Client {

	public static void main(String[] args) throws InterruptedException {

		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.println("Hello and Welcome to ultimate Snek!");
			System.out.println("Type \"Host\" if you want to host a game,");
			System.out.println("type \"Join\" if you want to join a game.");
			System.out.println("Or type \"Exit\" to close the game");
			
			String option = input.readLine();
			
			while(true) {
				if(option.equals("Host")) {
					//hostgame(uri);
					//isHost = true;
					//joingame(uri);
				}
				else if(option.equals("Join")) {
					//joingame(uri);
				}
				else if(option.equals("Exit")) {
					break;
				}
			}
			
			// Set the URI of the chat space
			// Default value
			System.out.print("Enter URI of the chat server or press enter for default: ");
			String uri = input.readLine();
			// Default value
			if (uri.isEmpty()) { 
				uri = "tcp://10.209.118.64:9001/chat?keep";
			}

			// Connect to the remote chat space 
			System.out.println("Connecting to chat space " + uri + "...");
			RemoteSpace chat = new RemoteSpace(uri);

			// Read user name from the console			
			System.out.print("Enter your name: ");
			String name = input.readLine();

			// Keep sending whatever the user types
			System.out.println("Start chatting...");
			while(true) {
				String message = input.readLine();
				chat.put(name, message);
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