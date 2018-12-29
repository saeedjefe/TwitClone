package saeed.example.com.test;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SendTwitt extends AppCompatActivity implements View.OnClickListener {

    EditText edtTweet;
    Button btnTweets;
    ListView viewTweetsListView;
    Button btnShowTweets;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_send_twitt );


        edtTweet = findViewById( R.id.edtTweet );
        btnTweets = findViewById( R.id.btnTweet );
        viewTweetsListView = findViewById( R.id.viewTweetsListView );
        btnShowTweets = findViewById( R.id.btnShowTweets );

        btnShowTweets.setOnClickListener( this );


        btnTweets.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ParseObject parseObject = new ParseObject( "MyTweets" );

               parseObject.put( "tweet", edtTweet.getText().toString() );
               parseObject.put("user", ParseUser.getCurrentUser().getUsername());

               parseObject.saveInBackground( new SaveCallback() {
                   @Override
                   public void done(ParseException e) {

                       if(e==null)
                       {
                           Toast.makeText( SendTwitt.this, "changes are saves", Toast.LENGTH_SHORT ).show();
                       }

                   }
               } );





            }
        } );


        }


    @Override
    public void onClick(View v) {



        // create your array list for the view you want to see
        final ArrayList <HashMap<String, String>> twittList = new ArrayList<>(  );
        //create a different type of array adapter called simple adapter
        final SimpleAdapter adapter = new SimpleAdapter(SendTwitt.this, twittList, android.R.layout.simple_list_item_2, new String[]{"tweetUserName", "tweetValue"}, new int []{android.R.id.text1, android.R.id.text2}  );
        try {
            ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery( "MyTweets" );
            parseQuery.whereContainedIn("user", ParseUser.getCurrentUser().getList( "Fanof" ));
            parseQuery.findInBackground( new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if(objects.size()>0 && e ==null)
                    {
                        for (ParseObject tweetObjects: objects)
                        {
                            HashMap<String, String> userTweet = new HashMap<>(  );
                            userTweet.put( "tweetUserName", tweetObjects.getString( "user" ) );
                            userTweet.put( "tweetValue", tweetObjects.getString( "tweet" ) );

                            twittList.add( userTweet );
                        }

                        viewTweetsListView.setAdapter( adapter );
                    }
                }
            } );


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}






















