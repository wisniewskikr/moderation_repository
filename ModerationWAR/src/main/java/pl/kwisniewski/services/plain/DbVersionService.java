package pl.kwisniewski.services.plain;

public interface DbVersionService {
	
	/**
	 * Method gets max version of data base.
	 * 
	 * @return int with max version of data base
	 */
	public int getMaxDbVersion();

}
