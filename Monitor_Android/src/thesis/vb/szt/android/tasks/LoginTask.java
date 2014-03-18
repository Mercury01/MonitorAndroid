package thesis.vb.szt.android.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import thesis.vb.szt.android.entity.AgentEntity;
import thesis.vb.szt.android.model.Model;
import thesis.vb.szt.android.model.Persistence;
import thesis.vb.szt.android.security.Security;
import android.os.AsyncTask;
import android.util.Log;

public class LoginTask extends AsyncTask<String, Void, List<AgentEntity>> {

	private LoginTaskCompleteListener listener;
//	private String result; //, error;
	
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
	protected List<AgentEntity> doInBackground(String... params) {
		
		
		BufferedReader br = null;
		HttpGet httpget = null;
		HttpResponse response;
		
		List<AgentEntity> agentList;
		
//		try {
		final String username = Model.getUsername();
		final String password = Model.getPasswordHash();
		final String url = params[0] + "?username=" + username + "&password=" + password;
		Log.i(getTag(), "Connecting to: " + url);
		httpget = new HttpGet(url);

		HttpParams httpParameters = new BasicHttpParams();
		// Set the timeout in milliseconds until a connection is established.
		int timeoutConnection = 3000;
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		// Set the default socket timeout (SO_TIMEOUT) 
		// in milliseconds which is the timeout for waiting for data.
		int timeoutSocket = 3000;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
		
		HttpClient httpclient = new DefaultHttpClient(httpParameters);
		
		try{
			Log.i(getTag(), "Reading http entity...");
			response = httpclient.execute(httpget);								
			
			int responseCode = response.getStatusLine().getStatusCode(); //HttpStatus.SC_OK;
			String reason = response.getStatusLine().getReasonPhrase();
			Log.i(getTag(), "Response http status: " + responseCode + " " + reason); //responseCode);
			
			if(responseCode == HttpStatus.SC_OK){
				HttpEntity entity = response.getEntity();
				if (entity != null){
					br = new BufferedReader(new InputStreamReader(entity.getContent()));
					String encodedXml = br.readLine();
					Model.setEncodedAgentList(Security.decodeString(encodedXml, null)); //TODO set key
					return Persistence.unMarshalAgentList(encodedXml);
				}
				else
					Log.e(getTag(), "Login response list is empty");
			}
			return null;
		} catch (ConnectTimeoutException e) {
			Log.e(getTag(), "Unable to execute login. Connection timed out.", e);
			return null;
		} catch (Exception e) {
			Log.e(getTag(), "Unable to execute login", e);
			return null;
		} finally {
			if (br!=null) 
				try {
					br.close();
				}catch (IOException e) {
					Log.e(getTag(), "Unable to close input stream", e);
			}
		}
	}
	
	
	
	
	@Override
	protected void onPostExecute(List<AgentEntity> resultList) {
		listener.onTaskComplete(resultList);
		super.onPostExecute(resultList);
	}
	
	public interface LoginTaskCompleteListener {
		public void onTaskComplete (List<AgentEntity> resultList);
	}

	private String getTag() {
		return getClass().getName();
	}
}
