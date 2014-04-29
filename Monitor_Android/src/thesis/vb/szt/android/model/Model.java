package thesis.vb.szt.android.model;

import java.security.MessageDigest;
import java.util.List;
import java.util.Map;

import thesis.vb.szt.android.entity.AgentEntity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class Model {
	private static List<AgentEntity> agentList;
	private static String encryptedAgentList;
	
	private static List<Map<String, String>> reportsList;
	private static String encryptedReportList;
	
	private static String username;
	private static String passwordHash;
	
	
	public static List<AgentEntity> getAgentList() {
		return agentList;
	}

	public static void setAgentList(List<AgentEntity> agentList) {
		Model.agentList = agentList;
	}

	public static List<Map<String, String>> getReportsList() {
		return reportsList;
	}

	public static void setReportsList(List<Map<String, String>> reportsList) {
		Model.reportsList = reportsList;
	}

	public static String getEncryptedReportList() {
		return encryptedReportList;
	}

	public static void setEncryptedReportList(String encodedReportList) {
		Model.encryptedReportList = encodedReportList;
	}

	public static String getUsername() {
		return username;
	}

	public static String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Encrypts and stores the given password string.
	 * @param password
	 */
	public static void setPassword(String password) {
		Log.i(getTag(), "Encrypting password");
		Model.passwordHash = sha1Hash(password);
	}
	
	public static void setEncryptedPassword(String encryptedPassword) {
		passwordHash = encryptedPassword;
	}

	public static void setUsername(String username) {
		Model.username = username;
	}
	
	private static String sha1Hash( String toHash )
	{
		StringBuffer hexStringBuffer = null;
	    try
	    {
	        MessageDigest digest = MessageDigest.getInstance( "SHA-1" );
	        byte[] bytes = toHash.getBytes("UTF-8");
	        digest.update(bytes, 0, bytes.length);
	        bytes = digest.digest();
	        
	        hexStringBuffer = new StringBuffer();
	        for (int i = 0; i < bytes.length; i++) {
	        	hexStringBuffer.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	          }
	    }
	    catch( Exception e )
	    {
	        Log.e(getTag(), "Unable to encrypt string", e);
	    }
	    
	    return hexStringBuffer == null ? null : hexStringBuffer.toString();
	}
	
	public static String getEncryptedAgentList() {
		return encryptedAgentList;
	}

	public static void setEncryptedAgentList(String encryptedAgentList) {
		Model.encryptedAgentList = encryptedAgentList;
	}

	public static Map<String, String> getReport(int index) {
		return reportsList.get(index);
	}
	
	private static String getTag() {
		return Model.class.getName();
	}
}
