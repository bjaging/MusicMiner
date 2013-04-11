package is.hi.musicminer;

import android.content.Context;

public class GetDataSuggestions extends GetData{

	public GetDataSuggestions(Context context) {
		super(context);
	}
	@Override
	protected void onProgressUpdate(String... string) {
		((SuggestionActivity) context).build(string[0]);
	}
}
