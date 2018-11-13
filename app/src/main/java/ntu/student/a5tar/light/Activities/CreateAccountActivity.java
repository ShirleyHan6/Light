package ntu.student.a5tar.light.Activities;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ntu.student.a5tar.light.R;

public class CreateAccountActivity extends AppCompatActivity {
    DatabaseHelper dbHelper=new DatabaseHelper(this, "UserDatabase.db", null,1);
    EditText email, password, repassword;
    TextView submit;
    int id;
    boolean check=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        email = (EditText) findViewById(R.id.emailRegInput);
        password = (EditText) findViewById(R.id.passwordRegInput);
        repassword = (EditText) findViewById(R.id.reenterPassword);
        submit = (TextView)findViewById(R.id.submitButton);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(CreateAccountActivity.this);
                mDialog.setMessage("Please waiting...");
                mDialog.show();

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                Cursor cursor = db.query("User", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {

                    // check whether the email has been registered
                    do {
                        String email_db = cursor.getString(cursor.getColumnIndex("email"));
                        if (email.getText().toString().trim().equals(email_db)) {
                            mDialog.dismiss();
                            Toast.makeText(CreateAccountActivity.this, "Email has already registered !", Toast.LENGTH_SHORT).show();
                            check = false;
                            cursor.moveToFirst();
                        }
                    } while (cursor.moveToNext());
                }
                cursor.close();
                if (check) {
                    mDialog.dismiss();

                    //check whether password and reentered password match
                    while (!password.getText().toString().equals(repassword.getText().toString())){
                        Toast.makeText(CreateAccountActivity.this, "Two input password must be consistent !", Toast.LENGTH_SHORT).show();
                    }

                    //sign up successfully and add user info to database
                    Toast.makeText(CreateAccountActivity.this, "Signed up successfully !", Toast.LENGTH_SHORT).show();

                    values.put("email", email.getText().toString());
                    values.put("password",password.getText().toString());
                    db.insert("User", null, values);
                    values.clear();

                    Cursor c=db.rawQuery("SELECT * FROM User WHERE email='"+email.getText().toString()+"'",new String[]{});
                    if(c.moveToFirst()&&c.getCount()>=1) {
                        id = c.getInt(c.getColumnIndex("user_id"));
                    }

                    SharedPreferences.Editor editor=getSharedPreferences("account"+id, MODE_PRIVATE).edit();
                    editor.putString("email",email.getText().toString());
                    editor.putString("password",password.getText().toString());
                    editor.putInt("id",id);
                    editor.apply();

                    // go to set profile
                    Intent toSetProfile = new Intent(CreateAccountActivity.this, SetProfileActivity.class);
                    toSetProfile.putExtra("create",id);
                    startActivity(toSetProfile);
                    finish();

                }
            }
        });
    }
}

