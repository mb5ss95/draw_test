package com.example.draw_test;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;

public class draw extends AppCompatActivity {

    draw_PaintView m;
    LinearLayout drawlinear;

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_draw);

        m = new draw_PaintView(this);
        m.set_Color(android.graphics.Color.BLACK);
        m.set_StrokeWidth(15);

        drawlinear = findViewById(R.id.draw_linear);
        drawlinear.addView(m);
        //drawlinear.setDrawingCacheEnabled(true);


        findViewById(R.id.draw_color_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(draw.this, Color_Books.class), 0);
            }
        });

        findViewById(R.id.draw_width_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(draw.this, draw_LineWidth.class);
                intent.putExtra("draw_LineWidth", m.get_StrokeWidth());
                startActivityForResult(intent, 1);
            }
        });

        findViewById(R.id.draw_white_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m.set_Color(android.graphics.Color.WHITE);
            }
        });

        findViewById(R.id.clear_btn).setOnClickListener(new View.OnClickListener() { //지우기 버튼 눌렸을때
            @Override
            public void onClick(View v) {
                drawlinear.setBackgroundColor(Color.WHITE);
                m.clear_point();
                m.invalidate();
            }
        });

        findViewById(R.id.save_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawlinear.buildDrawingCache(true);
                Bitmap captureView = drawlinear.getDrawingCache(true).copy(Bitmap.Config.RGB_565, false);
                drawlinear.destroyDrawingCache();
                FileOutputStream fos;

                String draw_name = "/draw.jpeg";
                try {
                    fos = new FileOutputStream(Environment.getExternalStorageDirectory().toString() + draw_name);
                    captureView.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), "[" + draw_name + "]로 저장 하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.load_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String draw_name = "/draw.jpeg";
                Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().toString() + draw_name);
                drawlinear.setBackgroundColor(Color.WHITE);
                drawlinear.setBackground(new BitmapDrawable(getResources(), bitmap));
            }
        });


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if(requestCode == 0) {
                int result = data.getIntExtra("Color_Books", 0);
                m.set_Color(result);
            }
            else{
                int result = data.getIntExtra("draw_LineWidth", 1);
                m.set_StrokeWidth(result);
            }
        }
    }

}