package pl.kwisniewski.spring.beans;

import java.io.Serializable;


public class LanguageBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String value;
	private String label;
	private boolean selected;
	
	/**
	 * Getter for property value.
	 *
	 * @return value	
	 */
	public String getValue() {
		return value;
	}
	/**
	 * Setter for property value.
	 *
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * Getter for property label.
	 *
	 * @return label	
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * Setter for property label.
	 *
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}		

}
