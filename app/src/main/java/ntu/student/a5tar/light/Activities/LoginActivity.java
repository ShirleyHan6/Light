package ntu.student.a5tar.light.Activities;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import ntu.student.a5tar.light.R;

public class LoginActivity extends AppCompatActivity {

    String txtEmail,image,photo,email;
    int id;
    LoginButton loginButton;
    ProgressDialog mDialog;
    CallbackManager callbackManager;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private CheckBox rememberPass;
    final Context context = this;
    EditText emailEdit,passwordEdit;
    DatabaseHelper dbHelper=new DatabaseHelper(this, "UserDatabase.db", null,1);
    boolean check=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        //remember password
        pref=PreferenceManager.getDefaultSharedPreferences(this);
        passwordEdit=(EditText) findViewById(R.id.passwordInput);
        emailEdit=(EditText) findViewById(R.id.emailInput);
        rememberPass=(CheckBox) findViewById(R.id.remember);
        boolean isRemember=pref.getBoolean("remember_password",false);
        if(isRemember){
            String email=pref.getString("email","");
            String password=pref.getString("password","");
            emailEdit.setText(email);
            passwordEdit.setText(password);
            rememberPass.setChecked(true);
        }


        TextView forgetPassword = (TextView)findViewById(R.id.forgetPasswordButton);
        forgetPassword.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg) {
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.activity_forget_password_prompt, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setView(promptsView);
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                    Snackbar.make(findViewById(R.id.myCoordinatorLayout), R.string.emailsent,
                                            Snackbar.LENGTH_SHORT)
                                            .show();
                                }
                                })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel(); }
                                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        //login with an email account
        TextView login = (TextView)findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog=new ProgressDialog(LoginActivity.this);
                mDialog.setMessage("Please waiting...");
                mDialog.show();

                SQLiteDatabase db=dbHelper.getWritableDatabase();
                Cursor cursor=db.query("User",null,null,null,null,null,null);
                String email=emailEdit.getText().toString();
                String password=passwordEdit.getText().toString();

                //search user in the database
                if (cursor.moveToFirst()) {
                    do{
                        int id=cursor.getInt(cursor.getColumnIndex("user_id"));
                        String email_db=cursor.getString(cursor.getColumnIndex("email"));
                        String password_db=cursor.getString(cursor.getColumnIndex("password"));
                        String username=cursor.getString(cursor.getColumnIndex("username"));

                        if (emailEdit.getText().toString().trim().equals(email_db)){
                            mDialog.dismiss();
                            if(passwordEdit.getText().toString().trim().equals(password_db)){
                                editor=pref.edit();
                                if(rememberPass.isChecked()){
                                    editor.putBoolean("remember_password",true);
                                    editor.putString("email", email);
                                    editor.putString("password", password);
                                }else{
                                    editor.clear();
                                }
                                editor.apply();
                                Toast.makeText(LoginActivity.this, "Login in successfully!",
                                        Toast.LENGTH_SHORT).show();
                                check=false;

                                //go to navigation page
                                Intent toNavigation = new Intent(LoginActivity.this, NavigationActivity.class);
                                toNavigation.putExtra("navigation",id);
                                toNavigation.putExtra("activity","log");
                                startActivity(toNavigation);
                                finish();
                                break;
                            }else{

                                //check password
                                Toast.makeText(LoginActivity.this, "Wrong Password!",
                                        Toast.LENGTH_SHORT).show();
                                check=false;
                                break;
                            }
                        }
                    }while(cursor.moveToNext());
                }
                cursor.close();

                //check whether user exists in the database
                if(check) {
                    mDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "User does not exist!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //create a new account
        TextView createAccount = (TextView)findViewById(R.id.createAccountButton);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toCreateAccount = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(toCreateAccount);
                finish();
            }
        });

        //login with facebook account
        loginButton = (LoginButton) findViewById(R.id.fb_login_bn);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));

        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                mDialog = new ProgressDialog(LoginActivity.this);
                mDialog.setMessage("Retrieving data...");
                mDialog.show();

                String accessToken = loginResult.getAccessToken().getToken();
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        mDialog.dismiss();
                        Log.d("response", response.toString());
                        email=getData(object);
                        photo=getImg(object);

                    }
                });

                //if user has already login previously, load profile image directly
                if(AccessToken.getCurrentAccessToken()!=null) {
                    email = AccessToken.getCurrentAccessToken().getUserId();
                    try {
                        URL profile_picture = new URL("https://graph.facebook.com/" + email +
                                "/picture?width=250&height=250");
                        photo = profile_picture.toString();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }

                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id,email");
                    request.setParameters(parameters);
                    request.executeAsync();

                    //store the user info to database
                    Toast.makeText(LoginActivity.this, "Login in successfully!",
                            Toast.LENGTH_SHORT).show();
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();

                    Cursor cursor = db.query("User", null, null, null, null, null, null);
                    values.put("email", email);
                    values.put("password", "");
                    db.insert("User", null, values);
                    values.clear();

                    Cursor c = db.rawQuery("SELECT * FROM User WHERE email='" + email + "'", new String[]{});
                    if(c.moveToFirst()&&c.getCount()>=1) {
                        id = c.getInt(c.getColumnIndex("user_id"));
                    }
                    SharedPreferences.Editor editor = getSharedPreferences("account" + id, MODE_PRIVATE).edit();
                    editor.putString("email", email);
                    editor.putString("password", "");
                    editor.putString("image", photo);
                    editor.putInt("id", id);
                    editor.apply();

                    // go to set profile
                    Intent toHome = new Intent(LoginActivity.this, SetProfileActivity.class);
                    toHome.putExtra("create", id);
                    startActivity(toHome);
                    finish();
            }
            @Override
            public void onCancel() {
            }
            @Override
            public void onError(FacebookException error) {
            }
        });
    }

    private String getData(JSONObject object){
        try{
            txtEmail=object.getString("email");
            return txtEmail;
        }catch (JSONException e){
            e.printStackTrace();
        }
        return txtEmail;
    }

    //load profile image from facebook
    private String getImg(JSONObject object){
        try{
            URL profile_picture=new URL("https://graph.facebook.com/"+object.getString("id")+
                    "/picture?width=250&height=250");
            image=profile_picture.toString();
        } catch(MalformedURLException e){
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
