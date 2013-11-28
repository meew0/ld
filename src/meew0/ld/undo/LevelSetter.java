package meew0.ld.undo;

import meew0.ld.level.Level;

public class LevelSetter {
	private int x, y, nd, od;
	private static Level l; 
	
	public LevelSetter(int posX, int posY, int newData) {
		x = posX; y = posY; nd = newData;
		od = l.getDataAt(posX, posY);
	}
	
	public void push() {
		l.setDataAt(x, y, nd);
	}
	
	public void undo() {
		l.setDataAt(x, y, od);
	}
	
	public static void setLevelTo(Level nl) {
		l = nl;
	}
}
