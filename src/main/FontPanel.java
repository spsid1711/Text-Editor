package main;

import java.awt.BorderLayout;
import javax.swing.plaf.synth.SynthSeparatorUI;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.Checkbox;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JPanel;
import javax.swing.text.SimpleAttributeSet;

public class FontPanel extends JPanel {
	Checkbox bold;
	Checkbox italic;
	DefaultPanel dPanel;
	public FontPanel() {
		setLayout(new BorderLayout());
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(8,1));
		add(panel, BorderLayout.WEST);
		
		  bold = new Checkbox("Bold");
	      italic = new Checkbox("Italic");
	      
	      bold.addItemListener(new listener());
	      italic.addItemListener(new listener());

	      panel.add(bold);
	      panel.add(italic);
		
		
	}
	
	public class listener implements ItemListener {
	      public void itemStateChanged(ItemEvent e) {
	    	  dPanel = new DefaultPanel();
	    	  Object source = e.getItemSelectable();
	    	  dPanel = new DefaultPanel();
	    	  if(source == bold) {
	    		  if(e.getStateChange() == 1) {
	    			  SimpleAttributeSet set = new SimpleAttributeSet();
	    				StyleConstants.setBold(set, true);
	    				dPanel.setAttribute(set);
	    				dPanel.setPaneText("some text");
	    				
	    				set = new SimpleAttributeSet();
	    				StyleConstants.setItalic(set, true);
    				
	    				Document doc = dPanel.getStyledDocument();
	    				try {
	    					doc.insertString(doc.getLength(), "second sample ", set);
	    				} catch (BadLocationException e1) {
	    					e1.printStackTrace();
    				}
	    		  } else {
	    			  System.out.println("working_2");
	    		  }
	    	  }	else {
	    		  if(e.getStateChange() == 1) {
	    			  System.out.println(e.getStateChange());
	    		  } else {
	    			  System.out.println("working_3");
	    		  }
	    	  }
	    	  
	    	  
	    	  
	      }    
	   }

}
