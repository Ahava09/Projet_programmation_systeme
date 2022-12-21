package communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket; 

import model.User;
import serializable.ArrayList;
import serializable.Calcul;
import serializable.List; 

public class Server extends ServerSocket {   
    private List <Socket> client=new ArrayList<>();
    private List <Serializable> message=new ArrayList<>();
    private List <User> users=new ArrayList<>();
    // getter
    public List<Socket> getClient() {
        return client;
    }
    public List<Serializable> getMessage() {
        return message;
    }
   
 
    public Server (int port) throws IOException{
        super(port);
        this.addClient().start();
    }
    
    public void sendMessage(Socket client,Serializable message) throws IOException{
        if(client!=null){
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(client.getOutputStream());
            objectOutputStream.writeObject(message);
            objectOutputStream.flush();
        }
    }
    public Message receiveMessage(Socket client) throws IOException, ClassNotFoundException{
        ObjectInputStream objectInputStream=new ObjectInputStream(client.getInputStream());
        Message message=(Message) objectInputStream.readObject();
        return message;

    }
    @SuppressWarnings("unchecked")
    public Thread listen(Socket client) throws IOException, ClassNotFoundException{
      
        return new Thread(()->{
            
            boolean b=true;
            while (b) {
                try { 
                    Message message=receiveMessage(client); 
                    if(message.getContent() instanceof String || message.getContent() instanceof Calcul){
                        Socket socket=getSocket(message.getCible());
                        this.sendMessage(socket, message);
                            
                    }
                    else if(message.getContent()==Code.connectedUser){
                        Socket socket=getSocket(message.getSender());
                        Message u=new Message();
                        u.setCible(message.getSender()); 
                        u.setContent(this.users);   

                        this.sendMessage(socket,u );
                    } 
                    else if (message.getContent() instanceof ArrayList){
                        List <Calcul> l= (List<Calcul>) message.getContent();
                        List<Socket> clientWithoutSender=new ArrayList<>();
                        for(Socket s:this.client){
                            if(s!=getSocket(message.getSender())){
                                clientWithoutSender.add(s);
                            }
                        }
                        
                        if(l.size()>0 && l.get(0) instanceof Calcul && clientWithoutSender.size()>0){
                      
                            for(Calcul c:l ){
                                Socket socket= clientWithoutSender.get(l.indexOf(c)% clientWithoutSender.size());
                                Message u=new Message();
                                u.setContent(c);
                                this.sendMessage(socket,u );
                            }
                        }                 
                    }


                } catch (ClassNotFoundException | IOException e) {
                    // e.printStackTrace();
                    System.out.println(e.getMessage());
                    b=false;
                }
            }
        });
    }
    
    public Socket getSocket(User user){
        for(Socket socket:this.client){
            String ip=socket.getRemoteSocketAddress().toString(); 
            if(socket.isConnected() && user.getAdressIP().equals(ip)){ 
                return socket;
            }
        }
        return null;
    }


    public Thread addClient() throws IOException{
       return new Thread(()->{
        while(this.getClient().size()<3)
            try {
                Socket c=(Socket) this.accept();
                this.client.add(c); 
                Message userInfo=(Message)receiveMessage(c);
                this.users.add((User)userInfo.getContent());
                this.listen(c).start();

            } catch (Exception e) { 
                e.printStackTrace();
            }
       });
    }



}
