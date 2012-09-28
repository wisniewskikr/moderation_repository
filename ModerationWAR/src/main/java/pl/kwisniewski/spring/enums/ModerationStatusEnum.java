package pl.kwisniewski.spring.enums;

public enum ModerationStatusEnum {
	
	NOT_CHECKED("1", "Not Checked"),
	CHECKED("2", "Checked"),
	REPORTED("3", "Reported"),
	CHECKING("4", "Checking");
	
	private String id;
	private String name;
	
	private ModerationStatusEnum(String id, String name){
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}	

}
