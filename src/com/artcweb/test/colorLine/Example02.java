package com.artcweb.test.colorLine;

import processing.core.PApplet;
import processing.core.PImage;

public class Example02 {
	public Frame frame;
	public Layer layer;
	public PApplet pApplet;
	
	public Example02() {
	}

	public Example02(PApplet pApplet,Frame frame, Layer layer) {
		this.pApplet =pApplet;
		this.frame = frame;
		this.layer = layer;
	}
	
	Example example02() throws Exception {
		PImage img = pApplet.loadImage("C:\\Users\\zhuzq\\Desktop\\zzzq\\Weaver\\Lenna.jpg");
		PImage[] imgs = { 
				pApplet.loadImage("Lenna_Black.jpg"), 
				pApplet.loadImage("Lenna_Blue.jpg"), 
				pApplet.loadImage("Lenna_Yellow.jpg"),
				pApplet.loadImage("Lenna_Red.jpg"), };
		WeaveConfig[] configs = { 
				new WeaveConfig(2000, 0, 35, pApplet.color(pApplet.color(0, 0, 0), 25)),
				new WeaveConfig(2000, 0, 35, pApplet.color(pApplet.color(13, 22, 189), 25)),
				new WeaveConfig(1500, 0, 35, pApplet.color(pApplet.color(242, 229, 78), 25)),
				new WeaveConfig(3000, 0, 35, pApplet.color(pApplet.color(181, 2, 2), 25)), };

		// crop to square
		int min_length = img.width < img.height ? img.width : img.height;
		img = img.get(0, 0, min_length, min_length);

		// setup frame
		frame = new CircleFrame(min_length / 2, 256); // new
														// SquareFrame(min_length,
														// 256);

		// scale it down... because bigger the frame, bigger number of threads
		// required...
		int cropped_size = 640;
		if (cropped_size < min_length) {
			min_length = cropped_size;
			img.resize(min_length, min_length);
		}

		// setup layer
		Layer layer = new MultipleImageLayer(pApplet,frame, img, imgs, configs);

		return new Example(pApplet,frame, layer);
	}

}
