package com.example.draw_test;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Color_Books extends Activity {

    int color;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_color);

        final ImageView mImageView = findViewById(R.id.imageView);
        final Button btn1 = findViewById(R.id.btn1);

        mImageView.setDrawingCacheEnabled(true);
        mImageView.buildDrawingCache(true);
        mImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN||event.getAction() == MotionEvent.ACTION_MOVE){
                    Bitmap bitmap = mImageView.getDrawingCache();

                    int pixel = bitmap.getPixel((int)event.getX(),(int)event.getY());

                    int r = Color.red(pixel);
                    int g = Color.green(pixel);
                    int b = Color.blue(pixel);

                    color = Color.rgb(r,g,b);
                    final View mColorView = findViewById(R.id.colorView);
                    mColorView.setBackgroundColor(Color.rgb(r,g,b));
                }
                return true;
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_OK, new Intent().putExtra("Color_Books", color));
                finish();
            }
        });
    }
}
