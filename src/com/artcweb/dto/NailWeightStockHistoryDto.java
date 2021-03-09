package com.artcweb.dto;
import com.artcweb.bean.NailWeightStockHistory;
 
/**
 * @ClassName: NailWeightStockHistoryDto
 * @Description: 图钉重量库存记录
 * @author zhuzq
 * @date 2021年03月09日 14:16:31
 */ 
public class NailWeightStockHistoryDto extends NailWeightStockHistory{

	private static final long serialVersionUID = 1L;
	
	private String rgb;
	private String rgbName;
	private String newSerialNumber;
	private String oldSerialNumber;
	public String getRgb() {
		return rgb;
	}
	public void setRgb(String rgb) {
		this.rgb = rgb;
	}
	
	public String getRgbName() {
		return rgbName;
	}
	public void setRgbName(String rgbName) {
		this.rgbName = rgbName;
	}
	public String getNewSerialNumber() {
		return newSerialNumber;
	}
	public void setNewSerialNumber(String newSerialNumber) {
		this.newSerialNumber = newSerialNumber;
	}
	public String getOldSerialNumber() {
		return oldSerialNumber;
	}
	public void setOldSerialNumber(String oldSerialNumber) {
		this.oldSerialNumber = oldSerialNumber;
	}
	
	
}