package com.artcweb.bean;
import com.artcweb.bean.BaseBean;
 
/**
 * @ClassName: NailPictureframeStock
 * @Description: 画框库存
 * @author zhuzq
 * @date 2021年07月30日 09:06:25
 */ 
public class NailPictureframeStock extends BaseBean{

	private static final long serialVersionUID = 1L;
	/**
	 * 颜色名称
	 */
	private String colorName;
	/**
	 * 库存数量
	 */
	private Integer stockNumber;
	/**
	 * 序号
	 */
	private Integer sort;
	
	private Integer black;
	private Integer white;
	private Integer blue;
	private Integer powder;
	private Integer gold;
	private Integer silver;
	private Integer rose;
 
	public String getColorName(){
		return this.colorName;
	}
	
	public void setColorName(String colorName){
		this.colorName = colorName;
	}
	public Integer getStockNumber(){
		return this.stockNumber;
	}
	
	public void setStockNumber(Integer stockNumber){
		this.stockNumber = stockNumber;
	}
	public Integer getSort(){
		return this.sort;
	}
	
	public void setSort(Integer sort){
		this.sort = sort;
	}

	
	public Integer getBlack() {
		return black;
	}

	
	public void setBlack(Integer black) {
		this.black = black;
	}

	
	public Integer getWhite() {
		return white;
	}

	
	public void setWhite(Integer white) {
		this.white = white;
	}

	
	public Integer getBlue() {
		return blue;
	}

	
	public void setBlue(Integer blue) {
		this.blue = blue;
	}

	
	public Integer getPowder() {
		return powder;
	}

	
	public void setPowder(Integer powder) {
		this.powder = powder;
	}

	
	public Integer getGold() {
		return gold;
	}

	
	public void setGold(Integer gold) {
		this.gold = gold;
	}

	
	public Integer getSilver() {
		return silver;
	}

	
	public void setSilver(Integer silver) {
		this.silver = silver;
	}

	
	public Integer getRose() {
		return rose;
	}

	
	public void setRose(Integer rose) {
		this.rose = rose;
	}
	
	
}