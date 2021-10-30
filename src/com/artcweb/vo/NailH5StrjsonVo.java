package com.artcweb.vo;
import com.artcweb.bean.NailH5Strjson;
 
/**
 * @ClassName: NailH5StrjsonVo
 * @Description: H5调用
 * @author zhuzq
 * @date 2020年12月07日 16:17:37
 */ 
public class NailH5StrjsonVo extends NailH5Strjson{
	
	private static final long serialVersionUID = 1L;
	
	
	private String order_sn;
	private String width;
	private String height;
	private String nail;
	private String original_url;

	// 输出结果图
	private String output_url;
	//取色图片
	private String scale_url;
	
	public String getOrder_sn() {
		return order_sn;
	}
	
	public void setOrder_sn(String order_sn) {
		this.order_sn = order_sn;
	}
	
	public String getWidth() {
		return width;
	}
	
	public void setWidth(String width) {
		this.width = width;
	}
	
	public String getHeight() {
		return height;
	}
	
	public void setHeight(String height) {
		this.height = height;
	}
	
	public String getNail() {
		return nail;
	}
	
	public void setNail(String nail) {
		this.nail = nail;
	}
	
	public String getOriginal_url() {
		return original_url;
	}
	
	public void setOriginal_url(String original_url) {
		this.original_url = original_url;
	}
	
	public String getOutput_url() {
		return output_url;
	}
	
	public void setOutput_url(String output_url) {
		this.output_url = output_url;
	}
	
	public String getScale_url() {
		return scale_url;
	}
	
	public void setScale_url(String scale_url) {
		this.scale_url = scale_url;
	}
	
	
	
}