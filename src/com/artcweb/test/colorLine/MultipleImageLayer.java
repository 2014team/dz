package com.artcweb.test.colorLine;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class MultipleImageLayer extends Layer {
	private PImage original_img;
	private PImage working_img;
	private Frame frame;
	private WeaveConfig[] configs;

	private Layer[] layers;

	PApplet pApplet;
	

	public MultipleImageLayer(PApplet pApplet, Frame frame, PImage original_img, PImage[] img, WeaveConfig[] configs)
			throws Exception {
		super(frame, original_img);

		this.pApplet = pApplet;

		for (int i = 0; i < img.length; ++i) {
			if (original_img.width != img[i].width || original_img.height != img[i].height) {
				throw new Exception("Images are not the same dimensions.");
			}
		}

		if (img.length != configs.length) {
			throw new Exception("Images and configs should be same quantity.");
		}
		this.frame = frame;
		this.configs = configs;

		PVector frame_size = this.frame.getSize();
		this.original_img = original_img.get(0, 0, (int) frame_size.x, (int) frame_size.y);

		this.layers = new MonochromeLayer[this.configs.length];
		for (int i = 0; i < this.configs.length; ++i) {
			this.layers[i] = new MonochromeLayer(pApplet, this.frame, img[i], this.configs[i]);
		}

		// set a random image as working image...
		this.working_img = img[0];
	}
	
	

	public ArrayList<Line> getLines() {
		ArrayList<Line> lines = new ArrayList();

		for (int i = 0; i < this.configs.length; i++) {
			lines.addAll(this.layers[i].getLines());
		}

		return lines;
	}

	public PImage getOriginalImage() {
		return this.original_img;
	}

	public PImage getWorkingImage() {
		return this.working_img;
	}

	public boolean drawNextLine() {
		for (int i = 0; i < this.configs.length; i++) {
			if (this.layers[i].drawNextLine() == false) {
				return false;
			}
		}

		return true;
	}
}