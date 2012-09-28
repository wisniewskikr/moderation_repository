package pl.kwisniewski.database.abstr;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractDaoImpl<T extends AbstractEntity> {	
	
	private static Logger LOG = LoggerFactory.getLogger(AbstractDaoImpl.class);
	
	@PersistenceContext
	protected EntityManager em;
			
	public Long create(T entity) {		
		em.persist(entity);
		return entity.getId();
	}
	
	public T read(Long id, Class<? extends AbstractEntity> entityClass){
		return (T)em.find(entityClass, id);
	}
	
	public T update(T entity){		
		return em.merge(entity);
	}
	
	public void delete(T entity){
		T deletedEntity = null;
		deletedEntity = em.merge(entity);
		em.remove(deletedEntity);
	}
	
	public void delete(Long id, Class<? extends AbstractEntity> entityClass){
		T entity = read(id, entityClass);
		delete(entity);
	}

}
