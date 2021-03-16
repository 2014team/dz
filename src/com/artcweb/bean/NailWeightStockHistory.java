package com.artcweb.bean;
import com.artcweb.bean.BaseBean;
 
/**
 * @ClassName: NailWeightStockHistory
 * @Description: 图钉重量库存记录
 * @author zhuzq
 * @date 2021年03月09日 14:16:31
 */ 
public class NailWeightStockHistory extends BaseBean{

	private static final long serialVersionUID = 1L;
	/**
	 * 重量库存id
	 */
	private Integer nailWeightStockId;
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
	
	
	// 图钉类型
	private String nailConfigId;
	
 
	public Integer getNailWeightStockId(){
		return this.nailWeightStockId;
	}
	
	public void setNailWeightStockId(Integer nailWeightStockId){
		this.nailWeightStockId = nailWeightStockId;
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

	public String getNailConfigId() {
		return nailConfigId;
	}

	public void setNailConfigId(String nailConfigId) {
		this.nailConfigId = nailConfigId;
	}
	
}