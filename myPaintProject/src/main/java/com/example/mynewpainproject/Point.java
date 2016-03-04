package com.example.mynewpainproject;

import java.io.Serializable;

public class Point implements Serializable {
	
	float x,y;

	public Point() {

	}

	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}
	
	
	

}
