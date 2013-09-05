package meew0.ld.level;

import java.awt.Color;

import javax.swing.JOptionPane;

public class Level {
	int[][] data;
	int width, height;
	Palette palette;

	private void _init(int w, int h) {
		data = new int[w][h];
		width = w;
		height = h;
	}

	public Level(int w, int h) {
		_init(w, h);
	}

	public Level(String levelString, String paletteString) {
		palette = new Palette(paletteString);
		
		String[] rows = levelString.split("\n");
		String[] meta = rows[0].split(",");
		int w = Integer.parseInt(meta[0]);
		int h = Integer.parseInt(meta[1]);
		_init(w, h);

		for (int i = 0; i < w; i++) {
			String[] pixels = rows[i].split(",");
			for (int j = 0; j < h; j++) {
				try {
					data[w][h] = Integer.parseInt(pixels[j]);
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null,
							"An error has occurred while parsing the level file. (NFE at row "
									+ j + ", column " + i + ")");
					data[w][h] = 0;
				}
			}
		}
	}
	
	public Color getColorAt(int x, int y) {
		try {
			return palette.getColor(data[x][y]);
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "An error has occurred while fetching colors. (" + x + ", " + y + ")");
			if(palette != null) {
				return palette.getColor(0);
			} else return Color.black;
		}
	}
	
	public int getDataAt(int x, int y) {
		try {
			return data[x][y];
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "An error has occurred while fetching data. (" + x + ", " + y + ")");
			return 0;
		}
	}
}
