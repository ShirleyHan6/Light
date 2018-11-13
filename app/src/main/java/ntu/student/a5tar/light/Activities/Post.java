package ntu.student.a5tar.light.Activities;

public class Post {
    private int postID;
    private int userID;
    private String username;
    private String postTitle;
    private String postDescription;

    public Post(int userID, String username, String postTitle, String postDescription){
        this.userID = userID;
        this.username = username;
        this.postTitle = postTitle;
        this.postDescription = postDescription;
    }

    public int getPostID(){ return this.postID; }

    public int setPostID(int postID){ return this.postID = postID;}

    public int getUserID() { return this.userID; }

    public void setUserID(int userID) { this.userID = userID; }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username) { this.username = username; }

    public String getPostTitle(){
        return this.postTitle;
    }

    public void setPostTitle(String postTitle){ this.postTitle  = postTitle; }

    public String getPostDescription(){
        return this.postDescription;
    }

    public void setPostDescription(String postDescription) { this.postDescription = postDescription; }

}
