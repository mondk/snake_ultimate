package snake_ultimate;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.apache.commons.lang3.RandomStringUtils;

public class StartWindow {
	
	//Testing of the starting screen that appears when a user starts the game
	//This class is not used in the final game

	public static void main(String[] args) throws UnknownHostException {
		 JFrame f = new JFrame("Snake Ulitimate");
		   //set size and location of frame
		   f.setSize(390, 300);
		   f.setLocation(100, 150);
		   //make sure it quits when x is clicked
		   f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		   //set look and feel
		   f.setDefaultLookAndFeelDecorated(true);
		   JLabel labelM = new JLabel("Please wirte your name ");
		   labelM.setBounds(50, 70, 200, 30);
		   
		   JLabel labelJ = new JLabel("Write preferred IP or use default");
		   labelJ.setBounds(50, 150, 200, 30);
		   
		   JTextField motto1 = new JTextField();
		   JTextField motto2 = new JTextField();
		   motto1.setBounds(50, 100, 200, 30);
		   
		   String generatedString = RandomStringUtils.randomAlphabetic(6);
		   motto1.setText(generatedString);
		   motto2.setBounds(50, 175, 200, 30);
		   motto2.setText(InetAddress.getLocalHost().getHostAddress());
		   
		   JButton start = new JButton("Start");
		   start.setBounds(50, 210, 100, 30);
		   start.setAction(null);
		   
		   JCheckBox host = new JCheckBox("Host");
		   host.setBounds(170,210,100,30);
		   //add elements to the frame
		   f.add(labelM);
		   f.add(labelJ);
		   f.add(motto1);
		   f.add(motto2);
		   f.add(host);
		   f.add(start);
		   f.setLayout(null);
		   f.setVisible(true);
		  }

}
