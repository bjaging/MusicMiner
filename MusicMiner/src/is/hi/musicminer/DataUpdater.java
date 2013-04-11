package is.hi.musicminer;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import android.content.Context;

// Holds methods to update data on the server
public class DataUpdater {
	
	//Use: DataUpdater.updateLikes(userid, likes);
	//Before: userid is a positive integer
	//		  likes is an array of positive integers
	//After: The likes have been updated to the database for userid
	public static void updateLikes(String likes, Context context) throws JSONException{
		
		ArrayList<NameValuePair> data = new ArrayList<NameValuePair>(1);
		data.add(new BasicNameValuePair(
				"usersLikes", 
				likes
		));
		new SendData(context).execute(data);

		}
	
	
}
