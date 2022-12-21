package communication;

import java.io.Serializable;
import java.util.Date;

import model.User;

public class Message implements Serializable{
    private User sender;
    private User cible;
    private Serializable content;
    private Date createAt=new Date();
    
    // getter
    public User getSender() {
        return sender;
    }
    public User getCible() {
        return cible;
    }
    public Serializable getContent() {
        return content;
    }
    public Date getCreateAt() {
        return createAt;
    }


    // setter
    public void setSender(User sender) {
        this.sender = sender;
    }
    public void setCible(User cible) {
        this.cible = cible;
    }
    public void setContent(Serializable content) {
        this.content = content;
    }

}


