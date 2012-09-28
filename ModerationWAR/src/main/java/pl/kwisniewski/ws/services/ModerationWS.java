package pl.kwisniewski.ws.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.kwisniewski.entities.plain.TextModerationEntity;
import pl.kwisniewski.spring.enums.ModerationStatusEnum;
import pl.kwisniewski.services.plain.TextModerationService;
import pl.kwisniewski.ws.elements.MessageListWS;
import pl.kwisniewski.ws.elements.MessageWS; 
import pl.kwisniewski.ws.elements.TextModerationRequestListWS;
import pl.kwisniewski.ws.elements.TextModerationRequestWS;
import pl.kwisniewski.ws.elements.TextModerationResponseListWS;
import pl.kwisniewski.ws.elements.TextModerationResponseWS;


@Path("moderation")
@Component 
public class ModerationWS{
	
	private static final Logger LOG = LoggerFactory.getLogger(ModerationWS.class);
	
	@Autowired
	public TextModerationService textModerationService;
	
		
	@POST
	@Path("provideTextModerationRequestListWS/")
	@Consumes(MediaType.APPLICATION_XML)
	public MessageListWS provideTextModerationRequestListWS(JAXBElement<TextModerationRequestListWS> element){
		
		MessageListWS response = new MessageListWS();
		
		TextModerationEntity textModerationEntity;
		
		TextModerationRequestListWS root = element.getValue();
		
		if(!isValidateTextModerationRequestListWS(response, root)){
			return response;
		}
		
		LOG.info("---company id: " + root.getCompanyId());
		
		List<TextModerationRequestWS> reqList = root.getTextModerationRequestWs();
		for (TextModerationRequestWS req : reqList) {
			
			LOG.info("---text id: " + req.getTextId());
			LOG.info("---text content: " + req.getTextContent());
			
			if(!isValidateProvidedTextModReq(response, req)){
				continue;
			}
			
			textModerationEntity = new TextModerationEntity();
			textModerationEntity.setCompanyId(root.getCompanyId());
			textModerationEntity.setTextId(req.getTextId());
			textModerationEntity.setTextContent(req.getTextContent());
			textModerationEntity.setModerationStatus(ModerationStatusEnum.NOT_CHECKED);
			
			textModerationService.create(textModerationEntity);
			
		}
		
		if(response.getMessageWs().isEmpty()){
			response = null;
		}
		
		return response;
		
	}
	
	@POST
	@Path("retrieveTextModerationResponseListWS/")
	public TextModerationResponseListWS retrieveTextModerationResponseListWS(){
		
		TextModerationResponseListWS responseList = new TextModerationResponseListWS();
		TextModerationResponseWS response;
		
		List<TextModerationEntity> listTextModeration = textModerationService.getListWithStatus(ModerationStatusEnum.CHECKED);
		
		for (TextModerationEntity textModeration : listTextModeration) {
			
			response = new TextModerationResponseWS();
			response.setTextId(textModeration.getTextId());
			response.setResult(textModeration.getResultStatus().name());
			responseList.getTextModerationResponseWs().add(response);
			
			textModeration.setModerationStatus(ModerationStatusEnum.REPORTED);
			textModerationService.update(textModeration);
			
		}
		
		return responseList;
		
	}
	
	
	// =================== HELP METHODS ================== //
	
	
	/**
	 * Method checks if object TextModerationRequestListWS is validate. If not appropriate message is created and
	 * false is returned.
	 * 
	 * @param messageList object MessageListWS with list of messages which are returned 
	 * @param reqList object TextModerationReqListWS which is checked
	 * @return true if object TextModerationRequestListWS is validate. Otherwise false
	 */
	public boolean isValidateTextModerationRequestListWS(MessageListWS messageList, TextModerationRequestListWS reqList){
		
		boolean result = true;
		
		Long companyId = reqList.getCompanyId();
		MessageWS message;
				
		if(companyId == 0){
			message = new MessageWS();
			message.setText("There is no company id in list of moderation request. This list of moderation request is skipped.");
			messageList.getMessageWs().add(message);
			return false;
		}
		
		return result;
		
	}
	
	/**
	 * Method checks if object ProvidedTextModReqWS is validate. If not appropriate message is created and
	 * false is returned.
	 * 
	 * @param messageList object MessageListWS with list of messages which are returned 
	 * @param req object ProvidedTextModReqWS which is checked
	 * @return true if object ProvidedTextModReqWS is validate. Otherwise false
	 */
	public boolean isValidateProvidedTextModReq(MessageListWS messageList, TextModerationRequestWS req){
		
		boolean result = true;
		
		Long textId = req.getTextId();
		String textContent = req.getTextContent();
		MessageWS message;
		
		if(StringUtils.isBlank(textContent) && textId == 0){
			message = new MessageWS();
			message.setText("One of moderation requests has no unique id and text content. This moderation request is skipped.");
			messageList.getMessageWs().add(message);
			return false;
		}
		
		if(StringUtils.isBlank(textContent)){
			message = new MessageWS();
			message.setText("There is no content for text with id: " + textId + ". This moderation request is skipped.");
			messageList.getMessageWs().add(message);
			return false;
		}
		
		if(textId == 0){
			message = new MessageWS();
			message.setText("There is no uniq id for text content: '" + textContent + "'. This moderation request is skipped.");
			messageList.getMessageWs().add(message);
			return false;
		}
		
		return result;
		
	}

}
