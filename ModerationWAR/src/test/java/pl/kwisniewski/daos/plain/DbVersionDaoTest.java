package pl.kwisniewski.daos.plain;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import pl.kwisniewski.database.abstr.AbstractSpringDBUnitAnnotation;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/conf/spring-conf-test.xml"})
@Transactional
public class DbVersionDaoTest extends AbstractSpringDBUnitAnnotation{
	
	@Autowired
	private DbVersionDao dao;
	
	@Test
	public void getMaxDbVersion() {

		executeDataFile("dbunit/DbVersionDaoTest.xml");
		int expected = 3;
		
		int actual = dao.getMaxDbVersion();
		
		Assert.assertEquals(expected, actual);

	}



}
