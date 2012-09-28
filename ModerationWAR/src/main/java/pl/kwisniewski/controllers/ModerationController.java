package pl.kwisniewski.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import pl.kwisniewski.commands.ModerationCommand;
import pl.kwisniewski.entities.plain.TextModerationEntity;
import pl.kwisniewski.services.plain.TextModerationService;
import pl.kwisniewski.spring.enums.ModerationStatusEnum;
import pl.kwisniewski.spring.enums.ModerationTypeEnum;
import pl.kwisniewski.spring.enums.ResultStatusEnum;

@Controller
public class ModerationController{

	private final static Logger LOGGER = Logger.getLogger(ModerationController.class.getName());
	
	@Autowired
	public Validator validator;
	
	@Autowired
	public TextModerationService textModerationService;
	
		
	/**
	 * Method inits controller.
	 * 
	 * @param model object ModelMap with model
	 * @param request object HttpServletRequest with request
	 * @param response object HttpServletResponse with response
	 * @param loc object Locale with locale
	 * @return object ModelAndView with model and view
	 */
	@RequestMapping("/moderationController_init.do")
	public ModelAndView init(ModelMap model, 
			HttpServletRequest request, HttpServletResponse response){
		
		ModerationCommand command = new ModerationCommand();
		model.addAttribute("command", command);
		
		TextModerationEntity textModeration = textModerationService.getFirsNotChecked();
		
		if(textModeration == null){
			return new ModelAndView("ModerationEmptyDbJSP");			
		}
		
		updateCommandWithTextModerationData(textModeration, command);
		setTextModerationStatusOnChecking(textModeration);
		
		return new ModelAndView("ModerationJSP");
		
	}
	
	@RequestMapping("/moderationController_next.do")
	public ModelAndView next(@ModelAttribute("command")ModerationCommand command, 
			BindingResult result, HttpServletRequest request, HttpServletResponse response){
		
		validator.validate(command, result);
		if(result.hasErrors()){
			return new ModelAndView("ModerationJSP");
		}
		
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		
		TextModerationEntity textModeration = textModerationService.read(command.getIdTextModeration());
		textModeration.setResultStatus(ResultStatusEnum.valueOf(command.getResult()));
		textModeration.setModerationStatus(ModerationStatusEnum.CHECKED);
		textModerationService.update(textModeration);
						
		return new ModelAndView(new RedirectView("moderationController_init.do"), parameterMap);
		
	}
	
	@RequestMapping("/moderationController_refresh.do")
	public ModelAndView refresh(@ModelAttribute("command")ModerationCommand command, 
			BindingResult result, HttpServletRequest request, HttpServletResponse response){
		
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		
		return new ModelAndView(new RedirectView("moderationController_init.do"), parameterMap);
		
	}
	
	/**
	 * Method updates object ModerationCommnand with data from TextModerationEntity.
	 * 
	 * @param textModeration object TextModerationEntity with text moderation data from data base 
	 * @param command object ModerationCommand with data displayed in GUI
	 */
	public void updateCommandWithTextModerationData(TextModerationEntity textModeration, ModerationCommand command){
		
		if(textModeration == null){
			return;
		}
		
		command.setIdTextModeration(textModeration.getId());
		command.setTextContent(textModeration.getTextContent());
		command.setModerationObjectId(textModeration.getId().toString());
		command.setModerationType(ModerationTypeEnum.TEXT.name());
				
	}
	
	/**
	 * Method sets status of text moderation to "checking".
	 * 
	 * @param textModeration object TextModerationEntity with text moderation data from data base
	 */
	public void setTextModerationStatusOnChecking(TextModerationEntity textModeration){
		
		if(textModeration == null){
			return;
		}
			
		textModeration.setModerationStatus(ModerationStatusEnum.CHECKING);
		textModerationService.update(textModeration);
		
	}

}
