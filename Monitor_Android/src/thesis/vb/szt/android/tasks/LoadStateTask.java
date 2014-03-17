package thesis.vb.szt.android.tasks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import thesis.vb.szt.android.entity.AgentEntity;
import thesis.vb.szt.android.model.Model;
import thesis.vb.szt.android.model.Persistence;
import thesis.vb.szt.android.security.Security;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class LoadStateTask extends AsyncTask<Void, Void, Boolean> {

	private LoadStateTaskCompleteListener listener;
	private Context context;
	private Persistence persistence;
	private BufferedReader reader;
	private String fileName;
	
	public LoadStateTask(Context context, LoadStateTaskCompleteListener listener) {
		this.context = context;
		this.listener = listener;
	}

	@Override
	protected Boolean doInBackground(Void... arg0) {
		try {
			if(true) { //TODO
				fileName = Persistence.getInternalFileName(context);
			} 
			if (false || false) { //TODO Set to external, or failed
				fileName = Persistence.getExternalFileName();
			}
			File file = new File(fileName);
			Persistence.checkOrCreateFile(file);
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			
			Persistence persistence = new Persistence(reader);
			String encodedAgentList = persistence.readAgentListXml();
			List<AgentEntity> agentList = Persistence.unMarshalAgentList(Security.decodeString(encodedAgentList, null));	//TODO set key
			if(agentList == null) {
				return false;
			} else {
				Model.setAgentList(agentList);
				return true;
			}
		} catch (Exception e) {
			Log.e(getTag(), "Unable to save application state", e);
			return false;
		} finally {
			if(reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					Log.e(getTag(), "Unable to close file reader", e);
				}
			}
		}
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		if(result) {
			listener.onTaskComplete(fileName);
		} else {
			listener.onError();
		}
		super.onPostExecute(result);
	}	
	
	public interface LoadStateTaskCompleteListener {
		public abstract void onTaskComplete(String fileName);
		public abstract void onError();
	}
	
    private String getTag() {
		return getClass().getName();
	}
}
