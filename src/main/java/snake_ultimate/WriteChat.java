package snake_ultimate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.jspace.RemoteSpace;

public class WriteChat implements Runnable{
	private RemoteSpace chat;
	private boolean isHost;
	private String name;
	
	WriteChat(RemoteSpace chat, String name, boolean isHost){
		this.chat=chat;
		this.isHost=isHost;
		this.name=name;
	}
	
	
	BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			
			try {
				String message = input.readLine();
				if(message.equals("start")&&isHost) {//only host can start game
					chat.put("ishost","start");
					continue;
				}
				chat.put(name, message); //writes to server
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
}
