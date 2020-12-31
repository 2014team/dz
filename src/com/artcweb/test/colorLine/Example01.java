package com.artcweb.test.colorLine;

import processing.core.PApplet;
import processing.core.PImage;

public class Example01 {
	public Frame frame;
	public Layer layer;
	public PApplet pApplet;
	
	public Example01() {
	}

	public Example01(PApplet pApplet,Frame frame, Layer layer) {
		this.pApplet =pApplet;
		this.frame = frame;
		this.layer = layer;
	}
	
	Example example01() throws Exception {
		PImage img = pApplet.loadImage("C:\\Users\\zhuzq\\Desktop\\zzzq\\Weaverzq.jpg");
		WeaveConfig config = new WeaveConfig(3000, 0, 35, pApplet.color(pApplet.color(0, 0, 0), 25));

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
		Layer layer = new MonochromeLayer(pApplet,frame, img, config);

		return new Example(pApplet,frame, layer);
	}

}
