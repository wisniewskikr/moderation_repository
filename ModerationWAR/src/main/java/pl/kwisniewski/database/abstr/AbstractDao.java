package pl.kwisniewski.database.abstr;

public interface AbstractDao<T extends AbstractEntity> {
	
	public Long create(T entity);
	
	public T read(Long id, Class<? extends AbstractEntity> entityClass);
	
	public T update(T entity);
	
	public void delete(T entity);
	
	public void delete(Long id, Class<? extends AbstractEntity> entityClass);

}
