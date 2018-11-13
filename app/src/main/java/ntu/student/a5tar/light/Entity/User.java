package ntu.student.a5tar.light.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the User Entity with the attributes email, password, facebookAccount, bookShelf, username, UID, and friend.
 *
 * @author jeko lonardo
 */

public class User {
    private String email;
    private String password;
    private String facebookAccount;
    private BookShelf bookShelf;
    private String username;
    private int UID;
    private User[] friend;

    public User(String email, String password, String facebookAccount, BookShelf bookShelf, String username, int UID, User[] friend) {
        this.email = email;
        this.password = password;
        this.facebookAccount = facebookAccount;
        this.bookShelf = bookShelf;
        this.username = username;
        this.UID = UID;
        this.friend = friend;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFacebookAccount() {
        return facebookAccount;
    }

    public void setFacebookAccount(String facebookAccount) {
        this.facebookAccount = facebookAccount;
    }

    public BookShelf getBookShelf() {
        return bookShelf;
    }

    public void setBookShelf(BookShelf bookShelf) {
        this.bookShelf = bookShelf;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUID() {
        return UID;
    }

    public void setUID(int UID) {
        this.UID = UID;
    }

    public User[] getFriend() {
        return friend;
    }

    public void setFriend(User[] friend) {
        this.friend = friend;
    }

    public boolean passCheck(String oldPass){
        return true;
    }

    public void savePass(String newPass){

    }

    public void updateBookShelf(BookInfo book, BookStatus bookStatus){
        bookShelf.update(book, bookStatus);
    }

    public void updateFriendList(User friend){

    }

    public List<BookInfo> getReadingList(){
        // Tests
        List<BookInfo> test = new ArrayList<>();
        return test;
    }

    public void saveProfile(String name, String username, String[] subject, Language languange){

    }
}
