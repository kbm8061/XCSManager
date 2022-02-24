package com.inzent.ecm.vo;

public class StaticVO {
	private String statDate;
	private String targetCount;
	private String processCount;
	private String waitCount;
	private String successCount;
	private String failCount;
	private String startDay;
	private String endDay;
	
	private String classification;
	private String status;
	private int count;
	
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getStartDay() {
		return startDay;
	}
	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}
	public String getEndDay() {
		return endDay;
	}
	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}
	public String getStatDate() {
		return statDate;
	}
	public void setStatDate(String statDate) {
		this.statDate = statDate;
	}
	public String getTargetCount() {
		return targetCount;
	}
	public void setTargetCount(String targetCount) {
		this.targetCount = targetCount;
	}
	public String getProcessCount() {
		return processCount;
	}
	public void setProcessCount(String processCount) {
		this.processCount = processCount;
	}
	public String getWaitCount() {
		return waitCount;
	}
	public void setWaitCount(String waitCount) {
		this.waitCount = waitCount;
	}
	public String getSuccessCount() {
		return successCount;
	}
	public void setSuccessCount(String successCount) {
		this.successCount = successCount;
	}
	public String getFailCount() {
		return failCount;
	}
	public void setFailCount(String failCount) {
		this.failCount = failCount;
	}
	
}
