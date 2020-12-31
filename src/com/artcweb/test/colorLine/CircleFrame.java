package com.artcweb.test.colorLine;

import processing.core.PVector;

class CircleFrame extends Frame {
	private PVector[] points;
	private int radius, count;

	public CircleFrame(int radius, int count) {
		this.radius = radius;
		this.count = count;

		this.points = new PVector[this.count];

		for (int i = 0; i < this.count; ++i) {
			double deg = Math.PI * 2 * i / this.count;

			this.points[i] = new PVector((float) (this.radius + Math.sin(deg) * this.radius),
					(float) (this.radius - Math.cos(deg) * this.radius));
		}
	}

	public PVector[] getPoints() {
		return this.points;
	}
}
