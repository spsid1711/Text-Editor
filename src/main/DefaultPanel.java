//author: Enes Tasbasi

package main;

import java.awt.BorderLayout;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.apache.poi.util.SystemOutLogger;

import com.inet.jortho.FileUserDictionary;
import com.inet.jortho.SpellChecker;

import javafx.scene.control.ScrollBar;

public class DefaultPanel extends JPanel {
	
	private File file;
	private JFileChooser fc;
	public JTextPane textPane;
	private String s = "";
	private StringBuilder sb;
	private Scanner in;
	private Boolean firstTime = true;
	private JMenuItem bold;
	private MutableAttributeSet set;
	private Timer timer;
	private StyledDocument doc;
	
	public DefaultPanel() {
		
		setLayout(new BorderLayout());
		
		// panel for the menu
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		add(panel, BorderLayout.NORTH);
		
		// main Menu
		JMenuBar menuBar = new JMenuBar();
		
		//File
		JMenu menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(menu);
		
		//Font
		JMenu menu2 = new JMenu("Font");
		menu2.setMnemonic(KeyEvent.VK_O);
		menuBar.add(menu2);
		
		//menu item : Bold
		bold = new JMenuItem("Bold");
		bold.setAccelerator(KeyStroke.getKeyStroke('B', Toolkit.getDefaultToolkit()
				.getMenuShortcutKeyMask()));
		bold.addActionListener(new Listener4());
		menu2.add(bold);
		
		//menu item : italic
		JMenuItem italic = new JMenuItem("Italic");
		italic.setAccelerator(KeyStroke.getKeyStroke('I', Toolkit.getDefaultToolkit()
				.getMenuShortcutKeyMask()));
		italic.addActionListener(new Listener5());
		menu2.add(italic);
		
		//menut item : Underline 
		JMenuItem underline = new JMenuItem("Underline");
		underline.setAccelerator(KeyStroke.getKeyStroke('U', Toolkit.getDefaultToolkit()
				.getMenuShortcutKeyMask()));
		underline.addActionListener(new Listener6());
		menu2.add(underline);
		
		//menu item ; set font size
		JMenuItem fontSize = new JMenuItem("Font size");
		fontSize.addActionListener(new Listener7());
		menu2.add(fontSize);
		
		//menu item : set font family
		JMenuItem fontFamily = new JMenuItem("Choose Font");
		fontFamily.addActionListener(new Listener8());
		menu2.add(fontFamily);
		
		//menu item : open
		JMenuItem item1 = new JMenuItem("Open");
		item1.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit()
				.getMenuShortcutKeyMask()));
		item1.setMnemonic(KeyEvent.VK_O);
		item1.addActionListener(new Listener1());
		menu.add(item1);
		
		//menu item : save
		JMenuItem item2 = new JMenuItem("Save");
		item2.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit()
				.getMenuShortcutKeyMask()));
		item2.setMnemonic(KeyEvent.VK_S);
		item2.addActionListener(new Listener2());
		menu.add(item2);
		
		//menu item : save as
		JMenuItem item3 = new JMenuItem("Save as");
		item3.setAccelerator(KeyStroke.getKeyStroke('A', Toolkit.getDefaultToolkit()
				.getMenuShortcutKeyMask()));
		item3.setMnemonic(KeyEvent.VK_V);
		item3.addActionListener(new Listener3());
		menu.add(item3);
		
		//menu item : exit
		JMenuItem item4 = new JMenuItem("Exit");
		item4.setMnemonic(KeyEvent.VK_E);
		item4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(firstTime == false) {
					Save();
				}
					System.exit(0);
					
			}
		});
		menu.add(item4);
		
		panel.add(menuBar);
		
		//panel2 for the textPane
		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(1,1));
		add(panel2);
		
		textPane = new JTextPane();
		panel2.add(textPane);
		
		
		JScrollPane scroll = new JScrollPane(textPane);
		panel2.add(scroll);
		
		//change the font using JOrtho
		SpellChecker.setUserDictionaryProvider(new FileUserDictionary());      
		SpellChecker.registerDictionaries( null, null );
	    SpellChecker.register(textPane);
		
		
		sb = new StringBuilder();
		
		set = textPane.getInputAttributes();
		
		doc = textPane.getStyledDocument();
		
		SetFontSize(20);
		
		textPane.setText(" ");
		
		
//		Toolkit toolkit = Toolkit.getDefaultToolkit();
//	    timer = new Timer();
//	    timer.scheduleAtFixedRate(new TimerTask() {
//
//			@Override
//			public void run() {
//				
//				AttributeSet attributeSet = textPane.getCharacterAttributes();
//				Object bold = attributeSet == null ? null : attributeSet.getAttribute( StyleConstants.Bold );
//				System.out.println( "Bold : " + bold );
//			}
//	    	
//	    }, 1000, 1000);
	}
		//listener to open a file, and print its text to the textPane
		public class Listener1 implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				OpenFile();
			}
		}
		
		//Listener2 for the save process
		public class Listener2 implements ActionListener {
			public void actionPerformed(ActionEvent e) {
					
			Save();
				
			}
		}
		
		//Listener3 for the save as process
		public class Listener3 implements ActionListener {
			public void actionPerformed(ActionEvent e) {
					
				SaveAs();
					
			}
		}
		
		//Listener4 to bold text
		public class Listener4 implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				
					if(StyleConstants.isBold(set) == false) {
						Bold();
					}
					else {
						ClearBold();
					}
				}
			}
		
		//Listener5 for italic
		public class Listener5 implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if(StyleConstants.isItalic(set) == false) {
					Italic();
				}
				else{
					ClearItalic();
				}
			}
		}
		
		public class Listener6 implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if(StyleConstants.isUnderline(set) == false) {
					UnderLine();
				}
				else {
					ClearUnderLine();
				}
			}
		}
		
		public class Listener7 implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				String size = JOptionPane.showInputDialog("Enter the font size");
				SetFontSize(Integer.parseInt(size));
			}
		}
		
		public class Listener8 implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				
				JFrame frame = new JFrame("Fonts");
				frame.setSize(200, 300);
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);

				GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
				String[] fnt = ge.getAvailableFontFamilyNames();
				JTextPane tp = new JTextPane();
				JScrollPane sb = new JScrollPane(tp, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				String g = "Fonts: ";
				for (int i = 0; i < fnt.length; i++){	
					g = g + fnt[i] + ", ";
				}
				tp.setText(g);
				frame.add(sb);
				frame.setVisible(true);
				
				String font = JOptionPane.showInputDialog("Enter the font");
				setFontFamily(font);
				
			}
		}
		
//		public class caretListener implements CaretListener {
//
//			@Override
//			public void caretUpdate(CaretEvent e) {
//				System.out.println(StyleConstants.isBold(set));
//				if(StyleConstants.isBold(set) == true) {
//					bold.setSelected(true);
//				} else {
//					bold.setSelected(false);
//				}
//				
//			}
//			
//		}
		
		public void Save() {
			
			if(firstTime == false) {
				BufferedWriter out;
				try {
					out = new BufferedWriter(new FileWriter(file));
					out.write(textPane.getText());
					s = textPane.getText();
					sb.append(textPane.getText());
					s = sb.toString();
					
					out.close();
				} catch (IOException e1) {
					System.out.println("A problem occured while saving the file.");
				}
			}
			
			else {
				JOptionPane.showMessageDialog(null, "A file is not open", "Error",
                        JOptionPane.ERROR_MESSAGE);
			}
		}
		
		public void SaveAs() {
			
//			if( firstTime == false) {
//				Save();
//			}
			
			JFileChooser dir = new JFileChooser(new File("C://"));
			
			int returnVal = dir.showSaveDialog(dir);
			
	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	        	
	        	try {
					FileWriter fw = new FileWriter(dir.getSelectedFile() + ".txt");
					firstTime = false;
					fw.write(textPane.getText());
					//s = textPane.getText();
					sb.equals(textPane.getText());
					s = sb.toString();
					System.out.println(sb.toString());
					
					
					fw.close();
				} catch (IOException e) {
					System.out.println("A problem occurred :(");
				}
	        		
	        }  else {
	        	System.out.println("Cannot save the file");
	        }
	        
	        
		}
		
		public void OpenFile() {
			if(firstTime == false) {
				Save();
				file = null;
			//	sb.equals("");
			//	s = "";
			}

		//	s = "";			
			
			fc = new JFileChooser();
			
		//	FileNameExtensionFilter ff = new FileNameExtensionFilter("Text Files", "txt");
		//	fc.setFileFilter(ff);
			
			int returnVal = fc.showOpenDialog(fc);

	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            file = fc.getSelectedFile();
	        } else {
	           System.out.println("Can't open the file");
	        }
	        
			try {
				in = new Scanner(file);
				while(in.hasNext()) {
				//	s = s + "" +in.nextLine();
					sb.append(sb.toString() + "" + in.nextLine());
					System.out.println(sb.toString());
				}
				
				textPane.setText(sb.toString());
				s = sb.toString();
				
			} catch (FileNotFoundException e1) {
				System.out.println("A problem occurred while oppening the file :(");
			} catch (IOException e1) {
				System.out.println("A problem occurred.");
			}
			firstTime = false;
		}
		public StringBuilder getSb() {
			if(firstTime == false) {
				Save();
			}
			sb.equals("");
			sb.append(textPane.getText());
			return sb;
		}

		public String getS() {
			return s;
			
		}
//
//		public void setS(String s) {
//			this.s = s;
//		}

		public void Bold() {
			set = textPane.getInputAttributes();
			StyleConstants.setBold(set, true);
			//textPane.setCharacterAttributes(set, true);
			doc.setCharacterAttributes(1, 0, set, false);
		}
		
		public void ClearBold() {
			StyleConstants.setBold(set, false);
			//textPane.setCharacterAttributes(set, true);
			doc.setCharacterAttributes(0, 0, set, false);
			
		}
		
		public void Italic() {
			set = textPane.getInputAttributes();
			StyleConstants.setItalic(set, true);
			//textPane.setCharacterAttributes(set, true); 	
			doc.setCharacterAttributes(0, 0, set, false);
		}
		
		public void ClearItalic() {
			StyleConstants.setItalic(set, false);
			//textPane.setCharacterAttributes(set, true);
			doc.setCharacterAttributes(0, 0, set, false);
			
		}
		
		public void UnderLine() {
			set = textPane.getInputAttributes();
			StyleConstants.setUnderline(set, true);
			//textPane.setCharacterAttributes(set, true);
			doc.setCharacterAttributes(0, 0, set, false);
		}
		
		public void ClearUnderLine() {
			StyleConstants.setUnderline(set, false);
			//textPane.setCharacterAttributes(set, true);
			doc.setCharacterAttributes(0, 0, set, false);
		}
		
		public void SetFontSize(int fontSize) {
			set = textPane.getInputAttributes();
			StyleConstants.setFontSize(set, fontSize);
			//textPane.setCharacterAttributes(set, true);
			doc.setCharacterAttributes(0, 0, set, false);
			
		}
		
		public int getFontSize() {
			return StyleConstants.getFontSize(set);
		}
		
		public void setFontFamily(String fam) {

			
			set = textPane.getInputAttributes();
			StyleConstants.setFontFamily(set, fam);
			//textPane.setCharacterAttributes(set, true);
			doc.setCharacterAttributes(0, 0, set, false);
			
		}
		
}

