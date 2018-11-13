package ntu.student.a5tar.light.Activities;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import java.util.List;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import ntu.student.a5tar.light.R;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private Context mContext;
    private List<Post> mPostList;
    private Post newPost;
    int count = 0;
    int userID;
    String email;
    DatabaseHelper dbHelperUserID;
    MyDbHelper dbHelper;
    SharedPreferences pref;
    public static int id;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView postTitle;
        TextView postDescription;
        TextView postUsername;
        ImageView deleteIcon;
        public ViewHolder(View view){
            super(view);
            cardView = (CardView) view;
            postTitle = (TextView) view.findViewById(R.id.post_title);
            postUsername = (TextView) view.findViewById(R.id.post_username);
            postDescription = (TextView) view.findViewById(R.id.post_desc);
            deleteIcon = (ImageView) view.findViewById(R.id.deleteIcon);
        }
    }
    public PostAdapter(List<Post> PostList){
        mPostList = PostList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType){
        if(mContext == null){
            mContext = parent.getContext();
        }

        dbHelper = new MyDbHelper(mContext, "Post.db", null, 1);
        dbHelperUserID = new DatabaseHelper(mContext, "UserDatabase.db", null,1);
        View view = LayoutInflater.from(mContext).inflate(R.layout.post_row,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){
        newPost = mPostList.get(position);
        holder.postTitle.setText(newPost.getPostTitle());
        holder.postDescription.setText(newPost.getPostDescription());
        holder.postUsername.setText(newPost.getUsername());
        final SQLiteDatabase db = dbHelperUserID.getWritableDatabase();
        final SQLiteDatabase db1 = dbHelper.getWritableDatabase();

        //user can delete his or her own posts

        pref = mContext.getSharedPreferences("account"+id, 0);
        email=pref.getString("email","");
        Cursor c=db.rawQuery("SELECT * FROM User WHERE email='"+email+"'",new String[]{});
        if(c!= null){if(c.moveToFirst()&&c.getCount()>=1){
            userID = c.getInt(c.getColumnIndex("user_id"));
            c.close();}}
        if(userID == mPostList.get(position).getUserID())
        {holder.deleteIcon.setImageResource(R.drawable.delete);}
        String[] colorlist = new String[5];
        colorlist[0] = "#33F0FFFF";
        colorlist[1] = "#33FFFFF0";
        colorlist[2] = "#33E6E6FA";
        colorlist[3] = "#33FFE4E1";
        colorlist[4] = "#33FFDAB9";
        holder.cardView.setBackgroundColor(Color.parseColor(colorlist[count]));
        count = (count + 1) % 5;
        holder.deleteIcon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String whereClause = "id=?";
                String whereArgs[] = {String.valueOf(mPostList.get(position).getPostID())};
                db1.delete("Post", whereClause, whereArgs);
            }
        });
    }
    @Override
    public int getItemCount(){
        return mPostList.size();
    }
    public void setID(int id){
        this.id=id;
    }
}
