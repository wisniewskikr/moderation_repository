package pl.kwisniewski.spring.enums;

public enum ResultStatusEnum {
	
	OK("1", "Ok"),
	WRONG("2", "Wrong"),
	PROBLEM("3", "Problem");
	
	private String id;
	private String name;
	
	private ResultStatusEnum(String id, String name){
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
