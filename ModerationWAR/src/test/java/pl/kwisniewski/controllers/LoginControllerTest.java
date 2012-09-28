package pl.kwisniewski.controllers;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.MessageSource;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import pl.kwisniewski.entities.plain.TextModerationEntity;
import pl.kwisniewski.services.plain.DbVersionService;
import pl.kwisniewski.services.plain.TextModerationService;
import pl.kwisniewski.spring.beans.LanguageBean;
import pl.kwisniewski.spring.enums.ModerationStatusEnum;
import pl.kwisniewski.spring.enums.ModerationTypeEnum;


public class LoginControllerTest {
	
	LoginController controller;
	
	@Before
	public void setUp(){
		
		controller = new LoginController();
		controller.setServletContext(mockServletContext());
		
	}
	
	@Test
	public void init(){
		
		controller.messageSource = mockMessageSource();
		controller.dbVersionService = mockDbVersionService();
		
		ModelMap model = new ModelMap();
		HttpServletRequest request = mockHttpServletRequestWithTestSession();
		HttpServletResponse response = mockHttpServletResponse();
		Locale loc = Locale.ENGLISH;
		
		ModelAndView modelAndView = controller.init(model, request, response, loc);
		
		HttpSession session = request.getSession();
		Locale testLocale = (Locale)session.getAttribute("org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE");
		List<LanguageBean> languageList = (List<LanguageBean>)session.getAttribute("languageList");
		
		
		assertEquals(Locale.ENGLISH, testLocale);	
		
		LanguageBean languageBean;
		languageBean = languageList.get(0);
		Assert.assertEquals("Polski", languageBean.getLabel());
		Assert.assertEquals("pl", languageBean.getValue());
		Assert.assertFalse(languageBean.isSelected());
		languageBean = languageList.get(1);
		Assert.assertEquals("English", languageBean.getLabel());
		Assert.assertEquals("en", languageBean.getValue());
		Assert.assertTrue(languageBean.isSelected());
		
		assertEquals("Language", session.getAttribute("language"));
		assertEquals("Login", session.getAttribute("login"));
		assertEquals("Password", session.getAttribute("password"));
		assertEquals("Login", session.getAttribute("loginButton"));
		assertEquals("3", session.getAttribute("dbVersion"));
		assertEquals("2.0", session.getAttribute("applicationVersion"));
		
		assertEquals(true, modelAndView.getView().toString().contains("moderationController_init.do"));
		
	}
	
	@Test
	public void loguot(){
		
		TextModerationEntity textModeration = new TextModerationEntity();
		textModeration.setId(Long.valueOf(1));
		textModeration.setTextContent("Content");
		textModeration.setModerationStatus(ModerationStatusEnum.CHECKING);
		controller.textModerationService = mockTextModerationService(textModeration);
		
		ModelMap model = new ModelMap();
		HttpServletRequest request = mockHttpServletRequest();
		HttpServletResponse response = mockHttpServletResponse();
		
		ModelAndView modelAndView = controller.loguot(model, request, response);
		
		assertEquals(ModerationStatusEnum.NOT_CHECKED, textModeration.getModerationStatus());
		
		assertEquals(true, modelAndView.getView().toString().contains("loginController_init.da"));
		
	}
	
	@Test
	public void loguotRequestParameterWithNoId(){
		
		TextModerationEntity textModeration = new TextModerationEntity();
		textModeration.setId(Long.valueOf(1));
		textModeration.setTextContent("Content");
		textModeration.setModerationStatus(ModerationStatusEnum.CHECKING);
		controller.textModerationService = mockTextModerationService(textModeration);
		
		ModelMap model = new ModelMap();
		HttpServletRequest request = mockHttpServletRequestWithNoId();
		HttpServletResponse response = mockHttpServletResponse();
		
		ModelAndView modelAndView = controller.loguot(model, request, response);
		
		assertEquals(ModerationStatusEnum.CHECKING, textModeration.getModerationStatus());
		
		assertEquals(true, modelAndView.getView().toString().contains("loginController_init.da"));
		
	}
	
	@Test
	public void loguotRequestParameterWithNoType(){
		
		TextModerationEntity textModeration = new TextModerationEntity();
		textModeration.setId(Long.valueOf(1));
		textModeration.setTextContent("Content");
		textModeration.setModerationStatus(ModerationStatusEnum.CHECKING);
		controller.textModerationService = mockTextModerationService(textModeration);
		
		ModelMap model = new ModelMap();
		HttpServletRequest request = mockHttpServletRequestWithNoType();
		HttpServletResponse response = mockHttpServletResponse();
		
		ModelAndView modelAndView = controller.loguot(model, request, response);
		
		assertEquals(ModerationStatusEnum.CHECKING, textModeration.getModerationStatus());
		
		assertEquals(true, modelAndView.getView().toString().contains("loginController_init.da"));
		
	}
	
	@Test
	public void setTextModerationStatusOnNotCheckedNullId(){
		
		TextModerationEntity textModeration = new TextModerationEntity();
		textModeration.setId(Long.valueOf(1));
		textModeration.setTextContent("Content");
		textModeration.setModerationStatus(ModerationStatusEnum.CHECKING);
		controller.textModerationService = mockTextModerationService(textModeration);
		
		String id = null; 
		String type = ModerationTypeEnum.TEXT.name();
		
		controller.setTextModerationStatusOnNotChecked(id, type);
		
		Assert.assertEquals(ModerationStatusEnum.CHECKING, textModeration.getModerationStatus());
		
	}
	
	@Test
	public void setTextModerationStatusOnNotCheckedNullType(){
		
		TextModerationEntity textModeration = new TextModerationEntity();
		textModeration.setId(Long.valueOf(1));
		textModeration.setTextContent("Content");
		textModeration.setModerationStatus(ModerationStatusEnum.CHECKING);
		controller.textModerationService = mockTextModerationService(textModeration);
		
		String id = "1"; 
		String type = null;
		
		controller.setTextModerationStatusOnNotChecked(id, type);
		
		Assert.assertEquals(ModerationStatusEnum.CHECKING, textModeration.getModerationStatus());
		
	}
	
	@Test
	public void setTextModerationStatusOnNotChecked(){
		
		TextModerationEntity textModeration = new TextModerationEntity();
		textModeration.setId(Long.valueOf(1));
		textModeration.setTextContent("Content");
		textModeration.setModerationStatus(ModerationStatusEnum.CHECKING);
		controller.textModerationService = mockTextModerationService(textModeration);
		
		String id = "1"; 
		String type = ModerationTypeEnum.TEXT.name();
		
		controller.setTextModerationStatusOnNotChecked(id, type);
		
		Assert.assertEquals(ModerationStatusEnum.NOT_CHECKED, textModeration.getModerationStatus());
		
	}
	
	@Test
	public void createLanguageBeanNotSelected(){
		
		String label = "Test Label 1";
		String value = "testValue1";
		Locale locale = Locale.ENGLISH;
		
		LanguageBean languageBean = controller.createLanguageBean(label, value, locale);
		
		Assert.assertEquals("Test Label 1", languageBean.getLabel());
		Assert.assertEquals("testValue1", languageBean.getValue());
		Assert.assertFalse(languageBean.isSelected());
		
	}
	
	@Test
	public void createLanguageBeanSelected(){
		
		String label = "Test Label 1";
		String value = "en";
		Locale locale = Locale.ENGLISH;
		
		LanguageBean languageBean = controller.createLanguageBean(label, value, locale);
		
		Assert.assertEquals("Test Label 1", languageBean.getLabel());
		Assert.assertEquals("en", languageBean.getValue());
		Assert.assertTrue(languageBean.isSelected());
		
	}
	
	@Test
	public void createLanguageBeanNullLabel(){
		
		String label = null;
		String value = "en";
		Locale locale = Locale.ENGLISH;
		
		LanguageBean languageBean = controller.createLanguageBean(label, value, locale);
		
		Assert.assertNull(languageBean);
		
	}
	
	@Test
	public void createLanguageBeanEmptyValue(){
		
		String label = "Test Label 1";
		String value = "";
		Locale locale = Locale.ENGLISH;
		
		LanguageBean languageBean = controller.createLanguageBean(label, value, locale);
		
		Assert.assertNull(languageBean);
		
	}
	
	@Test
	public void createLanguageBeanNullLocale(){
		
		String label = "Test Label 1";
		String value = "";
		Locale locale = null;
		
		LanguageBean languageBean = controller.createLanguageBean(label, value, locale);
		
		Assert.assertNull(languageBean);
		
	}
	
	@Test
	public void getLanguageList(){
		
		Locale locale = Locale.ENGLISH;
		
		List<LanguageBean> languageList = controller.getLanguageList(locale);
		
		LanguageBean languageBean;
		languageBean = languageList.get(0);
		Assert.assertEquals("Polski", languageBean.getLabel());
		Assert.assertEquals("pl", languageBean.getValue());
		Assert.assertFalse(languageBean.isSelected());
		languageBean = languageList.get(1);
		Assert.assertEquals("English", languageBean.getLabel());
		Assert.assertEquals("en", languageBean.getValue());
		Assert.assertTrue(languageBean.isSelected());
		
	}
	
	@Test
	public void addLocalizedLoginPageLabelsToSession(){
		
		HttpSession session = new TestSession();
		Locale locale = Locale.ENGLISH;
		
		controller.messageSource = mockMessageSource();
		controller.addLocalizedLoginPageLabelsToSession(session, locale);
		
		assertEquals("Language", session.getAttribute("language"));
		assertEquals("Login", session.getAttribute("login"));
		assertEquals("Password", session.getAttribute("password"));
		assertEquals("Login", session.getAttribute("loginButton"));
		
	}
	
	@Test
	public void addLocalizedLoginPageLabelsToSessionWrongLocale(){
		
		HttpSession session = new TestSession();
		Locale locale = Locale.CANADA;
		
		controller.messageSource = mockMessageSource();
		controller.addLocalizedLoginPageLabelsToSession(session, locale);		
		
		assertNull(session.getAttribute("language"));
		assertNull(session.getAttribute("login"));
		assertNull(session.getAttribute("password"));
		assertNull(session.getAttribute("loginButton"));
		
	}
	
	@Test
	public void getApplicationVersion(){
		
		String manifestPath = "META-INF/MANIFEST-TEST.MF";
		String applicationVersion = controller.getApplicationVersion(manifestPath);
		
		assertEquals("2.0", applicationVersion);
		
	}
	
	
	// ============= MOCKS AND HELP METHODS ============== //
	
	
	private HttpServletRequest mockHttpServletRequest(){
		
		HttpSession session = mockHttpSession();
		
		HttpServletRequest mock = Mockito.mock(HttpServletRequest.class);
		when(mock.getParameter(Mockito.eq("id"))).thenReturn("1");
		when(mock.getParameter(Mockito.eq("type"))).thenReturn("TEXT");
		when(mock.getSession()).thenReturn(session);
		return mock;
		
	}
	
	private HttpServletRequest mockHttpServletRequestWithTestSession(){
		
		HttpSession session = new TestSession();
		
		HttpServletRequest mock = Mockito.mock(HttpServletRequest.class);
		when(mock.getParameter(Mockito.eq("id"))).thenReturn("1");
		when(mock.getParameter(Mockito.eq("type"))).thenReturn("TEXT");
		when(mock.getSession()).thenReturn(session);
		return mock;
		
	}
	
	private HttpServletRequest mockHttpServletRequestWithNoId(){
		
		HttpSession session = mockHttpSession();
		
		HttpServletRequest mock = Mockito.mock(HttpServletRequest.class);
		when(mock.getParameter(Mockito.eq("id"))).thenReturn(null);
		when(mock.getParameter(Mockito.eq("type"))).thenReturn("TEXT");
		when(mock.getSession()).thenReturn(session);
		return mock;
		
	}
	
	private HttpServletRequest mockHttpServletRequestWithNoType(){
		
		HttpSession session = mockHttpSession();
		
		HttpServletRequest mock = Mockito.mock(HttpServletRequest.class);
		when(mock.getParameter(Mockito.eq("id"))).thenReturn("1");
		when(mock.getParameter(Mockito.eq("type"))).thenReturn(null);
		when(mock.getSession()).thenReturn(session);
		return mock;
		
	}
	
	private HttpSession mockHttpSession(){
		
		HttpSession mock = mock(HttpSession.class);
		return mock;
		
	}
	
	private HttpServletResponse mockHttpServletResponse(){
		
		HttpServletResponse mock = Mockito.mock(HttpServletResponse.class);
		return mock;
		
	}
	
	private TextModerationService mockTextModerationService(TextModerationEntity textModeration){
		
		TextModerationService mock = mock(TextModerationService.class);
		when(mock.read(Mockito.anyLong())).thenReturn(textModeration);
		return mock;
		
	}
	
	public MessageSource mockMessageSource(){
		
		Locale locale = Locale.ENGLISH;
		
		MessageSource mock = mock(MessageSource.class);
		when(mock.getMessage(Mockito.eq("language"), Mockito.any(Object[].class), Mockito.eq(locale))).thenReturn("Language");
		when(mock.getMessage(Mockito.eq("login"), Mockito.any(Object[].class), Mockito.eq(locale))).thenReturn("Login");
		when(mock.getMessage(Mockito.eq("password"), Mockito.any(Object[].class), Mockito.eq(locale))).thenReturn("Password");
		when(mock.getMessage(Mockito.eq("loginButton"), Mockito.any(Object[].class),Mockito.eq(locale))).thenReturn("Login");
		
		return mock;
		
	}
	
	public DbVersionService mockDbVersionService(){
		
		DbVersionService mock = mock(DbVersionService.class);
		when(mock.getMaxDbVersion()).thenReturn(3);
		return mock;
		
	}
	
	public ServletContext mockServletContext(){
		
		InputStream is = this.getClass().getResourceAsStream("/META-INF/MANIFEST-TEST.MF");
		
		ServletContext mock = mock(ServletContext.class);
		when(mock.getResourceAsStream(Mockito.anyString())).thenReturn(is);
		return mock;
		
	}
	
	private class TestSession implements HttpSession{

		Map<String, Object> attributeMap = new HashMap<String, Object>();
		
		/* (non-Javadoc)
		 * @see javax.servlet.http.HttpSession#getAttribute(java.lang.String)
		 */
		@Override
		public Object getAttribute(String key) {
			return attributeMap.get(key);
		}
		
		/* (non-Javadoc)
		 * @see javax.servlet.http.HttpSession#setAttribute(java.lang.String, java.lang.Object)
		 */
		@Override
		public void setAttribute(String key, Object value) {
			attributeMap.put(key, value);			
		}

		/* (non-Javadoc)
		 * @see javax.servlet.http.HttpSession#getAttributeNames()
		 */
		@Override
		public Enumeration getAttributeNames() {
			// TODO Auto-generated method stub
			return null;
		}

		/* (non-Javadoc)
		 * @see javax.servlet.http.HttpSession#getCreationTime()
		 */
		@Override
		public long getCreationTime() {
			// TODO Auto-generated method stub
			return 0;
		}

		/* (non-Javadoc)
		 * @see javax.servlet.http.HttpSession#getId()
		 */
		@Override
		public String getId() {
			// TODO Auto-generated method stub
			return null;
		}

		/* (non-Javadoc)
		 * @see javax.servlet.http.HttpSession#getLastAccessedTime()
		 */
		@Override
		public long getLastAccessedTime() {
			// TODO Auto-generated method stub
			return 0;
		}

		/* (non-Javadoc)
		 * @see javax.servlet.http.HttpSession#getMaxInactiveInterval()
		 */
		@Override
		public int getMaxInactiveInterval() {
			// TODO Auto-generated method stub
			return 0;
		}

		/* (non-Javadoc)
		 * @see javax.servlet.http.HttpSession#getServletContext()
		 */
		@Override
		public ServletContext getServletContext() {
			// TODO Auto-generated method stub
			return null;
		}

		/* (non-Javadoc)
		 * @see javax.servlet.http.HttpSession#getSessionContext()
		 */
		@Override
		public HttpSessionContext getSessionContext() {
			// TODO Auto-generated method stub
			return null;
		}

		/* (non-Javadoc)
		 * @see javax.servlet.http.HttpSession#getValue(java.lang.String)
		 */
		@Override
		public Object getValue(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		/* (non-Javadoc)
		 * @see javax.servlet.http.HttpSession#getValueNames()
		 */
		@Override
		public String[] getValueNames() {
			// TODO Auto-generated method stub
			return null;
		}

		/* (non-Javadoc)
		 * @see javax.servlet.http.HttpSession#invalidate()
		 */
		@Override
		public void invalidate() {
			// TODO Auto-generated method stub
			
		}

		/* (non-Javadoc)
		 * @see javax.servlet.http.HttpSession#isNew()
		 */
		@Override
		public boolean isNew() {
			// TODO Auto-generated method stub
			return false;
		}

		/* (non-Javadoc)
		 * @see javax.servlet.http.HttpSession#putValue(java.lang.String, java.lang.Object)
		 */
		@Override
		public void putValue(String arg0, Object arg1) {
			// TODO Auto-generated method stub
			
		}

		/* (non-Javadoc)
		 * @see javax.servlet.http.HttpSession#removeAttribute(java.lang.String)
		 */
		@Override
		public void removeAttribute(String arg0) {
			// TODO Auto-generated method stub
			
		}

		/* (non-Javadoc)
		 * @see javax.servlet.http.HttpSession#removeValue(java.lang.String)
		 */
		@Override
		public void removeValue(String arg0) {
			// TODO Auto-generated method stub
			
		}		

		/* (non-Javadoc)
		 * @see javax.servlet.http.HttpSession#setMaxInactiveInterval(int)
		 */
		@Override
		public void setMaxInactiveInterval(int arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}

}
