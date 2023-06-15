package com.example.game.SpashScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.game.ModelView.* ;
import com.example.game.R;

public class MainActivity extends AppCompatActivity {
    // * -- Buttons to animate --
    private Button[][] block ;
    ProgressBar progressBar ;
    CountDownTimer countDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // * -- Initialize Buttons --
        InitializeWidgets() ;
        TriggerAnimation()  ;
        LoadingAnimation()  ;
    }
    private void InitializeWidgets(){
        block = new Button[3][3] ;

        block[0][0] = findViewById(R.id.btn1);
        block[0][1] = findViewById(R.id.btn2);
        block[0][2] = findViewById(R.id.btn3);
        block[1][0] = findViewById(R.id.btn4);
        block[1][1] = findViewById(R.id.btn5);
        block[1][2] = findViewById(R.id.btn6);
        block[2][0] = findViewById(R.id.btn7);
        block[2][1] = findViewById(R.id.btn8);
        block[2][2] = findViewById(R.id.btn9);

        progressBar = findViewById(R.id.progressBar);
    }
    private void TriggerAnimation(){
        // right_to_left1
        block[0][2].setAnimation(AnimationUtils.loadAnimation(MainActivity.this , R.anim.right_to_left1));
        block[1][1].setAnimation(AnimationUtils.loadAnimation(MainActivity.this , R.anim.right_to_left1));
        block[1][2].setAnimation(AnimationUtils.loadAnimation(MainActivity.this , R.anim.right_to_left1));
        // left_to_right1
        block[2][0].setAnimation(AnimationUtils.loadAnimation(MainActivity.this , R.anim.left_to_right1));
        // fade1 + green
        block[0][1].setAnimation(AnimationUtils.loadAnimation(MainActivity.this , R.anim.top_to_bottom1));
        block[0][1].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this ,R.color.red))) ;
        block[2][1].setAnimation(AnimationUtils.loadAnimation(MainActivity.this , R.anim.top_to_bottom1));
        block[2][1].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this ,R.color.red))) ;
        block[2][2].setAnimation(AnimationUtils.loadAnimation(MainActivity.this , R.anim.bottom_to_up2));
        block[2][2].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this ,R.color.red))) ;
    }
    private void LoadingAnimation(){
        ShowProgressBar();
        long totalTimeInMillis = 3000 ;
        long intervalInMillis = 1000 ;
        countDownTimer = new CountDownTimer(totalTimeInMillis, intervalInMillis) {
            @Override
            public void onTick(long millisUntilFinished) {
                int progress = (int) ((totalTimeInMillis - millisUntilFinished) * 100 / totalTimeInMillis);
                progressBar.setProgress(progress);
            }
            @Override
            public void onFinish() {
                GoToMenu() ;
                progressBar.setProgress(100);
            }
        };
        countDownTimer.start() ;
    }
    private void ShowProgressBar(){
        new Handler().postDelayed(()->{
            progressBar.setVisibility(View.VISIBLE);
        } , 1000) ;
    }
    private void GoToMenu(){
        new Handler().postDelayed(
                ()->{
                    startActivity(new Intent(MainActivity.this , StartMenu.class));
                    finish() ;
                }
                ,500) ;
    }
}
// SHA1: 20:B8:5A:2D:A4:5C:A6:2C:67:DA:5A:E6:04:4E:DF:BC:0F:7B:90:55