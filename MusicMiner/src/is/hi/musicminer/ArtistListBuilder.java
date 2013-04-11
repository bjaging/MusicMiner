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


import java.util.Iterator;
import java.util.NavigableSet;
import java.util.TreeMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

//Handles building a list of artists.

public class ArtistListBuilder extends ListBuilder {
	
	// Contains Artist objects for all artists in the list.
	protected Artist[] artists;
	
	public ArtistListBuilder(TreeMap<String, Integer> list, Context context) {
		this.list = list;
		this.context = context;
		this.artists = new Artist[getLength()];
		NavigableSet<String> set = list.navigableKeySet();
		Iterator<String> i = set.iterator();
		int c = 0;
		while(i.hasNext()){
			String aid = i.next();
			int match = list.get(aid);
			artists[c] = buildItem(aid, match);
			c++;
		}
		sort(artists);		
	}
	
	@Override
	protected  Artist buildItem(String aid, int match) {
		//TODO: þetta þarf helst að gerast asynchronously
		return new Artist(aid, match);
	}
	
	@Override
	public  LinearLayout getListView() {
		LinearLayout layout = new LinearLayout(context);
		layout.setOrientation(LinearLayout.VERTICAL);
		for(int i = 0; i<getLength(); i++) {
			layout.addView(getItemView(artists[i], layout));
		}
		return layout;
	}
	
	@Override
	protected LinearLayout getItemView(Item artist, LinearLayout parent) {
		artist = (Artist) artist;
		
		final LayoutInflater inflater = LayoutInflater.from((Activity) context);
		LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.artist, parent, false);
		
		TextView name = (TextView) layout.findViewById(R.id.name);
		name.setText(artist.getName());
		TextView match = (TextView) layout.findViewById(R.id.match);
		System.out.println("MATCH: "+artist.getMatch());
		match.setText(""+artist.getMatch());
		
		
		// Open details about the artist
		match.setTag(artist);
		match.setOnClickListener(new View.OnClickListener() {

    		public void onClick(View v) {
    			openItem((Artist) v.getTag());
    		}
    	});
		
		return layout;
	}
	
	private void openItem(Artist artist) {
		Intent nextScreen = new Intent(context, ArtistActivity.class);
		nextScreen.putExtra("artist", artist);
    	context.startActivity(nextScreen);
	}

}
