package communication;
 
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;

import serializable.ArrayList;
import serializable.List; 

public class Client extends Socket implements Serializable{
    private List <Client> client=new ArrayList<>();
    private List <Serializable> message=new ArrayList<>();

    // getter
    public List<Client> getClient() {
        return client;
    }
    public List<Serializable> getMessage() {
        return message;
    }
   
    
    public void sendMessage(Serializable message) throws IOException{
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(this.getOutputStream());
        objectOutputStream.writeObject(message);
        objectOutputStream.flush();
    }
    public Serializable receiveMessage() throws IOException, ClassNotFoundException{
        ObjectInputStream objectInputStream=new ObjectInputStream(this.getInputStream());
        Serializable rep=(Serializable) objectInputStream.readObject();
        // objectInputStream.close();
        return rep;
    }
    

    // constructor
    public Client(String adress,int port) throws UnknownHostException, IOException{
        super(adress,port);
    }
    


}
