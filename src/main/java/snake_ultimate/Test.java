package snake_ultimate;


//Testing of a very simple version of the game
//This class is not used in the final game

import java.awt.*;
import javax.swing.*;
public class Test extends JPanel implements Runnable{

	Test(){
		JFrame j = new JFrame();
		j.setBackground(Color.WHITE);
		j.setSize(1000,1000);
		j.add(this);
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		j.setResizable(false);
		j.setVisible(true);
	}
	
	int x [] = {100, 266, 633, 900};
	int y [] = {100, 400, 400, 100};
	Color color[] = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};
			
	protected void paintComponent(Graphics g){
		for(int i = 0; i < 4; i++) {
			g.setColor(color[i]);
			drawCircle(g,x[i],y[i],5);
		}
		
	}
	
	 public void drawCircle(Graphics cg, int xCenter, int yCenter, int r) {
	     cg.drawOval(xCenter-r, yCenter-r, 2*r, 2*r);
	 }//end drawCircle
	
	public void run(){
			while(true){
				repaint();
				for(int i = 0; i < 4; i++) {
					//x[i]++;
					y[i]++;
				}
				try{Thread.sleep(10);}catch(Exception e){}
			}
	}
	
	public static void main(String args[]){
		Test tt = new Test();
		Thread t = new Thread(tt);
		t.start();
	}
}