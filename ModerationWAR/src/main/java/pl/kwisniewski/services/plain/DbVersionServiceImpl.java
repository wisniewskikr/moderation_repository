package pl.kwisniewski.services.plain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.kwisniewski.daos.plain.DbVersionDao;

@Service
@Transactional
public class DbVersionServiceImpl implements DbVersionService {

	@Autowired
	private DbVersionDao dao;

	/* (non-Javadoc)
	 * @see pl.kwisniewski.services.plain.DbVersionService#getMaxDbVersion()
	 */
	@Override
	public int getMaxDbVersion() {
		return dao.getMaxDbVersion();
	}
	
	
	// =================== GETTERS AND SETTERS ====================== //
	
	
	public void setDao(DbVersionDao dao) {
		this.dao = dao;
	}

}
