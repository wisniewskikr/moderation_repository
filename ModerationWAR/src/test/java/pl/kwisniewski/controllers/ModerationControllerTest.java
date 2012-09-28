package pl.kwisniewski.controllers;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.ModelAndView;

import pl.kwisniewski.commands.ModerationCommand;
import pl.kwisniewski.entities.plain.TextModerationEntity;
import pl.kwisniewski.services.plain.TextModerationService;
import pl.kwisniewski.spring.enums.ModerationStatusEnum;
import pl.kwisniewski.spring.enums.ModerationTypeEnum;
import pl.kwisniewski.spring.enums.ResultStatusEnum;

public class ModerationControllerTest {
	
	
	private ModerationController moderationController;
	
	@Before
	public void setUp(){
		moderationController = new ModerationController();
		moderationController.validator = mockValidator();
		moderationController.textModerationService = mockTextModerationService();
		
	}
	
	@Test
	public void init(){
		
		ModelMap model = new ModelMap();
		HttpServletRequest request = mockHttpServletRequest();
		HttpServletResponse response = mockHttpServletResponse();
		
		ModelAndView modelAndView = moderationController.init(model, request, response);
		
		ModerationCommand command = (ModerationCommand)model.get("command");		
		assertEquals(Long.valueOf(1), command.getIdTextModeration());
		assertEquals("Content", command.getTextContent());
		assertEquals("1", command.getModerationObjectId());
		assertEquals(ModerationTypeEnum.TEXT.name(), command.getModerationType());
		
		assertEquals("ModerationJSP", modelAndView.getViewName());
		
	}
	
	@Test
	public void initSkip(){
		
		ModelMap model = new ModelMap();
		HttpServletRequest request = mockHttpServletRequest();
		HttpServletResponse response = mockHttpServletResponse();
		moderationController.textModerationService = mockTextModerationServiceNullTextModeration();
		
		ModelAndView modelAndView = moderationController.init(model, request, response);
		
		ModerationCommand command = (ModerationCommand)model.get("command");		
		assertEquals(null, command.getIdTextModeration());
		assertEquals(null, command.getTextContent());
		assertEquals(null, command.getModerationObjectId());
		assertEquals(null, command.getModerationType());
		
		assertEquals("ModerationEmptyDbJSP", modelAndView.getViewName());
		
	}
	
	@Test
	public void nextResultStatusOK(){
		
		TextModerationEntity textModeration = new TextModerationEntity();
		textModeration.setId(Long.valueOf(1));
		textModeration.setTextContent("Content");
		
		ModerationCommand command = new ModerationCommand();
		command.setResult("OK");
		BindingResult result = mockBindingResultNoErrors();
		HttpServletRequest request = mockHttpServletRequest();
		HttpServletResponse response = mockHttpServletResponse();
		moderationController.textModerationService = mockTextModerationService(textModeration);
		
		ModelAndView modelAndView = moderationController.next(command, result, request, response);
		
		assertEquals(ResultStatusEnum.OK, textModeration.getResultStatus());
		assertEquals(ModerationStatusEnum.CHECKED, textModeration.getModerationStatus());
		
		assertEquals(true, modelAndView.getView().toString().contains("moderationController_init.do"));
		
	}
	
	@Test
	public void nextResultStatusPROBLEM(){
		
		TextModerationEntity textModeration = new TextModerationEntity();
		textModeration.setId(Long.valueOf(1));
		textModeration.setTextContent("Content");
		
		ModerationCommand command = new ModerationCommand();
		command.setResult("PROBLEM");
		BindingResult result = mockBindingResultNoErrors();
		HttpServletRequest request = mockHttpServletRequest();
		HttpServletResponse response = mockHttpServletResponse();
		moderationController.textModerationService = mockTextModerationService(textModeration);
		
		ModelAndView modelAndView = moderationController.next(command, result, request, response);
		
		assertEquals(ResultStatusEnum.PROBLEM, textModeration.getResultStatus());
		assertEquals(ModerationStatusEnum.CHECKED, textModeration.getModerationStatus());
		
		assertEquals(true, modelAndView.getView().toString().contains("moderationController_init.do"));
		
	}
	
	@Test
	public void nextResultStatusWRONG(){
		
		TextModerationEntity textModeration = new TextModerationEntity();
		textModeration.setId(Long.valueOf(1));
		textModeration.setTextContent("Content");
		
		ModerationCommand command = new ModerationCommand();
		command.setResult("WRONG");
		BindingResult result = mockBindingResultNoErrors();
		HttpServletRequest request = mockHttpServletRequest();
		HttpServletResponse response = mockHttpServletResponse();
		moderationController.textModerationService = mockTextModerationService(textModeration);
		
		ModelAndView modelAndView = moderationController.next(command, result, request, response);
		
		assertEquals(ResultStatusEnum.WRONG, textModeration.getResultStatus());
		assertEquals(ModerationStatusEnum.CHECKED, textModeration.getModerationStatus());
		
		assertEquals(true, modelAndView.getView().toString().contains("moderationController_init.do"));
		
	}
	
	@Test
	public void nextWithValidationError(){
		
		TextModerationEntity textModeration = new TextModerationEntity();
		textModeration.setId(Long.valueOf(1));
		textModeration.setTextContent("Content");
		
		ModerationCommand command = new ModerationCommand();
		command.setResult("WRONG");
		BindingResult result = mockBindingResultWithErrors();
		HttpServletRequest request = mockHttpServletRequest();
		HttpServletResponse response = mockHttpServletResponse();
		moderationController.textModerationService = mockTextModerationService(textModeration);
		
		ModelAndView modelAndView = moderationController.next(command, result, request, response);
		
		assertEquals("ModerationJSP", modelAndView.getViewName());
		
	}
	
	@Test
	public void updateCommandWithTextModerationData(){
		
		TextModerationEntity textModeration = new TextModerationEntity();
		textModeration.setId(Long.valueOf(1));
		textModeration.setTextContent("Content");
		ModerationCommand command = new ModerationCommand();
		
		moderationController.updateCommandWithTextModerationData(textModeration, command);
		
		assertEquals(Long.valueOf(1), command.getIdTextModeration());
		assertEquals("Content", command.getTextContent());
		assertEquals("1", command.getModerationObjectId());
		assertEquals(ModerationTypeEnum.TEXT.name(), command.getModerationType());
		
	}
	
	@Test
	public void updateCommandWithTextModerationDataSkip(){
		
		TextModerationEntity textModeration = null; 
		ModerationCommand command = new ModerationCommand();
		
		moderationController.updateCommandWithTextModerationData(textModeration, command);
		
		assertEquals(null, command.getIdTextModeration());
		assertEquals(null, command.getTextContent());
		assertEquals(null, command.getModerationObjectId());
		assertEquals(null, command.getModerationType());
		
	}
	
	@Test
	public void setTextModerationStatusOnChecking(){
		
		TextModerationEntity textModeration = new TextModerationEntity();
		moderationController.setTextModerationStatusOnChecking(textModeration);
		
		assertEquals(ModerationStatusEnum.CHECKING, textModeration.getModerationStatus());
		
	}
	
	
	@Test
	public void setTextModerationStatusOnCheckingSkip(){
		
		TextModerationEntity textModeration = null;
		moderationController.setTextModerationStatusOnChecking(textModeration);
		
		assertEquals(null, textModeration);
		
	}
	
	// ============= MOCKS AND HELP METHODS ============== //
	
	private HttpServletRequest mockHttpServletRequest(){
		
		HttpServletRequest mock = Mockito.mock(HttpServletRequest.class);
		return mock;
		
	}
	
	private HttpServletResponse mockHttpServletResponse(){
		
		HttpServletResponse mock = Mockito.mock(HttpServletResponse.class);
		return mock;
		
	}
	
	private Validator mockValidator(){
		
		Validator mock = mock(Validator.class);
		return mock;
		
	}
	
	private TextModerationService mockTextModerationService(){
		
		TextModerationEntity textModeration = new TextModerationEntity();
		textModeration.setId(Long.valueOf(1));
		textModeration.setTextContent("Content");
		
		TextModerationService mock = mock(TextModerationService.class);
		when(mock.getFirsNotChecked()).thenReturn(textModeration);
		return mock;
		
	}
	
	private TextModerationService mockTextModerationService(TextModerationEntity textModeration){
				
		TextModerationService mock = mock(TextModerationService.class);
		when(mock.read(Mockito.anyLong())).thenReturn(textModeration);
		return mock;
		
	}
	
	private TextModerationService mockTextModerationServiceNullTextModeration(){
		
		TextModerationEntity textModeration = null;
		
		TextModerationService mock = mock(TextModerationService.class);
		when(mock.getFirsNotChecked()).thenReturn(textModeration);
		return mock;
		
	}
	
	private BindingResult mockBindingResultNoErrors(){
		
		BindingResult mock = mock(BindingResult.class);
		when(mock.hasErrors()).thenReturn(Boolean.FALSE);
		return mock;
		
	}
	
	private BindingResult mockBindingResultWithErrors(){
		
		BindingResult mock = mock(BindingResult.class);
		when(mock.hasErrors()).thenReturn(Boolean.TRUE);
		return mock;
		
	}

}
