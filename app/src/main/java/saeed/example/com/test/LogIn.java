package saeed.example.com.test;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import javax.security.auth.callback.Callback;

public class LogIn extends AppCompatActivity {

    private EditText edtEnterYourUserNameLogIn, edtEnterYourPasswordLogIn;
    private Button btnLogIn;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_log_in );

        setTitle( "LogIn" );

        edtEnterYourPasswordLogIn = findViewById( R.id.edtEnterYourPasswordLogIn );
        edtEnterYourUserNameLogIn = findViewById( R.id.edtEnterYourUserNamelogIn );

        btnLogIn = findViewById( R.id.btnLogIn );


btnLogIn.setOnClickListener( new View.OnClickListener() {
    @Override
    public void onClick(View v) {




        ParseUser.logInInBackground( edtEnterYourUserNameLogIn.getText().toString(), edtEnterYourPasswordLogIn.getText().toString(), new LogInCallback() {
         @Override
         public void done(ParseUser user, ParseException e) {
             if(e==null)
             {
                 if(user!=null)
                 {
                     Toast.makeText( LogIn.this, user.getUsername()+"logged in successfully", Toast.LENGTH_LONG ).show();

                     Intent intent = new Intent( LogIn.this, UsersTab.class );
                     startActivity(intent);
                 }
                 else
                 {
                     Toast.makeText( LogIn.this, user.getUsername()+"is not a current user", Toast.LENGTH_LONG ).show();
                 }
             }
         }
     } );
    }
} );







    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate( R.menu.login_menu, menu );
        return true;

    }



    public void imageViewIsTapped(View v)
    {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE  );
        inputMethodManager.hideSoftInputFromWindow( getCurrentFocus().getWindowToken(), 0 );

    }
}
