package pl.kwisniewski.spring.utils;

import static junit.framework.Assert.*;

import org.junit.Test;

public class UtilEncryptionTest {
	
	@Test
	public void byteArrayToHexStringNull(){
		
		byte[] byteArray = null;
		
		String result = UtilEncryption.byteArrayToHexString(byteArray);
		
		assertNull(result);
		
	}
	
	@Test
	public void byteArrayToHexString(){
		
		byte[] byteArray = "admin".getBytes();
		
		String result = UtilEncryption.byteArrayToHexString(byteArray);
		
		assertEquals("61646D696E", result);
		
	}

	@Test
	public void encryptNull(){
		
		String text = null;
		
		String result = UtilEncryption.encrypt(text);
		
		assertNull(result);
		
	}
	
	@Test
	public void encrypt(){
		
		String text = "admin";
		
		String result = UtilEncryption.encrypt(text);
		
		assertEquals("D033E22AE348AEB5660F", result);
		
	}
	
}
