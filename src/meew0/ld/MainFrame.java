package meew0.ld;

import java.awt.Color;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JFrame;
import javax.swing.UIManager;

import meew0.ld.level.Level;

public class MainFrame extends JFrame implements DesignPanelListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public static String VERSION = "01";
	
	public MainFrame() throws IOException {
		super("ld"+VERSION);
		
		this.setBackground(Color.white);
		
		String[] levelRows = Files.readAllLines(Paths.get("testlevel.ldl"), Charset.defaultCharset()).toArray(new String[]{});
		String[] palRows = Files.readAllLines(Paths.get("testpal.ldp"), Charset.defaultCharset()).toArray(new String[]{});
		Level lv = new Level(levelRows, palRows);
		
		DesignPanel panel = new DesignPanel(lv);
		panel.registerPanelListener(this);
		this.add(panel);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.setSize(600, 400);
		this.setVisible(true);
		
	}

	public static void main(String[] args) throws Throwable {
		// TODO Auto-generated method 
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		new MainFrame();
	}

	@Override
	public void onPanelClick(int x, int y) {
		// TODO Auto-generated method stub
		System.out.println("You clicked on pixel " + x + ", " + y);
	}

}
