/*****************************************************
 * 		MusicMiner									**
 * 													**
 * 		Authors: Aron Sk�lason						**
 * 				 Bjarni Gr�tar Ing�lfsson			**
 * 				 Sigur�ur �li �rnason				**
 * 				 Tinna Fr�mann J�kulsd�ttir			**
 * 													**
 *****************************************************/

package is.hi.musicminer;

import android.app.Application;

//Global data for the application.
//Contains the "builder" for current user's suggested artist-list and user-list.

public class ApplicationData extends Application {
	
	private ArtistListBuilder abuilder; //Current user's artist suggestions if any
	private UserListBuilder ubuilder;  //Current user's most alike users if any

    public ArtistListBuilder getArtistListBuilder()
    {
      return abuilder;
    }

    public void setArtistListBuilder(ArtistListBuilder a)
    {
      abuilder = a;
    }
    
    public UserListBuilder getUserListBuilder()
    {
      return ubuilder;
    }

    public void setUserListBuilder(UserListBuilder u)
    {
      ubuilder = u;
    }
}
