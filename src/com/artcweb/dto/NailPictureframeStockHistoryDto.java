package com.artcweb.dto;
import com.artcweb.bean.NailPictureframeStockHistory;
 
/**
 * @ClassName: NailPictureframeStockHistoryDto
 * @Description: 画框库存记录
 * @author zhuzq
 * @date 2021年07月30日 11:33:33
 */ 
public class NailPictureframeStockHistoryDto extends NailPictureframeStockHistory{

	private static final long serialVersionUID = 1L;
	private String colorName;
	private String sizeName;
	public String getColorName() {
		return colorName;
	}
	public void setColorName(String colorName) {
		this.colorName = colorName;
	}
	public String getSizeName() {
		return sizeName;
	}
	public void setSizeName(String sizeName) {
		this.sizeName = sizeName;
	}
	
	
	
}