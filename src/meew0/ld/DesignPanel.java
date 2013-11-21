package meew0.ld;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import meew0.ld.level.Level;

public class DesignPanel extends JPanel implements MouseListener,
		MouseMotionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Level l;

	private int pixelSize, ps;
	private float dimension;
	private int oX = 0, oY = 0;
	private int origX = 0, origY = 0;

	private boolean showGrid = true, down = false;

	List<DesignPanelListener> listeners;

	public DesignPanel(Level level) {
		super(true);

		this.addMouseListener(this);
		this.addMouseMotionListener(this);

		listeners = new ArrayList<DesignPanelListener>();

		l = level;

		pixelSize = level.getPixelSize();
		setDimension(1.0f);
	}

	public void registerPanelListener(DesignPanelListener l) {
		listeners.add(l);
	}

	public void setDimension(float newDim) {
		dimension = newDim;
		ps = (int) ((float) pixelSize * dimension);
		this.setSize(l.getWidth() * ps, l.getHeight() * ps);
	}

	public float getDimension() {
		return dimension;
	}

	public void toggleGrid() {
		showGrid = !showGrid;
		this.repaint();
	}

	public boolean isGrid() {
		return showGrid;
	}

	@Override
	protected void paintComponent(Graphics g) { // Invalidation
		callInvalidate();
		System.out.println("Redrawing panel");
		for (int i = 0; i < l.getWidth(); i++) {
			for (int j = 0; j < l.getHeight(); j++) {
				g.setColor(l.getColorAt(i, j));
				// for (int k = 0; k < ps; k++) {
				// for (int m = 0; m < ps; m++) {
				// g.drawLine(j * ps + m, i * ps + k, j * ps + m, i * ps
				// + k);
				// }
				// }
				g.fillRect(j * ps - oX, i * ps - oY, ps, ps);
				// g.drawLine((i - 1) * ps - 1, j * ps - 1, i * ps - 1, j * ps -
				// 1);r
				// g.drawLine(i * ps - 1, (j - 1) * ps - 1, i * ps - 1, j * ps -
				// // 1);
				// System.out.println("" + i + " " + j);
			}
		}

		if (showGrid) {
			for (int i = 0; i < l.getWidth(); i++) {
				for (int j = 0; j < l.getHeight(); j++) {
					g.setColor(Color.black);
					g.drawLine(i * ps - 1 - oX, j * ps - 1 - oY, (i + 1) * ps
							- 1 - oX, j * ps - 1 - oY);
					g.drawLine(i * ps - 1 - oX, j * ps - 1 - oY, i * ps - 1
							- oX, (j + 1) * ps - 1 - oY);
				}
			}
		}
		System.out.println("--------------");
	}

	private void callPanelClick(int x, int y) {
		for (DesignPanelListener listener : listeners) {
			listener.onPanelClick(x, y);
		}
	}

	private void callInvalidate() {
		for (DesignPanelListener listener : listeners) {
			listener.onInvalidation();
		}
	}

	private void callInvalidate2() {
		for (DesignPanelListener listener : listeners) {
			listener.onInvalidation2();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("Mouse click");
		int x = e.getX();
		int y = e.getY();

		if (e.getButton() == MouseEvent.BUTTON1) {
			double pX = x / ps;
			double pY = y / ps;

			callPanelClick((int) pX, (int) pY);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() != MouseEvent.BUTTON1) {
			System.out.println("Starting dragging process");
			origX = e.getX();
			origY = e.getY();
			down = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		down = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (down) {
			System.out.println("Dragging");
			oX = origX - e.getX();
			oY = origY - e.getY();
			this.repaint();
			callInvalidate2();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

}
