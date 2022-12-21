package serializable;

import java.io.Serializable;
import java.util.Random;

import model.User;

public class Calcul implements Serializable{
    private User Sender;
    private User Resolver;

    private String value ;
    private int a;
    private int b;
    private int c;


    public void setVariable(int a,int b,int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    public String getEquation() {
        return "(Eq):  ax²+  bx+  c = 0\n".replace("a", this.a+"").replace("b", this.b+"").replace("c", this.c+"");
    }
    public String getValue() {
       return value;
   }
    public void setSender(User sender) {
        Sender = sender;
    }
    public void setResolver(User resolver) {
        Resolver = resolver;
    }
    public User getResolver() {
        return Resolver;
    }
    public User getSender() {
        return Sender;
    }
 
    public static List<Calcul> random(){
        List<Calcul> rep =new ArrayList<>();

        for(int i=0;i<10;i++){
            Calcul c=new Calcul();
            c.setVariable( rand(10), rand(20), rand(5));
            rep.add(c);
        }
        return rep;
    }
    public static int rand(int max){
        int min=1;
        return new Random().nextInt((max - min) + 1) + min;
    }

    public String solve(User resolver){
        this.setResolver(resolver);
        double racineDelta=Math.sqrt(this.b*this.b-4 *this.a*this.c); //(b^2 - 4 ac)^1/2
        double [] value=new double[]{(-this.b-racineDelta)/(2*this.a) ,(-this.b+racineDelta)/(2*this.a)};


        String rep="(E):  ax²+  bx+  c = 0\n".replace("a", this.a+"").replace("b", this.b+"").replace("c", this.c+"");
        rep+="√Δ="+Math.pow(racineDelta,2)+"\n";
        rep+="x'="+value[0]+"\n";
        rep+="x\"="+value[1]+"\n\n";
        rep+="solve by "+resolver.getName() +" :)";
        this.value=rep;
        return rep;
    }
}
