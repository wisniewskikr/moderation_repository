package pl.kwisniewski.services.plain;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import pl.kwisniewski.daos.plain.DbVersionDao;

public class DbVersionServiceTest {
	
	private DbVersionServiceImpl service;
	
	@Before
	public void setUp(){
		service = new DbVersionServiceImpl();
		service.setDao(mockDbVersionDao());
	}
	
	@Test
	public void getMaxDbVersion(){
		
		int result = service.getMaxDbVersion();
		Assert.assertEquals(3, result);
	}
	
	
	// ================= HELP METHODS ===================== //
	
	
	public DbVersionDao mockDbVersionDao(){
		
		DbVersionDao mock = Mockito.mock(DbVersionDao.class);
		Mockito.when(mock.getMaxDbVersion()).thenReturn(3);
		return mock;
		
	}

}
