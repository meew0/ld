package meew0.ld;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import meew0.ld.level.PaletteEntry;

public class PaletteListRenderer extends JLabel implements ListCellRenderer<PaletteEntry> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PaletteListRenderer() {
		this.setOpaque(true);
	}
	
	@Override
	public Component getListCellRendererComponent(
			JList<? extends PaletteEntry> arg0, PaletteEntry arg1, int arg2,
			boolean arg3, boolean arg4) {
		
		this.setFont(arg0.getFont());
		
		this.setText(" (" + arg1.getNum() + ") " + arg1.getName());
		this.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 0, arg1.getColor()));
		
		if(arg3) this.setBackground(arg0.getSelectionBackground());
		else this.setBackground(arg0.getBackground());
		
		return this;
	}

}
