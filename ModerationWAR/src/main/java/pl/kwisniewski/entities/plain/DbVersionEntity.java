package pl.kwisniewski.entities.plain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import pl.kwisniewski.database.abstr.AbstractEntity;

@NamedQueries(value = { 
		@NamedQuery(name="DbVersionEntity.getMaxDbVersion", 
			query="SELECT MAX(d.version) FROM DbVersionEntity d")
	})

@Entity
@Table(name="db_version")
public class DbVersionEntity extends AbstractEntity{

	
	private static final long serialVersionUID = 1L;
	
	private int version;

	
	@Column(name="version")
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}	

}
