package is.hi.musicminer;

import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.Session;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        getFBLikes();
        
        Button btn_getSuggestions = (Button) findViewById(R.id.btn_getSuggestions);
    	btn_getSuggestions.setOnClickListener(new View.OnClickListener() {

    		public void onClick(View v) {
    			getSuggestions();
    		}
    	});

    	Button btn_trending = (Button) findViewById(R.id.btn_trending);
    	btn_trending.setOnClickListener(new View.OnClickListener() {

    		public void onClick(View v) {
    			getTrending();
    		}
    	});
        

        
    }

    private void getFBLikes() {
    	GetFbInfoAsync g = new GetFbInfoAsync();
		g.execute("");
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_suggestion, menu);
        return true;
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.menu_logout:
	            logout();
	            return true;
	        case R.id.menu_settings:
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
    
    
    private void logout() {
    	Session.getActiveSession().closeAndClearTokenInformation();
    	Intent nextActivity = new Intent(MainActivity.this, LoginActivity.class);
		this.finish();
    	startActivity(nextActivity);
	}

	private void getSuggestions(){
    	//Hreinsum gamla lista úr minni
		ApplicationData appData = ((ApplicationData)getApplicationContext());
		appData.setUserListBuilder(null);	
		appData.setArtistListBuilder(null);
		
    	Intent nextScreen = new Intent(getApplicationContext(), SuggestionActivity.class);
    	startActivity(nextScreen);	        	
    }
    
    private void getTrending(){
    	Intent nextScreen = new Intent(getApplicationContext(), TrendingActivity.class);
    	startActivity(nextScreen);
    }
    
    public class GetFbInfoAsync extends AsyncTask<String, Void, Void> {

		protected Void doInBackground(String... params) {
			JSONObject userLikes = FBInfoFetcher.getUserLikes();
			String likes = userLikes.toString();
	    	
	    	try {
	    		DataUpdater.updateLikes(likes, MainActivity.this);
	    	} catch( JSONException e) {
	    		e.printStackTrace();
	    	}
			return null;
		}

       		
  }   
}
