package snake_ultimate;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.RemoteSpace;
import org.jspace.SequentialSpace;

public class ReadChat implements Runnable{

	private RemoteSpace chat;
	private String name;
	ReadChat(RemoteSpace chat2,String name){
		this.chat=chat2;
		this.name=name;
	}
	@Override
	public void run() {
		while(true) {
			Object[] t = null;
			try {
				t = chat.get(new FormalField(String.class),new FormalField(String.class),new ActualField(name)); //gets from server
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(t[0] + ": " + t[1]); //and print message
		}
	}
	
}