package com.jizhi.model;

import java.io.Serializable;

public class SysConfig implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private String configType;
	private String configValue;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getConfigType() {
		return configType;
	}
	public void setConfigType(String configType) {
		this.configType = configType;
	}
	public String getConfigValue() {
		return configValue;
	}
	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}
}
