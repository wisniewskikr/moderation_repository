package pl.kwisniewski.controllers;
 

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import pl.kwisniewski.entities.plain.TextModerationEntity;
import pl.kwisniewski.services.plain.DbVersionService;
import pl.kwisniewski.services.plain.TextModerationService;
import pl.kwisniewski.spring.beans.LanguageBean;
import pl.kwisniewski.spring.enums.ModerationStatusEnum;
import pl.kwisniewski.spring.enums.ModerationTypeEnum;


@Controller
public class LoginController implements ServletContextAware{
	
	private final static Logger LOGGER = Logger.getLogger(LoginController.class.getName());
	
	private ServletContext context;
	
	@Autowired
	protected TextModerationService textModerationService;
	
	@Autowired
    protected MessageSource messageSource;
	
	@Autowired
	protected DbVersionService dbVersionService;
	
	/**
	 * Method inits controller.
	 * 
	 * @param model object ModelMap with model
	 * @param request object HttpServletRequest with request
	 * @param response object HttpServletResponse with response
	 * @param loc object Locale with locale
	 * @return object ModelAndView with model and view
	 */
	@RequestMapping("/loginController_init.da")
	public ModelAndView init(ModelMap model, 
			HttpServletRequest request, HttpServletResponse response,
			Locale loc){
		
		HttpSession session = request.getSession();
		
		Locale locale = (Locale)session.getAttribute("org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE");
		if(locale == null){
			locale = Locale.ENGLISH;
			session.setAttribute("org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE", locale);
		}
				
		session.setAttribute("languageList", getLanguageList(locale));
		
		addLocalizedLoginPageLabelsToSession(session, locale);
		
		session.setAttribute("dbVersion", String.valueOf(dbVersionService.getMaxDbVersion()));
		session.setAttribute("applicationVersion", getApplicationVersion("/META-INF/MANIFEST.MF"));
		
		return new ModelAndView(new RedirectView("moderationController_init.do"));
		
	}
	
	/**
	 * Method logouts users from application.
	 * 
	 * @param model object ModelMap with model
	 * @param request object HttpServletRequest with request
	 * @param response object HttpServletResponse with response
	 * @return object ModelAndView with model and view
	 */
	@RequestMapping("/loginController_logout.do")
	public ModelAndView loguot(ModelMap model, 
			HttpServletRequest request, HttpServletResponse response){
		
		Map<String, String> parameterMap = new HashMap<String, String>();
		
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		setTextModerationStatusOnNotChecked(id, type);			
		
		request.getSession().invalidate();
		
		return new ModelAndView(new RedirectView("loginController_init.da"), parameterMap);
		
	}
	
	/**
	 * Method sets status of moderation to "not checked".
	 * 
	 * @param id object String with id of moderation object
	 * @param type object String with type of moderation, for instance: text. 
	 */
	public void setTextModerationStatusOnNotChecked(String id, String type){
		
		if(StringUtils.isBlank(type) || StringUtils.isBlank(id)){
			LOGGER.severe("Error with changing moderation status on checkded. Wrong type: " + type + " or id: " + id);
			return;
		}			
			
		ModerationTypeEnum moderationTypeEnum = ModerationTypeEnum.valueOf(type);
		
		switch (moderationTypeEnum) {
		case TEXT:
			
			TextModerationEntity textModeration = textModerationService.read(Long.valueOf(id));
			textModeration.setModerationStatus(ModerationStatusEnum.NOT_CHECKED);
			textModerationService.update(textModeration);
			break;
			
		default:
			break;
		}		
		
	}
	
	/**
	 * Method gets list of language which user can choose in
	 * GUI of application.
	 * 
	 * @param locale object Locale with language choose by user
	 * @return list of all possible language in application
	 */
	protected List<LanguageBean> getLanguageList(Locale locale){
		
		List<LanguageBean> list = new ArrayList<LanguageBean>();		
		
		list.add(createLanguageBean("Polski", "pl", locale));
		list.add(createLanguageBean("English", "en", locale));
		
		return list;
		
	}
	
	/**
	 * Method creates object LanguageBean basing on label, value and locale. 
	 * 
	 * @param label object String with label of language
	 * @param value object String with value of language
	 * @param locale object Locale with language choose by user
	 * @return object LanguageBean basing on label, value and locale
	 */
	protected LanguageBean createLanguageBean(String label, String value, Locale locale){
		
		if(StringUtils.isBlank(label) || StringUtils.isBlank(value) || locale == null){
			LOGGER.log(Level.SEVERE, "Can not create language because label, value or locale is empty");
			return null;
		}
		
		LanguageBean language = new LanguageBean();
		
		language.setLabel(label);		
		language.setValue(value);
		if(locale.getLanguage().equals(value)){
			language.setSelected(true);			
		}
		
		return language;
		
	}	
	
	/**
	 * Method adds localized labels of page Login to session. On this page regular localization
	 * with spring tags does not work. 
	 * 
	 * @param session object HttpSession with current session
	 * @param locale object Locale with localization data
	 */
	protected void addLocalizedLoginPageLabelsToSession(HttpSession session, Locale locale){
		
		String language = messageSource.getMessage("language", null, locale);
		String login = messageSource.getMessage("login", null, locale);
		String password = messageSource.getMessage("password", null, locale);
		String loginButton = messageSource.getMessage("loginButton", null, locale);
		String loginErrorMessage = messageSource.getMessage("loginErrorMessage", null, locale);
		
		session.setAttribute("language", language);
		session.setAttribute("login", login);
		session.setAttribute("password", password);
		session.setAttribute("loginButton", loginButton);
		session.setAttribute("loginErrorMessage", loginErrorMessage);
		
	}
	
	/**
	 * Method gets application version from manifest file.
	 * 
	 * @param manifestPath object String with path to manifest file
	 * @return object String with application version
	 */
	protected String getApplicationVersion(String manifestPath){
		
		String applicationVersion;
		Manifest manifest = null;
		
		try {
			InputStream inputStream = context.getResourceAsStream(manifestPath);
			manifest = new Manifest(inputStream);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Problem with reading file 'MANIFEST.MF'", e);
		}
		
		Attributes attributes = manifest.getMainAttributes();
		applicationVersion = attributes.getValue("Implementation-Build");
		if(applicationVersion == null){
			applicationVersion = attributes.getValue("Manifest-Version");			
		}
		
		return applicationVersion;
		
	}
	
	// ====================	GETTERS AND SETTERS ============================= //

	@Override
	public void setServletContext(ServletContext context) {
		this.context = context;
	}

}
