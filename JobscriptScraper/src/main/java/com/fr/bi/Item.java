package com.fr.bi;

import java.util.Map;

public class Item {

	private String path;

	private String memoSql;

	private String sfcId;

	private String processId;

	private Map<String, String> argumentMap;

	public Item(String aPath, String aParamStr) {
		this.path = aPath;
		this.memoSql = aParamStr;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMemoSql() {
		return memoSql;
	}

	public void setMemoSql(String paramStr) {
		this.memoSql = paramStr;
	}

	public Map<String, String> getArgumentMap() {
		return argumentMap;
	}

	public void setArgumentMap(Map<String, String> argumentMap) {
		this.argumentMap = argumentMap;
	}

	public String getSfcId() {
		return sfcId;
	}

	public void setSfcId(String sfcId) {
		this.sfcId = sfcId;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	@Override
	public String toString() {
		return "Item [path=" + path + ", memoSql=" + memoSql + ", sfcId=" + sfcId + ", processId=" + processId
				+ ", argumentMap=" + argumentMap + "]";
	}

}
