package meew0.ld.level;

import java.awt.Color;
import java.util.HashMap;

import javax.swing.JOptionPane;

public class Palette {
	HashMap<Integer, Color> data;

	public Palette() {
	}
	
	private void _init(String[] rows) {
		data = new HashMap<Integer, Color>();
		for (String row : rows) {
			try {
				String[] rDat = row.split(" ");
				int num;
				Color color;
				num = Integer.parseInt(rDat[0]);
				color = new Color(Integer.parseInt(rDat[1]),
						Integer.parseInt(rDat[2]), Integer.parseInt(rDat[3]));
				data.put(num, color);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null,
						"An error has occurred while parsing the palette file. (NFE at: " + row + ")");
			}
		}
		if(!data.containsKey(0)) {
			data.put(0, Color.black);
		}
	}

	public Palette(String pStr) {
		String[] rows = pStr.split("\n");
		_init(rows);
	}
	
	public Palette(String[] rows) {
		_init(rows);
	}
	
	public Color getColor(int i) {
		return data.get(i);
	}
	
	public void put(int i, Color c) {
		data.put(i, c);
	}
}
