package ntu.student.a5tar.light.Control;

/**
 * This class implements the Register Class
 *
 * @author jeko lonardo
 */

public class Register {

    public static void register(String email, String password, String reenterPassword, UserManager userManager) throws Exception{
        enterParticulars();
        if (checkMatchPass()) {
            userManager.register(email, password);
        }
        else{
            throw new Exception();
        }
    }

    public static void enterParticulars(){

    }

    public static boolean checkMatchPass(){
        return true;
    }
}
