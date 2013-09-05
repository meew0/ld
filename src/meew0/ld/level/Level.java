package meew0.ld.level;

import java.awt.Color;

import javax.swing.JOptionPane;

public class Level {
	int[][] data;
	int width, height;

	private void _init(int w, int h) {
		data = new int[w][h];
		width = w;
		height = h;
	}

	public Level(int w, int h) {
		_init(w, h);
	}

	public Level(String levelString, String paletteString) {
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
}
