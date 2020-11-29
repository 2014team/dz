package com.artcweb.baen;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

public class NailTotalCount implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;

	// 总钉子数
	private String totalNailNumber;
	// 总重量
	private String totalWeight;
	// 总包数
	private String totalrPieces;
	// 详细列表
	ConcurrentHashMap<Integer, NailCount> nailCountDetailMap;

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
	public ConcurrentHashMap<Integer, NailCount> getNailCountDetailMap() {
		return nailCountDetailMap;
	}
	public void setNailCountDetailMap(ConcurrentHashMap<Integer, NailCount> nailCountDetailMap) {
		this.nailCountDetailMap = nailCountDetailMap;
	}

	public String getTotalrPieces() {
		return totalrPieces;
	}

	public void setTotalrPieces(String totalrPieces) {
		this.totalrPieces = totalrPieces;
	}
	
	

}
