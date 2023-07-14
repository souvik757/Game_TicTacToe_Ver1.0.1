package com.example.game.GameActivity;
import static androidx.core.content.ContextCompat.getSystemService;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.game.GameLogic.* ;

import com.example.game.ModelView.MainMenu;
import com.example.game.ModelView.StartMenu;
import com.example.game.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;

public class StartGame_3x3 extends AppCompatActivity {
    private int counter = -1 ;
    private Button[][] button;
    private Button ButtonRetry ;
    private LinearLayout Board ;
    private ImageView PlayerTurn ;
    private ImageView CircleWin ;
    private ImageView CrossWin ;
    private boolean IsGameOver = false ;
    private Map<Button , Integer> val = new HashMap<>() ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game_3x3);
        InitializeWidgets() ;
        InitializeViews();
        InitializeAnimations();
    }
    private void InitializeWidgets(){
        button = new Button[3][3] ;
        button[0][0] = findViewById(R.id.Button1) ;
        button[0][1] = findViewById(R.id.Button2) ;
        button[0][2] = findViewById(R.id.Button3) ;
        button[1][0] = findViewById(R.id.Button4) ;
        button[1][1] = findViewById(R.id.Button5) ;
        button[1][2] = findViewById(R.id.Button6) ;
        button[2][0] = findViewById(R.id.Button7) ;
        button[2][1] = findViewById(R.id.Button8) ;
        button[2][2] = findViewById(R.id.Button9) ;
        ButtonRetry  = findViewById(R.id.Button_retry) ;
        InitializeBoard() ;
    }

    private void InitializeBoard() {
        for (int i = 0 ; i < 3  ; i ++){
            for (int j = 0 ; j < 3 ; j ++)
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
        Board.setAnimation(AnimationUtils.loadAnimation(StartGame_3x3.this , R.anim.bottom_to_up1));
        PlayerTurn.setAnimation(AnimationUtils.loadAnimation(StartGame_3x3.this , R.anim.bottom_to_up2));
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
        if(counter < 8){
            if(!winner.equals("draw")){
                IsGameOver = true ;
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
                IsGameOver = true ;
                if(winner.equals("circle")){
                    CircleWin.setVisibility(View.VISIBLE);
                }
                if(winner.equals("cross")){
                    CrossWin.setVisibility(View.VISIBLE);
                }
            }
            else{
                IsGameOver = true ;
                _vibrate_();
                ChangeBackground();
                showCustomSnackbar("Game draw");
            }
            DisableCLick() ;
        }

    }
    private String CheckForWinner(){
        ThreeLogic obj = new ThreeLogic(button , val) ;
        int X = obj.CheckRow() ;
        int Y = obj.CheckCol() ;
        int Z = obj.CheckDiagonal() ;
        if(X != -1 || Y != -1 || Z != -1){
            if(X != -1){
                if(X == raw.circle)
                    return "circle";
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
                if(Z == raw.circle) {
                    return "circle";
                }
                if(Z == raw.cross) {
                    return "cross";
                }
            }
        }
        return "draw" ;
    }
    private void ChangeBackground(){
        int startColor = getResources().getColor(R.color.ash);
        int endColor = getResources().getColor(R.color.red);

        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), startColor, endColor);
        colorAnimation.setDuration(3000);

        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                Board.setBackgroundColor((int) animator.getAnimatedValue());
            }
        });
        colorAnimation.start();
    }
    private void DisableCLick(){
        for (int i = 0 ; i < 3  ; i ++){
            for (int j = 0 ; j < 3 ; j ++)
                button[i][j].setEnabled(false);
        }
    }
    public void GoToMenu(View view){
        AlertBoxMenu(StartGame_3x3.this , "Your progress will be lost !");
    }
    public void GoToExit(View view){
        AlertBoxExit(StartGame_3x3.this , "Do you really want to exit ?");
    }
    public void LaunchGame(View view){
        if(IsGameOver)
            AlertBoxRetry(StartGame_3x3.this , "Play again ?");
        else
            AlertBoxRetry(StartGame_3x3.this , "Your progress will be lost !");
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
            startActivity(new Intent(this , StartGame_3x3.class));
            finish() ;
        });
        builder.setNegativeButton("cancel", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void _vibrate_(){
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (vibrator != null && vibrator.hasVibrator()) {
            vibrator.vibrate(200);
        }
    }
    private void showCustomSnackbar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), " ", Snackbar.LENGTH_LONG);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        View customSnackbarView = getLayoutInflater().inflate(R.layout.custom_snackbar_layout, null);
        ImageView iconImageView = customSnackbarView.findViewById(R.id.iconImageView);
        iconImageView.setImageResource(R.drawable.baseline_cancel_24);
        TextView messageTextView = customSnackbarView.findViewById(R.id.messageTextView);
        messageTextView.setText(message);
        snackbarLayout.addView(customSnackbarView, 0);
        snackbarLayout.setBackgroundColor(getColor(R.color.red));
        snackbar.show();
    }
    @Override
    public void onBackPressed() {
        if(IsGameOver)
            AlertBoxExit(StartGame_3x3.this , "title screen !");
        else
            AlertBoxExit(StartGame_3x3.this , "Your progress will be lost !");
    }
}