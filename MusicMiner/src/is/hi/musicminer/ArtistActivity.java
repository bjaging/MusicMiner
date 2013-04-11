package is.hi.musicminer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

// Show info about one artist
public class ArtistActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);
        
        Intent i = getIntent();
        Artist artist = (Artist) i.getParcelableExtra("artist");
        
        TextView name = (TextView) findViewById(R.id.name);
        name.setText(artist.getName());
        
        TextView about = (TextView) findViewById(R.id.about);
        about.setText(artist.getWebsite());
        
        GetFbPic g = new GetFbPic();
		g.execute(artist.getPic());
        
        ApplicationData appData = ((ApplicationData)getApplicationContext());
        ArtistListBuilder abuilder = appData.getArtistListBuilder(); 	
        System.out.println("Artists: "+abuilder.artists.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_artist, menu);
        return true;
    }
    

    public void setPicture(String photo_url_str){
    	Bitmap bitmap_pic = null;
    	System.out.println("PHOTO: "+photo_url_str);
		try {
			URL newurl = new URL(photo_url_str);
			bitmap_pic = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	ImageView profile_photo = (ImageView) findViewById(R.id.imgview_artist_pic);
    	profile_photo.setImageBitmap(bitmap_pic);
    }
    
    public class GetFbPic extends AsyncTask<String, Bitmap, Bitmap> {

		protected Bitmap doInBackground(String... params) {
			Bitmap bitmap_pic = null;
			System.out.println("IMG: "+params[0]);
			try {
				URL newurl = new URL(params[0]);
				System.out.println("IMG: "+params[0]);
				bitmap_pic = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    	
	  		return bitmap_pic;
		}
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			ImageView profile_photo = (ImageView) findViewById(R.id.imgview_artist_pic);
	    	profile_photo.setImageBitmap(result);
		}

       		
  }  
}
