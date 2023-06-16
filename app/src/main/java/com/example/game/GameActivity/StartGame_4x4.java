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
import com.example.game.GameLogic.ThreeLogic;
import com.example.game.GameLogic.raw;
import com.example.game.ModelView.MainMenu;
import com.example.game.ModelView.StartMenu;
import com.example.game.R;

import java.util.HashMap;
import java.util.Map;
public class StartGame_4x4 extends AppCompatActivity {
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
        setContentView(R.layout.activity_start_game4x4);
        InitializeWidgets() ;
        InitializeViews() ;
        InitializeAnimations() ;
    }
    private void InitializeWidgets(){
        button = new Button[4][4] ;
        button[0][0] = findViewById(R.id.Button1) ;
        button[0][1] = findViewById(R.id.Button2) ;
        button[0][2] = findViewById(R.id.Button3) ;
        button[0][3] = findViewById(R.id.Button4) ;

        button[1][0] = findViewById(R.id.Button5) ;
        button[1][1] = findViewById(R.id.Button6) ;
        button[1][2] = findViewById(R.id.Button7) ;
        button[1][3] = findViewById(R.id.Button8) ;

        button[2][0] = findViewById(R.id.Button9) ;
        button[2][1] = findViewById(R.id.Button10) ;
        button[2][2] = findViewById(R.id.Button11) ;
        button[2][3] = findViewById(R.id.Button12) ;

        button[3][0] = findViewById(R.id.Button13) ;
        button[3][1] = findViewById(R.id.Button14) ;
        button[3][2] = findViewById(R.id.Button15) ;
        button[3][3] = findViewById(R.id.Button16) ;

        ButtonRetry  = findViewById(R.id.Button_retry) ;
        InitializeBoard() ;
    }
    private void InitializeBoard() {
        for (int i = 0 ; i < 4  ; i ++){
            for (int j = 0 ; j < 4 ; j ++)
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
        Board.setAnimation(AnimationUtils.loadAnimation(StartGame_4x4.this , R.anim.bottom_to_up1));
        PlayerTurn.setAnimation(AnimationUtils.loadAnimation(StartGame_4x4.this , R.anim.bottom_to_up2));
    }

    public void GameAct(View view){
        // increase counter
        counter++ ;
        if(counter == 1)
            ButtonRetry.setVisibility(View.VISIBLE);
        view.findViewById(view.getId()).setEnabled(false);
        if(counter % 2 == 0) { // circle ::
            view.findViewById(view.getId()).setBackground(getDrawable(R.drawable.circle_bg));
            PlayerTurn.setBackground(getDrawable(R.drawable.cross));
            val.put(view.findViewById(view.getId()) , raw.circle) ;
        }
        else { // cross ::
            view.findViewById(view.getId()).setBackground(getDrawable(R.drawable.cross_bg));
            PlayerTurn.setBackground(getDrawable(R.drawable.circle));
            val.put(view.findViewById(view.getId()) , raw.cross) ;
        }
        String winner = CheckForWinner() ;
        if(counter < 15){
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
        for (int i = 0 ; i < 4  ; i ++){
            for (int j = 0 ; j < 4 ; j ++)
                button[i][j].setEnabled(false);
        }
    }
    public void GoToMenu(View view){
        AlertBoxMenu(StartGame_4x4.this , "Your progress will be lost !");
    }
    public void GoToExit(View view){
        AlertBoxExit(StartGame_4x4.this , "Do you really want to exit ?");
    }
    public void LaunchGame(View view){
        AlertBoxRetry(StartGame_4x4.this , "Your progress will be lost !");
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
            startActivity(new Intent(this , StartGame_4x4.class));
            finish() ;
        });
        builder.setNegativeButton("cancel", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}