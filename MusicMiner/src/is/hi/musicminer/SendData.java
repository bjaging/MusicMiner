package is.hi.musicminer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import android.content.Context;
import android.os.AsyncTask;
public class SendData extends AsyncTask<ArrayList<NameValuePair>, Void, Void>{
	/**
	 * Send data asynchronously
	 *
	 */
	Context context;

	public SendData(Context context) {
		this.context = context;
	}

	@Override
	/**
	 * Send the values in params to the server
	 */
	protected Void doInBackground(ArrayList<NameValuePair>... params) {
		String server = "http://shrimput.heliohost.org/MusicMiner/DATA.php";
		HttpPost httppost=new HttpPost(server);
		HttpClient httpclient=new DefaultHttpClient();

		try {
			httppost.setEntity(new UrlEncodedFormEntity(params[0]));
			HttpResponse rs=httpclient.execute(httppost);

			String status = rs.getStatusLine().toString();
			if (status.indexOf("OK")>0)
			{
				System.out.println("Successfully sent");
			}

		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}