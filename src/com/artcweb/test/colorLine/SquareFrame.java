package com.artcweb.test.colorLine;

import processing.core.PVector;

class SquareFrame extends Frame {
	private PVector[] points;
	private int length, count;

	public SquareFrame(int length, int count) {
		this.length = length;
		this.count = count;

		this.points = new PVector[this.count];
		float step = (float) this.length / this.count * 4;

		// Top line
		int offset = 0;
		for (int i = 0; i < this.count / 4; ++i) {
			this.points[offset] = new PVector(i * step, 0);
			offset += 1;
		}

		// Right line
		for (int i = 0; i < this.count / 4; ++i) {
			this.points[offset] = new PVector(this.length, i * step);
			offset += 1;
		}

		// Bottom line
		for (int i = (this.count / 4) - 1; i >= 0; --i) {
			this.points[offset] = new PVector(i * step, this.length);
			offset += 1;
		}

		// Left line
		for (int i = (this.count / 4) - 1; i >= 0; --i) {
			this.points[offset] = new PVector(0, i * step);
			offset += 1;
		}
	}

	public PVector[] getPoints() {
		return this.points;
	}
}
