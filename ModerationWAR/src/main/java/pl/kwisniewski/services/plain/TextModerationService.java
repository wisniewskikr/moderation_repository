package pl.kwisniewski.services.plain;

import java.util.List;

import pl.kwisniewski.entities.plain.TextModerationEntity;
import pl.kwisniewski.spring.enums.ModerationStatusEnum;

public interface TextModerationService {
	
	public Long create(TextModerationEntity entity);
	
	public TextModerationEntity read(Long id);
	
	public TextModerationEntity update(TextModerationEntity entity);
	
	public void delete(TextModerationEntity entity);
	
	public void delete(Long id);
	
	/**
	 * Method gets first object TextModerationEntity with status "NOT_CHECKED".
	 * 
	 * @return object TextModerationEntity first in db with status "NOT_CHECKED"
	 */
	public TextModerationEntity getFirsNotChecked();
	
	/**
	 * Method gets list of objects TextModerationEntity with specified status.
	 * 
	 * @param status object ModerationStatusEnum specifies status
	 * @return list of objects TextModerationEntity with specified status
	 */
	public List<TextModerationEntity> getListWithStatus(ModerationStatusEnum status);

}
