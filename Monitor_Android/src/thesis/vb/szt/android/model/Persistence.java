package thesis.vb.szt.android.model;

import java.io.BufferedReader;
import java.io.File;
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
	
	private PrintWriter writer;
	private BufferedReader reader;

	public Persistence(PrintWriter writer) {
		super();
		this.writer = writer;
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
	public boolean persistAgentListXml() throws IOException {
		writer.print(Model.getEncodedAgentList());
		writer.flush();
		return true;
	}
	
	/**
	 * Takes care of updating the Model as well
	 * @return
	 * @throws IOException
	 */
	public String readAgentListXml() throws IOException {
		String encodedAgentList = reader.readLine();
		Model.setEncodedAgentList(encodedAgentList);
		return encodedAgentList;
	}
	
//	private String getAgentLine(AgentEntity agentEntity) {
//		return agentEntity.getId() + " "  +
//				agentEntity.getName() + " " + 
//				 agentEntity.getAddress();
//	}

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
		if(file.getParentFile() == null || ( !file.getParentFile().exists() && !file.getParentFile().mkdirs())) {
			result = false;
		}
		if(!file.exists() && !file.createNewFile()) {
			result = false;
		}
		return result;
	}
	
	public static String getInternalFileName(Context context) {
		return getExternalFileName(); //TODO 
//		return context.getFilesDir() + "monitor/agentlist_for_" + Model.getUsername();
	}
	
	public static String getExternalFileName() {
		return Environment.getExternalStorageDirectory().getPath() + "/monitor/agentlist_for_" + Model.getUsername();
	}
	
	private static String getTag() {
		return Persistence.class.getName();
	}
}
