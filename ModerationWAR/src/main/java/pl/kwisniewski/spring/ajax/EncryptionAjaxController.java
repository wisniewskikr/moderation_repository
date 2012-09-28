package pl.kwisniewski.spring.ajax;
 

import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.kwisniewski.spring.utils.UtilEncryption;


@Controller
public class EncryptionAjaxController{
	
	private final static Logger LOGGER = Logger.getLogger(EncryptionAjaxController.class.getName());
	
	/**
	 * Method encrypts password.
	 * 
	 * @param password object String with password
	 * @return object String with encrypted password
	 */
	@RequestMapping(value = "/encryptionAjaxController_encryptPassword.da", method=RequestMethod.GET)
	@ResponseBody
	public String encryptPassword(@RequestParam String password){
		
		String result = null;
		
		if(StringUtils.isBlank(password)){
			return null;
		}
		
		result = UtilEncryption.encrypt(password);
		return result;
		
	}

}
