package pl.kwisniewski.commands;

import org.springmodules.validation.bean.conf.loader.annotation.handler.Length;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

import pl.kwisniewski.spring.abstracts.AbstrModerationCommand;

public class ModerationCommand extends AbstrModerationCommand{
	
	private static final long serialVersionUID = 1L;
	
	
	private Long idTextModeration;
	@Length(min = 1, message = "error.textNotEmpty")
	private String textContent;
	@NotNull(message = "error.selectOneAssesment")
	private String result;
			
	
	public Long getIdTextModeration() {
		return idTextModeration;
	}
	public void setIdTextModeration(Long idTextModeration) {
		this.idTextModeration = idTextModeration;
	}
	
	public String getTextContent() {
		return textContent;
	}
	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
			
}
