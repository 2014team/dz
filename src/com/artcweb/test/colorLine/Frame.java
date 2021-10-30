package com.artcweb.test.colorLine;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

abstract class Frame {
	abstract PVector[] getPoints();

	PApplet pApplet;
	
	public Frame() {
	}

	public PShape getFrameShape(PApplet pApplet) {
		this.pApplet = pApplet;
		
		PShape frame = pApplet.createShape();
		frame.setStroke(pApplet.color(0, 0, 255));

		frame.beginShape(pApplet.POINTS);

		PVector[] points = this.getPoints();
		for (int i = 0; i < points.length; i++) {
			frame.vertex(points[i].x, points[i].y);
		}

		frame.endShape();
		return frame;
	}

	public PShape[] getLineShapes(ArrayList<Line> lines) {
		PVector size = this.getSize();
		PShape[] shapes = new PShape[lines.size() - 1];

		PVector[] points = this.getPoints();
		for (int i = 1; i < lines.size(); ++i) {
			Line start_line = lines.get(i - 1);
			Line end_line = lines.get(i);

			shapes[i - 1] = pApplet.createShape();
			shapes[i - 1].beginShape(pApplet.LINES);
			shapes[i - 1].strokeWeight(1);

			// add a line to bottom to fixate the size of the shape...
			shapes[i - 1].vertex(0, size.y);
			shapes[i - 1].vertex(size.x, size.y);
			shapes[i - 1].stroke(end_line.c);

			shapes[i - 1].vertex(points[start_line.index].x, points[start_line.index].y);
			shapes[i - 1].vertex(points[end_line.index].x, points[end_line.index].y);
			shapes[i - 1].endShape();
		}

		return shapes;
	}

	public PVector getSize() {
		float width = 0;
		float height = 0;

		PVector[] points = this.getPoints();
		for (int i = 0; i < points.length; i++) {
			if (width < points[i].x)
				width = points[i].x;
			if (height < points[i].y)
				height = points[i].y;
		}

		return new PVector(width, height);
	}
}
