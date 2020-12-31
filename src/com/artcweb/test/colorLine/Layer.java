package com.artcweb.test.colorLine;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

abstract class Layer {
	abstract ArrayList<Line> getLines();

	abstract PImage getOriginalImage();

	abstract PImage getWorkingImage();

	abstract public boolean drawNextLine();

	public Layer(Frame frame, PImage img) throws Exception {
		PVector frame_size = frame.getSize();
		if (frame_size.x > img.width || frame_size.y > img.height) {
			throw new Exception("Frame should be smaller or equal to image size.");
		}
	}

	public Layer createColorLayer(PApplet pApplet, Frame frame, PImage img, WeaveConfig[] configs) throws Exception {
		PVector frame_size = frame.getSize();

		PImage original_img = img.copy().get(0, 0, (int) frame_size.x, (int) frame_size.y);

		PImage[] imgs = new PImage[configs.length];

		for (int i = 0; i < configs.length; ++i) {
			imgs[i] = new PImage(img.width, img.height, pApplet.RGB);
		}

		for (int x = 0; x < img.width; ++x) {
			for (int y = 0; y < img.height; ++y) {
				int i = y * img.width + x;
				int pixel_color = img.pixels[i];
				PVector pixel_color_vect = new PVector(pApplet.red(pixel_color), pApplet.green(pixel_color),
						pApplet.blue(pixel_color));

				for (int j = 0; j < configs.length; ++j) {
					int draw_color = configs[j].draw_color;
					PVector draw_color_vect = new PVector(pApplet.red(draw_color), pApplet.green(draw_color),
							pApplet.blue(draw_color));

					float dist = PVector.dist(draw_color_vect, pixel_color_vect);
					dist = color_level(dist); // make dark darker, bright
												// brighter

					imgs[j].pixels[i] = pApplet.color(dist, dist, dist);
				}
			}
		}

		return new MultipleImageLayer(pApplet, frame, original_img, imgs, configs);
	}

	public float color_level(float x) {
		return (float) (255 * 1.0 / (1 + Math.exp(9 - 0.05 * x)));
	}

}