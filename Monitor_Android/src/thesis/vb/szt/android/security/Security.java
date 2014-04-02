package thesis.vb.szt.android.security;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.ApacheBase64;

import android.util.Log;

public class Security {
	private static final byte[] iv = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
	/** 
	 * 
	 * @param plainString String to encrypt
	 * @param secret Secret to use for encryption
	 * @return Encrypted and <u>Base64 encoded</u>
	 * @throws IOException because of encoding 
	 * @throws GeneralSecurityException caused by encryption errors
	 */
	public static String encryptString(String plainString, SecretKey secret) throws IOException, GeneralSecurityException {
//		Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding"); // /CBC/PKCS5Padding
//		cipher.init(Cipher.ENCRYPT_MODE, secret);
//
//		return ApacheBase64.encodeBase64String(cipher.doFinal(plainString.getBytes("UTF-8")));
		
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secret, new IvParameterSpec(iv));
		byte[] result = cipher.doFinal(plainString.getBytes("UTF-8"));
		return ApacheBase64.encodeBase64String(result);
		
//		return android.util.Base64.encodeToString(cipher.doFinal(plainString.getBytes("UTF-8")), android.util.Base64.DEFAULT); //TODO refactor
		//TODO error here
	}
	
	
	
	
	public static String decodeString(String encryptedString, SecretKey secret) throws IOException, GeneralSecurityException {
		if (encryptedString != null && !encryptedString.isEmpty()) {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));
			return new String(cipher.doFinal(ApacheBase64.decodeBase64(encryptedString)));
		} else {
			Log.e(getTag(), "Unable to decrypt empty agent list");
			return null;
		}
	}
	
	private static String getTag() {
		return Security.class.getName();
	}
}
