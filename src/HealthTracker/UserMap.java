package HealthTracker;

import java.io.*;
import java.util.HashMap;

/************************************************************************************************
 * Data structure to hold the Users. Saves a HashMap of users as an .ser
 * This class would include a bunch of checks and protections for threaded programming if
 * the app became network connected- or we'd use a real database
 ***********************************************************************************************/
/***************************************************************************
 * Run the main if you need to create a new HashMap of Users-
 * Needs to be done if User Class has changed.
 ***************************************************************************/
public class UserMap implements Serializable {

    //HashMap with the username as the key
    private HashMap<String, User> data = new HashMap<String, User>();

    UserMap() {

        /************************************************************************
         * Loads data.ser
         *
         ******************************************************************/
        try {
            FileInputStream fis = new FileInputStream("data.ser");
            ObjectInputStream in = new ObjectInputStream(fis);
            data = (HashMap)in.readObject();
            in.close();
            fis.close();
            System.out.println("Data loaded");
        } catch (FileNotFoundException e) {
            System.out.println(" ERROR: Cannot load a file, data.ser.");
        } catch (IOException e) {
            System.out.println(" ERROR: Cannot read from file, operation failed");
        } catch (ClassNotFoundException e) {
            System.out.println(" Cast to HashMap<String, User> failed");
        }


    }



    public boolean containsUsername(String username){
        if (data.containsKey(username)){
            return true;
        }
        else {
            return false;
        }
    }

    public void saveData() {
        try {
            FileOutputStream fos = new FileOutputStream("data.ser");
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(data);
            out.close();
        }
        catch(Exception e) {
            System.out.println("Error saving");
        }
    }

    public User getUser(String username, String password) throws usernameNotFoundException, wrongPasswordException {
        try {
            User user = data.get(username);
            if(user.getPassword().equals(password)){
                return user;
            }
            else{
                throw new wrongPasswordException();
            }
        }
        catch(wrongPasswordException e){
            throw e;
        }
        catch (Exception e){
            throw new usernameNotFoundException();
        }
    }

    public void updateUser(User user){
        data.remove(user.getUsername());
        data.put(user.getUsername(), user);
    }


    public void removeUser(String username){
        data.remove(username);
    }

    public void add(User u){
        data.put(u.getUsername(), u);
    }


    public static void main(String[] args) {
        User user1 = new User("Steve", "12345");
        User user2 = new User("Johnny1", "1234567");
        HashMap<String, User> newData = new HashMap<String, User>();

        newData.put(user1.getUsername(), user1);
        newData.put(user2.getUsername(), user2);

        try
        {
            FileOutputStream fos =
                    new FileOutputStream("data.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(newData);
            oos.close();
            fos.close();
            System.out.printf("Serialized HashMap data is saved in data.ser");
        }catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
}