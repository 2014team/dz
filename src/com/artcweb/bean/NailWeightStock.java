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
	 * 小钉库存(单位千克)
	 */
	private String stock_1;
	/**
	 * 玫瑰库存(单位千克)
	 */
	private String stock_2;
	/**
	* 钻石库存(单位千克)
	*/
	private String stock_3;
	/**
	* 大钉库存(单位千克)
	*/
	private String stock_4;
	/**
	* 迷你库存(单位千克)
	*/
	private String stock_5;
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
	
	
	public String getStock_1() {
		return stock_1;
	}

	
	public void setStock_1(String stock_1) {
		this.stock_1 = stock_1;
	}

	
	public String getStock_2() {
		return stock_2;
	}

	
	public void setStock_2(String stock_2) {
		this.stock_2 = stock_2;
	}

	
	public String getStock_3() {
		return stock_3;
	}

	
	public void setStock_3(String stock_3) {
		this.stock_3 = stock_3;
	}

	
	public String getStock_4() {
		return stock_4;
	}

	
	public void setStock_4(String stock_4) {
		this.stock_4 = stock_4;
	}

	public Integer getSort(){
		return this.sort;
	}
	
	public void setSort(Integer sort){
		this.sort = sort;
	}

	
	public String getStock_5() {
		return stock_5;
	}

	
	public void setStock_5(String stock_5) {
		this.stock_5 = stock_5;
	}
	
	
}