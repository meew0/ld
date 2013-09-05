package meew0.ld.level;

import java.awt.Color;

public class Level {
	Color[][] data;
	
	public Level(int w, int h) {
		data = new Color[w][h];
	}
	
	public Level(String levelString) {
		String[] rows = levelString.split("\n");
		String[] meta = rows[0].split(",");
	}
}
