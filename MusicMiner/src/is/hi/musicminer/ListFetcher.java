package is.hi.musicminer;


import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;

public class ListFetcher {
	
	//Use: SparseIntArray[] info = getSuggestions(userid);
	//Before: userid is a positive integer
	//After: info[0] contains (artistid -> match) values
	//		 info[1] contains (userid -> match) values
	public static void getSuggestions(String userid, Context context) {
		getData("getSuggestions", userid, context);
	}
	
	public static void getArtistsFromUser(String userid, Context context){
		getData("getArtistsFromUser", userid, context);
	}
	
	public static void getTrending(String userid, Context context){
		getData("getTrending", userid, context);
	}
	
	private static JSONObject getData(String type, String value, Context context) {
		//TODO: sækja suggestions á server og henda í sparseintarray
		// Passa að höndla error niðurstöðu
		ArrayList<NameValuePair> data = new ArrayList<NameValuePair>(1);
		data.add(new BasicNameValuePair(
				type, 
				value
		));
		JSONObject result = null;
		if (type.equals("getSuggestions")){
			new GetDataSuggestions(context).execute(data);
		}
		if (type.equals("getArtistsFromUser")){
			new GetDataArtistsFromUser(context).execute(data);
		}
		if (type.equals("getTrending")){
			new GetDataTrending(context).execute(data);
		}
	
		return result;
	}
}
