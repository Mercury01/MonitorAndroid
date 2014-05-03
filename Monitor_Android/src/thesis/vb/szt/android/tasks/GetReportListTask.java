package thesis.vb.szt.android.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;

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

import thesis.vb.szt.android.entity.ReportListRequest;
import thesis.vb.szt.android.model.Model;
import thesis.vb.szt.android.model.Persistence;
import thesis.vb.szt.android.security.Keys;
import thesis.vb.szt.android.security.Security;
import android.os.AsyncTask;
import android.util.Log;

public class GetReportListTask extends AsyncTask<String, Void, List<Map<String, String>>> {

	private GetReportListTaskCompleteListener listener;
	private String mac;
	private int start;
	private int limit;
	private String timeStamp;
	
//	private String result; //, error;

	public GetReportListTask (int start, int limit, String mac, String timeStamp, GetReportListTaskCompleteListener taskCompleteListener) throws ClassCastException {
		super();
			if(taskCompleteListener instanceof GetReportListTaskCompleteListener) {
				this.start = start;
				this.limit = limit;
				this.mac = mac;
				this.listener = taskCompleteListener;
				this.timeStamp = timeStamp;
				Log.i(getTag(), "LoginTask instantiated");
			} else {
				throw new ClassCastException("tascCompleteListener must implement LoginTaskCompleteListener");
			}
	}
	
	
	@Override
	/** Parameters are: 
	 *  -URL of the service
	 *  -Request entity
	 */
	protected List<Map<String, String>> doInBackground(String... params) {
		
		
		BufferedReader br = null;
		HttpGet httpget = null;
		HttpClient httpclient = null;
		HttpResponse response;
		
		final String username = Model.getUsername();
		final String password = Model.getPasswordHash();
		SecretKey key = null;
		try {
			
			key = Keys.generateSymmetricKeyForMobiles(password);
//			String timestamp = null;
//			if(Model.getReportsList() != null && Model.getReportsList().size() > 0)
//				timestamp = Model.getReportsList().get(Model.getReportsList().size() - 1).get("timestamp");
			
			ReportListRequest request = new ReportListRequest(mac, start, limit, timeStamp);
			String plainRequestString = Persistence.marshalReportListRequest(request);
			
			String encryptedRequest = Security.encryptString(plainRequestString, key);
			
			String encodedEncryptedRequest = URLEncoder.encode(encryptedRequest, "UTF-8");
			Log.i(getTag(), "Encrypted:  " + encryptedRequest);
			Log.i(getTag(), "URLEncoded: " + encodedEncryptedRequest);
		
			final String url = params[0] + "?username=" + username + "&encryptedQuery=" + encodedEncryptedRequest;
			Log.i(getTag(), "Connecting to: " + url);
			httpget = new HttpGet(url);
	
			HttpParams httpParameters = new BasicHttpParams();
			// Set the timeout in milliseconds until a connection is established.
			int timeoutConnection = 3000000;
			HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
			// Set the default socket timeout (SO_TIMEOUT) 
			// in milliseconds which is the timeout for waiting for data.
			int timeoutSocket = 3000000;
			HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
			
			httpclient = new DefaultHttpClient(httpParameters);
			
			} catch (IOException e ) {
				Log.e(getTag(), "Unable to encode password", e);
				return null;
			} catch (GeneralSecurityException e) {
				Log.e(getTag(), "Unable to encrypt password", e);
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
					String decryptedXml = Security.decryptString(encryptedXml, key);
//					Model.setEncryptedReportList(encryptedXml);	//TODO
					List<Map<String, String>> reportList = Persistence.unMarshalReportList(decryptedXml);
//					Model.setReportsList(reportList);
					return reportList;
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
	protected void onPostExecute(List<Map<String, String>> result) {
		super.onPostExecute(result);
		listener.onTaskComplete(result);
	}
	
	public interface GetReportListTaskCompleteListener {
		public void onTaskComplete (List<Map<String, String>> result);
	}

	private String getTag() {
		return getClass().getName();
	}
}
