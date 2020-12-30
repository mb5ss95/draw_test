package com.example.draw_test;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class text extends AppCompatActivity {

    public class File_manager {

        Context context;

        public File_manager(Context context) {
            this.context = context;
        }

        public void save(String file_name, String str) {
            if (str == null || str.equals("")) {
                return;
            }
            try {
                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory().toString() + file_name);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
                writer.write(str);
                writer.close();
                output.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public String load(String str) {
            String result = "something wrong";
            try {
                FileInputStream input = new FileInputStream(Environment.getExternalStorageDirectory().toString() + str);
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                result = reader.readLine();
                reader.close();
                input.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

    }

    File_manager file_manager = new File_manager(text.this);
    EditText editText;

    private void show_dialog(){
        final String[] items = {"빙그레체", "~~체", "여우비체", "굵게", "기울게", "기본 폰트"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("  다양한 글꼴을 선택하세요.");
        builder.setIcon(R.drawable.android_green);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int index) {
                //String selectedText = items[index];
                //Typeface typeface = null;
                switch (index) {
                    case 0:
                        editText.setTypeface(getResources().getFont(R.font.bing));
                        break;
                    case 1:
                        editText.setTypeface(getResources().getFont(R.font.bauhs93));
                        break;
                    case 2:
                        editText.setTypeface(getResources().getFont(R.font.yoonsun));
                        break;
                    case 3:
                        editText.setTypeface(editText.getTypeface(), Typeface.BOLD);
                        break;
                    case 4:
                        editText.setTypeface(editText.getTypeface(), Typeface.ITALIC);
                        break;
                    case 5:
                        editText.setTextColor(Color.BLACK);
                        editText.setTypeface(null, Typeface.NORMAL);
                        break;
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_text);

        editText = findViewById(R.id.text_edit);



        findViewById(R.id.text_handwrite_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(text.this, text_HandWrite.class), 0);
            }
        });
        findViewById(R.id.text_rewrite_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });
        findViewById(R.id.text_save_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text_name = "/text.txt";
                file_manager.save(text_name, editText.getText().toString());
                Toast.makeText(getApplicationContext(), "[" + text_name + "]로 저장 하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.text_load_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(file_manager.load("/text.txt"));
            }
        });
        findViewById(R.id.text_font_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_dialog();
            }
        });

        findViewById(R.id.text_color_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(text.this, Color_Books.class), 1);
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                String result = data.getStringExtra("text_HandWrite");
                editText.setText(editText.getText() + result);
            } else {
                int result = data.getIntExtra("Color_Books", 0);
                editText.setTextColor(result);
            }
        }
    }

}
