package snake_ultimate;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyListenerTest {

	static char lastInput =' ';
	static char newInput = ' ';
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		KeyListener listener = new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode()==KeyEvent.VK_LEFT) {
					newInput='a';
				}
				else if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
					newInput='d';
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
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
