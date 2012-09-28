package pl.kwisniewski.ws.services;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.junit.Before;
import org.junit.Test;

import pl.kwisniewski.entities.plain.TextModerationEntity;
import pl.kwisniewski.services.plain.TextModerationService;
import pl.kwisniewski.spring.enums.ModerationStatusEnum;
import pl.kwisniewski.spring.enums.ResultStatusEnum;
import pl.kwisniewski.ws.elements.MessageListWS;
import pl.kwisniewski.ws.elements.TextModerationRequestListWS;
import pl.kwisniewski.ws.elements.TextModerationRequestWS; 
import pl.kwisniewski.ws.elements.TextModerationResponseListWS;
import pl.kwisniewski.ws.elements.TextModerationResponseWS;

public class ModerationWSTest {
	
	ModerationWS moderationWS;
	
	@Before
	public void setUp(){
		moderationWS = new ModerationWS();
		moderationWS.textModerationService = mockTextModerationService();
	}
	
	@Test
	public void provideTextModerationRequestListWS(){
		
		TextModerationRequestWS req = new TextModerationRequestWS();
		req.setTextId(1L);
		req.setTextContent("Test content");
		
		TextModerationRequestListWS reqList = new TextModerationRequestListWS();
		reqList.setCompanyId(1L);
		reqList.getTextModerationRequestWs().add(req);
		
		JAXBElement<TextModerationRequestListWS> element = new JAXBElement<TextModerationRequestListWS>(new QName(""), TextModerationRequestListWS.class, reqList);
		
		MessageListWS messageList = moderationWS.provideTextModerationRequestListWS(element);
		
		assertEquals(null, messageList);
		
	}
	
	@Test
	public void provideTextModerationRequestListWSNoTextId(){
		
		TextModerationRequestWS req = new TextModerationRequestWS();
		req.setTextContent("Test content");
		
		TextModerationRequestListWS reqList = new TextModerationRequestListWS();
		reqList.setCompanyId(1L);
		reqList.getTextModerationRequestWs().add(req);
		
		JAXBElement<TextModerationRequestListWS> element = new JAXBElement<TextModerationRequestListWS>(new QName(""), TextModerationRequestListWS.class, reqList);
		
		MessageListWS messageList = moderationWS.provideTextModerationRequestListWS(element);
		
		String actualText = messageList.getMessageWs().get(0).getText();
		String expectedText = "There is no uniq id for text content: 'Test content'. This moderation request is skipped.";
		assertEquals(expectedText, actualText);
		
	}
	
	@Test
	public void provideTextModerationRequestListWSNoTextContent(){
		
		TextModerationRequestWS req = new TextModerationRequestWS();
		req.setTextId(1L);
		
		TextModerationRequestListWS reqList = new TextModerationRequestListWS();
		reqList.setCompanyId(1L);
		reqList.getTextModerationRequestWs().add(req);
		
		JAXBElement<TextModerationRequestListWS> element = new JAXBElement<TextModerationRequestListWS>(new QName(""), TextModerationRequestListWS.class, reqList);
		
		MessageListWS messageList = moderationWS.provideTextModerationRequestListWS(element);
		
		String actualText = messageList.getMessageWs().get(0).getText();
		String expectedText = "There is no content for text with id: 1. This moderation request is skipped.";
		assertEquals(expectedText, actualText);	
		
	}
	
	@Test
	public void provideTextModerationRequestListWSNoTextIdAndTextContent(){
		
		TextModerationRequestWS req = new TextModerationRequestWS();
		
		TextModerationRequestListWS reqList = new TextModerationRequestListWS();
		reqList.setCompanyId(1L);
		reqList.getTextModerationRequestWs().add(req);
		
		JAXBElement<TextModerationRequestListWS> element = new JAXBElement<TextModerationRequestListWS>(new QName(""), TextModerationRequestListWS.class, reqList);
		
		MessageListWS messageList = moderationWS.provideTextModerationRequestListWS(element);
		
		String actualText = messageList.getMessageWs().get(0).getText();
		String expectedText = "One of moderation requests has no unique id and text content. This moderation request is skipped.";
		assertEquals(expectedText, actualText);	
		
	}
	
	@Test
	public void isValidateTextModerationRequestListWS(){
		
		MessageListWS messageList = new MessageListWS();
		TextModerationRequestListWS reqList = new TextModerationRequestListWS();
		reqList.setCompanyId(1L);
		
		boolean result = moderationWS.isValidateTextModerationRequestListWS(messageList, reqList);
		
		assertEquals(true, result);
		
	}
	
	@Test
	public void isValidateTextModerationRequestListWSNoCompanyId(){
		
		MessageListWS messageList = new MessageListWS();
		TextModerationRequestListWS reqList = new TextModerationRequestListWS();
		
		boolean result = moderationWS.isValidateTextModerationRequestListWS(messageList, reqList);
		
		String actualText = messageList.getMessageWs().get(0).getText();
		String expectedText = "There is no company id in list of moderation request. This list of moderation request is skipped.";
		
		assertEquals(false, result);
		assertEquals(expectedText, actualText);
		
	}
	
	@Test
	public void isValidateProvidedTextModReq(){
		
		MessageListWS messageList = new MessageListWS();
		TextModerationRequestWS req = new TextModerationRequestWS();
		req.setTextId(1L);
		req.setTextContent("Test content");
		
		boolean result = moderationWS.isValidateProvidedTextModReq(messageList, req);
		
		assertEquals(true, result);		
		
	}
	
	@Test
	public void isValidateProvidedTextModReqNoIdAndContent(){
		
		MessageListWS messageList = new MessageListWS();
		TextModerationRequestWS req = new TextModerationRequestWS();
		
		boolean result = moderationWS.isValidateProvidedTextModReq(messageList, req);
		
		String actualText = messageList.getMessageWs().get(0).getText();
		String expectedText = "One of moderation requests has no unique id and text content. This moderation request is skipped.";
		assertEquals(false, result);
		assertEquals(expectedText, actualText);		
		
	}
	
	@Test
	public void isValidateProvidedTextModReqNoContent(){
		
		MessageListWS messageList = new MessageListWS();
		TextModerationRequestWS req = new TextModerationRequestWS();
		req.setTextId(1L);
		
		boolean result = moderationWS.isValidateProvidedTextModReq(messageList, req);
		
		String actualText = messageList.getMessageWs().get(0).getText();
		String expectedText = "There is no content for text with id: 1. This moderation request is skipped.";
		assertEquals(false, result);
		assertEquals(expectedText, actualText);	
		
	}
	
	@Test
	public void isValidateProvidedTextModReqNoId(){
		
		MessageListWS messageList = new MessageListWS();
		TextModerationRequestWS req = new TextModerationRequestWS();
		req.setTextContent("Test content");
		
		boolean result = moderationWS.isValidateProvidedTextModReq(messageList, req);
		
		String actualText = messageList.getMessageWs().get(0).getText();
		String expectedText = "There is no uniq id for text content: 'Test content'. This moderation request is skipped.";
		assertEquals(false, result);
		assertEquals(expectedText, actualText);		
		
	}
	
	@Test
	public void retrieveTextModerationResponseListWS(){
		
		List<TextModerationEntity> listTextModeration = new ArrayList<TextModerationEntity>();
		TextModerationEntity textModeration;
		
		textModeration = new TextModerationEntity();
		textModeration.setTextId(1L);
		textModeration.setResultStatus(ResultStatusEnum.OK);
		textModeration.setModerationStatus(ModerationStatusEnum.CHECKED);
		listTextModeration.add(textModeration);
		
		textModeration = new TextModerationEntity();
		textModeration.setTextId(2L);
		textModeration.setResultStatus(ResultStatusEnum.PROBLEM);
		textModeration.setModerationStatus(ModerationStatusEnum.CHECKED);
		listTextModeration.add(textModeration);
		
		textModeration = new TextModerationEntity();
		textModeration.setTextId(3L);
		textModeration.setResultStatus(ResultStatusEnum.WRONG);
		textModeration.setModerationStatus(ModerationStatusEnum.CHECKED);
		listTextModeration.add(textModeration);
		
		moderationWS.textModerationService = mockTextModerationServiceGetListWithStatus(listTextModeration);
		TextModerationResponseListWS responseListWS = moderationWS.retrieveTextModerationResponseListWS();
		
		List<TextModerationResponseWS> responseList = responseListWS.getTextModerationResponseWs();
		
		assertEquals(3, responseList.size());
		
		assertEquals(1, responseList.get(0).getTextId());
		assertEquals(ResultStatusEnum.OK.name(), responseList.get(0).getResult());
		assertEquals(ModerationStatusEnum.REPORTED, listTextModeration.get(0).getModerationStatus());
		
		assertEquals(2, responseList.get(1).getTextId());
		assertEquals(ResultStatusEnum.PROBLEM.name(), responseList.get(1).getResult());
		assertEquals(ModerationStatusEnum.REPORTED, listTextModeration.get(1).getModerationStatus());
		
		assertEquals(3, responseList.get(2).getTextId());
		assertEquals(ResultStatusEnum.WRONG.name(), responseList.get(2).getResult());
		assertEquals(ModerationStatusEnum.REPORTED, listTextModeration.get(2).getModerationStatus());
		
	}
	
	@Test
	public void retrieveTextModerationResponseListWSEmpty(){
		
		List<TextModerationEntity> listTextModeration = new ArrayList<TextModerationEntity>();
		
		moderationWS.textModerationService = mockTextModerationServiceGetListWithStatus(listTextModeration);
		TextModerationResponseListWS responseListWS = moderationWS.retrieveTextModerationResponseListWS();
		
		List<TextModerationResponseWS> responseList = responseListWS.getTextModerationResponseWs();
		
		assertEquals(true, responseList.isEmpty());
		
	}
	
	// ============== HELP METHODS ================== //
	
	private TextModerationService mockTextModerationService(){
		
		TextModerationService mock = mock(TextModerationService.class);
		return mock;
		
	}
	
	private TextModerationService mockTextModerationServiceGetListWithStatus(List<TextModerationEntity> listTextModeration){
		
		TextModerationService mock = mock(TextModerationService.class);
		when(mock.getListWithStatus(any(ModerationStatusEnum.class))).thenReturn(listTextModeration);
		return mock;
		
	}

}
