package com.sample.bean;


import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.sample.ejb.SingletonBean;
import com.sample.model.Property;

 

 

@ManagedBean(name="manager")
public class PropertyManager {
  
	@EJB
	SingletonBean ejb;

	ArrayList  cacheList  = new ArrayList ();
	/**
	 * 
	 */
	private Property selectedProp;
	public Property getSelectedProp() {
		return selectedProp;
	}

	public void setSelectedProp(Property selectedProp) {
		this.selectedProp = selectedProp;
	}
	private String key;
	private String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void save() {
		System.out.println("Saved!");
		ejb.put(key, value);
	}

	public void clear() {
		 
		System.out.println("Removing "+selectedProp);
		ejb.delete(selectedProp);

	}
	public List getCacheList() {
		return ejb.getCache();
	}


}
