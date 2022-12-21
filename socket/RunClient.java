 
 
 
import javax.swing.UIManager;

import ui.Frame;

public class RunClient{
    public static void main(String arg[]) throws Exception{
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        new Frame("Raberson") ;
       
    }
}