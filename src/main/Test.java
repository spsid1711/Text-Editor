package main;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JTextPane;

import com.inet.jortho.FileUserDictionary;
import com.inet.jortho.SpellChecker;

public class Test extends JFrame{

    public static void main(String[] args){
        new Test().setVisible( true );
    }
    
    private Test(){
        // Build the test frame for the sample
        super("JOrtho Sample");
        JEditorPane text = new JTextPane();
        text.setText( "This is a simppler textt with spellingg errors." );
        add( text );
        setSize(200, 160);
        setDefaultCloseOperation( EXIT_ON_CLOSE );
        setLocationRelativeTo( null );

        // Create user dictionary in the current working directory of your application
        SpellChecker.setUserDictionaryProvider( new FileUserDictionary() );
        
        // Load the configuration from the file dictionaries.cnf and 
        // use the current locale or the first language as default
        // You can download the dictionary files from http://sourceforge.net/projects/jortho/files/Dictionaries/
        SpellChecker.registerDictionaries( null, null );

        // enable the spell checking on the text component with all features
        SpellChecker.register( text );
    }
}