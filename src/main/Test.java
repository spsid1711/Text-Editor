package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

public class Test extends JFrame{
	
	static JTextPane pane;

    public static void main(String[] args) throws FileNotFoundException, DocumentException {
    	JFrame frame = new JFrame("Editor");
		frame.setSize(700, 500);
		frame.setLocationRelativeTo(null);
		
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
		
		pane = new JTextPane();
		
		StyledDocument doc = pane.getStyledDocument();

        Style style = pane.addStyle("I'm a Style", null);
        StyleConstants.setForeground(style, Color.red);

        try { doc.insertString(doc.getLength(), "BLAH ",style); }
        catch (BadLocationException e){}

		panel.add(pane);
		
		JButton button = new JButton("click me");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Test();
			}
		});
		panel.add(button);
		frame.add(panel);
		frame.setVisible(true);
    }
    
    @SuppressWarnings("deprecation")
	private Test() {
    	Document document = new Document();
    	
    	try {
    		PdfWriter writer;
    		  writer = PdfWriter.getInstance(document,
    		            new FileOutputStream("C:\\Users\\Bera\\Desktop\\Java Projects\\newPDF.pdf"));
    		
    		document.open();
    		
    		PdfContentByte cb = writer.getDirectContent();
    		PdfTemplate tp = cb.createTemplate(500,500);
    		
    		Graphics2D g2;
    		
    		g2 = tp.createGraphics( 500, 500);
    		
    		pane.print(g2);
    		g2.dispose();
    		
    		cb.addTemplate(tp, 30, 300);
    	}  catch (Exception e) {
    	      System.err.println(e.getMessage());
    	}
    	
    	document.close();
    	
    }
    
    public void pdf() throws FileNotFoundException, DocumentException {
    	//Document document = new Document(PageSize.A4, 50, 50, 50, 50);
    	
    //	PdfWriter writer = PdfWriter.getInstance(document,

 //   			new FileOutputStream("C:\\Users\\Bera\\Desktop\\Java Projects\\newPDF.pdf"));

    	//		document.open();
    			
    			Anchor anchorTarget = new Anchor("First page of the document.");
    		      anchorTarget.setName("BackToTop");
    		      Paragraph paragraph1 = new Paragraph();

    		      paragraph1.setSpacingBefore(50);

    		      paragraph1.add(anchorTarget);
    	//	      document.add(paragraph1);

    	//	document.add(new Paragraph("Some more text on the " +

    	//	" first page with different color and font type.", 

    	//	FontFactory.getFont(FontFactory.COURIER, 14, Font.BOLD,	new CMYKColor(0, 255, 0, 0))));
    		
    		
    		
    	//	document.close();
    }
    
    
 }
//C:\\Users\\Bera\\Desktop\\Java Projects\\newPDF.pdf
