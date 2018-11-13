package ntu.student.a5tar.light.Control;

import ntu.student.a5tar.light.Entity.UserList;

/**
 * This class implements the EmailLogin Class implementing LoginInterface interface with attributes email, password, and userList.
 *
 * @author jeko lonardo
 */

public class EmailLogin implements LoginInterface {
    private String email;
    private String password;
    private UserList userList;

    public EmailLogin(String email, String password, UserList userList) {
        this.email = email;
        this.password = password;
        this.userList = userList;
    }

    public void login() throws Exception{
        emailLogin();
        if (userList.emailExist(email)) {
            if (userList.passCheck(password)) {

            } else {
                throw new Exception();
            }
        } else {
            throw new Exception();
        }
    }

    public void emailLogin(){

    }

}
