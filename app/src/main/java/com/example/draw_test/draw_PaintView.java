package com.example.draw_test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class draw_PaintView extends View {
    ArrayList<Point> points = new ArrayList<>();
    int color;
    int width;

    public draw_PaintView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint p = new Paint();
        for (int i = 1; i < points.size(); i++) {
            p.setColor(points.get(i).color);
            p.setStrokeWidth(points.get(i).width);
            p.setStrokeJoin(Paint.Join.ROUND);
            p.setStrokeCap(Paint.Cap.ROUND);
            if (!points.get(i).check)
                continue;
            canvas.drawLine(points.get(i - 1).x, points.get(i - 1).y, points.get(i).x, points.get(i).y, p);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                points.add(new Point(x, y, false, color, width));
            case MotionEvent.ACTION_MOVE:
                points.add(new Point(x, y, true, color, width));
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        invalidate();
        return true;
    }

    public void clear_point(){
        points.clear();
    }

    public void set_StrokeWidth(int width){
        this.width = width;
    }

    public void set_Color(int color){
        this.color = color;
    }

    public int get_StrokeWidth(){
        return this.width;
    }

    class Point {
        float x;
        float y;
        boolean check;
        int color;
        int width;

        public Point(float x, float y, boolean check, int color, int width) {
            this.x = x;
            this.y = y;
            this.check = check;
            this.color = color;
            this.width = width;
        }
    }
}
