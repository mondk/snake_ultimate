package snake_ultimate;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyListenerTest {

	static char lastInput =' ';
	static char newInput = ' ';
	public static void main(String[] args) {
		
		//Test to check if the keys pressed gets registered correctly 
		//This class is not used in the final game
		
		KeyListener listener = new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_LEFT) {
					newInput='a';
				}
				else if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
					newInput='d';
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_LEFT) {
					lastInput =newInput;
					newInput=' ';
				}
				
				else if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
					lastInput =newInput;
					newInput=' ';
				
			}
			}
		};
		while(true) {
			System.out.println("Last: "+lastInput);
			System.out.println("New: "+newInput);
		}
	}
	
	

}
