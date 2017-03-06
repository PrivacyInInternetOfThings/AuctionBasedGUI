import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Runnable r = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				JPanel gui = new JPanel(new BorderLayout(3, 3));

				
				
				JOptionPane.showMessageDialog(null, gui, "Auction Based Traffic Simulation", JOptionPane.PLAIN_MESSAGE);

			}
			
		};
		SwingUtilities.invokeLater(r);
	}

}
