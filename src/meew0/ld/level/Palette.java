package meew0.ld.level;

import java.awt.Color;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

public class Palette {
	HashMap<Integer, Color> data;
	HashMap<Integer, String> names;
	String path;

	public Palette() {
	}
	
	private void setUnifiedPath(String newPath) {
		try {
			path = Paths.get(new URI("StandardPalette.ldp")).toAbsolutePath().toString();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	private void _init(String[] rows) {
		data = new HashMap<Integer, Color>();
		names = new HashMap<Integer, String>();
		for (String row : rows) {
			try {
				String[] rDat = row.split(" ");
				int num;
				Color color;
				num = Integer.parseInt(rDat[0]);
				color = new Color(Integer.parseInt(rDat[1]),
						Integer.parseInt(rDat[2]), Integer.parseInt(rDat[3]));
				data.put(num, color);
				String name = rDat[4];
				names.put(num, name);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null,
						"An error has occurred while parsing the palette file. (NFE at: "
								+ row + ")");
			}
		}
		if (!data.containsKey(0)) {
			data.put(0, Color.black);
		}
	}

	public Palette(String pStr, String p) {
		setUnifiedPath(p);
		String[] rows = pStr.split("\n");
		_init(rows);
	}

	public Palette(String[] rows, String p) {
		setUnifiedPath(p);
		_init(rows);
	}

	public Palette(boolean b) {
		if(b) {
			try {
				String[] lines = Files.readAllLines(Paths.get("StandardPalette.ldp"), Charset.defaultCharset()).toArray(new String[]{});
				_init(lines);
			} catch (IOException e) {
				e.printStackTrace();
			}
			setUnifiedPath("StandardPalette.ldp");
		}
	}

	public Color getColor(int i) {
		return data.get(i);
	}
	
	public String getName(int i) {
		return names.get(i);
	}
	
	public List<PaletteEntry> getEntriesList() {
		ArrayList<PaletteEntry> list = new ArrayList<PaletteEntry>();
		
		for(int i : data.keySet()) {
			PaletteEntry entry = new PaletteEntry();
			
			entry.setNum(i);
			entry.setColor(data.get(i));
			entry.setName(names.get(i));
			
			list.add(entry);
		}
		
		return list;
	}

	public void put(int i, Color c) {
		data.put(i, c);
	}
	
	public String getPath() {
		return path;
	}
}
