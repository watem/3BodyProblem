import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

/**
* This is the main class of the 3-body problem involving graphing, it is the frame of the simulation
*
*@author Matthew Williams, Yulia Kosharych
*@version 17-05-2017
*/
public class Main {

	static Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
	static Dimension dim = new Dimension(1200,800);
	static Dimension dim2 = new Dimension(1000,800);
	static Dimension dim3 = new Dimension(800,600);
	static Dimension frameDim = screenDim;


	/**
	* This is the main method which initiates window parameters (such as the size, location and contentPanel), then displays the simulation graph.
	*
	* @author Matthew Williams, Yulia Kosharych
	* @version 17-05-2017
	*/
	public static void main(String[] args) {

		JFrame win = new JFrame("Realtime: Drawing of a Solar System [IN PROGRESS]");

		MainPanel mainPanel = new MainPanel();

		win.setSize(frameDim);
		win.setLocationRelativeTo(null);
		win.setContentPane(mainPanel);
		win.setVisible(true);

	}

}
