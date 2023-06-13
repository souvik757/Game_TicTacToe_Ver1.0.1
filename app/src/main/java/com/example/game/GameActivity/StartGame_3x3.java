package com.example.game.GameActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.game.R;

public class StartGame_3x3 extends AppCompatActivity {
    private int counter = -1 ;
    private Button[][] Button ;
    private LinearLayout Board ;
    private ImageView PlayerTurn ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game_3x3);
        InitializeWidgets() ;
        InitializeViews();
        InitializeAnimations();
    }
    private void InitializeWidgets(){
        Button = new Button[3][3] ;
        Button[0][0] = findViewById(R.id.Button1) ;
        Button[0][1] = findViewById(R.id.Button2) ;
        Button[0][2] = findViewById(R.id.Button3) ;
        Button[1][0] = findViewById(R.id.Button4) ;
        Button[1][1] = findViewById(R.id.Button5) ;
        Button[1][2] = findViewById(R.id.Button6) ;
        Button[2][0] = findViewById(R.id.Button7) ;
        Button[2][1] = findViewById(R.id.Button8) ;
        Button[2][2] = findViewById(R.id.Button9) ;
    }
    private void InitializeViews(){
        Board = findViewById(R.id.GameBoard) ;
        PlayerTurn = findViewById(R.id.Button_turn) ;
        PlayerTurn.setBackground(getDrawable(R.drawable.circle));
    }
    private void InitializeAnimations(){
        Board.setAnimation(AnimationUtils.loadAnimation(StartGame_3x3.this , R.anim.bottom_to_up1));
        PlayerTurn.setAnimation(AnimationUtils.loadAnimation(StartGame_3x3.this , R.anim.bottom_to_up2));
    }
    public void GameAct(View view){
        // increase counter
        counter++ ;
        view.findViewById(view.getId()).setEnabled(false);
        if(counter % 2 == 0) { // circle ::
            view.findViewById(view.getId()).setBackground(getDrawable(R.drawable.circle_bg));
            PlayerTurn.setBackground(getDrawable(R.drawable.cross));
        }
        else{ // cross ::
            view.findViewById(view.getId()).setBackground(getDrawable(R.drawable.cross_bg));
            PlayerTurn.setBackground(getDrawable(R.drawable.circle));
        }
    }
}