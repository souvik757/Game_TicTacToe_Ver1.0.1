package com.example.game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import com.example.game.ModelView.* ;

public class MainActivity extends AppCompatActivity {
    // * -- Buttons to animate --
    private Button[][] block ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // * -- Initialize Buttons --
        InitializeWidgets() ;
        TriggerAnimation()  ;
        new Handler().postDelayed(
                ()->{
                    startActivity(new Intent(MainActivity.this , StartMenu.class));
                    finish() ;
                }
                ,1500) ;
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
}
// SHA1: 20:B8:5A:2D:A4:5C:A6:2C:67:DA:5A:E6:04:4E:DF:BC:0F:7B:90:55