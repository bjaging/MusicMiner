/*****************************************************
 * 		Suggester									**
 * 													**
 * 		Authors: Aron Skúlason						**
 * 				 Bjarni Grétar Ingólfsson			**
 * 				 Sigurður Óli Árnason				**
 * 				 Tinna Frímann Jökulsdóttir			**
 * 													**
 *****************************************************/

package is.hi.musicminer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;

//Contains information about the user, including 
//major information from facebook.

public class User extends Item {

	public User(String id, int match) {
		this.id = id;
		this.match = match;
		build();
	}

	@Override
	protected void build() {
		GetFbInfoAsync g = new GetFbInfoAsync();
		g.execute(this.id);
		try {
			g.get(1000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}
	
	// The following is to implement Parcelable
	
	public int describeContents() {
        return 0;
    }
	
	// write your object's data to the passed-in Parcel
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(id);
        out.writeInt(match);
        out.writeString(name);
        
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    // get the values back
    private User(Parcel in) {
        id = in.readString();
        match = in.readInt();
        name = in.readString();
    }
    // end of parcelable code
    //////////////////////////////////
    
    //Get the info from facebook
    private class GetFbInfoAsync extends AsyncTask<String, JSONObject, JSONObject> {

		protected JSONObject doInBackground(String... params) {
			JSONObject userInfo = FBInfoFetcher.getUserInfo(params[0]);
			setUserInfo(userInfo);	
			return userInfo;
		}
    }

	public void setUserInfo(JSONObject userInfo) {
		try {
			JSONArray arr = userInfo.getJSONArray("data");
			JSONObject obj = arr.getJSONObject(0);
			this.name = obj.getString("name");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}   

}
