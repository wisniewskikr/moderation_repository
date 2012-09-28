package pl.kwisniewski.daos.plain;

import static junit.framework.Assert.assertEquals;

import java.util.List;
import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import pl.kwisniewski.daos.plain.TextModerationDao;
import pl.kwisniewski.database.abstr.AbstractSpringDBUnitAnnotation;
import pl.kwisniewski.entities.plain.TextModerationEntity;
import pl.kwisniewski.spring.enums.ModerationStatusEnum;
import pl.kwisniewski.spring.enums.ResultStatusEnum;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/conf/spring-conf-test.xml"})
@Transactional
public class TextModerationDaoTest extends AbstractSpringDBUnitAnnotation{
	
	@Autowired
	private TextModerationDao dao;
	
	@Test
	public void create() {

		executeDataFile("dbunit/TextModerationDaoTest.xml");
		long expected = 0;

		TextModerationEntity entity = new TextModerationEntity();
		entity.setCompanyId(4L);
		entity.setTextId(1L);
		entity.setTextContent("Text 4");
		entity.setModerationStatus(ModerationStatusEnum.CHECKED);
		entity.setModerationDescription("Moderation description 4");
		entity.setResultStatus(ResultStatusEnum.OK);
		entity.setResultDescription("Result descripton 4");
		dao.create(entity);

		long actual = entity.getId();

		Assert.assertNotSame(expected, actual);

	}

	@Test
	public void read() {
		
		executeDataFile("dbunit/TextModerationDaoTest.xml");
		
		long id = 1;
		TextModerationEntity entity = dao.read(id, TextModerationEntity.class);
				
		Assert.assertEquals(Long.valueOf(1), entity.getCompanyId());
		Assert.assertEquals(Long.valueOf(1), entity.getTextId());
		Assert.assertEquals("Text 1", entity.getTextContent());
		Assert.assertEquals(ModerationStatusEnum.CHECKED, entity.getModerationStatus());
		Assert.assertEquals("Moderation description 1", entity.getModerationDescription());
		Assert.assertEquals(ResultStatusEnum.OK, entity.getResultStatus());
		Assert.assertEquals("Result description 1", entity.getResultDescription());

	}	

	@Test
	public void update() {

		executeDataFile("dbunit/TextModerationDaoTest.xml");

		long id = 1;
		TextModerationEntity entity = dao.read(id, TextModerationEntity.class);
		entity.setModerationStatus(ModerationStatusEnum.REPORTED);
		dao.update(entity);

		Assert.assertEquals(Long.valueOf(1), entity.getCompanyId());
		Assert.assertEquals(Long.valueOf(1), entity.getTextId());
		Assert.assertEquals("Text 1", entity.getTextContent());
		Assert.assertEquals(ModerationStatusEnum.REPORTED, entity.getModerationStatus());
		Assert.assertEquals("Moderation description 1", entity.getModerationDescription());
		Assert.assertEquals(ResultStatusEnum.OK, entity.getResultStatus());
		Assert.assertEquals("Result description 1", entity.getResultDescription());

	}

	@Test
	public void delete() {
		
		executeDataFile("dbunit/TextModerationDaoTest.xml");
		
		long id = 1;		
		TextModerationEntity entity = dao.read(id, TextModerationEntity.class);
		dao.delete(entity);
		entity = dao.read(id, TextModerationEntity.class);
		
		Assert.assertNull(entity);

	}
	
	@Test
	public void deleteById() {
		
		executeDataFile("dbunit/TextModerationDaoTest.xml");
		
		long id = 1;
		dao.delete(id, TextModerationEntity.class);
		TextModerationEntity entity = dao.read(id, TextModerationEntity.class);
				
		Assert.assertNull(entity);

	}
	
	@Test
	public void getFirsNotChecked(){
		
		executeDataFile("dbunit/TextModerationDaoTest.xml");
		
		TextModerationEntity entity = dao.getFirsNotChecked();
		
		assertEquals(Long.valueOf(2), entity.getId());
		
	}
	
	@Test
	public void getListWithStatusNotChecked(){
		
		executeDataFile("dbunit/TextModerationDaoTest.xml");
		
		ModerationStatusEnum status = ModerationStatusEnum.NOT_CHECKED;
		List<TextModerationEntity> list = dao.getListWithStatus(status);
		
		assertEquals(2, list.size());
		assertEquals(Long.valueOf(2), list.get(0).getId());		
		assertEquals(Long.valueOf(3), list.get(1).getId());		
		
	}
	
	@Test
	public void getListWithStatusChecked(){
		
		executeDataFile("dbunit/TextModerationDaoTest.xml");
		
		ModerationStatusEnum status = ModerationStatusEnum.CHECKED;
		List<TextModerationEntity> list = dao.getListWithStatus(status);
		
		assertEquals(1, list.size());
		assertEquals(Long.valueOf(1), list.get(0).getId());		
		
	}
	
	@Test
	public void getListWithStatusChecking(){
		
		executeDataFile("dbunit/TextModerationDaoTest.xml");
		
		ModerationStatusEnum status = ModerationStatusEnum.CHECKING;
		List<TextModerationEntity> list = dao.getListWithStatus(status);
		
		assertEquals(true, list.isEmpty());
		
	}

}
