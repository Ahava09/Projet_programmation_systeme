package ui;


import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;

import model.User;
import serializable.List;
import serializable.ArrayList;
import serializable.Calcul;

public class CalculContainer extends JPanel{
    private JButton newCalcul=new JButton("New Equation");
    private List<CalculBox> calculBox =new ArrayList<>();
    // getter
    public JButton getNewCalcul() {
        return newCalcul;
    }
    public List<CalculBox> getCalculBox() {
        return calculBox;
    }
    public CalculContainer(User user){
        super(null); 
        this.add(this.getNewCalcul()); 
        this.newCalcul.addActionListener((e)->{ 
            this.setBackground(Color.decode("0xf7f7f7"));
            this.calculBox=new ArrayList<>();
            List<Calcul> cl=Calcul.random();
            for(Calcul c :cl){
                c.setSender(user);
                CalculBox cb=new CalculBox();
                cb.setCalcul(c);
                this.calculBox.add(cb);
            }
            try {
                user.sendMessage(null, cl);
            } catch (IOException e1) {
                System.out.println(e1.getMessage());
            }
            this.repaint();
        });
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.removeAll();
        this.add(this.getNewCalcul());
        this.newCalcul.setBounds(this.getWidth()-200,0,200,40); 
        int i=0;
        for(CalculBox c:this.calculBox){
            this.add(c);
            c.setBounds(20,20+(120*(i++)),200,100);
            
        } 
    }

}