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


import java.util.TreeMap;

import android.content.Context;
import android.widget.LinearLayout;

// A parent class for classes that build lists of suggested items for the user.
// The main role of the class is to build a view to display the list.

public abstract class ListBuilder {
	
	// Contains a list of page IDs for the items in the list 
	// along with their match percentage.
	protected TreeMap<String, Integer> list;
	// The parent activity that will be displaying the list.
	protected Context context;
	
	//Use: Item i = buildItem(id, match)
	//Before: id and match are positive integers and 0<=match<=100
	//After: Item is the Item for id
	protected abstract Item buildItem(String id, int match);
	
	//Use: LinearLayout layout = builder.getListView()
	//After: layout is the user interface that shows the items.
	public abstract LinearLayout getListView();
	
	//Use: LinearLayout layout = getItemView(item, parent)
	//Before: item is an Item object for an item responding to an id in list 
	protected abstract LinearLayout getItemView(Item item, LinearLayout parent);
	
	
	//Use: l = getLength();
	//After: l is the number of ids in the list.
	public int getLength() {
		return list.size();
	}
	
	// sort the list in descending order with respect to their match
	protected void sort(Item[] list) {
		for(int i=0; i<getLength(); i++) {
			//list[0..i-1] is in descending order
			for(int j=i; j>0; j--) {
				//the elements in list[j..i] are in descending order
				if(list[j].getMatch()>list[j-1].getMatch()) {
					Item tmp = list[j-1];
					list[j-1] = list[j];
					list[j] = tmp;
				}
			}
		}
	}
	
}