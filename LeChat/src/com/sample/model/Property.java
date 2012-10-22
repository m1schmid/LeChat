package com.sample.model;

public class Property {
	@Override
	public String toString() {
		return "Property [key=" + key + ", value=" + value + "]";
	}

 
private String key;

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
  
 
private String value;
}
