/*package snake_ultimate;
import java.awt.*;
import javax.swing.*;
public class Test {

  public static void main(String[] arguments) {

    MyPanel panel = new MyPanel();
    try {
		Thread.sleep(5000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    // create a basic JFrame
   // JFrame.setDefaultLookAndFeelDecorated(true);
    
    JFrame frame = new JFrame("JFrame Color Example");
    frame.setSize(1000,1000);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    // add panel to main frame
    frame.add(panel);
  }
}

// create a panel that you can draw on.
class MyPanel extends JPanel {
	public void paint(Graphics g) {
		g.setColor(Color.red);
	//	g.fillRect(10,10,100,100);
		drawCircle(g, 500, 500, 20);
		}
  

  
  
  public void drawCircle(Graphics cg, int xCenter, int yCenter, int r) {
      cg.drawOval(xCenter-r, yCenter-r, 2*r, 2*r);
  }//end drawCircle
}*/

package snake_ultimate;

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