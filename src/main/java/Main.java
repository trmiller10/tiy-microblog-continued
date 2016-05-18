import spark.ModelAndView;
import spark.Session;
import spark.*;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;

import static spark.Spark.halt;

public class Main {

    static HashMap<String, User> users = new HashMap<>();

    static int sequence = 0;

    public static void main(String[] args) {

        addTestUsers();
        //addTestMessages();

        Spark.init();

        // TODO: this creates an end-point for the webroot
        Spark.get(
                "/",
                (request, response) -> {

                    // TODO: create a HashMap to hold our model
                    HashMap hashMap = new HashMap();

                    // TODO: check if the session contains an element with a key "user"
                    //HSN: change this to User user = ... .attributes.contains("user");
                    if (request.session().attributes().contains("user")) {

                        // TODO: IF SO:

                        //ask the returning user to login


                        // TODO: get the user from the session
                        Session session = request.session();
                        User user = session.attribute("user");
                        // TODO: place the user into the model HashMap
                        hashMap.put("user", user);


                        // TODO: return a ModelAndView for the messages template
                        return new ModelAndView(hashMap, "messages.mustache");


                        //   return new ModelAndView(hashMap, "index.mustache");


                        // TODO: IF NOT:
                        // TODO: return a ModelAndView for the index template
                    } else {

                        return new ModelAndView(hashMap, "index.mustache");
                    }
                },

                new MustacheTemplateEngine()
        );


        Spark.post(
                "/login",
                (request, response) -> {
                    //TODO: get the loginName from the request's queryParams
                    String submittedName = request.queryParams("username");

                    //TODO: get the loginPassword from the request's queryParams
                    String submittedPassword = request.queryParams("password");

                    //TODO: check if the session contains the user
                    //boolean sessionUserCheck = request.session().attributes().contains(submittedName);

                    User user = users.get(submittedName);

                    //TODO: check if userPassword matches the one stored in the ArrayList


                    //TODO: if user does not exist in database, then redirect to "/create-user"
                    if(user == null){
                        response.redirect("/create-user");
                        halt();
                        return null;
                    } else
                        //TODO: if userPassword does not match the one associated with the userName in the ArrayList, then redirect to "/login"
                        if(!user.getPassword().equals(submittedPassword)){
                            response.redirect("/login");
                        } else {
                            request.session().attribute("user", user);
                            response.redirect("/");
                        }
                    return "";
                }
        );

        Spark.post(
                "/logout",
                (request, response) -> {
                    request.session().invalidate();
                    response.redirect("/");
                    halt();
                    return "";
                }
        );

        Spark.get(
                "/create-user",
                (request, response) -> {
                    HashMap m = new HashMap();

                    //if session DOES contain a user, then what are they doing here?
                    if(request.session().attributes().contains("user")){
                        response.redirect("/");
                        return null;

                    } else {
                        return new ModelAndView(m, "createuser.mustache");
                    }
                },
                new MustacheTemplateEngine()
        );

        Spark.post(
                "/create-user",
                (request, response) -> {


                    /**
                     * NOTE: should write this program so that it lets you back out back to the login page if you
                     * accidentally mistyped your info and also tells you to retry your new user name if it already
                     * exists
                     */
                    // TODO: get the loginName and password value from the request's queryParams
                    String name = request.queryParams("loginName");
                    String password = request.queryParams("userPassword");
                    // TODO: create a new instance of a User for the loginName
                    //User user = new User(name);
                    // TODO: Add the user into the session
                    User newUser = new User(name, password);
                    request.session().attribute("user", newUser);

                    users.put(name, newUser);

                    // TODO: Redirect to /
                    response.redirect("/");

                    // TODO: halt the request
                    halt();

                    // TODO: return an empty string
                    return "";
                }
        );


        Spark.post(
                "/create-message",
                (request, response) -> {

                    // TODO: get the user from the session
                    User user = request.session().attribute("user");

                    // TODO: get the submitted message text from the request's queryParams
                    String submittedMessage = request.queryParams("message");
                    sequence = sequence + 1;
                    // TODO: Create a new message for submitted message text
                    Message newMessage = new Message(submittedMessage, sequence);

                    // TODO: add the new message to the user's array of messages
                    //HSN: User class already has its own Array List for messages, use that one here instead
                    //newMessage.setMessageText(submittedMessage);
                    user.getUserMessage().add(newMessage);

                    // TODO: redirect to the webroot, /
                    response.redirect("/");

                    // TODO: halt this request
                    halt();
                    // TODO: return an empty string

                    return "";
                }
        );


        Spark.post(
                "/delete-message",
                (request, response) -> {

                    //get the current user
                    User user = request.session().attribute("user");

                    //create an integer variable to hold the converted number typed into the delete message # field
                    int deleteId = Integer.valueOf(request.queryParams("deleteId"));

                    //retrieve the user's message arraylist
                    ArrayList userArray = user.getUserMessage();

                    Message messageToBeDeleted = getMessage(userArray, deleteId);

                    //retrieve the message in the array list by matching its ID to the deleteId
                    userArray.remove(messageToBeDeleted);



                    response.redirect("/");

                    halt();

                    return "";
                }
        );



        //messages.mustache has a "post" method that calls for the "/edit-message" endpoint

        Spark.post(
                "/edit-message",
                (request, response) -> {

                    //todo: get user
                    User user = request.session().attribute("user");

                    //todo: get edit ID
                    int editId = Integer.valueOf(request.queryParams("id"));

                    //todo: query edit text
                    String editText = request.queryParams("editText");

                    //create a new message to replace the one being edited

                    ArrayList userArray = user.getUserMessage();

                    Message originalMessage = getMessage(userArray, editId);

                    //   update the item's name
                    originalMessage.setMessageText(editText);

                    //   update the item's ID
                    originalMessage.setMessageId(editId);

                    response.redirect("/");

                    halt();

                    //todo:
                    return null;
                }
        );

    }

    static Message getMessage(ArrayList<Message> userMessages, int id) {
        //   loop over the list of grocery items
        for (Message message : userMessages) {

            //groceryList.forEach(GroceryItem -> GroceryItem.id == new GroceryItem());
            //   check if this item's id match the id of the item being deleted.
            if (id == message.messageId) {
                //   if so, return this item
                return message;
            }
            // it's possible that the list of grocery items is empty or the id provided isn't actually in the list.
            // If that happens we won't reach the return statement in the loop above. Because of this we must add
            // a default return statement that returns null.
        }
        return null;
    }



        // this method adds a set of test users to log in with.
    static void addTestUsers() {
        users.put("Alice", new User("Alice", "cats"));
        users.put("Bob", new User("Bob", "bob"));
        users.put("Charlie", new User("Charlie", "password"));
        users.put("Doug", new User("Doug", "password"));
    }


/*
    static void addTestMessages() {
        messages.add(new Message(0, -1, "Alice", "Hello world!"));
        messages.add(new Message(1, -1, "Bob", "This is another thread!"));
        messages.add(new Message(2, 0, "Charlie", "Cool thread, Alice."));
        messages.add(new Message(3, 2, "Alice", "Thanks"));
        messages.add(new Message(4, -1, "Doug", "Bob is an idiot."));
    }
    */
}
