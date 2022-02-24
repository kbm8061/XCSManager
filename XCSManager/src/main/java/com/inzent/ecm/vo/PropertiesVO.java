package com.inzent.ecm.vo;

import org.springframework.beans.factory.annotation.Value;

/**
 * VO for Table XCSMANAGER_USER
 */
public class PropertiesVO {

	@Value("${jdbc.maxinactiveinterval}")
	private String maxinactiveinterval;

	@Value("${ecm.engineIp}")
	private String engineIp;

	@Value("${ecm.enginePort}")
	private String enginePort;

	@Value("${ecm.description}")
	private String description;

	@Value("${ecm.engineId}")
	private String engineId;

	@Value("${ecm.enginePw}")
	private String enginePw;

	@Value("${ecm.gateway}")
	private String gateway;

	@Value("${ecm.eclassId}")
	private String eclassId;

	@Value("${common.tempPath}")
	private String commonTempPath;

	public String getMaxinactiveinterval() {
		return maxinactiveinterval;
	}

	public void setMaxinactiveinterval(String maxinactiveinterval) {
		this.maxinactiveinterval = maxinactiveinterval;
	}

	public String getEngineIp() {
		return engineIp;
	}

	public void setEngineIp(String engineIp) {
		this.engineIp = engineIp;
	}

	public String getEnginePort() {
		return enginePort;
	}

	public void setEnginePort(String enginePort) {
		this.enginePort = enginePort;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEngineId() {
		return engineId;
	}

	public void setEngineId(String engineId) {
		this.engineId = engineId;
	}

	public String getEnginePw() {
		return enginePw;
	}

	public void setEnginePw(String enginePw) {
		this.enginePw = enginePw;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public String getEclassId() {
		return eclassId;
	}

	public void setEclassId(String eclassId) {
		this.eclassId = eclassId;
	}

	public String getCommonTempPath() {
		return commonTempPath;
	}

	public void setCommonTempPath(String commonTempPath) {
		this.commonTempPath = commonTempPath;
	}

}
