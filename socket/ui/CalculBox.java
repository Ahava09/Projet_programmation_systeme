package ui;

import java.awt.Graphics;

import javax.swing.JTextPane; 

import serializable.Calcul;

public class CalculBox extends JTextPane{
    private Calcul calcul;
   
    // setter
    public void setCalcul(Calcul calcul) {
        this.calcul = calcul;
        this.setText(this.calcul.getEquation());
        this.repaint();
    }

    // getter
    public Calcul getCalcul() {
        return calcul;
    }
 
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(this.calcul!=null && this.calcul.getValue()!=null){
            this.setText(this.calcul.getValue());
        }
    }
    
}
