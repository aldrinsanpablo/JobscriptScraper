package com.fr.bi;

import java.util.Map;

public class Item {

	private String path;

	private String paramStr;

	private Map<String, String> argumentMap;

	public Item(String aPath, String aParamStr) {
		this.path = aPath;
		this.paramStr = aParamStr;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getParamStr() {
		return paramStr;
	}

	public void setParamStr(String paramStr) {
		this.paramStr = paramStr;
	}

	public Map<String, String> getArgumentMap() {
		return argumentMap;
	}
	
	public void setArgumentMap(Map<String, String> argumentMap) {
		this.argumentMap = argumentMap;
	}
	
	@Override
	public String toString() {
		return "Item [path=" + path + ", paramStr=" + paramStr + ", argumentMap=" + this.getArgumentMap() + "]";
		// return "Item [" + this.getArgumentMap() + "]";
	}
	
}
