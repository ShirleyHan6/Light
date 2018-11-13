package ntu.student.a5tar.light.Control;

import java.util.List;

import ntu.student.a5tar.light.Entity.BookInfo;
import ntu.student.a5tar.light.Entity.Language;
import ntu.student.a5tar.light.Entity.User;
import ntu.student.a5tar.light.Entity.UserList;

/**
 * This class implements the UserManager Control.
 *
 * @author jeko lonardo
 */

public class UserManager {
    private UserList userList;

    public void register(String email, String password) throws Exception{
        if (!userList.emailExist(email)) {
            userList.addUser(email, password);
        }else{
            throw new Exception();
        }
    }

    public void changePass(User self){
        String oldPass = "";
        String newPass = "";
        //ask for input
        if (self.passCheck(oldPass)){
            self.savePass(newPass);
        }else{
            passChangeFail();
        }

    }


    public void forgetPass(User self){
        String newPass = "";
        sendResetEmail();
        self.savePass(newPass);
    }

    public void passChangeFail(){

    }

    public void sendResetEmail(){

    }

    public void addFriend(String username, User self){
        User friend = userList.searchUser(username);
        if (friend != null){
            self.updateFriendList(friend);
        } else {
            addFriendFail();
        }
    }

    public List<BookInfo> seeFriendReadingList(User friend){
        List<BookInfo> readList = friend.getReadingList();
        if (readList.size() > 0){
            return readList;
        }
        else{
            noReadList();
            return null;
        }
    }

    public void setUserProfile(String name, String username, String[] subject, Language language, User self){
        self.saveProfile(name, username, subject, language);
    }

    public void updateUserProfile(String name, String username, String[] subject, Language language, User self){
        self.saveProfile(name, username, subject, language);
    }

    public void addFriendFail(){

    }

    public void noReadList(){

    }

}
