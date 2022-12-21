package ui;
 
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JTextPane; 

import communication.Message;
import model.User;

public class MessageBox extends JPanel{
    private User user;
    private JTextPane messageContent;
    private JTextPane sender;
    // getter
    public User getUser() {
        return user;
    }
    public JTextPane getSender() {
        return sender;
    }

    // constructor
    public MessageBox(User user,Message message){
        super();
        this.user=user;
        this.setLayout(null);

        // this.setBackground(Color.white);
        this.setBorder(new BorderRadius(30,0,30,0));
        this.setSize(240, 150); 
    
        this.repaint();
        messageContent=new JTextPane();
        sender=new JTextPane();

        messageContent.setBounds(1, 31, this.getWidth()-2, 116); 
        sender.setBounds(1, 1, this.getWidth()-2, 28); 
        

        messageContent.setText((String)message.getContent());
        sender.setText(user==message.getSender()?"you":(String)message.getSender().getName());
        this.add(messageContent);
        this.add(sender);
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawLine(0, 30, this.getWidth(), 30);
    }
}
