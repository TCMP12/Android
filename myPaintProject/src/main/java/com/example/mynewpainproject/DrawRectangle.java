package com.example.mynewpainproject;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.io.Serializable;

public class DrawRectangle extends Shape implements Serializable{

	public DrawRectangle(Point start, Point end, int color) {
		this.color = color;
		pointStart = start;
		pointEnd   = end;
		
		
	}

	@Override
	public void DrawShape(Canvas canvas, Paint paint) {
		paint.setColor(color);
		Point[] points = points();		
		canvas.drawRect(points[0].x, points[0].y, points[1].x, points[1].y, paint);
		
	}
	
}
