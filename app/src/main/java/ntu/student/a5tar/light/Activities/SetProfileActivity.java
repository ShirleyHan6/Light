package ntu.student.a5tar.light.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import ntu.student.a5tar.light.R;

public class SetProfileActivity extends AppCompatActivity {

    EditText name, des;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_profile);
        name=findViewById(R.id.nickname);
        des=findViewById(R.id.description);

        TextView next = (TextView)findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get user id
                int id = getIntent().getIntExtra("create", 0);

                //store user profile into user database
                SharedPreferences.Editor editor = getSharedPreferences("account" + id, MODE_PRIVATE).edit();
                editor.putString("username", name.getText().toString());
                editor.putString("description", des.getText().toString());
                editor.apply();

                //go to choose interested subjects
                Intent toChooseSubject = new Intent(SetProfileActivity.this, ChooseSubjectsActivity.class);
                toChooseSubject.putExtra("profile",id);
                startActivity(toChooseSubject);
                finish();
            }
        });
    }
}
