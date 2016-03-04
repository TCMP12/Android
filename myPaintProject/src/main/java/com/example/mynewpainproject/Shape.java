package com.example.mynewpainproject;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.io.Serializable;

public abstract class Shape implements Serializable {
	
	Point pointStart,pointEnd;
	int color;
	
	public abstract void DrawShape(Canvas canvas, Paint paint);

	protected Point[] points(){
		if(pointStart == null || pointEnd == null)
			return null;
		
		Point[] reterned = {new Point(pointStart.x, pointStart.y), new Point(pointEnd.x, pointEnd.y)};

		if(pointStart.x > pointEnd.x){
			reterned[0].x = pointEnd.x;
			reterned[1].x = pointStart.x;			
		}
		if(pointStart.y > pointEnd.y){
			reterned[0].y = pointEnd.y;
			reterned[1].y = pointStart.y;
		}
		return reterned;
		
	}

}


