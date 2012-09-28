package pl.kwisniewski.spring.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UtilEncryption {
	
	private static final Logger LOG = LoggerFactory.getLogger(UtilEncryption.class);
	
	/**
	 * Method encrypts text.
	 * 
	 * @param text object String with text which should be encrypted
	 * @return object String with encrypted text
	 */
	public static String encrypt(String text) {
		
		if(StringUtils.isBlank(text)){
			return null;
		}
		
		try {

			java.security.MessageDigest d = null;
			d = java.security.MessageDigest.getInstance("SHA-1");
			d.reset();
			d.update(text.getBytes());
			
			String result = byteArrayToHexString(d.digest());
			if(result.length() > 20){
				result = result.substring(0, 20);
			}
			
			return result;

		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage());
			return null;
		}

	}
	 
	 /**
	 * Method transforms byte array to hex string.
	 * 
	 * @param byteArray array of bytes
	 * @return object String from array of byte
	 */
	protected static String byteArrayToHexString(byte[] byteArray){
		
		if(byteArray == null){
			return null;
		}
		
	     StringBuffer sb = new StringBuffer(byteArray.length * 2);
	     for (int i = 0; i < byteArray.length; i++){
	       int v = byteArray[i] & 0xff;
	       if (v < 16) {
	         sb.append('0');
	       }
	       sb.append(Integer.toHexString(v));
	     }
	     return sb.toString().toUpperCase();
	  }

}
