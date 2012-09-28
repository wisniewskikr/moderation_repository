package pl.kwisniewski.entities.plain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import pl.kwisniewski.database.abstr.AbstractEntity;
import pl.kwisniewski.spring.enums.ModerationStatusEnum;
import pl.kwisniewski.spring.enums.ResultStatusEnum;

@NamedQueries(value = { 
	@NamedQuery(name="TextModerationEntity.getFirsNotChecked", 
		query="SELECT t FROM TextModerationEntity t WHERE t.moderationStatus = 'NOT_CHECKED' ORDER BY t.id"),
	@NamedQuery(name="TextModerationEntity.getListWithStatus", 
		query="SELECT t FROM TextModerationEntity t WHERE t.moderationStatus = :status ORDER BY t.id") 	
})

@Entity
@Table(name="text_moderation")
public class TextModerationEntity extends AbstractEntity{
	
	
	private static final long serialVersionUID = 1L;
	
	private Long companyId;
	private Long textId;
	private String textContent;
	private ModerationStatusEnum moderationStatus;
	private String moderationDescription;
	private ResultStatusEnum resultStatus;
	private String resultDescription;
	
	
	@Column(name="company_id")
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	@Column(name="text_id")
	public Long getTextId() {
		return textId;
	}
	public void setTextId(Long textId) {
		this.textId = textId;
	}
	
	@Column(name="text_content")
	public String getTextContent() {
		return textContent;
	}
	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name="moderation_status")
	public ModerationStatusEnum getModerationStatus() {
		return moderationStatus;
	}
	public void setModerationStatus(ModerationStatusEnum moderationStatus) {
		this.moderationStatus = moderationStatus;
	}
	
	@Column(name="moderation_description")
	public String getModerationDescription() {
		return moderationDescription;
	}
	public void setModerationDescription(String moderationDescription) {
		this.moderationDescription = moderationDescription;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name="result_status")
	public ResultStatusEnum getResultStatus() {
		return resultStatus;
	}
	public void setResultStatus(ResultStatusEnum resultStatus) {
		this.resultStatus = resultStatus;
	}
	
	@Column(name="result_description")
	public String getResultDescription() {
		return resultDescription;
	}
	public void setResultDescription(String resultDescription) {
		this.resultDescription = resultDescription;
	}	

}
