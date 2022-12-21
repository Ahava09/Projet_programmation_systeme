package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
// import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer; 
import java.awt.Dimension; 
import communication.Code;
import model.User;

public class Frame extends JFrame{
    private User user;
    private User cible;
    private JComboBox <User>userList=new JComboBox<>();
    private JTextArea text=new JTextArea();
    private JButton submit;
    private JScrollPane scrollPane;
    

    // getter
    public User getUser() {
        return user;
    }
    public JTextArea getText() {
        return text;
    }
    public Frame(String name) throws UnknownHostException, IOException{
        super();
        this.setLayout(null);
        this.getContentPane().setLayout(null);


        this.setSize(700,500);
        this.getContentPane().setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.user=new User(name);
        this.user.setMessageContainer(new JPanel(null));
        this.user.getMessageContainer().setBackground(Color.white); 
          


        
        this.userList.setRenderer(new ItemRenderer());
        
        
       
        this.userList.addActionListener(e -> {
            this.cible = (User) ((JComboBox<?>) e.getSource()).getSelectedItem();
        });


        submit =new JButton("submit");
        submit.setBackground(Color.WHITE);
        // this.submit.setLayout(null);
        this.getText().setBorder(BorderFactory.createLineBorder(Color.black));
        submit.addActionListener((e)->{
            try {
                if(cible!=null){ 
                    user.handleStringMessage( user.sendMessage(cible, this.getText().getText()));
                }
            } catch (IOException e1) { 
                e1.printStackTrace();
            }
        });


        this.setTitle(name);
        this.getContentPane().add(this.userList);
        this.getContentPane().add(this.getText());
        this.getContentPane().add(submit);
        scrollPane = new JScrollPane(this.user.getMessageContainer());
        // scrollPane.setLayout(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.getContentPane().add(scrollPane);
        this.getContentPane().add(user.getCalculContainer());
        
        this.setVisible(true);


        new Timer(10000, (e)->{
            try {
                this.getUser().sendMessage(null, Code.connectedUser);
                this.userList.removeAllItems();
                for(User u:this.getUser().getConnectedUser()){
                    if(!u.getAdressIP().equals(this.getUser().getAdressIP()))
                    this.userList.addItem(u);
                }
                
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }).start();


    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.userList.setBounds(this.getWidth()-400, 1, 385, 30); 
        this.submit.setBounds(this.getWidth()-100,this.getHeight()-100, 100, 65); 
        this.getText().setBounds(this.getWidth()-400,this.getHeight()-100,300-2, 100);

        scrollPane.setBounds(this.getWidth()-400,32, 400, this.getHeight()-132); 
        this.user.getCalculContainer().setBounds(0,0, this.getWidth()-400-50, this.getHeight());
        // scrollPane.setBounds(this.getWidth()-400,32, 400, this.getHeight()-132); 
        this.user.getMessageContainer().setPreferredSize( new Dimension(400, 1000000)); 
        // this.user.getMessageContainer().setBounds(this.getWidth()-400,32, 400, this.getHeight()-132); 
        user.getCalculContainer().repaint();

        this.user.getMessageContainer().repaint();
    }
} 