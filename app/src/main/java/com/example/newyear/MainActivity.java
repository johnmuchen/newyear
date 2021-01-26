package com.example.newyear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnStart;
    Button btnOrderHao;
    Button btnOrderRui;
    Button btnOrderYuan;
    int order_cnt;
    SharedPreferences sharedPreferences;
    static String HAO_ORDER = "HaoOrder";
    static String RUI_ORDER = "RuiOrder";
    static String YUAN_ORDER = "YuanOrder";
    static String Q_NUM_PLAYER_VISIBILITY = "QVisible";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = (Button) findViewById(R.id.btnStart);
        btnOrderHao = (Button) findViewById(R.id.btnOrderHao);
        btnOrderRui = (Button) findViewById(R.id.btnOrderRui);
        btnOrderYuan = (Button) findViewById(R.id.btnOrderYuan);
        sharedPreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(HAO_ORDER, 1);
        editor.putInt(RUI_ORDER, 2);
        editor.putInt(YUAN_ORDER, 3);
        editor.putInt(Q_NUM_PLAYER_VISIBILITY, 1);
        editor.apply();

        btnStart.setText("遊戲開始");
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
                startActivity(intent);
            }
        });

        order_cnt = 0;
        btnOrderHao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order_cnt = SetOrder(order_cnt);
                Toast.makeText(getApplicationContext(), "Hao : " + String.valueOf(order_cnt), Toast.LENGTH_SHORT).show();
                editor.putInt(HAO_ORDER, order_cnt);
                editor.commit();
            }
        });
        btnOrderRui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order_cnt = SetOrder(order_cnt);
                Toast.makeText(getApplicationContext(), "Rui : " + String.valueOf(order_cnt), Toast.LENGTH_SHORT).show();
                editor.putInt(RUI_ORDER, order_cnt);
                editor.commit();
            }
        });
        btnOrderYuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order_cnt = SetOrder(order_cnt);
                Toast.makeText(getApplicationContext(), "Yuan : " + String.valueOf(order_cnt), Toast.LENGTH_SHORT).show();
                editor.putInt(YUAN_ORDER, order_cnt);
                editor.commit();
            }
        });


    }
    private int SetOrder(int order_cnt) {
        order_cnt = order_cnt + 1;
        if (order_cnt>3) {
            order_cnt = 1;
        }
        return order_cnt;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_setting:
                Intent intent = new Intent(MainActivity.this, SetupActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}