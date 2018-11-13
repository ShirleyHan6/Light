package ntu.student.a5tar.light.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.List;

import ntu.student.a5tar.light.Activities.MyDbHelper;
import ntu.student.a5tar.light.Activities.Post;
import ntu.student.a5tar.light.Activities.PostActivity;
import ntu.student.a5tar.light.Activities.PostAdapter;
import ntu.student.a5tar.light.R;

public class ThoughtFragment extends Fragment{
    private MyDbHelper dbHelper;
    private PostAdapter adapter;
    private List<Post> postList = new ArrayList<>();
    private Post newPost;
    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView recyclerView;
    public static int id;

    //get user id
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getArguments();
        if(bundle!=null){
            id=bundle.getInt("fragment");
        }
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.fragment_thought, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        dbHelper = new MyDbHelper(getActivity(), "Post.db", null, 1);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM POST order by id desc", null);

        //retrieve posts from post database
        if(cursor!=null){if(cursor.moveToFirst()){
            do{
                int userID = cursor.getInt(cursor.getColumnIndex("userID"));
                String userName = cursor.getString(cursor.getColumnIndex("userName"));
                String postTitle = cursor.getString(cursor.getColumnIndex("postTitle"));
                String postDesc = cursor.getString(cursor.getColumnIndex("postDesc"));
                int postID = cursor.getInt(cursor.getColumnIndex("id"));
                newPost = new Post(userID, userName, postTitle, postDesc);
                newPost.setPostID(postID);
                postList.add(newPost);
                Log.d("MainActivity", "userName is " + userName);
                Log.d("MainActivity", "postTitle is " + postTitle);
                Log.d("MainActivity", "postDesc is " + postDesc);
            }while(cursor.moveToNext());
            cursor.close();
        }}
        adapter = new PostAdapter(postList);
        adapter.setID(id);
        recyclerView.setAdapter(adapter);

        //refresh Thoughts
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeColors(Color.GREEN);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshPosts();
            }
        });
        dbHelper = new MyDbHelper(getActivity(), "Post.db", null, 1);
        ImageView plussign = (ImageView)view.findViewById(R.id.plussign);
        plussign.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toPost = new Intent(getActivity().getApplication(), PostActivity.class);
                toPost.putExtra("post",id);
                startActivity(toPost);
            }
        });
    }

    private void refreshPosts(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(100);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        Cursor cursor = db.rawQuery("SELECT * FROM POST order by id desc", null);
                        if(cursor!=null){if(cursor.moveToFirst()){
                            postList.clear();
                            do{
                                int userID = cursor.getInt(cursor.getColumnIndex("userID"));
                                String userName = cursor.getString(cursor.getColumnIndex("userName"));
                                String postTitle = cursor.getString(cursor.getColumnIndex("postTitle"));
                                String postDesc = cursor.getString(cursor.getColumnIndex("postDesc"));
                                int postID = cursor.getInt(cursor.getColumnIndex("id"));
                                newPost = new Post(userID, userName, postTitle, postDesc);
                                newPost.setPostID(postID);
                                postList.add(newPost);
                                Log.d("MainActivity", "userName is " + userName);
                                Log.d("MainActivity", "postTitle is " + postTitle);
                                Log.d("MainActivity", "postDesc is " + postDesc);
                            }while(cursor.moveToNext());
                            cursor.close();
                        }}
                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
}
