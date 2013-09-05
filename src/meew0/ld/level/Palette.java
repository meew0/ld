package meew0.ld.level;

import java.awt.Color;
import java.util.HashMap;

public class Palette {
	HashMap<Integer, Color> data;

	public Palette() {
		data = new HashMap<Integer, Color>();
	}

	public Palette(String pStr) {
		String[] rows = pStr.split("\n");
		for (String row : rows) {
			String[] rDat = row.split(" ");
			int num;
			Color color;
			num = Integer.parseInt(rDat[0]);
			color = new Color(Integer.parseInt(rDat[1]),
					Integer.parseInt(rDat[2]), Integer.parseInt(rDat[3]));
		}
	}
}
