package ntu.student.a5tar.light.Activities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {
    //user database
    public static final String CREATE_USER = "create table User ("
            + "user_id integer primary key autoincrement, "
            + "email text, "
            + "password text, "
            + "facebookAccount text, "
            + "username text)";

    //book database
    public static final String CREATE_BOOK ="create table Book ("
            + "book_id text, "
            + "user_id integer, "
            //+ "subject text, "
            //+ "download integer,"
            + "primary key (user_id, book_id))";

    private Context mContext;

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_BOOK);
        //Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
    }
}