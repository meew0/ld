package meew0.ld.level;

import java.awt.Color;
import java.util.List;

import javax.swing.JOptionPane;

public class Level {
	int[][] data;
	int width, height, ps;
	String palettePath;
	
	public int getPixelSize() {
		return ps;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public List<PaletteEntry> getPaletteEntriesList() {
		return palette.getEntriesList();
	}

	Palette palette;

	private void _init(int w, int h, int pixelSize) {
		data = new int[w][h];
		width = w;
		height = h;
		ps = pixelSize;
	}
	
	private void _init2(String[] rows) {
		palettePath = rows[0];
		String[] meta = rows[1].split(",");
		int w = Integer.parseInt(meta[0]);
		int h = Integer.parseInt(meta[1]);
		int pixelSize = Integer.parseInt(meta[2]);
		_init(w, h, pixelSize);

		for (int i = 0; i < w; i++) {
			String[] pixels = rows[i+2].split(",");
			for (int j = 0; j < h; j++) {
				try {
					data[i][j] = Integer.parseInt(pixels[j]);
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null,
							"An error has occurred while parsing the level file. (NFE at row "
									+ j + ", column " + i + ")");
					data[i][j] = 0;
				}
			}
		}
	}

	public Level(int w, int h, int pixelSize) {
		_init(w, h, pixelSize);
	}

	public Level(String levelString, String paletteString) {
		palette = new Palette(paletteString);
		
		String[] rows = levelString.split("\n");
		_init2(rows);
		
	}

	public Level(String[] levelRows, String[] paletteRows) {
		palette = new Palette(paletteRows);
		
		_init2(levelRows);
		
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
	
	public void setDataAt(int x, int y, int v) {
		try {
			data[x][y] = v;
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "An error has occurred while setting data. (" + x + ", " + y + ")");
		}
		System.out.println("Internal pixel value: " + data[x][y]);
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
