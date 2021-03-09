package com.artcweb.bean;
import com.artcweb.bean.BaseBean;
 
/**
 * @ClassName: NailWeightStock
 * @Description: 图钉重量库存
 * @author zhuzq
 * @date 2021年03月09日 10:04:19
 */ 
public class NailWeightStock extends BaseBean{

	private static final long serialVersionUID = 1L;
	/**
	 * RGB值
	 */
	private String rgb;
	private String rgbName;
	/**
	 * 新编号
	 */
	private String newSerialNumber;
	/**
	 * 旧编号
	 */
	private String oldSerialNumber;
	/**
	 * 库存(单位克)
	 */
	private String stock;
	/**
	 * 排序
	 */
	private Integer sort;
	
	

	public String getRgbName() {
		return rgbName;
	}

	public void setRgbName(String rgbName) {
		this.rgbName = rgbName;
	}
 
	public String getRgb(){
		return this.rgb;
	}
	
	public void setRgb(String rgb){
		this.rgb = rgb;
	}
	public String getNewSerialNumber(){
		return this.newSerialNumber;
	}
	
	public void setNewSerialNumber(String newSerialNumber){
		this.newSerialNumber = newSerialNumber;
	}
	public String getOldSerialNumber(){
		return this.oldSerialNumber;
	}
	
	public void setOldSerialNumber(String oldSerialNumber){
		this.oldSerialNumber = oldSerialNumber;
	}
	public String getStock(){
		return this.stock;
	}
	
	public void setStock(String stock){
		this.stock = stock;
	}
	public Integer getSort(){
		return this.sort;
	}
	
	public void setSort(Integer sort){
		this.sort = sort;
	}
}