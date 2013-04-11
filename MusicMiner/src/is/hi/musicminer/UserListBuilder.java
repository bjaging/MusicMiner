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

//Handles building a list of users.

public class UserListBuilder extends ListBuilder {
	
	
	// Contains User objects for all users in the list.
	private User[] users;
	
	public UserListBuilder(TreeMap<String, Integer> list, Context context) {
				
		
		this.list = list;
		this.context = context;
		this.users = new User[getLength()];
		NavigableSet<String> set = list.navigableKeySet();
		Iterator<String> i = set.iterator();
		int c = 0;
		while(i.hasNext()){
			String uid = i.next();
			int match = list.get(uid);
			users[c] = buildItem(uid, match);
			c++;
				
		}
		
		sort(users);
	}
	
	@Override
	protected User buildItem(String id, int match) {
		// þetta þarf helst að gerast asynchronously
		
		return new User(id,match);
	}

	@Override
	public  LinearLayout getListView() {
		LinearLayout layout = new LinearLayout(context);
		layout.setOrientation(LinearLayout.VERTICAL);
		for(int i = 0; i<getLength(); i++) {
			layout.addView(getItemView(users[i], layout));
		}
		return layout;
	}
	
	@Override
	protected LinearLayout getItemView(Item user, LinearLayout parent) {
		user = (User) user;
		
		final LayoutInflater inflater = LayoutInflater.from((Activity) context);
		LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.artist, parent, false);
		
		TextView name = (TextView) layout.findViewById(R.id.name);
		name.setText(user.getName());
		TextView match = (TextView) layout.findViewById(R.id.match);
		match.setText(user.getMatch()+"%");
		
		// Open details about the artist
		match.setTag(user);
		match.setOnClickListener(new View.OnClickListener() {

    		public void onClick(View v) {
    			openItem((User) v.getTag());
    		}
    	});
		
		return layout;
	}
	
	private void openItem(User user) {
		Intent nextScreen = new Intent(context, UserActivity.class);
		nextScreen.putExtra("user", user);
    	context.startActivity(nextScreen);
    	
	}


}