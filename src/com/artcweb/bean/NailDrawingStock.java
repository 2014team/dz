package com.artcweb.bean;
import com.artcweb.bean.BaseBean;
 
/**
 * @ClassName: NailDrawingStock
 * @Description: 图纸库存
 * @author zhuzq
 * @date 2021年03月09日 11:49:59
 */ 
public class NailDrawingStock extends BaseBean{

	private static final long serialVersionUID = 1L;
	/**
	 * 款式
	 */
	private String style;
	/**
	 * 打印尺寸（小）
	 */
	private String printSize;
	/**
	 * 画框尺寸
	 */
	private String frameSize;
	/**
	 * 是否可用0:可以1：不可以
	 */
	private Integer status;
	/**
	 * 数量
	 */
	private Integer number;
 
	public String getStyle(){
		return this.style;
	}
	
	public void setStyle(String style){
		this.style = style;
	}
	public String getPrintSize(){
		return this.printSize;
	}
	
	public void setPrintSize(String printSize){
		this.printSize = printSize;
	}
	public String getFrameSize(){
		return this.frameSize;
	}
	
	public void setFrameSize(String frameSize){
		this.frameSize = frameSize;
	}
	public Integer getStatus(){
		return this.status;
	}
	
	public void setStatus(Integer status){
		this.status = status;
	}
	public Integer getNumber(){
		return this.number;
	}
	
	public void setNumber(Integer number){
		this.number = number;
	}
}