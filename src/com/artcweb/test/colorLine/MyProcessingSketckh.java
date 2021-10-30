package com.artcweb.test.colorLine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;



public class MyProcessingSketckh extends PApplet {

	// how many lines to be drawn in every iteration?
	int fps = 100;

	// pause after every iteration?
	boolean stepByStep = false;

	boolean paused = true;
	boolean done = false;

	Frame frame;
	Layer layer;

	int block_size;
	
	
	
	
	
	
	
	
	
    public static void main(String[] args) {
        PApplet.main("com.yuangudashen.processing.sketck.MyProcessingSketckh");

    }

    public void settings() {
    	 size(1200, 600);
    	 block_size = height;
    }

    public void setup() {
		  try {
//			  	PImage img = loadImage("C:\\Users\\zhuzq\\Desktop\\zzzq\\Weaver\\zq.jpg");
//				WeaveConfig config = new WeaveConfig(3000, 0, 35, color(color(0, 0, 0), 25));
//
//				// crop to square
//				int min_length = img.width < img.height ? img.width : img.height;
//				img = img.get(0, 0, min_length, min_length);
//
//				// setup frame
//				this.frame = new CircleFrame(min_length / 2, 256); // new
//																// SquareFrame(min_length,
//																// 256);
//
//				// scale it down... because bigger the frame, bigger number of threads
//				// required...
//				int cropped_size = 640;
//				if (cropped_size < min_length) {
//					min_length = cropped_size;
//					img.resize(min_length, min_length);
//				}
//				
//				this.layer = new MonochromeLayer(this,frame, img, config);
			  
			  
			  
			  
			  
			  
			  
//			  
//			  PImage img = loadImage("C:\\Users\\zhuzq\\Desktop\\zzzq\\Weaver\\Lenna.jpg");
//				PImage[] imgs = { 
//						loadImage("C:\\Users\\zhuzq\\Desktop\\zzzq\\Weaver\\Lenna_Black.jpg"), 
//						loadImage("C:\\Users\\zhuzq\\Desktop\\zzzq\\Weaver\\Lenna_Blue.jpg"), 
//						loadImage("C:\\Users\\zhuzq\\Desktop\\zzzq\\Weaver\\Lenna_Yellow.jpg"),
//						loadImage("C:\\Users\\zhuzq\\Desktop\\zzzq\\Weaver\\Lenna_Red.jpg"), };
//				WeaveConfig[] configs = { 
//						new WeaveConfig(2000, 0, 35, color(color(0, 0, 0), 25)),
//						new WeaveConfig(2000, 0, 35, color(color(13, 22, 189), 25)),
//						new WeaveConfig(1500, 0, 35, color(color(242, 229, 78), 25)),
//						new WeaveConfig(3000, 0, 35, color(color(181, 2, 2), 25)), };
//
//				// crop to square
//				int min_length = img.width < img.height ? img.width : img.height;
//				img = img.get(0, 0, min_length, min_length);
//
//				// setup frame
//				frame = new CircleFrame(min_length / 2, 256); // new
//																// SquareFrame(min_length,
//																// 256);
//
//				// scale it down... because bigger the frame, bigger number of threads
//				// required...
//				int cropped_size = 640;
//				if (cropped_size < min_length) {
//					min_length = cropped_size;
//					img.resize(min_length, min_length);
//				}
//
//				// setup layer
//				this.layer = new MultipleImageLayer(this,frame, img, imgs, configs);
//			  
			  
			  PImage img = loadImage("C:\\Users\\zhuzq\\Desktop\\zzzq\\Weaver\\zq.jpg");
			  WeaveConfig[] configs = {
					    new WeaveConfig(2000, 0, 35, color(color(0, 0, 0), 25)),
					    new WeaveConfig(2000, 0, 35, color(color(13, 22, 189), 25)),
					    new WeaveConfig(1500, 0, 35, color(color(242, 229, 78), 25)),
					    new WeaveConfig(3000, 0, 35, color(color(181, 2, 2), 25)),
					  };

				// crop to square
				int min_length = img.width < img.height ? img.width : img.height;
				img = img.get(0, 0, min_length, min_length);

				// scale it down... because bigger the frame, bigger number of threads
				// required...
				int cropped_size = 640;
				if (cropped_size < min_length) {
					min_length = cropped_size;
					img.resize(min_length, min_length);
				}

				// setup frame
				frame = new CircleFrame(min_length / 2, 256); // new
																// SquareFrame(min_length,
																// 256);

				// setup layer
			 layer = new ColorImageLayer().createColorLayer(this,frame, img, configs);
				
			  
			  
			  
			  
			  
				
			  } catch(Exception e) {
			    println(e);
			    exit();
			  }
    }
    
    
    public void drawAll() {
    	  background(255);

    	  if (frame == null || layer == null) {
    	    return;
    	  }

    	  translate(0, 0);
    	  shape(frame.getFrameShape(this), 0, 0, block_size, block_size);

    	  translate(block_size, 0);
    	  image(layer.getOriginalImage(), 0, 0, block_size, block_size);

    	  translate(-block_size, 0);
    	  PShape[] lines = frame.getLineShapes(layer.getLines());
    	  for (PShape line : lines) {
    	    shape(line, 0, 0, block_size, block_size);
    	  }
    	}
    
    
    public void mouseReleased() {
    	  // toggle pause
    	  paused = !paused;
    	}
    
    
    
    

    public void draw() {        
    	 if (done) return;

    	  if (!paused) {
    	    for (int i = 0; i < fps; ++i) {
    	      boolean layerDone = layer.drawNextLine();
    	      if (layerDone) {
    	        done = true;
    	      }
    	    }
    	  }

    	  drawAll();

    	  if (done) {
    	   // selectOutput("Select a file to write to:", "writeToFile");
    		  ss();
    	  }

    	  if (stepByStep) {
    	    paused = true;
    	  }
    }

    public void writeToFile(File selection) {
    	  if (selection == null) {
    	    return;
    	  }

    	  PrintWriter file;
    	  file = createWriter(selection.getAbsolutePath());
    	  file = createWriter("F:\\11.txt");
    	  for (Line line : layer.getLines()) {
    	    file.println(line.index + " ; Color: #" + hex(line.c));
    	  }
    	  file.flush();
    	  file.close();
    	}
    
    
    public void ss(){
    	 BufferedWriter out = null;
		try {
			
			StringBuffer sb = new StringBuffer();
			 for (Line line : layer.getLines()) {
				 sb.append(line.index + " ; Color: #" + hex(line.c));
				 sb.append(System.getProperty("line.separator"));
    	  }
			out = new BufferedWriter(new FileWriter("F:\\runoob.txt"));
			 out.write(sb.toString());
	         out.close();
	         System.out.println("文件创建成功！");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

}