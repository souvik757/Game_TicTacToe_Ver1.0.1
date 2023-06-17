package com.example.game.GameActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.game.GameLogic.NLogic;
import com.example.game.GameLogic.raw;
import com.example.game.ModelView.MainMenu;
import com.example.game.ModelView.StartMenu;
import com.example.game.R;

import java.util.HashMap;
import java.util.Map;

public class StartGame_6x6 extends AppCompatActivity {
    private int counter = -1 ;
    private Button[][] button;
    private Button ButtonRetry ;
    private Map<Button , Integer> val = new HashMap<>() ;
    private LinearLayout Board ;
    private ImageView PlayerTurn ;
    private ImageView CircleWin ;
    private ImageView CrossWin ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game6x6);
        InitializeWidgets() ;
        InitializeViews() ;
        InitializeAnimations() ;
    }
    private void InitializeWidgets(){
        button = new Button[6][6] ;
        button[0][0] = findViewById(R.id.Button1)  ;
        button[0][1] = findViewById(R.id.Button2)  ;
        button[0][2] = findViewById(R.id.Button3)  ;
        button[0][3] = findViewById(R.id.Button4)  ;
        button[0][4] = findViewById(R.id.Button5)  ;
        button[0][5] = findViewById(R.id.Button6)  ;

        button[1][0] = findViewById(R.id.Button7)  ;
        button[1][1] = findViewById(R.id.Button8)  ;
        button[1][2] = findViewById(R.id.Button9)  ;
        button[1][3] = findViewById(R.id.Button10) ;
        button[1][4] = findViewById(R.id.Button11) ;
        button[1][5] = findViewById(R.id.Button12) ;

        button[2][0] = findViewById(R.id.Button13) ;
        button[2][1] = findViewById(R.id.Button14) ;
        button[2][2] = findViewById(R.id.Button15) ;
        button[2][3] = findViewById(R.id.Button16) ;
        button[2][4] = findViewById(R.id.Button17) ;
        button[2][5] = findViewById(R.id.Button18) ;

        button[3][0] = findViewById(R.id.Button19) ;
        button[3][1] = findViewById(R.id.Button20) ;
        button[3][2] = findViewById(R.id.Button21) ;
        button[3][3] = findViewById(R.id.Button22) ;
        button[3][4] = findViewById(R.id.Button23) ;
        button[3][5] = findViewById(R.id.Button24) ;

        button[4][0] = findViewById(R.id.Button25) ;
        button[4][1] = findViewById(R.id.Button26) ;
        button[4][2] = findViewById(R.id.Button27) ;
        button[4][3] = findViewById(R.id.Button28) ;
        button[4][4] = findViewById(R.id.Button29) ;
        button[4][5] = findViewById(R.id.Button30) ;

        button[5][0] = findViewById(R.id.Button31) ;
        button[5][1] = findViewById(R.id.Button32) ;
        button[5][2] = findViewById(R.id.Button33) ;
        button[5][3] = findViewById(R.id.Button34) ;
        button[5][4] = findViewById(R.id.Button35) ;
        button[5][5] = findViewById(R.id.Button36) ;

        ButtonRetry  = findViewById(R.id.Button_retry) ;
        InitializeBoard() ;
    }
    private void InitializeBoard() {
        for (int i = 0 ; i < 6  ; i ++){
            for (int j = 0 ; j < 6 ; j ++)
                val.putIfAbsent(button[i][j] , raw.empty) ;
        }
    }
    private void InitializeViews(){
        Board = findViewById(R.id.GameBoard) ;
        PlayerTurn = findViewById(R.id.Button_turn) ;
        CircleWin = findViewById(R.id.winner_circle) ;
        CrossWin = findViewById(R.id.winner_cross) ;
        PlayerTurn.setBackground(getDrawable(R.drawable.circle));
    }
    private void InitializeAnimations(){
        Board.setAnimation(AnimationUtils.loadAnimation(StartGame_6x6.this , R.anim.bottom_to_up1));
        PlayerTurn.setAnimation(AnimationUtils.loadAnimation(StartGame_6x6.this , R.anim.bottom_to_up2));
    }
    public void GameAct(View view){
        // increase counter
        counter++ ;
        if(counter == 1)
            ButtonRetry.setVisibility(View.VISIBLE);
        view.findViewById(view.getId()).setEnabled(false);
        if(counter % 2 == 0) { // circle ::
            view.findViewById(view.getId()).setBackground(getDrawable(R.drawable.circle_bg_6x6));
            PlayerTurn.setBackground(getDrawable(R.drawable.cross));
            val.put(view.findViewById(view.getId()) , raw.circle) ;
        }
        else { // cross ::
            view.findViewById(view.getId()).setBackground(getDrawable(R.drawable.cross_bg_6x6));
            PlayerTurn.setBackground(getDrawable(R.drawable.circle));
            val.put(view.findViewById(view.getId()) , raw.cross) ;
        }
        String winner = CheckForWinner() ;
        if(counter < 35){
            if(!winner.equals("draw")){
                if(winner.equals("circle")){
                    CircleWin.setVisibility(View.VISIBLE);
                }
                if(winner.equals("cross")){
                    CrossWin.setVisibility(View.VISIBLE);
                }
                DisableCLick() ;
            }
        }
        else{
            if(!winner.equals("draw")){
                if(winner.equals("circle")){
                    CircleWin.setVisibility(View.VISIBLE);
                }
                if(winner.equals("cross")){
                    CrossWin.setVisibility(View.VISIBLE);
                }
            }
            else{
                ChangeBackground();
                Toast.makeText(this, "draw", Toast.LENGTH_SHORT).show();
            }
            DisableCLick() ;
        }

    }

    private String CheckForWinner(){
        NLogic obj = new NLogic(button , val) ;
        int X = obj.CheckRow() ;
        int Y = obj.CheckCol() ;
        int Z = obj.CheckDiagonal() ;
        if(X != -1 || Y != -1 || Z != -1){
            if(X != -1){
                if(X == raw.circle)
                    return "circle" ;
                if(X == raw.cross)
                    return "cross" ;
            }
            if(Y != -1){
                if(Y == raw.circle)
                    return "circle" ;
                if(Y == raw.cross)
                    return "cross" ;
            }
            if(Z != -1){
                if(Z == raw.circle)
                    return "circle" ;
                if(Z == raw.cross)
                    return "cross" ;
            }
        }
        return "draw" ;
    }

    private void ChangeBackground(){
        int startColor = getResources().getColor(R.color.ash);
        int endColor = getResources().getColor(R.color.red);

        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), startColor, endColor);
        colorAnimation.setDuration(3000); // Duration in milliseconds

        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                Board.setBackgroundColor((int) animator.getAnimatedValue());
            }
        });
        colorAnimation.start();
    }
    private void DisableCLick(){
        for (int i = 0 ; i < 6  ; i ++){
            for (int j = 0 ; j < 6 ; j ++)
                button[i][j].setEnabled(false);
        }
    }
    public void GoToMenu(View view){
        AlertBoxMenu(StartGame_6x6.this , "Your progress will be lost !");
    }
    public void GoToExit(View view){
        AlertBoxExit(StartGame_6x6.this , "Do you really want to exit ?");
    }
    public void LaunchGame(View view){
        AlertBoxRetry(StartGame_6x6.this , "Your progress will be lost !");
    }
    public void AlertBoxMenu(Context context , String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setTitle("Alert !");
        builder.setCancelable(true);
        builder.setPositiveButton("confirm", (DialogInterface.OnClickListener) (dialog, which) -> {
            // do things. . .
            startActivity(new Intent(this , StartMenu.class));
            finish() ;
        });
        builder.setNegativeButton("cancel", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void AlertBoxExit(Context context , String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setTitle("Alert !");
        builder.setCancelable(true);
        builder.setPositiveButton("confirm", (DialogInterface.OnClickListener) (dialog, which) -> {
            // do things. . .
            startActivity(new Intent(this , MainMenu.class));
            finish() ;
        });
        builder.setNegativeButton("cancel", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void AlertBoxRetry(Context context , String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setTitle("Alert !");
        builder.setCancelable(true);
        builder.setPositiveButton("confirm", (DialogInterface.OnClickListener) (dialog, which) -> {
            // do things. . .
            startActivity(new Intent(this , StartGame_6x6.class));
            finish() ;
        });
        builder.setNegativeButton("cancel", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    @Override
    public void onBackPressed() {
        AlertBoxExit(StartGame_6x6.this , "Your progress will be lost !");
    }
}