package pl.kwisniewski.daos.plain;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import pl.kwisniewski.database.abstr.AbstractDaoImpl;
import pl.kwisniewski.entities.plain.DbVersionEntity;

@Repository
public class DbVersionDaoImpl extends AbstractDaoImpl<DbVersionEntity> implements DbVersionDao {

	/* (non-Javadoc)
	 * @see pl.kwisniewski.daos.plain.DbVersionDao#getMaxDbVersion()
	 */
	@Override
	public int getMaxDbVersion() {
		
		Query query = em.createNamedQuery("DbVersionEntity.getMaxDbVersion");		
		return (Integer)query.getSingleResult();
		
	}

	
}
