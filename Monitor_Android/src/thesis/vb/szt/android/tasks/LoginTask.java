package thesis.vb.szt.android.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.util.List;

import javax.crypto.SecretKey;

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
import thesis.vb.szt.android.security.Keys;
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
		HttpClient httpclient = null;
		HttpResponse response;
		
		final String username = Model.getUsername();
		final String password = Model.getPasswordHash();
		SecretKey key = null;
		try {
			
			key = Keys.generateSymmetricKeyForMobiles(password);
//			String encryptedPassword = URLEncoder.encode(Security.encryptString(password, key), "UTF-8"); //Security.encryptString(password, key);
//			Log.i(getTag(), "Encrypted:  " + Security.encryptString(password, key));
//			Log.i(getTag(), "URLEncoded: " + encryptedPassword);
		
			final String url = params[0] + "?username=" + username;// + "&encryptedQuery=" + encryptedPassword;
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
			
			httpclient = new DefaultHttpClient(httpParameters);
			
			} catch (IOException e ) {
				Log.e(getTag(), "Unable to encode password", e);
				return null;
			} 
		
		try{
			Log.i(getTag(), "Reading http entity...");
			response = httpclient.execute(httpget);								
			
			int responseCode = response.getStatusLine().getStatusCode();
			String reason = response.getStatusLine().getReasonPhrase();
			Log.i(getTag(), "Response http status: " + responseCode + " " + reason); 
			
			if(responseCode == HttpStatus.SC_OK){
				HttpEntity entity = response.getEntity();
				if (entity != null){
					br = new BufferedReader(new InputStreamReader(entity.getContent()));
					String encryptedXml = br.readLine();
					String deCodedXml = Security.decryptString(encryptedXml, key);
					Model.setEncodedAgentList(encryptedXml);
					return Persistence.unMarshalAgentList(deCodedXml);
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
