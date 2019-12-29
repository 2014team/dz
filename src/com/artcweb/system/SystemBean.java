package com.artcweb.system;

import java.io.Serializable;

public class SystemBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String userName;//用户名
	private String computerName;//计算机名
	private String userDomain;//计算机域名
	private String ip;//ip
	private String hostName;;//本地主机名

	
	
	private String systemVersion;;//操作系统的版本
	private String systemName;;//操作系统的名称
	private String systemArch;;//操作系统的构架

	
	private String jdkVersion;//JDK版本
	private String jdkPath;//jdk路径
	
	private String memoryTotal;//内存总量
	private String memoryUsed;//当前内存使用量
	private String memoryFree;//当前内存剩余量
	
	public String getUserName() {
	
		return userName;
	}
	
	public void setUserName(String userName) {
	
		this.userName = userName;
	}
	
	public String getComputerName() {
	
		return computerName;
	}
	
	public void setComputerName(String computerName) {
	
		this.computerName = computerName;
	}
	
	public String getUserDomain() {
	
		return userDomain;
	}
	
	public void setUserDomain(String userDomain) {
	
		this.userDomain = userDomain;
	}
	
	public String getIp() {
	
		return ip;
	}
	
	public void setIp(String ip) {
	
		this.ip = ip;
	}
	
	public String getHostName() {
	
		return hostName;
	}
	
	public void setHostName(String hostName) {
	
		this.hostName = hostName;
	}
	
	public String getSystemVersion() {
	
		return systemVersion;
	}
	
	public void setSystemVersion(String systemVersion) {
	
		this.systemVersion = systemVersion;
	}
	
	public String getSystemName() {
	
		return systemName;
	}
	
	public void setSystemName(String systemName) {
	
		this.systemName = systemName;
	}
	
	public String getSystemArch() {
	
		return systemArch;
	}
	
	public void setSystemArch(String systemArch) {
	
		this.systemArch = systemArch;
	}
	
	public String getJdkVersion() {
	
		return jdkVersion;
	}
	
	public void setJdkVersion(String jdkVersion) {
	
		this.jdkVersion = jdkVersion;
	}
	
	public String getJdkPath() {
	
		return jdkPath;
	}
	
	public void setJdkPath(String jdkPath) {
	
		this.jdkPath = jdkPath;
	}
	
	public String getMemoryTotal() {
	
		return memoryTotal;
	}
	
	public void setMemoryTotal(String memoryTotal) {
	
		this.memoryTotal = memoryTotal;
	}
	
	public String getMemoryUsed() {
	
		return memoryUsed;
	}
	
	public void setMemoryUsed(String memoryUsed) {
	
		this.memoryUsed = memoryUsed;
	}
	
	public String getMemoryFree() {
	
		return memoryFree;
	}
	
	public void setMemoryFree(String memoryFree) {
	
		this.memoryFree = memoryFree;
	}
	
	
	
	
	
	
	
	

}
