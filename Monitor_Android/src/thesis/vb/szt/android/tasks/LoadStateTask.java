package thesis.vb.szt.android.tasks;

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
//	private Persistence persistence;
//	private BufferedReader reader;
//	private String fileName;
	
	public LoadStateTask(Context context, LoadStateTaskCompleteListener listener) {
		this.context = context;
		this.listener = listener;
	}

	@Override
	protected Boolean doInBackground(Void... arg0) {
		boolean isExternal = false;
		Log.i(getTag(), "Loading state from: " + (isExternal ? "external" : "internal") + " storage");
		boolean result = false;
		Persistence persistence = new Persistence();
		String encodedAgentList = "";
		try {
			
			if(isExternal) {
//				fileName = Persistence.getInternalFileName(context);
				encodedAgentList = persistence.readAgentListXmlFromExternal();
				if(encodedAgentList != null) {
					result = true;
				}
			} 
			if (!isExternal || !result) { //If user set storage to external, or it failed to load from external
				encodedAgentList = persistence.readAgentListXmlFromInternal(context);
				if(encodedAgentList != null) {
					result = true;
				}
			}
//			Log.i(getTag(), "Loading state from: " + fileName);
//			File file = new File(fileName);
//			Persistence.checkOrCreateFile(file);
//			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
//			
//			Persistence persistence = new Persistence(reader);
			
//			String 
			
			List<AgentEntity> agentList = Persistence.unMarshalAgentList(Security.decodeString(encodedAgentList, null));	//TODO set key
			if(agentList == null) {
				return false;
			} else {
				Model.setAgentList(agentList);
				return true;
			}
		} catch (Exception e) {
			Log.e(getTag(), "Unable to load application state", e);
			return false;
		} 
//		finally {
//			if(reader != null) {
//				try {
//					reader.close();
//				} catch (IOException e) {
//					Log.e(getTag(), "Unable to close file reader", e);
//				}
//			}
//		}
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		if(result) {
			listener.onTaskComplete();
		} else {
			listener.onError();
		}
		super.onPostExecute(result);
	}	
	
	public interface LoadStateTaskCompleteListener {
		public abstract void onTaskComplete();
		public abstract void onError();
	}
	
    private String getTag() {
		return getClass().getName();
	}
}
