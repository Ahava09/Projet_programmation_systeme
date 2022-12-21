 
  
import communication.Server; 

public class RunServer{
    public static void main(String arg[]) throws Exception{
        Server server=new Server(5000);
        
        Thread.sleep(1000*60*30);//maty ny server reef 30 min
        server.close();



        // for(Calcul c :Calcul.random()){
        //     System.out.println(c.solve());
        // }
    }
}