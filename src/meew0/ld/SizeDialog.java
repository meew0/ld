package meew0.ld;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JSpinner;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SizeDialog extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SizeDialog(final MainFrame frame) {
		getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		
		final JSpinner spinner = new JSpinner();
		getContentPane().add(spinner);
		
		final JSpinner spinner_1 = new JSpinner();
		getContentPane().add(spinner_1);

		final JSpinner spinner_2 = new JSpinner();
		getContentPane().add(spinner_2);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setLevelSize((int) spinner.getValue(), (int) spinner_1.getValue(), (int) spinner_2.getValue());
			}
		});
		
		getContentPane().add(btnOk);
	}

}
