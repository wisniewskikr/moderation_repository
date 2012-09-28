package pl.kwisniewski.daos.plain;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import pl.kwisniewski.database.abstr.AbstractDaoImpl;
import pl.kwisniewski.entities.plain.TextModerationEntity;
import pl.kwisniewski.spring.enums.ModerationStatusEnum;

@Repository
public class TextModerationDaoImpl extends AbstractDaoImpl<TextModerationEntity> implements TextModerationDao {

	/* (non-Javadoc)
	 * @see pl.kwisniewski.daos.plain.TextModerationDao#getFirsNotChecked()
	 */
	@Override
	public TextModerationEntity getFirsNotChecked() {
		
		Query query = em.createNamedQuery("TextModerationEntity.getFirsNotChecked");
		List<TextModerationEntity> list = query.getResultList();
		
		if(!list.isEmpty()){
			return list.get(0);			
		}else{
			return null;
		}
		
	}
	
	/* (non-Javadoc)
	 * @see pl.kwisniewski.daos.plain.TextModerationDao#getListWithStatus(pl.kwisniewski.spring.enums.ModerationStatusEnum)
	 */
	public List<TextModerationEntity> getListWithStatus(ModerationStatusEnum status){
		
		Query query = em.createNamedQuery("TextModerationEntity.getListWithStatus");
		query.setParameter("status", status);
		return query.getResultList();
		
	}

	
}
