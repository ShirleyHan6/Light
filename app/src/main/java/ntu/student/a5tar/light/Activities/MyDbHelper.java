package ntu.student.a5tar.light.Activities;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.widget.Toast;


public class MyDbHelper extends SQLiteOpenHelper {

    //post database
    public static final String CreatePost = "create table Post ("
            + "id integer primary key autoincrement,"
            + "userID integer,"
            + "userName text,"
            + "postTitle text,"
            + "postDesc text)";

    private Context mContext;

    public MyDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        try{
             db.execSQL(CreatePost);
        }
        catch(SQLiteException e)
        {
            e.printStackTrace();
        }
        Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
    }
}