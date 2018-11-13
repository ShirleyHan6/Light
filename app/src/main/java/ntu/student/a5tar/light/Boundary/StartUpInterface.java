package ntu.student.a5tar.light.Boundary;

import ntu.student.a5tar.light.Control.EmailLogin;
import ntu.student.a5tar.light.Control.FacebookLogin;
import ntu.student.a5tar.light.Control.LoginInterface;
import ntu.student.a5tar.light.Control.Register;
import ntu.student.a5tar.light.Control.UserManager;
import ntu.student.a5tar.light.Entity.UserList;

/**
 * This class implements the StartUpInterface Boundary
 *
 * @author jeko lonardo
 */

public class StartUpInterface {

    private String email;
    private String fbUsername;
    private String password;
    private String reenterPassword;
    private UserManager userManager;
    private UserList userList;


    public void registerPage(){
        try {
            Register.register(email, password, reenterPassword, userManager);
        }catch(Exception e){
            loginFail();
        }

    }

    public void loginPage(boolean facebookLogin){
        try {
            if (facebookLogin) {
                LoginInterface login = new FacebookLogin(fbUsername, password, userList);
                login.login();
            } else {
                LoginInterface login = new EmailLogin(email, password, userList);
                login.login();
            }
        } catch (Exception e){
            loginFail();
        }
    }

    public void loginFail(){

    }



}
