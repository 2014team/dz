package com.artcweb.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.artcweb.service.ImageService;
import com.artcweb.test.RGB;

import javafx.scene.paint.Color;

public class RGBController {
	
	public static void main(String[] args) throws Exception {
		RGBController rGBController = new RGBController();
		BufferedImage img = ImageIO.read(new File("C:/Users/Administrator/Desktop/test/112625_7744.jpg"));
		
		 int iw = img.getWidth();
		 int ih = img.getHeight();
		 for (int y = 0; y < ih; y++) {
		      for (int x = 0; x < iw; x++) {
		        int pixel = img.getRGB(x, y);
		        int alpha = (pixel >> 24) & 0xFF;
		        
		        
		        int red = (pixel >> 16) & 0xFF;
		        int green = (pixel >> 8) & 0xFF;
		        int blue = pixel & 0xFF;
		        
		        Color color1 = Color.rgb(red,green,blue);
		        
		        pixel = rGBController.compare(color1);
			        img.setRGB(x, y, pixel);
		      }
		    }

	    
	    ImageIO.write(img, "gif", new File("F:\\xx.gif"));

	}
	
	
	

	
	
	
	public int compare(Color color1){
		List<RGB> list = init();
		RGB result = list.get(0);
		
		double max=0.0;
		for (int i = 0;i <list.size();i++) {
			if(getColorSemblance(list.get(i).getColor(),color1) >  max){
				max = getColorSemblance(list.get(i).getColor(),color1);
				result = list.get(i);
			}
		}
		
		int r = result.getR();
		int g =  result.getG();
		int b =  result.getB();
		System.out.println("---------r="+r+"----g"+g+"-------b"+b);
		int rgb = ((r << 16) | ((g << 8) | b));
		
		return rgb;
	}

	//颜色相识度
	public static double getColorSemblance(Color color1, Color color2){
        // 此处Color为javafx.scene.paint.Color，getRed()为红色通道的程度，getRed() * 255为红色通道的值
        double semblance = (255 - (Math.abs(color1.getRed() - color2.getRed()) * 255 * 0.297 + Math.abs(color1.getGreen() - color2.getGreen()) * 255 * 0.593 + Math.abs(color1.getBlue() - color2.getBlue()) * 255 * 11.0 / 100)) / 255;
        return semblance;
    }
	
	public  List<RGB>  init(){
		List<RGB> list = new ArrayList<RGB>();
		list.add(new RGB(246, 246, 246));
		list.add(new RGB(255,229,216));
		list.add(new RGB(231,158,109));
		list.add(new RGB(254,201,155));
		list.add(new RGB(209,208,206));
		list.add(new RGB(204,154,142));
		list.add(new RGB(0,57,188));
		list.add(new RGB(111,121,189));
		list.add(new RGB(217,197,230));
		list.add(new RGB(176,227,228));
		       
		list.add(new RGB(224,134,80));
		list.add(new RGB(233,185,167));
		list.add(new RGB(245,212,215));
		list.add(new RGB(255,204,140));
		list.add(new RGB(195,81,49));
		list.add(new RGB(234,105,83));
		list.add(new RGB(171,25,45));
		list.add(new RGB(81,54,41));
		list.add(new RGB(240,160,85));
		list.add(new RGB(255,164,137));
		        
		list.add(new RGB(255,186,143));
		list.add(new RGB(243,208,179));
		list.add(new RGB(0,172,222));
		list.add(new RGB(255,255,0));
		list.add(new RGB(255,0,0));
		list.add(new RGB(250,118,153));
		list.add(new RGB(252,164,188));
		list.add(new RGB(0,0,0));
		list.add(new RGB(55,58,54));
		list.add(new RGB(99,101,106));
		
		list.add(new RGB(177,180,179));
		list.add(new RGB(148,214,0));
		return list;
	
	}
	
	
}
