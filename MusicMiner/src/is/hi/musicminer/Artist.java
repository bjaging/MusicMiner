/*****************************************************
 * 		MusicMiner									**
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

/**
 *  Contains information about the artist, including major information from facebook.
 */
public class Artist extends Item{

	/**The artists profile picture*/
	private String pic;
	/** The number of likes that the artist has gotten on facebook*/
	private String likes;
	/** The artist's about clause*/
	private String about;
	/** The artist's website url*/
	private String website;
	/** The facebook page url*/
	private String page_url;
	
	

	/**Use: Artist a = new Artist(id, match)
	 * Before: id is the artist's page ID. Match is the match score for the user
	 * After: a contains all major information about the user with user ID = userid
	 */	
	public Artist(String id, int match) {
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
	
	/** 
	 * @return This artist's profile picture
	 */
	public String getPic() {
		return this.pic;
	}
	/** 
	 * @return This artist's number of likes
	 */
	public String getLikes() {
		return this.likes;
	}
	/** 
	 * @return This artist's about clause
	 */
	public String getAbout() {
		return this.about;
	}
	/** 
	 * @return This artist's website url
	 */
	public String getWebsite() {
		return this.website;
	}
	/** 
	 * @return This artist's facebook page url
	 */
	public String getPageUrl() {
		return this.page_url;
	}

	//************************The following is to implement Parcelable ************** //
	
	public int describeContents() {
        return 0;
    }
	
	// write your object's data to the passed-in Parcel
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(id);
        out.writeInt(match);
        out.writeString(likes);
        out.writeString(name);
        out.writeString(pic);
        out.writeString(about);
        out.writeString(website);
        
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Artist> CREATOR = new Parcelable.Creator<Artist>() {
        public Artist createFromParcel(Parcel in) {
            return new Artist(in);
        }

        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };

    // get the values back
    private Artist(Parcel in) {
        id = in.readString();
        match = in.readInt();
        likes = in.readString();
        name = in.readString();
        pic = in.readString();
        about = in.readString();
        website = in.readString();
    }
   
    //***************************End of parcelable code********************************//
   
    
    //Get the info from facebook
    private class GetFbInfoAsync extends AsyncTask<String, JSONObject, JSONObject> {

		protected JSONObject doInBackground(String... params) {
			JSONObject artistInfo = FBInfoFetcher.getArtistInfo(params[0]);
			setArtistInfo(artistInfo);	
			return artistInfo;
		}
			
    }

	public void setArtistInfo(JSONObject artistInfo) {
		try {
			JSONArray arr = artistInfo.getJSONArray("data");
			JSONObject obj = arr.getJSONObject(0);

			this.about = obj.getString("about");
			this.likes = obj.getString("fan_count");
			this.website = obj.getString("website");
			this.page_url = obj.getString("page_url");
			this.name = obj.getString("name");
			this.pic = obj.getString("pic_large");
			this.about = obj.getString("about");
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}   
}
	