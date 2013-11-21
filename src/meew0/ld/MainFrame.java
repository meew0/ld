package meew0.ld;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Enumeration;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.FontUIResource;

import meew0.ld.level.Level;
import meew0.ld.level.PaletteEntry;

public class MainFrame extends JFrame implements DesignPanelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static String VERSION = "03";

	public static final int CURSOR = 0;
	public static final int PIXEL = 1;
	public static final int LINE = 2;
	public static final int ALL = 3;

	private DesignPanel panel;
	private JLabel statusLabel;
	private int tool, v = 0;

	private boolean modified = false;

	public MainFrame() throws IOException {
		super("ld" + VERSION);

		this.setBackground(Color.white);

		JMenuBar menuBar = new JMenuBar();
		// Init menus
		JMenu fileMenu = new JMenu("File");
		JMenuItem newFileItem = new JMenuItem("New");
		newFileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				ActionEvent.CTRL_MASK));
		JMenuItem openFileItem = new JMenuItem("Load");
		openFileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				ActionEvent.CTRL_MASK));
		JMenuItem saveFileItem = new JMenuItem("Save");
		saveFileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.CTRL_MASK));
		JMenuItem saveAsFileItem = new JMenuItem("Save as");
		JMenuItem exitFileItem = new JMenuItem("Exit");
		exitFileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,
				ActionEvent.ALT_MASK));

		newFileItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				newFile();
			}
		});
		openFileItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					openFile();
				} catch (IOException e) {
				}
			}
		});
		saveFileItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
//				try {
//					saveFile();
//				} catch (IOException e) {
//				}
			}
		});

		// Init file menu
		fileMenu.add(newFileItem);
		fileMenu.addSeparator();
		fileMenu.add(openFileItem);
		fileMenu.add(saveFileItem);
		fileMenu.add(saveAsFileItem);
		fileMenu.addSeparator();
		fileMenu.add(exitFileItem);

		menuBar.add(fileMenu);
		// Init tools menu
		JMenu toolsMenu = new JMenu("Tools");
		JMenuItem undoToolItem = new JMenuItem("Undo");
		undoToolItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
				ActionEvent.CTRL_MASK));
		JMenuItem redoToolItem = new JMenuItem("Redo");
		redoToolItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y,
				ActionEvent.CTRL_MASK));
		JMenuItem noToolItem = new JMenuItem("Cursor");
		noToolItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
		JMenuItem setToolItem = new JMenuItem("Set pixel");
		setToolItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
		JMenuItem setLineToolItem = new JMenuItem("Set line");
		setLineToolItem.setAccelerator(KeyStroke
				.getKeyStroke(KeyEvent.VK_F4, 0));
		JMenuItem setAllItem = new JMenuItem("Fill");
		setAllItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
		JMenuItem zoomInItem = new JMenuItem("Zoom in");
		zoomInItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, 0));
		JMenuItem zoomOutItem = new JMenuItem("Zoom out");
		zoomOutItem
				.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, 0));
		JMenuItem toggleGridItem = new JMenuItem("Toggle grid");
		toggleGridItem.setAccelerator(KeyStroke
				.getKeyStroke(KeyEvent.VK_F11, 0));

		noToolItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tool = CURSOR;
			}
		});
		setToolItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tool = PIXEL;
			}
		});
		setLineToolItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tool = LINE;
			}
		});
		setAllItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tool = ALL;
			}
		});
		zoomInItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				panel.setDimension(panel.getDimension() * 2.0f);
			}
		});
		zoomOutItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				panel.setDimension(panel.getDimension() * 0.5f);
			}
		});
		toggleGridItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				panel.toggleGrid();
				repaint();
			}
		});

		toolsMenu.add(undoToolItem);
		toolsMenu.add(redoToolItem);
		toolsMenu.addSeparator();
		toolsMenu.add(noToolItem);
		toolsMenu.add(setToolItem);
		toolsMenu.add(setLineToolItem);
		toolsMenu.add(setAllItem);
		toolsMenu.addSeparator();
		toolsMenu.add(zoomInItem);
		toolsMenu.add(zoomOutItem);
		toolsMenu.add(toggleGridItem);

		menuBar.add(fileMenu);
		menuBar.add(toolsMenu);

		menuBar.setBackground(UIManager.getColor("Panel.background"));

		this.setJMenuBar(menuBar);

		String[] levelRows = Files.readAllLines(Paths.get("StandardLevel.ldl"),
				Charset.defaultCharset()).toArray(new String[] {});
		String[] palRows = Files.readAllLines(Paths.get(levelRows[0]),
				Charset.defaultCharset()).toArray(new String[] {});
		Level lv = new Level(levelRows, palRows);

		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());

		panel = new DesignPanel(lv);
		panel.registerPanelListener(this);
		p.add(panel, BorderLayout.CENTER);

		statusLabel = new JLabel("1.0x");
		statusLabel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0,
				Color.gray));
		p.add(statusLabel, BorderLayout.PAGE_END);

		final JList<PaletteEntry> list = new JList<PaletteEntry>(lv
				.getPaletteEntriesList().toArray(new PaletteEntry[0]));
		list.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.gray));
		list.setCellRenderer(new PaletteListRenderer());
		list.setPreferredSize(new Dimension(100, 0));

		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				v = list.getSelectedValue().getNum();
			}
		});

		p.add(list, BorderLayout.LINE_END);

		this.add(p);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.setSize(600, 400);
		this.setVisible(true);

	}

	public void setLevelSize(int w, int h, int pixelSize) {
		panel.l.setSize(w, h);
		panel.l.setPixelSize(pixelSize);
		panel.repaint();
		this.repaint();
		modified = true;
	}

	public void newFile() {
		if (!checkModified())
			return;
		Level lv = new Level(0, 0, 0);
		panel.l = lv;
		SizeDialog d = new SizeDialog(this);
		d.setVisible(true);
	}

	public void openFile() throws IOException {
		if (!checkModified())
			return;
		JFileChooser fd = new JFileChooser();
		int r = fd.showOpenDialog(this);
		if (r == JFileChooser.APPROVE_OPTION) {
			String[] levelRows = Files.readAllLines(
					Paths.get(fd.getSelectedFile().getAbsolutePath()),
					Charset.defaultCharset()).toArray(new String[] {});
			String[] palRows = Files.readAllLines(Paths.get(levelRows[0]),
					Charset.defaultCharset()).toArray(new String[] {});
			panel.l = new Level(levelRows, palRows);
			panel.repaint();
			this.repaint();
		}
	}

	private boolean checkModified() {
		if (modified) {
			if (JOptionPane.showConfirmDialog(this,
					"You have unsaved changes! Are you sure you want to do this?",
					"ld", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
				return false;
			}
		}
		return true;
	}

	public static void setFont(FontUIResource f) {
		Enumeration<Object> keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value != null && value instanceof FontUIResource) {
				UIManager.put(key, f);
			}
		}
	}

	public static void initLAF() {
		setFont(new FontUIResource("Segoe UI", Font.PLAIN, 12));

		UIManager.put("Separator.foreground", Color.gray);
		UIManager.put("MenuItem.acceleratorForeground", Color.gray);
		UIManager.put("Separator.shadow", UIManager.get("MenuItem.background"));

		UIManager.put("Menu.selectionBackground", Color.lightGray);
	}

	public static void main(String[] args) throws Throwable {
		// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		initLAF();
		new MainFrame();
	}

	@Override
	public void onPanelClick(int x, int y) {
		System.out.println("You clicked on pixel " + x + ", " + y);
		if (x < panel.l.getWidth() && y < panel.l.getHeight()) {
			switch (tool) {
			case PIXEL:
				panel.l.setDataAt(y, x, v);
				modified = true;
				break;
			case LINE:
				for (int i = 0; i < panel.l.getWidth(); i++)
					panel.l.setDataAt(y, i, v);
				modified = true;
				break;
			case ALL:
				for (int i = 0; i < panel.l.getWidth(); i++)
					for (int j = 0; j < panel.l.getHeight(); j++)
						panel.l.setDataAt(i, j, v);
				modified = true;
				break;
			default:
				System.out.println("Cursor or invalid tool");
			}
			System.out.println("Pixel value: " + panel.l.getDataAt(x, y));
		} else {
			System.out.println("Pixel outside of bounds!");
		}
		panel.repaint();
		this.repaint();
	}

	@Override
	public void onInvalidation() {
		statusLabel.setText("" + panel.getDimension() + "x - Grid "
				+ (panel.isGrid() ? "enabled" : "disabled"));
	}

	@Override
	public void onInvalidation2() {
		this.repaint();
	}

}
