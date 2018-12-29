package saeed.example.com.test;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.ArrayList;


public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private EditText edtEnterYourUserName, edtEnterYourPassword, edtEnterYourEmail;
    private Button btnSignup, btnTurnIntoLogIn;



     ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );



        setTitle( "Sign Up" );


        ParseInstallation.getCurrentInstallation().saveInBackground();


        btnSignup = findViewById( R.id.btnSignUp );
        edtEnterYourUserName = findViewById( R.id.edtEnterYourUserName );
        edtEnterYourPassword = findViewById( R.id.edtEnterYourPassword );

        edtEnterYourPassword.setOnKeyListener( new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(keyCode == KeyEvent.KEYCODE_ENTER&&event.getAction()==KeyEvent.ACTION_DOWN)
                {
                    onClick(btnSignup);

                }
                return false;
            }
        } );

        edtEnterYourEmail = findViewById( R.id.edtEnterYourEmail );

        btnTurnIntoLogIn = findViewById( R.id.btnTurnIntoLogin );


        if(ParseUser.getCurrentUser()!=null)
        {
            Intent intent =new Intent( SignUp.this, UsersTab.class );

            startActivity( intent );
        }



        progressDialog = new ProgressDialog(SignUp.this  );



        btnTurnIntoLogIn.setOnClickListener( SignUp.this );
        btnSignup.setOnClickListener( SignUp.this );




        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate( R.menu.singup_menu, menu );
        return true;

    }
    public void imageViewIsTapped(View v)
    {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE  );
        inputMethodManager.hideSoftInputFromWindow( getCurrentFocus().getWindowToken(), 0 );

    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btnSignUp:

                final ParseUser parseUser = new ParseUser();

                parseUser.setUsername( edtEnterYourUserName.getText().toString() );
                parseUser.setPassword( edtEnterYourPassword.getText().toString() );
                parseUser.setEmail( edtEnterYourEmail.getText().toString() );

                progressDialog.setMessage( "signing up" );
                progressDialog.show();


                parseUser.signUpInBackground( new SignUpCallback() {



                    @Override
                    public void done(ParseException e) {
                        if(e == null)
                        {
                            progressDialog.dismiss();

                            Toast.makeText( SignUp.this, "sucessfully signed up", Toast.LENGTH_LONG ).show();

                            Intent intent  = new Intent( SignUp.this, UsersTab.class );
                            intent.putExtra( "username", parseUser.getUsername() );
                            startActivity( intent );

                        }

                        else
                        {
                            progressDialog.dismiss();
                            Toast.makeText( SignUp.this, "failed", Toast.LENGTH_LONG ).show();


                        }
                    }
                } );

                break;



            case  R.id.btnTurnIntoLogin:

                Intent intent = new Intent( SignUp.this, LogIn.class );
                startActivity( intent );

                break;

        }

    }
}
