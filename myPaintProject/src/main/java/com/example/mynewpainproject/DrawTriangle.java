package com.example.mynewpainproject;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.io.Serializable;

public class DrawTriangle extends Shape implements Serializable{


	static final long serialVersionUID = 90L;
	int lala= 1;
	int adhsgfsdhf;
	int hjgfsdh;
	public DrawTriangle(Point start, Point end, int paint) {
		this.color = paint;
		pointEnd = start;
		pointStart =   end;
	}

	@Override
	public void DrawShape(Canvas canvas, Paint paint) {
		
		float x1 = pointEnd.x;
		float y1 = pointEnd.y;
		float x2 = pointStart.x;
		float y2 = pointStart.y;
		float z = (int) Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
		
		canvas.drawLine(x2+z/2, y2, x2-z/2, y2, paint);
		canvas.drawLine(x1, y1, x2+z/2, y2, paint);
		canvas.drawLine(x1, y1, x2-z/2, y2, paint);
		
	}

}
