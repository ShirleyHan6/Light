package ntu.student.a5tar.light.Entity;

import java.util.List;

/**
 * This class implements the UserList Entity with the attribute userList.
 *
 * @author jeko lonardo
 */

public class UserList {
    private List<User> userList;

    public UserList(List<User> userList) {
        this.userList = userList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public boolean emailExist(String email){
        return true;
    }

    public void addUser(String email, String password){

    }

    public User searchUser(String username){
        return userList.get(0);
    }

    public boolean passCheck(String password){
        return true;
    }

    public boolean fbCheck(String username, String password){
        return true;
    }
}
