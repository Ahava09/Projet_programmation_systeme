package ui;

import javax.swing.border.Border; 
import java.awt.*;
public class BorderRadius  implements Border 
{
    private int r;

    BorderRadius(int top,int left,int bottom,int right) {
        this.insets=new Insets(top,left,bottom,right);
    }

    public Insets getBorderInsets(Component c) {
        this.insets=new Insets(this.r+1, this.r+1, this.r+2, this.r);
        return this.insets; 
    }
    Insets insets;
    public boolean isBorderOpaque() {
        return true;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, 
    int width, int height) {
        // g.drawRoundRect(x, y, width-1, height-1, r, r);
        int w = c.getWidth() ;
        int h = c.getHeight() ; 
        g.setColor(Color.white);
        g.fillRoundRect(x, y, w, h, 20, 20);
        g.setColor(Color.decode("0xbabfc4"));
        g.drawRoundRect(x, y, w-1, h-1, 20, 20);
    }
}