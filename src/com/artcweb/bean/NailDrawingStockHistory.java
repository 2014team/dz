package com.artcweb.bean;
import com.artcweb.bean.BaseBean;
 
/**
 * @ClassName: NailDrawingStockHistory
 * @Description: 图纸库存记录
 * @author zhuzq
 * @date 2021年03月10日 10:41:36
 */ 
public class NailDrawingStockHistory extends BaseBean{

	private static final long serialVersionUID = 1L;
	/**
	 * 图纸库存ID
	 */
	private Integer nailDrawingStockId;
	/**
	 * 库存量
	 */
	private String stock;
	/**
	 * 单价
	 */
	private String price;
	/**
	 * 总价
	 */
	private String total;
 
	public Integer getNailDrawingStockId(){
		return this.nailDrawingStockId;
	}
	
	public void setNailDrawingStockId(Integer nailDrawingStockId){
		this.nailDrawingStockId = nailDrawingStockId;
	}
	public String getStock(){
		return this.stock;
	}
	
	public void setStock(String stock){
		this.stock = stock;
	}
	public String getPrice(){
		return this.price;
	}
	
	public void setPrice(String price){
		this.price = price;
	}
	public String getTotal(){
		return this.total;
	}
	
	public void setTotal(String total){
		this.total = total;
	}
}