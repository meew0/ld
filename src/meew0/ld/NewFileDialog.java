package meew0.ld;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;

public class NewFileDialog extends JFrame {
	public NewFileDialog() {
		getContentPane().setLayout(new GridLayout(3, 3, 10, 10));
		
		JLabel lblCreateNewFile = new JLabel("Size:");
		lblCreateNewFile.setFont(new Font("Tahoma", Font.PLAIN, 11));
		getContentPane().add(lblCreateNewFile);
		
		JSpinner spinner = new JSpinner();
		getContentPane().add(spinner);
		
		JSpinner spinner_1 = new JSpinner();
		getContentPane().add(spinner_1);
		
		JLabel lblUsePalette = new JLabel("Use palette:");
		getContentPane().add(lblUsePalette);
		
		textField = new JTextField();
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton button = new JButton("...");
		getContentPane().add(button);
		
		JButton btnCancel = new JButton("Cancel");
		getContentPane().add(btnCancel);
		
		JButton btnOk = new JButton("OK");
		getContentPane().add(btnOk);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;
}
