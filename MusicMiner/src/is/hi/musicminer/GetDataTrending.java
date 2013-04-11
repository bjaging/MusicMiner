package is.hi.musicminer;

import android.content.Context;

public class GetDataTrending extends GetData{
	
	public GetDataTrending(Context context) {
		super(context);
	}
	@Override
	protected void onProgressUpdate(String... string) {
		((TrendingActivity) context).build(string[0]);
		
	}

}