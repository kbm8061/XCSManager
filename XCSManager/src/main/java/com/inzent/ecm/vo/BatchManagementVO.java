package com.inzent.ecm.vo;

public class BatchManagementVO {
	private String server;
	private String agent;
	private String threadCnt;
	private String runYn;
	
	private int startNo;
	private int endNo;
	private int selectCount;
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	public String getThreadCnt() {
		return threadCnt;
	}
	public void setThreadCnt(String threadCnt) {
		this.threadCnt = threadCnt;
	}
	public String getRunYn() {
		return runYn;
	}
	public void setRunYn(String runYn) {
		this.runYn = runYn;
	}
	public int getStartNo() {
		return startNo;
	}
	public void setStartNo(int startNo) {
		this.startNo = startNo;
	}
	public int getEndNo() {
		return endNo;
	}
	public void setEndNo(int endNo) {
		this.endNo = endNo;
	}
	public int getSelectCount() {
		return selectCount;
	}
	public void setSelectCount(int selectCount) {
		this.selectCount = selectCount;
	}
	
}
