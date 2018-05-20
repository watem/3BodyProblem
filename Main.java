import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Main {

	static Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
	static Dimension dim = new Dimension(1200,800);
	static Dimension dim2 = new Dimension(1000,800);
	static Dimension dim3 = new Dimension(800,600);
	
	static Dimension frameDim = screenDim;
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		JFrame win = new JFrame("Realtime: Drawing of a Solar System [IN PROGRESS]");
		
		MainPanel mainPanel = new MainPanel();
		
		win.setSize(frameDim);
		win.setLocationRelativeTo(null);
		win.setContentPane(mainPanel);
		win.setVisible(true);
		
	}

}
