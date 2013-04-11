package is.hi.musicminer;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;

// Holds methods to get information from facebook
public class FBInfoFetcher {
	
	public static JSONObject getUserLikes(){
		String fqlQuery1 = "SELECT page_id, created_time  FROM page_fan WHERE type=\"Musician/band\" AND uid = me() ";
		JSONObject response1 = executeFqlQuery(fqlQuery1);
		response1 = prepareJSON(response1);
		
		String fqlQuery2 = "SELECT uid FROM user WHERE uid=me()";
		JSONObject response2 = executeFqlQuery(fqlQuery2);

		JSONObject result = new JSONObject();
		try {
			result.put("user",response2.get("data"));
			result.put("likes",response1.get("data"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static JSONObject getArtistInfo(String pid){
	 	String fqlQuery1 = "SELECT name, about, fan_count, website, page_url, pic_large FROM page WHERE page_id="+pid;
	 	JSONObject response=null;
		response = executeFqlQuery(fqlQuery1);
	 	return response;
	}
	
	public static JSONObject getUserInfo(String uid){
	 	String fqlQuery1 = "SELECT name FROM user WHERE uid="+uid;
	 	JSONObject response=null;
		response = executeFqlQuery(fqlQuery1);
	 	return response;
	}
	
	
	private static JSONObject executeFqlQuery(String fqlQuery){
		Bundle params = new Bundle();
	    params.putString("q", fqlQuery);
	    Session session = Session.getActiveSession();
	    Request request = new Request(session,"/fql",params,HttpMethod.GET); 
	    Response response = Request.executeAndWait(request);
        JSONObject jsonObject = response.getGraphObject().getInnerJSONObject();
	    return jsonObject;

	}
	
	private static JSONObject prepareJSON(JSONObject json){

        try {
        	Iterator<?> keys1 = json.keys();
			while( keys1.hasNext() ){
				String key = (String)keys1.next();
				JSONArray arr = json.getJSONArray(key);
				for(int i=0; i<arr.length(); i++){	
					JSONObject obj = arr.getJSONObject(i);
					obj.put("page_id", obj.get("page_id").toString());
					arr.put(i, obj);
				}
				json.put("data", arr);
			}
        } catch (JSONException e) {
        	e.printStackTrace();
		}
        return json;
	}
}
