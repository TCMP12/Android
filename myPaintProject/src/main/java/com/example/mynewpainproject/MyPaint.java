package com.example.mynewpainproject;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MyPaint extends View {
	
	private static int SHAPE;
	private static int COLOR =Color.RED;
	Point start = new Point();
	Point end   = new Point();
	Paint paint = new Paint();
	
	public MyPaint(Context context) {
		super(context);
		initial();
	}
	public MyPaint(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initial();
	}
	public MyPaint(Context context, AttributeSet attrs) {
		super(context, attrs);
		initial();
	}

	public void initial(){
		paint = new Paint();
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(4);
		paint.setColor(COLOR);
	}

	public static int getSHAPE() {
		return SHAPE;
	}
	public static void setSHAPE(int sHAPE) {
		SHAPE = sHAPE;
	}
	public static int getCOLOR() {
		return COLOR;
	}
	public static void setCOLOR(int cOLOR) {
		COLOR = cOLOR;
	}
	@Override
	protected void onDraw(Canvas canvas) {
		for (Shape s : shapes) {
			s.DrawShape(canvas, paint);
		}
		System.out.println("draw");
		if(tempshapes != null){
			tempshapes.DrawShape(canvas, paint);
			System.out.println(tempshapes);
		}
		
	}
	
	ArrayList<Shape> shapes = new ArrayList<Shape>();
	Shape tempshapes;

	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction()== MotionEvent.ACTION_DOWN){
			initial();
			start = new Point();
			start.x = event.getX();
			start.y = event.getY();
			end = new Point(start.x, start.y);
			switch (SHAPE) {
			case 1:
				DrawCircle circle = new DrawCircle(start, end, getCOLOR());
				tempshapes = circle;
				break;
			case 2:
				DrawLine line = new DrawLine(start, end, getCOLOR());
				tempshapes=line;
				invalidate();
				break;
				case 3:
					DrawRectangle rectangle = new DrawRectangle(start, end, getCOLOR());
					tempshapes = rectangle;
					invalidate();
					break;
					
				case 4:
					DrawTriangle triangle = new DrawTriangle(start, end, getCOLOR());
					tempshapes = triangle;
					invalidate();
					break;
	
				default:
					break;
			}
		}
		
		if(event.getAction()== MotionEvent.ACTION_MOVE){
			end.x = event.getX();
			end.y = event.getY();

			invalidate();
		}
		if(event.getAction()== MotionEvent.ACTION_UP){
			shapes.add(tempshapes);
			tempshapes = null;
			start = null;
			end = null;
		}
		return true;
	}
	
}
