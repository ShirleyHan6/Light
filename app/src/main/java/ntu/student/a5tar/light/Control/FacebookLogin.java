package ntu.student.a5tar.light.Control;

import ntu.student.a5tar.light.Entity.UserList;

/**
 * This class implements the FacebookLogin Class implementing LoginInterface interface with attributes fbUsername, password and userList.
 *
 * @author jeko lonardo
 */

public class FacebookLogin implements LoginInterface {
    private String fbUsername;
    private String password;
    private UserList userList;

    public FacebookLogin(String fbUsername, String password, UserList userList) {
        this.fbUsername = fbUsername;
        this.password = password;
        this.userList = userList;
    }

    public void login() throws Exception{

        facebookLogin();
        if (userList.fbCheck(fbUsername, password)){

        }
        else{
            throw new Exception();
        }
    }

    public void facebookLogin(){

    }

}
