package pl.kwisniewski.daos.plain;

import pl.kwisniewski.database.abstr.AbstractDao;
import pl.kwisniewski.entities.plain.DbVersionEntity;

public interface DbVersionDao extends AbstractDao<DbVersionEntity>{
	
	/**
	 * Method gets max version of data base.
	 * 
	 * @return int with max version of data base
	 */
	public int getMaxDbVersion();
	
}
