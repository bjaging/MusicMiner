package is.hi.musicminer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class GetData extends AsyncTask<ArrayList<NameValuePair>, String, Void> {

	Context context;
	InputStream is = null;
	StringBuilder sb = null;

	public GetData (Context context) {
		this.context = context;
	}
	
	@Override
	/**
	 * Send the values in params to the server
	 */
	protected Void doInBackground(ArrayList<NameValuePair>... params) {

		String server = "http://shrimput.heliohost.org/MusicMiner/algorithm.php";
		HttpPost httppost=new HttpPost(server);
		HttpClient httpclient=new DefaultHttpClient();

		try {
			httppost.setEntity(new UrlEncodedFormEntity(params[0]));
			System.out.println("PARAMS-0:"+params[0].toString());
			HttpResponse rs=httpclient.execute(httppost);
			HttpEntity entity = rs.getEntity();
			is = entity.getContent();
			
			try{
				BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"),8);
				sb = new StringBuilder();
				sb.append(reader.readLine());
				String line = "0";
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
			}
			catch(Exception e){
				Log.e("log_tag", "Error converting result "+e.toString());
			}
			is.close();
			publishProgress(sb.toString());	
								
		
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
