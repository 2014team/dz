package com.artcweb.bean;
import com.artcweb.bean.BaseBean;
 
/**
 * @ClassName: NailPictureframeStockHistory
 * @Description: 画框库存记录
 * @author zhuzq
 * @date 2021年07月30日 11:33:34
 */ 
public class NailPictureframeStockHistory extends BaseBean{

	private static final long serialVersionUID = 1L;
	/**
	 * 库存id
	 */
	private Integer nailPictureframeId;
	/**
	 * 画框ID
	 */
	private Integer frameId;
	/**
	 * 单价
	 */
	private String price;
	/**
	 * 总价
	 */
	private String total;
	/**
	 * 库存量
	 */
	private Integer stockNumber;
	
	
 
	

	public Integer getFrameId() {
		return frameId;
	}

	public void setFrameId(Integer frameId) {
		this.frameId = frameId;
	}

	public Integer getNailPictureframeId(){
		return this.nailPictureframeId;
	}
	
	public void setNailPictureframeId(Integer nailPictureframeId){
		this.nailPictureframeId = nailPictureframeId;
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
	public Integer getStockNumber(){
		return this.stockNumber;
	}
	
	public void setStockNumber(Integer stockNumber){
		this.stockNumber = stockNumber;
	}
}