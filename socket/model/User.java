package model;
 
import java.io.IOException;
import java.io.Serializable; 
import java.net.UnknownHostException;
import serializable.ArrayList;
import serializable.Calcul;
import serializable.List;

import javax.swing.JPanel;

import communication.Client;
import communication.Message;
import ui.CalculBox;
import ui.CalculContainer;
import ui.MessageBox;

public class User implements Serializable{
    private String name;
    private transient Client socket;
    private transient List<Message> message=new ArrayList<>();
    private transient  List<User> connectedUser=new ArrayList<>();
    private String adressIP;
    private transient JPanel messageContainer;
    private transient CalculContainer calculContainer=new CalculContainer(this);
    // getter
    public String getName() {
        return name;
    }
    public Client getSocket() {
        return socket;
    }
    public List<Message> getMessage() {
        return message;
    }
    public String getAdressIP() {
        return adressIP;
    }
    public JPanel getMessageContainer() {
        return messageContainer;
    }
    public List<User> getConnectedUser() {
        return connectedUser;
    }
    public CalculContainer getCalculContainer() {
        return calculContainer;
    }

    // setter
    public void setName(String name) {
        this.name = name;
    }
    public void setSocket(Client socket) {
        this.socket = socket;
    }
    public void setMessageContainer(JPanel messageContainer) {
        this.messageContainer = messageContainer;
    }
    public void setConnectedUser(List<User> connectedUser) {
        this.connectedUser = connectedUser;
    }

    // method
    public void sendMessage(Message message) throws IOException{
        message.setSender(this);
        this.getSocket().sendMessage(message);
        this.getMessage().add(message);
    }
    public Message sendMessage(User cible,Serializable content) throws IOException{
        Message message=new Message();
        message.setCible(cible);
        message.setSender(this);
        message.setContent(content);
        this.sendMessage(message);
        return message;
    }
    
    
    public Thread receiveMessage(){
        return new Thread(()->{
            boolean b=true;
            while(b)
                try{
                    Message message=(Message) this.getSocket().receiveMessage();
                    message.setCible(this);
                    this.handle(message);
                    
                    this.getMessage().add(message);
                }
                catch(Exception e){
                    System.out.println(e.getMessage());
                    b=false;
                }
        });
    }


    // constructor
    public User(String name) throws UnknownHostException, IOException{
        this.setName(name);
        this.socket=new Client("localhost", 5000);
        this.adressIP= this.socket.getLocalSocketAddress().toString();
        this.receiveMessage().start();
        this.sendMessage(null, this);
        
        
    }
    public User(boolean isAdmin){
        this.name="Admin";
    }
    
    public void handle(Message message) throws IOException,Exception{
        if(message.getContent() instanceof ArrayList<?> && 
        ((ArrayList<?>)message.getContent()).size()>0 && 
        ((ArrayList<?>)message.getContent()).get(0) instanceof User){
            this.handleUserConnected(message);
        }
        else if(message.getContent() instanceof String){
            this.handleStringMessage(message);
        }
        else if(message.getContent() instanceof Calcul){
            this.handleCalcul(message);
        }
        
        
    }
    @SuppressWarnings("unchecked")
    public void handleUserConnected(Message message){
       this.setConnectedUser((List<User>) message.getContent());
    }
    int messageCount=-1; 
    public MessageBox handleStringMessage(Message message){
        System.out.println(message.getCible().getName()+", "+message.getSender().getName()+" text you : \""+message.getContent()+"\"");
        
        int x=message.getSender()==this?this.getMessageContainer().getWidth()-240-20:10;
        MessageBox box=new MessageBox(this, message);
        box.setLocation(x,(++messageCount)*180+30);


        this.getMessageContainer().add(box);
        
        this.messageContainer.repaint();
        this.messageContainer.getParent().repaint();
        this.messageContainer.getParent().getParent().repaint();
        return box;
    }
    
    public void handleCalcul(Message message) throws IOException,Exception{
        Calcul calcul=((Calcul)message.getContent());
        if(calcul.getValue()==null){
            calcul.solve(this);
            message.setSender(this);
            try{
                Thread.sleep(Calcul.rand(2000));
            }catch(Exception e){
                System.out.println(e);
            }
            message.setCible(calcul.getSender());
            this.sendMessage(message);
        }
        else{
            for(CalculBox cb:this.calculContainer.getCalculBox()){
                if(calcul.getEquation().equals(cb.getCalcul().getEquation())){
                    cb.setCalcul(calcul);

                }
            }
        }



    }

}
