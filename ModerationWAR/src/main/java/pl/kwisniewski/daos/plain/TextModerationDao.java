package pl.kwisniewski.daos.plain;

import java.util.List;

import pl.kwisniewski.database.abstr.AbstractDao;
import pl.kwisniewski.entities.plain.TextModerationEntity;
import pl.kwisniewski.spring.enums.ModerationStatusEnum;

public interface TextModerationDao extends AbstractDao<TextModerationEntity>{
	
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
