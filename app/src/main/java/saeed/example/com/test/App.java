package saeed.example.com.test;

import android.app.Application;
import android.widget.SimpleAdapter;

import com.parse.Parse;

import java.util.ArrayList;
import java.util.HashMap;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("drol5QUfI1arMfWwkMcqm6ZRa9sH8w31Q1Nyiyvf")
                // if defined
                .clientKey("HixzaJvFxfv28lHlzfMKVtgedu9jgACzsv1uT5x1")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}


//   for (ParseObject tweetObject : objects) {
//           HashMap<String, String> userTweet = new HashMap<>();
//
//        userTweet.put( "tweetUserName", tweetObject.getString( "user" ) );
//        userTweet.put( "tweetValue", tweetObject.getString( "tweet" ) );
//
//        tweetList.add( userTweet );
//
//        ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery( "MyTweets" );
//        parseQuery.whereContainedIn( "user", ParseUser.getCurrentUser().getList( "fanOf" ) );
//final ArrayList<HashMap<String, String>> tweetList = new ArrayList<>(  );
//    final SimpleAdapter adapter = new SimpleAdapter( SendTwitt.this, tweetList, android.R.layout.simple_list_item_2, new String[] {"tweetUserName","tweetValue" }, new int[] {android.R.id.text1, android.R.id.text2 }  );
