package com.artcweb.vo;

import com.artcweb.bean.NailPictureframeStockHistory;

/**
 * @ClassName: NailPictureframeStockHistoryVo
 * @Description: 画框库存记录
 * @author zhuzq
 * @date 2021年07月30日 11:33:34
 */
public class NailPictureframeStockHistoryVo extends NailPictureframeStockHistory {

	private static final long serialVersionUID = 1L;

	// 时间
	private String createDateStr;

	// 查询-开始时间
	private String beginDate;

	// 查询-结束时间
	private String endDate;
	
	private Integer black;
	private Integer white;
	private Integer blue;
	private Integer powder;
	private Integer gold;
	private Integer silver;
	


	public String getCreateDateStr() {
		return createDateStr;
	}

	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	
	public Integer getBlack() {
		return black;
	}

	
	public void setBlack(Integer black) {
		this.black = black;
	}

	
	public Integer getWhite() {
		return white;
	}

	
	public void setWhite(Integer white) {
		this.white = white;
	}

	
	public Integer getBlue() {
		return blue;
	}

	
	public void setBlue(Integer blue) {
		this.blue = blue;
	}

	
	public Integer getPowder() {
		return powder;
	}

	
	public void setPowder(Integer powder) {
		this.powder = powder;
	}

	
	public Integer getGold() {
		return gold;
	}

	
	public void setGold(Integer gold) {
		this.gold = gold;
	}

	
	public Integer getSilver() {
		return silver;
	}

	
	public void setSilver(Integer silver) {
		this.silver = silver;
	}

	
	
}