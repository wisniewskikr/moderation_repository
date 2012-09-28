package pl.kwisniewski.controllers;

import static junit.framework.Assert.assertEquals;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/conf/spring-conf-test.xml"})
public class LoginControllerWithContextTest {
	
	@Autowired
	private LoginController controller;
	
	
	@Test
	public void addLocalizedLoginPageLabelsToSessionEN(){
		
		HttpSession session = new TestSession();
		Locale locale = Locale.ENGLISH;
		
		controller.addLocalizedLoginPageLabelsToSession(session, locale);
		
		assertEquals("Language", session.getAttribute("language"));
		assertEquals("Login", session.getAttribute("login"));
		assertEquals("Password", session.getAttribute("password"));
		assertEquals("Login", session.getAttribute("loginButton"));
		
	}
	
	@Test
	public void addLocalizedLoginPageLabelsToSessionDE(){
		
		HttpSession session = new TestSession();
		Locale locale = Locale.GERMAN;
		
		controller.addLocalizedLoginPageLabelsToSession(session, locale);
		
		assertEquals("Language_de", session.getAttribute("language"));
		assertEquals("Login_de", session.getAttribute("login"));
		assertEquals("Password_de", session.getAttribute("password"));
		assertEquals("Login_de", session.getAttribute("loginButton"));
		
	}
	
	
	// ============= MOCKS AND HELP METHODS ============== //
	
	
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
