package saeed.example.com.test;

import android.arch.lifecycle.Lifecycle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;
import android.view.Menu;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.nio.file.SecureDirectoryStream;
import java.util.ArrayList;
import java.util.List;

public class UsersTab extends AppCompatActivity implements AdapterView.OnItemClickListener  {


    String receivedIntentStringExtra;

     ArrayList<String  > arrayList;

    ListView lstView;

    String followedUsers;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_users_tab );

        lstView = findViewById( R.id.lstView );
        arrayList = new ArrayList<>();
        final ArrayAdapter arrayAdapter = new ArrayAdapter( UsersTab.this, android.R.layout.simple_list_item_checked, arrayList );

        //make two choices for the listView
        lstView.setChoiceMode( AbsListView.CHOICE_MODE_MULTIPLE );
        lstView.setOnItemClickListener( this );

        Intent receivedIntent = getIntent();
        receivedIntentStringExtra = receivedIntent.getStringExtra( "username" );

        setTitle( receivedIntentStringExtra );

        try {

            final ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();

            parseQuery.whereNotEqualTo( "username", ParseUser.getCurrentUser().getUsername() );

            parseQuery.findInBackground( new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                    if (e == null&&objects.size()>0) {

                            for (ParseUser parseUser : objects) {

                                arrayList.add( parseUser.getUsername() );
                            }
                            lstView.setAdapter( arrayAdapter );





                            for (String twitterUsers : arrayList) {
                                if(ParseUser.getCurrentUser().getList( "Fanof" ) != null) {
                                    if (ParseUser.getCurrentUser().getList( "Fanof" ).contains( twitterUsers ))
                                    {
                                        followedUsers += twitterUsers + " \t";
                                        lstView.setItemChecked( arrayList.indexOf( twitterUsers ), true );

                                        Toast.makeText( UsersTab.this, ParseUser.getCurrentUser().getUsername() +" is following " + twitterUsers, Toast.LENGTH_SHORT ).show();

                                    }
                                }




                        }
                    } else {
                        Toast.makeText( UsersTab.this, "failed", Toast.LENGTH_LONG ).show();
                    }

                }
            } );


        } catch (Exception e) {
            e.printStackTrace();
        }





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate( R.menu.userstab_menu, menu );
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.logout_item:

                ParseUser.logOutInBackground( new LogOutCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null)
                        {
                            Intent intent = new Intent( UsersTab.this, SignUp.class );
                            startActivity( intent );
                            finish();
                        }
                    }
                } );
                break;

            case R.id.sendTweetItem:

                Intent intent = new Intent( UsersTab.this, SendTwitt.class );
                startActivity( intent );



                break;
        }

        return true;
    }



    @Override
    protected void onResume() {

        Toast.makeText( UsersTab.this, "welcome "+ ParseUser.getCurrentUser().getUsername(), Toast.LENGTH_LONG ).show();
        setTitle( "TwitterClone" );
        super.onResume();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        CheckedTextView checkedTextView = (CheckedTextView) view;

        if(checkedTextView.isChecked())
        {
            Toast.makeText( UsersTab.this,arrayList.get( position )+" is followed", Toast.LENGTH_LONG   ).show();
            ParseUser.getCurrentUser().add( "Fanof", arrayList.get( position ) );
        }
        else
        {
            Toast.makeText( UsersTab.this,arrayList.get( position )+" is unfollowed", Toast.LENGTH_LONG   ).show();
            ParseUser.getCurrentUser().getList( "Fanof").remove( arrayList.get(position) );
            List currentUserFanOfList = ParseUser.getCurrentUser().getList( "Fanof" );
            ParseUser.getCurrentUser().put("Fanof", currentUserFanOfList);



        }

        ParseUser.getCurrentUser().saveInBackground( new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Toast.makeText( UsersTab.this, "changes are saved", Toast.LENGTH_SHORT  ).show();
            }
        } );




    }
}
