package thesis.vb.szt.android.tasks;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import thesis.vb.szt.android.entity.AgentEntity;
import thesis.vb.szt.android.model.Model;
import thesis.vb.szt.android.model.Persistence;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class SaveStateTask extends AsyncTask<Void, Void, Boolean> {

	private SaveStateTaskCompleteListener listener;
	private Context context;
	private Persistence persistence;
	private PrintWriter pw;
	private String fileName;
	
	public SaveStateTask(Context context, SaveStateTaskCompleteListener listener) {
		super();
		this.context = context;
		this.listener = listener;
		Log.i(getTag(), "SaveStateTask instantiated");
	}



	@Override
	protected Boolean doInBackground(Void... params) {
		try {
			//TODO internal-external
			if(true) {
//				final File dir = new File(context.getFilesDir() + "/nfs/guille/groce/users/nicholsk/workspace3/SQLTest");
//				dir.mkdirs(); //create folders where write files
//				final File file = new File(dir, "BlockForTest.txt");
				fileName = Persistence.getInternalFileName(context);
			} else {
				fileName = Persistence.getExternalFileName();
			}
			File file = new File(fileName);
			if(!Persistence.checkOrCreateFile(file)) {
				Log.e(getTag(), "File not found: " + fileName);
			}
			pw = new PrintWriter(new FileOutputStream(file));//context.openFileOutput(fileName, Context.MODE_PRIVATE));
			
			persistence = new Persistence(pw);
			persistence.persistAgentListXml();
			return true;
		} catch (Exception e) {
			Log.e(getTag(), "Unable to save application state", e);
			return false;
		} finally {
			if(pw != null) {
				pw.close();
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
	
	public interface SaveStateTaskCompleteListener {
		public abstract void onTaskComplete(String fileName);
		public abstract void onError();
	}
	
    private String getTag() {
		return getClass().getName();
	}
}
