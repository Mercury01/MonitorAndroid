package thesis.vb.szt.android.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import thesis.vb.szt.android.entity.AgentEntity;

import android.os.AsyncTask;
import android.util.Log;

public class LoginTask extends AsyncTask<String, Void, ArrayList<AgentEntity>> {

	private LoginTaskCompleteListener listener;
	private String result; //, error;
	
	public LoginTask (LoginTaskCompleteListener taskCompleteListener) throws ClassCastException {
		super();
//		Context context, 
//		this.context = context;
			if(taskCompleteListener instanceof LoginTaskCompleteListener) {
				this.listener = taskCompleteListener;
				Log.i(getTag(), "LoginTask instantiated");
			} else {
				throw new ClassCastException("tascCompleteListener must implement LoginTaskCompleteListener");
			}
	}
	
	
	@Override
	protected ArrayList<AgentEntity> doInBackground(String... params) {
		
		HttpClient httpclient = new DefaultHttpClient();
		InputStream is = null;
		HttpGet httpget = null;
		HttpResponse response;
		
//		try {
			httpget = new HttpGet(params[0]);
//		} catch (UnsupportedEncodingException e) {
//			Log.e(getTag(), "Unable to encode URL", e);
//			e.printStackTrace();
//			return "Encoding error";
//		}
		
		try{
			Log.i(getTag(), "Reading http entity...");
//			response = httpclient.execute(httpget);								TODO
			
			int responseCode = HttpStatus.SC_OK;//response.getStatusLine().getStatusCode();
			Log.i(getTag(), "Response http status code: " + HttpStatus.SC_OK); //responseCode);
			
			if(responseCode == HttpStatus.SC_OK){
				String entity = ""; //HttpEntity entity = response.getEntity();
				if (entity != null){
//					is = entity.getContent();
//					BufferedReader in = new BufferedReader(new InputStreamReader(is));
					result = "Success";
//					result = in.readLine();
				}
				else
					Log.e(getTag(), "Login response list is empty");
//					error = "Response Entity is empty.";
			}
			return new ArrayList<AgentEntity>();
		} catch (Exception e) {
			Log.e(getTag(), "Unable to execute login", e);
			return null;
		} finally {
			if (is!=null) 
				try {
					is.close();
				}catch (IOException e) {
					Log.e(getTag(), "Unable to close input stream", e);
			}
		}
	}
	
	@Override
	protected void onPostExecute(ArrayList<AgentEntity> resultList) {
		listener.onTaskComplete(resultList);
		super.onPostExecute(resultList);
	}
	
	public interface LoginTaskCompleteListener {
		public void onTaskComplete (ArrayList<AgentEntity> resultList);
	}

	private String getTag() {
		return getClass().getName();
	}
}
