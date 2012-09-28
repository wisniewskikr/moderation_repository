package pl.kwisniewski.services.plain;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import pl.kwisniewski.daos.plain.TextModerationDao;
import pl.kwisniewski.entities.plain.TextModerationEntity;
import pl.kwisniewski.spring.enums.ModerationStatusEnum;

public class TextModerationServiceTest {
	
	TextModerationServiceImpl textModerationService;
	
	@Before
	public void setUp(){
		textModerationService = new TextModerationServiceImpl();
		textModerationService.setDao(mockTextModerationDao());
	}
	
	@Test
	public void getListWithStatusNotChecked(){
		
		ModerationStatusEnum status = ModerationStatusEnum.NOT_CHECKED;
		List<TextModerationEntity> list = textModerationService.getListWithStatus(status);
		Assert.assertEquals(2, list.size());
		Assert.assertEquals(Long.valueOf(1), list.get(0).getId());
		Assert.assertEquals(Long.valueOf(2), list.get(1).getId());
		
	}
	
	@Test
	public void getListWithStatusChecked(){
		
		ModerationStatusEnum status = ModerationStatusEnum.CHECKED;
		List<TextModerationEntity> list = textModerationService.getListWithStatus(status);
		Assert.assertEquals(true, list.isEmpty());
		
	}
	
	@Test
	public void getListWithStatusChecking(){
		
		ModerationStatusEnum status = ModerationStatusEnum.CHECKING;
		List<TextModerationEntity> list = textModerationService.getListWithStatus(status);
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(Long.valueOf(3), list.get(0).getId());
		
	}
	
	
	// ====================== HELP METHODS ========================== //
	
	private TextModerationDao mockTextModerationDao(){
		
		List<TextModerationEntity> listNotChecked = new ArrayList<TextModerationEntity>();
		TextModerationEntity entity;
		
		entity = new TextModerationEntity();
		entity.setId(1L);
		listNotChecked.add(entity);
		entity = new TextModerationEntity();
		entity.setId(2L);
		listNotChecked.add(entity);
		
		List<TextModerationEntity> listChecked = new ArrayList<TextModerationEntity>();
		
		List<TextModerationEntity> listChecking = new ArrayList<TextModerationEntity>();
		entity = new TextModerationEntity();
		entity.setId(3L);
		listChecking.add(entity);
		
		TextModerationDao mock = Mockito.mock(TextModerationDao.class);
		Mockito.when(mock.getListWithStatus(Mockito.eq(ModerationStatusEnum.NOT_CHECKED))).thenReturn(listNotChecked);
		Mockito.when(mock.getListWithStatus(Mockito.eq(ModerationStatusEnum.CHECKED))).thenReturn(listChecked);
		Mockito.when(mock.getListWithStatus(Mockito.eq(ModerationStatusEnum.CHECKING))).thenReturn(listChecking);
		return mock;
		
	}

}
