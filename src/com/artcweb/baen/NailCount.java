package com.artcweb.baen;

import java.io.Serializable;

public class NailCount implements Serializable {
	private static final long serialVersionUID = 1L;

	// 序号
	private String indexId;
	// 钉子数量
	private String nailNumber;
	// 公斤数（每公斤颗数）
	private String kilogramNumber;
	// 需要重量
	private String requreWeight;
	// 需要包数、件数
	private String requrePieces;

	
	// 总钉子数
	private String totalNailNumber;
	// 总重量
	private String totalWeight;

	public String getIndexId() {
		return indexId;
	}

	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}

	public String getNailNumber() {
		return nailNumber;
	}

	public void setNailNumber(String nailNumber) {
		this.nailNumber = nailNumber;
	}

	public String getKilogramNumber() {
		return kilogramNumber;
	}

	public void setKilogramNumber(String kilogramNumber) {
		this.kilogramNumber = kilogramNumber;
	}

	public String getRequreWeight() {
		return requreWeight;
	}

	public void setRequreWeight(String requreWeight) {
		this.requreWeight = requreWeight;
	}

	public String getRequrePieces() {
		return requrePieces;
	}

	public void setRequrePieces(String requrePieces) {
		this.requrePieces = requrePieces;
	}

	public String getTotalNailNumber() {
		return totalNailNumber;
	}

	public void setTotalNailNumber(String totalNailNumber) {
		this.totalNailNumber = totalNailNumber;
	}

	public String getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(String totalWeight) {
		this.totalWeight = totalWeight;
	}

}
