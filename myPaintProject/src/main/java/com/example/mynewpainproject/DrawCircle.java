package com.example.mynewpainproject;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import java.io.Serializable;

public class DrawCircle extends Shape implements Serializable {
	
	float Radius;
	float center;


	public DrawCircle(Point start, Point end, int  color) {
		this.color = color;
		pointStart = start;
		pointEnd = end;
		
	}

	@Override
	public void DrawShape(Canvas canvas, Paint paint) {

		float x = Math.abs(pointStart.x - pointEnd.x);
		float y = Math.abs(pointStart.y - pointEnd.y);
		
		if(x>y)
			Radius = x;
		else 
			Radius = y;

		paint.setColor(color);

		canvas.drawCircle(pointStart.x,pointStart.y, Radius, paint);
//		RectF rf=new RectF(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y);
//		canvas.drawOval(rf, paint);//(rf, 2, 2, true, paint);

	}


	

}
