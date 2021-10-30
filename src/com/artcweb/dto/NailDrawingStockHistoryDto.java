package com.artcweb.dto;
import com.artcweb.bean.NailDrawingStockHistory;
 
/**
 * @ClassName: NailDrawingStockHistoryDto
 * @Description: 图纸库存记录
 * @author zhuzq
 * @date 2021年03月10日 10:41:36
 */ 
public class NailDrawingStockHistoryDto extends NailDrawingStockHistory{

	private static final long serialVersionUID = 1L;
	private String style;
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	
	
}