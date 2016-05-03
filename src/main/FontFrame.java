package main;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FontFrame extends JFrame {
	
	public FontFrame() {
		JFrame frame = new JFrame("Set Font");
		frame.setContentPane(new FontPanel());
		frame.setSize(400, 300);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(frame.HIDE_ON_CLOSE);
	}
	
}
