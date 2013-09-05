package meew0.ld;

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainFrame extends JFrame {
	
	public static String VERSION = "01";
	
	public MainFrame() {
		super("ld"+VERSION);
		
		
	}

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		// TODO Auto-generated method 
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	}

}
