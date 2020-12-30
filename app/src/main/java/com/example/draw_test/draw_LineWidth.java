package com.example.draw_test;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class draw_LineWidth extends Activity {

    int width;

    TextView textView;
    Button up, down, select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_linewidth);

        textView = findViewById(R.id.value);
        up = findViewById(R.id.up);
        down = findViewById(R.id.down);
        select = findViewById(R.id.select);


        Intent intent = getIntent();
        width = intent.getIntExtra("draw_LineWidth", 10);

        textView.setText("" + width);

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                width = width - 2;
                textView.setText(""+width);
            }
        });

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                width = width + 2;
                textView.setText(""+width);
            }
        });

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_OK, new Intent().putExtra("draw_LineWidth", width));
                finish();
            }
        });
    }

}
