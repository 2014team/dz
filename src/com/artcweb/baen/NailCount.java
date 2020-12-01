
package com.artcweb.baen;

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

	/*@Override
	public int compareTo(NailCount o) {
		return this.indexId.compareTo(o.getIndexId());
	}*/

}
