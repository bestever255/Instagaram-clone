package com.example.bestever.instagaram;

import android.content.Intent;
import android.icu.text.TimeZoneFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnKeyListener, View.OnClickListener {

    // btn1 is sign in  , btn2 is sign up
    Button btn1;
    EditText username , password;
    Boolean signUpActive = true;
    TextView mode;
    ImageView backgroundImage;
    RelativeLayout background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mode = (TextView)findViewById(R.id.mode);
        btn1 = (Button)findViewById(R.id.btn1);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        backgroundImage = (ImageView)findViewById(R.id.backgroundimage);
        background = (RelativeLayout)findViewById(R.id.background);


        ChangeText();


        password.setOnKeyListener(this);

        background.setOnClickListener(this);
        backgroundImage.setOnClickListener(this);

        if(ParseUser.getCurrentUser() != null){
            ShowUserList();
        }



    }
    public void SignUp(View view){

        if(username.getText().toString().matches("")  || password.getText().toString().matches("")){
            Toast.makeText(this, "a username and password required", Toast.LENGTH_SHORT).show();
        }else{

            if(btn1.getText().equals("sign up")) {
                ParseUser user = new ParseUser();

                user.setUsername(username.getText().toString());
                user.setPassword(password.getText().toString());

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {

                        if (e == null) {
                            Log.i("SignUp", "Successful");

                            ShowUserList();
                        } else {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }else{
                ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {

                        if(user != null){
                            Log.i("LogInStatus" , "Log in successful");
                            ShowUserList();
                        }else{
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }

        }
    }


    public void ChangeText(){

        mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn1.getText().equals("sign up")) {
                    mode.setText("Sign up");
                    btn1.setText("Log in");

                }else {

                    mode.setText("Log in");
                    btn1.setText("sign up");
                }
            }
        });
    }


    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {

        if(i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN);
        SignUp(view);


        return false;
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.backgroundimage || view.getId() == R.id.background){

            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken() , 0);

        }
    }

    public void ShowUserList(){
        Intent intent = new Intent(getApplicationContext() , UserListActivity.class);
        startActivity(intent);


    }
}

