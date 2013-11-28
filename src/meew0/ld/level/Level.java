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
		palette = new Palette(true);
	}

	public Level(String levelString, String paletteString, String paletteP) {
		palette = new Palette(paletteString, paletteP);
		
		String[] rows = levelString.split("\n");
		_init2(rows);
		
	}

	public Level(String[] levelRows, String[] paletteRows, String paletteP) {
		palette = new Palette(paletteRows, paletteP);
		
		_init2(levelRows);
		
	}
	
	public void setPalette(Palette newPal) {
		palette = newPal;
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
	
	public void setSize(int newX, int newY) {
		int[][] newData = new int[newX][newY];
		for(int i = 0; i < newX; i++) {
			for(int j = 0; j < newY; j++) {
				if(i >= width || j >= height) {
					newData[i][j] = 0;
				} else newData[i][j] = 
						data[i][j];
			}
		}
		data = new int[newX][newY];
		data = newData.clone();
		width = newX; height = newY;
	}
	
	public void setPixelSize(int newPs) {
		ps = newPs;
	}
	
	public String[] generateString() {
		String[] rows = new String[height+2];
		rows[0] = palette.getPath();
		rows[1] = "" + width + "," + height + "," + ps;
		for(int i = 0; i < height; i++) {
			rows[i+2] = "";
			for(int j = 0; j < width; j++) {
				rows[i+2] += data[i][j];
				rows[i+2] += ",";
			}
			rows[i+2] = rows[i+2].substring(0, rows[i+2].length()-1);
		}
		return rows;
	}
}
