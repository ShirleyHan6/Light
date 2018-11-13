package ntu.student.a5tar.light.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import ntu.student.a5tar.light.R;

public class ChooseSubjectsActivity extends AppCompatActivity {
    ArrayList<String> interest=new ArrayList<>();
    Button a,b,c,d,g,h;
    int at = 0;
    int bt = 0;
    int ct = 0;
    int dt = 0;
    int gt = 0;
    int ht = 0;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_subjects);
        int id=getIntent().getIntExtra("profile",0);

        a=(Button) findViewById(R.id.leftst);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                at += 1;
                if(at % 2 == 1) {
                    a.setBackgroundColor(Color.parseColor("#99CCFF"));
                    interest.add(a.getText().toString());
                }
                else{
                    a.setBackgroundColor(Color.parseColor("#FFFAFA"));
                }
            }
        });

        b=(Button) findViewById(R.id.rightst);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt += 1;
                if(bt % 2 == 1) {
                    interest.add(b.getText().toString());
                    b.setBackgroundColor(Color.parseColor("#99CCFF"));
                }
                else{
                    b.setBackgroundColor(Color.parseColor("#FFFAFA"));
                }
            }
        });

        c=(Button) findViewById(R.id.lefta);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ct += 1;
                if(ct % 2 == 1){
                    interest.add(c.getText().toString());
                    c.setBackgroundColor(Color.parseColor("#99CCFF"));
                }
                else{
                    c.setBackgroundColor(Color.parseColor("#FFFAFA"));
                }
            }
        });

        d=(Button) findViewById(R.id.righta);
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dt += 1;
                if(dt % 2 == 1) {
                    interest.add(d.getText().toString());
                    d.setBackgroundColor(Color.parseColor("#99CCFF"));
                }
                else{
                    d.setBackgroundColor(Color.parseColor("#FFFAFA"));
                }
            }
        });


        g=(Button) findViewById(R.id.leftc);
        g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gt += 1;
                if(gt % 2 == 1) {
                    interest.add(g.getText().toString());
                    g.setBackgroundColor(Color.parseColor("#99CCFF"));
                }
                else{
                    g.setBackgroundColor(Color.parseColor("#FFFAFA"));
                }
            }
        });

        h=(Button) findViewById(R.id.rightc);
        h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ht += 1;
                if(ht % 2 == 1) {
                    interest.add(h.getText().toString());
                    h.setBackgroundColor(Color.parseColor("#99CCFF"));
                }
                else{
                    h.setBackgroundColor(Color.parseColor("#FFFAFA"));
                }
            }
        });

        //store user's interested subject list
        TextView login = (TextView)findViewById(R.id.continueButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set<String> interestset = new HashSet<String>(interest);
                SharedPreferences.Editor editor=getSharedPreferences("account"+id, MODE_PRIVATE).edit();
                editor.putStringSet("interest", interestset);
                editor.apply();

                //go to navigation page
                Intent toHomepage = new Intent(ChooseSubjectsActivity.this, NavigationActivity.class);
                toHomepage.putExtra("activity","subject");
                toHomepage.putExtra("login",id);
                startActivity(toHomepage);
                finish();
            }
        });

    }
}
