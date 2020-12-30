package com.example.draw_test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class text_HandWrite extends Activity implements View.OnClickListener {

    private static final String LABEL_FILE = "2350-common-hangul.txt";
    private static final String MODEL_FILE = "optimized_hangul_tensorflow.pb";

    private text_HandWrite_HangulClassifier classifier;
    private text_HandWrite_PaintView paintView;
    private Button alt1, alt2, alt3, alt4, alt5;
    private LinearLayout altLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_handwrite);

        init_wiget();

        paintView = (text_HandWrite_PaintView) findViewById(R.id.paintView);

        TextView drawHereText = (TextView) findViewById(R.id.drawHere);
        paintView.setDrawText(drawHereText);
        paintView.setTextView(alt1, alt2, alt3, alt4, altLayout);

        loadModel();

    }

    private void init_wiget() {
        altLayout = (LinearLayout) findViewById(R.id.altLayout);
        altLayout.setVisibility(View.INVISIBLE);

        alt1 = (Button) findViewById(R.id.alt1);
        alt2 = (Button) findViewById(R.id.alt2);
        alt3 = (Button) findViewById(R.id.alt3);
        alt4 = (Button) findViewById(R.id.alt4);
        alt5 = (Button) findViewById(R.id.alt5);

        alt1.setOnClickListener(this);
        alt2.setOnClickListener(this);
        alt3.setOnClickListener(this);
        alt4.setOnClickListener(this);
        alt5.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.alt1:
                setResult(Activity.RESULT_OK, new Intent().putExtra("text_HandWrite", alt1.getText().toString()));
                this.finish();
                break;
            case R.id.alt2:
                setResult(Activity.RESULT_OK, new Intent().putExtra("text_HandWrite", alt2.getText().toString()));
                this.finish();
                break;
            case R.id.alt3:
                setResult(Activity.RESULT_OK, new Intent().putExtra("text_HandWrite", alt3.getText().toString()));
                this.finish();
                break;
            case R.id.alt4:
                setResult(Activity.RESULT_OK, new Intent().putExtra("text_HandWrite", alt4.getText().toString()));
                this.finish();
                break;
            case R.id.alt5:
                paintView.reset();
                break;
        }
    }


    /**
     * Load pre-trained model in memory.
     */
    private void loadModel() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    classifier = text_HandWrite_HangulClassifier.create(getAssets(),
                            MODEL_FILE, LABEL_FILE, text_HandWrite_PaintView.FEED_DIMENSION,
                            "input", "keep_prob", "output");

                    paintView.setModel(classifier);
                } catch (final Exception e) {
                    throw new RuntimeException("Error loading pre-trained model.", e);
                }
            }
        }).start();
    }


    @Override
    protected void onResume() {
        paintView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        paintView.onPause();
        super.onPause();
    }
}
