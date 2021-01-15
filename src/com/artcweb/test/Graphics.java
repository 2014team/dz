package com.artcweb.test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Graphics{
	

		 private static final int WIDTH = 600;
		    private static final int HEIGHT = 600;
		    private static final String OUT_PATH = "d:/image.jpg";
		    public static void main(String[]args) throws Exception
		    {
//		        BufferedImage buffImg = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
//		        Graphics  g = buffImg.getGraphics();
//		        g.setColor(Color.WHITE);
//		        g.fillRect(0, 0, WIDTH, HEIGHT);
//		        g.setColor(Color.BLACK);
//		        g.fillOval(0, 0, 30, 30);
//		        g.fillOval(100, 100, 30, 30);
//		        g.drawLine(15, 15, 115, 115);
//		        g.dispose();
//		        ImageIcon icon = new ImageIcon(buffImg);
//		        File f = new File(OUT_PATH);
//		        OutputStream out;
//		        try {
//		            out = new FileOutputStream(f);
//		            ImageIO.write(buffImg, "jpg", out);
//		            out.close();
//		        } catch (FileNotFoundException e) {
//		            // TODO Auto-generated catch block
//		            e.printStackTrace();
//		        } catch (IOException e) {
//		            // TODO Auto-generated catch block
//		            e.printStackTrace();
//		        }
//		     
		    	  BufferedImage bi = new BufferedImage(600, 800, BufferedImage.TYPE_INT_RGB);
			     Graphics2D g2d = bi.createGraphics();
			    	 
			    	
			    	
			     Ellipse2D.Double circle = null;
			    	for(int i=0;i<100;i++){
			    		for(int j=0;j<100;j++){
			    		circle = new Ellipse2D.Double(i*10,j*10,10,10);
				    	g2d.setPaint(new Color(0, 0, 255));
				    	g2d.fill(circle);
				    	circle = null;
			    		}
				    	
			    	}
			    	
			    	//g2d.setStroke(new BasicStroke(2f)); 
			    	 
			    	g2d.dispose();
			    	 
			    	ImageIO.write(bi,"PNG",new File("d://abc.png")); // write to file    
		    	
		         
		    }
		    
			 
		         
		     
		    

}
