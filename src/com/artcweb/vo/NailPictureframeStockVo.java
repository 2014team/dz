package com.artcweb.vo;
import com.artcweb.bean.NailPictureframeStock;
 
/**
 * @ClassName: NailPictureframeStockVo
 * @Description: 画框库存
 * @author zhuzq
 * @date 2021年07月30日 09:06:26
 */ 
public class NailPictureframeStockVo extends NailPictureframeStock{
	
	private static final long serialVersionUID = 1L;
	

	// 时间
	private String createDateStr;

	// 查询-开始时间
	private String beginDate;

	// 查询-结束时间
	private String endDate;

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

	
}