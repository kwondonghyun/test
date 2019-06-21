package com.example.mocon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    FloatingActionButton fab_main, fab_edit, fab_tran;
    CalendarView calendarView;
    TextView tv_title_1, tv_title_2;
    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    Integer year, month, days, now_year, now_month, now_days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_title_1 = findViewById(R.id.your_experience_title);
        tv_title_2 = findViewById(R.id.your_day_title);
        calendarView = findViewById(R.id.calendar);
        fab_main = findViewById(R.id.fab_main);
        fab_edit = findViewById(R.id.fab_edit);
        fab_tran = findViewById(R.id.fab_tran);
        fab_main.setOnClickListener(this);
        fab_edit.setOnClickListener(this);
        fab_tran.setOnClickListener(this);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);


        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String getTime = sdf.format(date);
        String[] separated = getTime.split("-");
         year = Integer.parseInt(separated[0]);
         month = Integer.parseInt(separated[1]);
         days = Integer.parseInt(separated[2]);
         now_year = year;
         now_month = month;
         now_days = days;

        tv_title_1.setText(month+"월 "+days+"일 의 경험");
        tv_title_2.setText(month+"월 "+days+"일 의 하루");

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int _year, int _month, int _dayOfMonth) {
                year = _year;
                month = _month+1;
                days = _dayOfMonth;
                tv_title_1.setText(month+"월 "+days+"일 의 경험");
                tv_title_2.setText(month+"월 "+days+"일 의 하루");

            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fab_main:
                anim();
                break;

            case R.id.fab_tran:
                anim();
                Intent intent1  = new Intent(MainActivity.this,RemindActivity.class);
                startActivity(intent1);
                finish();
                break;

            case R.id.fab_edit:
                if(now_year < year || now_month < month || now_days < days){
                    Toast.makeText(this, "미래 날짜에는 작성할 수 없습니다.", Toast.LENGTH_SHORT).show();
                }else if (now_year > year || now_month > month || now_days > days){
                    Toast.makeText(this, "과거 날짜는 기록만 확인이 가능합니다.", Toast.LENGTH_SHORT).show();
                }else{
                    anim();
                    Intent intent2  = new Intent(MainActivity.this,RatingActivity.class);
                    startActivity(intent2);
                }

                break;
        }
    }



    public void anim(){

        if (isFabOpen) {
            fab_tran.startAnimation(fab_close);
            fab_edit.startAnimation(fab_close);
            fab_tran.setClickable(false);
            fab_edit.setClickable(false);
            isFabOpen = false;
        } else {
            fab_tran.startAnimation(fab_open);
            fab_edit.startAnimation(fab_open);
            fab_tran.setClickable(true);
            fab_edit.setClickable(true);
            isFabOpen = true;
        }
    }
}
