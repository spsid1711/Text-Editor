//author: Enes Tasbasi

package main;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.rtf.RTFEditorKit;

import com.inet.jortho.FileUserDictionary;
import com.inet.jortho.SpellChecker;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

public class DefaultPanel extends JPanel {
	
	private File file;
	private JFileChooser fc;
	public JTextPane textPane;
	private StringBuilder sb;
	private Scanner in;
	private Boolean firstTime = true;
	private JMenuItem bold;
	private MutableAttributeSet set;
	private StyledDocument doc;
	private RTFEditorKit rtf;
	
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
		
		menu2.addSeparator();
		
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
		
		menu.addSeparator();
		
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
		
		JMenu menu3 = new JMenu("PDF");
		
		JMenuItem pdf = new JMenuItem("Export to PDF");
		pdf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser dir = new JFileChooser(new File("C://"));
				
				int returnVal = dir.showSaveDialog(dir);
				
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		        	PDF(dir.getSelectedFile());
		        }
				
			}
		});
		menu3.add(pdf);
		
		menuBar.add(menu3);
		panel.add(menuBar);
		
		//panel2 for the textPane
		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(1,1));
		add(panel2);
		
		textPane = new JTextPane();
		panel2.add(textPane);
		
		JPanel noWrapPanel = new JPanel( new BorderLayout() );
		noWrapPanel.add( textPane );
		
		JScrollPane scroll = new JScrollPane(noWrapPanel);
		scroll.setViewportView(textPane);
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

				GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
				String[] fnt = ge.getAvailableFontFamilyNames();
				
				String input = (String) JOptionPane.showInputDialog(null, "Choose the font ", 
						"Font", JOptionPane.QUESTION_MESSAGE, null, fnt, fnt[0]);
				setFontFamily(input);
				
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
					Document doc = textPane.getStyledDocument();
					out.write(textPane.getText());
					sb.append(textPane.getText());
					
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
			
			if( firstTime == false) {
				Save();
			}
			
			JFileChooser dir = new JFileChooser(new File("C://"));
			
			int returnVal = dir.showSaveDialog(dir);
			
	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	        
	       
	        	
	        	try {
					FileWriter fw = new FileWriter(dir.getSelectedFile() + ".rtf");
					firstTime = false;
					fw.write(textPane.getText());
					sb.equals(textPane.getText());
					
					
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
			}		
			
			fc = new JFileChooser();
			
			int returnVal = fc.showOpenDialog(fc);

	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            file = fc.getSelectedFile();
	        } else {
	           System.out.println("Can't open the file");
	        }
	        
			try {
				
				FileInputStream fi = new FileInputStream(file);
				rtf.read(fi, textPane.getDocument(), 0);
				
//				in = new Scanner(file);
//				while(in.hasNext()) {
//					sb.append(in.nextLine());
//				}
//				
//				textPane.setText(sb.toString());
				
			} catch (FileNotFoundException e1) {
				System.out.println("A problem occurred while oppening the file :(");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			firstTime = false;
		}
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
		
		public void PDF(File file) {
			com.itextpdf.text.Document document = new com.itextpdf.text.Document();
			
	        	try {
		    		PdfWriter writer;
		    		  writer = PdfWriter.getInstance(document,
		    		            new FileOutputStream(file));
		    		
		    		document.open();
		    		
		    		PdfContentByte cb = writer.getDirectContent();
		    		PdfTemplate tp = cb.createTemplate(500,500);
		    		
		    		Graphics2D g2;
		    		
		    		g2 = tp.createGraphics( 500, 500);
		    		
		    		textPane.print(g2);
		    		g2.dispose();
		    		
		    		cb.addTemplate(tp, 30, 300);
		    	}  catch (Exception e) {
		    	      System.err.println(e.getMessage());
		    	}
		    	
		    	document.close();
		    	
		    	File f = new File(file.getAbsolutePath());
		    	
		    	if(!Desktop.isDesktopSupported()){
		            System.out.println("Desktop is not supported");
		            return;
		        }
		    	
		    	Desktop desktop = Desktop.getDesktop();
		    	if(file.exists())
					try {
						desktop.open(file);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        
	    	
	    
	    }
}

