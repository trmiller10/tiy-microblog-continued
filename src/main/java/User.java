import java.util.ArrayList;
import java.util.HashMap;

public class User {

    // TODO: create a name property
    public String name;


    String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // TODO: create a message property. This should be an ArrayList that holds Message objects.
    public ArrayList<Message> userMessage = new ArrayList<>();

    // TODO: Create a constructor that accepts one argument, the user's name
    public User(String name){

        this.name = name;
    }

    // TODO: create a getter for name
    public String getName(){

        return name;
    }

    // TODO: create a setter for name
    public void setName(String name) {

        this.name = name;
    }

    // TODO: create a getter for messages
    public ArrayList getUserMessage(){

        return userMessage;
    }

    // TODO: create a setter for messages
    public void setUserMessage(ArrayList<Message> userMessage) {

        this.userMessage = userMessage;
    }

}

