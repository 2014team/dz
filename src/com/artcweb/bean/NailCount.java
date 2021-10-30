
package com.artcweb.bean;

import java.io.Serializable;

public class NailCount implements Serializable{

	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/
	private static final long serialVersionUID = 1L;

	// 编号
	private String indexId;

	// 钉子数量
	private String nailNumber;

	// 公斤数（每公斤颗数）
	private String kilogramNumber;

	// 需要重量
	private String requreWeight;

	// 需要包数、件数
	private String requrePieces;

	// rgb值
	private String rgb;
	
	// 排序
	private int sort;
	
	private String nailNumberAvg;
	private String requreWeightAvg;
	private String requrePiecesAvg;
	
	private String nailNumberRatio;
	private String requreWeightRatio;
	private String requrePiecesRatio;

	public NailCount() {
		super();
	}

	public NailCount(String nailNumber, String requreWeight, String requrePieces) {
		super();
		this.nailNumber = nailNumber;
		this.requreWeight = requreWeight;
		this.requrePieces = requrePieces;
	}

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

	public String getRgb() {
		return rgb;
	}

	public void setRgb(String rgb) {
		this.rgb = rgb;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getNailNumberAvg() {
		return nailNumberAvg;
	}

	public void setNailNumberAvg(String nailNumberAvg) {
		this.nailNumberAvg = nailNumberAvg;
	}

	public String getRequreWeightAvg() {
		return requreWeightAvg;
	}

	public void setRequreWeightAvg(String requreWeightAvg) {
		this.requreWeightAvg = requreWeightAvg;
	}

	public String getRequrePiecesAvg() {
		return requrePiecesAvg;
	}

	public void setRequrePiecesAvg(String requrePiecesAvg) {
		this.requrePiecesAvg = requrePiecesAvg;
	}

	public String getNailNumberRatio() {
		return nailNumberRatio;
	}

	public void setNailNumberRatio(String nailNumberRatio) {
		this.nailNumberRatio = nailNumberRatio;
	}

	public String getRequreWeightRatio() {
		return requreWeightRatio;
	}

	public void setRequreWeightRatio(String requreWeightRatio) {
		this.requreWeightRatio = requreWeightRatio;
	}

	public String getRequrePiecesRatio() {
		return requrePiecesRatio;
	}

	public void setRequrePiecesRatio(String requrePiecesRatio) {
		this.requrePiecesRatio = requrePiecesRatio;
	}

	/*@Override
	public int compareTo(NailCount o) {
		return this.indexId.compareTo(o.getIndexId());
	}*/
	
}
