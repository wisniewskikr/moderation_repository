package pl.kwisniewski.spring.abstracts;

import java.io.Serializable;

public abstract class AbstrModerationCommand implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	protected String moderationObjectId;
	protected String moderationType;
	
	
	public String getModerationObjectId() {
		return moderationObjectId;
	}
	public void setModerationObjectId(String moderationObjectId) {
		this.moderationObjectId = moderationObjectId;
	}
	
	public String getModerationType() {
		return moderationType;
	}
	public void setModerationType(String moderationType) {
		this.moderationType = moderationType;
	}
	
}
