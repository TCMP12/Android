package com.example.mynewpainproject;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.io.Serializable;

public class DrawLine extends Shape implements Serializable{
	


	public DrawLine(Point start,Point end, int color) {
		this.color = color;
		pointEnd = start;
		pointStart =   end;
	}

	@Override
	public void DrawShape(Canvas canvas, Paint paint) {
		paint.setColor(color);

		canvas.drawLine(
				pointEnd.x, 
				pointEnd.y, 
				pointStart.x, 
				pointStart.y, paint);
		
		
	}

	@Override
	public String toString() {
		return "DrawLine [PointOnMouseUp="
				+ pointStart + ", PointOnMouseDown=" + pointEnd
				+ "]";
	}
	
	

}
