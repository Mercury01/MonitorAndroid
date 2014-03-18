package thesis.vb.szt.android.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import thesis.vb.szt.android.entity.AgentEntity;
import thesis.vb.szt.android.entity.AgentList;

/**
 * Does not open, or close streams. These have to be managed outside the class.
 * @author Bazint
 *
 */
public class Persistence {
	
	
	private final static String AGENTLIST_FILE_PREFIX = "agentlist_for_";
	private BufferedReader reader; //TODO read from both external and internal

	public Persistence() {
		super();
	}
	
	public Persistence(BufferedReader reader) {
		super();
		this.reader = reader;
	}

	/**
	 * Gets the agentList from the model
	 * @return
	 * @throws IOException
	 */
	public boolean persistAgentListXmlToExternal() throws IOException {
		String filename = getExternalFileName();
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new File(filename));
			pw.write(Model.getEncodedAgentList());
			pw.flush();
			return true;
		} catch (Exception e) {
			Log.e(getTag(), "Unable to persist agentlist to external storage", e);
			return false;
		} finally {
			if(pw != null) {
				pw.close();
			}	
		}
	}
	
	public boolean persistAgentListXmlToInternal(Context context) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(context.openFileOutput(AGENTLIST_FILE_PREFIX + Model.getUsername(), Context.MODE_PRIVATE));
			pw.write(Model.getEncodedAgentList());
			pw.flush();
			return true;
		} catch (Exception e) {
			Log.e(getTag(), "Unable to persist agentlist to internal storage", e);
			return false;
		} finally {
			if(pw != null) {
				pw.close();
			}	
		}
		
	}
	
	/**
	 * Takes care of updating the Model as well
	 * @return
	 * @throws IOException
	 */
	public String readAgentListXmlFromExternal() throws IOException {
		String filename = getExternalFileName();
		BufferedReader bw = null;
		try {
			bw = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
			String encodedAgentList = bw.readLine();
			Model.setEncodedAgentList(encodedAgentList);
			return encodedAgentList;
		} catch (Exception e) {
			Log.e(getTag(), "Unable to read agentlist from external storage", e);
			return "";
		} finally {
			if(bw != null) {
				bw.close();
			}	
		}
	}
	
	/**
	 * Takes care of updating the Model as well
	 * @return
	 * @throws IOException
	 */
	public String readAgentListXmlFromInternal(Context context) throws IOException {
		BufferedReader bw = null;
		try {
			bw = new BufferedReader(new InputStreamReader(context.openFileInput(AGENTLIST_FILE_PREFIX + Model.getUsername())));
			String encodedAgentList = bw.readLine();
			Model.setEncodedAgentList(encodedAgentList);
			return encodedAgentList;
		} catch (Exception e) {
			Log.e(getTag(), "Unable to read agentlist from internal storage", e);
			return "";
		} finally {
			if(bw != null) {
				bw.close();
			}	
		}
	}

	public boolean persistReport() throws IOException {
		final List<Map<String, String>> reportsList = Model.getReportsList();
		for (Map<String, String> reportMap : reportsList) {
			for (Entry<String, String> reportEntry : reportMap.entrySet()) {
//				fileOutputStream.w //TODO
			}
		}
		return false;
	}
	
	public static List<AgentEntity> unMarshalAgentList(String xml) throws IOException {	
		Log.i(getTag(), "Parsing xml: \n" + xml);
		try {	
			Serializer serializer = new Persister();
			AgentList l = serializer.read(AgentList.class, xml); 
			return l.getAgentList();
		} catch (Exception e) {
			Log.e(getTag(), "Unable to read agent list xml", e);
			return null;
		} 
	}
	
	public static boolean checkOrCreateFile(File file) throws IOException{
		boolean result = true;
		File a = file.getParentFile();
		boolean aa = file.getParentFile().exists() ;
		boolean aaa = file.getParentFile().mkdir();
		if(file.getParentFile() == null || ( !file.getParentFile().exists() && !file.getParentFile().mkdirs())) {
			result = false;
		}
		if(!file.exists() && !file.createNewFile()) {
			result = false;
		}
		return result;
	}
	
//	private String getInternalFileName() {
//		return Environment.getDataDirectory() + "/" + AGENTLIST_FILE_PREFIX + Model.getUsername();
//	}
	
	private String getExternalFileName() {
		return Environment.getExternalStorageDirectory().getPath() + "/monitor/agentlist_for_" + Model.getUsername();
	}
	
	private static String getTag() {
		return Persistence.class.getName();
	}
}
