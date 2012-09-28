package pl.kwisniewski.services.plain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.kwisniewski.daos.plain.TextModerationDao;
import pl.kwisniewski.entities.plain.TextModerationEntity;
import pl.kwisniewski.spring.enums.ModerationStatusEnum;

@Service
@Transactional
public class TextModerationServiceImpl implements TextModerationService {

	@Autowired
	private TextModerationDao dao;

	public Long create(TextModerationEntity entity) {
		return dao.create(entity);
	}

	public void delete(TextModerationEntity entity) {
		dao.delete(entity);		
	}

	public TextModerationEntity read(Long id) {
		return dao.read(id, TextModerationEntity.class);
	}

	public TextModerationEntity update(TextModerationEntity entity) {
		return dao.update(entity);		
	}	
	
	public void delete(Long id){
		dao.delete(id, TextModerationEntity.class);
	}

	@Override
	public TextModerationEntity getFirsNotChecked() {
		return dao.getFirsNotChecked();
	}

	@Override
	public List<TextModerationEntity> getListWithStatus(
			ModerationStatusEnum status) {
		return dao.getListWithStatus(status);
	}

	
	// ===================== GETTERS AND SETTERS ================================ //
	
	public void setDao(TextModerationDao dao) {
		this.dao = dao;
	}	

}
