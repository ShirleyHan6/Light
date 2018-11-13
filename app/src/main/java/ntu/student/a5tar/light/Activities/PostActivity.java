package ntu.student.a5tar.light.Activities;

import android.content.ContentValues;
import android.database.Cursor;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.text.TextWatcher;
import android.text.Editable;
import android.widget.EditText;
import ntu.student.a5tar.light.R;

public class PostActivity extends AppCompatActivity implements TextWatcher{
    private MyDbHelper dbHelper = new MyDbHelper(this, "Post.db", null, 1);;
    Button submit;
    TextView textCount;
    EditText editText1;
    EditText editText2;
    int userID;
    String username, email;
    DatabaseHelper dbHelperUserID = new DatabaseHelper(this, "UserDatabase.db", null,1);
    SharedPreferences pref;
    public static int id;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        //get user id
        id=getIntent().getIntExtra("post",0);

        //retrieve user info from user database
        SQLiteDatabase db = dbHelperUserID.getWritableDatabase();
        pref = getSharedPreferences("account"+id, MODE_PRIVATE);
        username=pref.getString("username","");
        email=pref.getString("email","");

        editText1 = (EditText) findViewById(R.id.editText1);
        editText2 = (EditText) findViewById(R.id.editText2);

        textCount = (TextView) findViewById(R.id.textCount);
        textCount.setText(Integer.toString(140));
        textCount.setTextColor(Color.GRAY);
        editText2.addTextChangedListener(this);

        dbHelper.getWritableDatabase();
        submit = (Button) findViewById(R.id.submit_btn);

        Cursor c=db.rawQuery("SELECT * FROM User WHERE email='"+email+"'",new String[]{});
        if(c!= null){if(c.moveToFirst()&&c.getCount()>=1){
        userID = c.getInt(c.getColumnIndex("user_id"));
        c.close();}}

        //store user's post into post database

        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("userID", userID);
                values.put("userName", username);
                values.put("postTitle", editText1.getText().toString());
                values.put("postDesc", editText2.getText().toString());
                db.insert("Post", null, values);
                values.clear();
                finish();
            }
        });
        ImageView backforpost = (ImageView)findViewById(R.id.backforpost);
        backforpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //TextWatcherMethod
    public void afterTextChanged(Editable statusText) {
        int count = 140 - statusText.length();
        textCount.setText(Integer.toString(count));
        textCount.setTextColor(Color.GRAY);
        if(count<10)
            textCount.setTextColor(Color.YELLOW);
        if(count<0)
            textCount.setTextColor(Color.RED);
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count){
    }
}

