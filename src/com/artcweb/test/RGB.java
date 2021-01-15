
package com.artcweb.test;

import javafx.scene.paint.Color;
public class RGB {

	private int r;

	private int g;

	private int b;

	private Color color;

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public RGB(int r, int g, int b) {
		this.color = Color.rgb(r, g, b);
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
}
