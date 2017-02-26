package com.utad.dint08_practicapaint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class PizarraView extends View {
	private float x, y;
	private boolean dibujando = false;
	private Paint paint; //paint de dibujar
	private Paint canvasPaint; //paint de canvas
	private Path path; //path para ir trazando las lineas
	private Canvas drawCanvas; //canvas
	private Bitmap canvasBitmap; //canvas para guardar

	//Constructores, (evitar fallos renderizado en xml)
	public PizarraView(Context context) {
		super(context);
		init();
	}
	public PizarraView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	public PizarraView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	//Configuración por defecto
	private void init() {
		paint = new Paint();
		path = new Path();
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(1); //Grosor pincel
		paint.setColor(Color.BLACK); //Color pincel
		canvasPaint = new Paint(Paint.DITHER_FLAG);
	}

	//Tamaño asignado a la vista
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		drawCanvas = new Canvas(canvasBitmap);
	}

	//Pinta los trazos del touch del usuario
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
		canvas.drawPath(path, paint);
	}

	//Registra los touch del usuario
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float touchX = event.getX();
		float touchY = event.getY();

		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				path.moveTo(touchX, touchY);
				break;
			case MotionEvent.ACTION_MOVE:
				path.lineTo(touchX, touchY);
				break;
			case MotionEvent.ACTION_UP:
				path.lineTo(touchX, touchY);
				drawCanvas.drawPath(path, paint);
				path.reset();
				break;
			default:
				return false;
		}
		invalidate();
		return true;
	}

	public void cambiarColor(int color) { //Recibe el int del color
		invalidate();
		paint.setColor(color);
	}

	public void cambiarGrosor(int grosor) {
		paint.setStrokeWidth(grosor);
	}

	public void nuevoDibujo() {
		drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
		invalidate();
	}

	public void borrarPintado(boolean borrado) {
		if(borrado) {
			paint.setColor(Color.rgb(255, 255, 150));
		}
	}
}