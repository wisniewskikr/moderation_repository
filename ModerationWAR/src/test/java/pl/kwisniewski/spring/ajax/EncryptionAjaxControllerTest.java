package pl.kwisniewski.spring.ajax;

import static junit.framework.Assert.assertEquals;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class EncryptionAjaxControllerTest {
	
	EncryptionAjaxController controller;
	
	@Before
	public void setUp(){
		controller = new EncryptionAjaxController();
	}
	
	@Test
	public void encryptPasswordNull(){
		
		String text = null;
		
		String result = controller.encryptPassword(text);
		
		Assert.assertNull(result);
		
	}
	
	@Test
	public void encryptPasswordBlank(){
		
		String text = "";
		
		String result = controller.encryptPassword(text);
		
		Assert.assertNull(result);
		
	}
	
	@Test
	public void encryptPassword(){
		
		String text = "admin";
		
		String result = controller.encryptPassword(text);
		
		assertEquals("D033E22AE348AEB5660F", result);
		
	}

}
