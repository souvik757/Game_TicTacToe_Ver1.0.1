package com.example.game.GameActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.game.GameLogic.* ;

import com.example.game.ModelView.MainMenu;
import com.example.game.ModelView.StartMenu;
import com.example.game.R;
import com.example.game.UtilsClasses.FireStoreDataFields;
import com.example.game.UtilsClasses.UtilMethods;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class StartGame_3x3 extends AppCompatActivity {
    private int counter = -1 ;
    private String currPlayer ;
    // layouts
    private LinearLayout Board ;
    private RelativeLayout winStats ;
    private ImageView toWin ;
    private ImageView toLose ;
    private Button[][] button;
    private FloatingActionButton ButtonRetry ;
    private ImageView PlayerTurn ;
    private ImageView CircleWin ;
    private ImageView CrossWin ;
    private boolean IsGameOver = false ;
    private Map<Button , Integer> val = new HashMap<>() ;
    // firebase var
    private boolean status = true ;
    private FirebaseAuth mAuth ;
    private DocumentReference mDocReference ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game_3x3);

        InitializeWidgets() ;
        InitializeDatabase();
        InitializeViews();
        InitializeAnimations();
    }

    /**
     * Initializers
     */
    private void InitializeWidgets(){
        currPlayer = getIntent().getStringExtra("selected_player") ;
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

        winStats = findViewById(R.id.status) ;
        toWin = findViewById(R.id.hasToWin) ;
        toLose = findViewById(R.id.hasToLose) ;
        ButtonRetry  = findViewById(R.id.Button_retry) ;
        InitializeBoard() ;
    }
    private void InitializeDatabase(){
        mAuth = FirebaseAuth.getInstance() ;
        if(mAuth.getCurrentUser() == null){
            showCustomSnack_Bar("create an account to save progress" , R.color.blue);
            status = false ;
        }
        else {
            String collectionPath = UtilMethods.extractNameFromEmail(mAuth.getCurrentUser().getEmail());
            mDocReference = FirebaseFirestore.getInstance().collection(collectionPath).document("playerInfo");
        }
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
        PlayerTurn.setBackground(AppCompatResources.getDrawable(StartGame_3x3.this , R.drawable.baseline_panorama_fish_eye_24));
    }
    private void InitializeAnimations(){
        Board.setAnimation(AnimationUtils.loadAnimation(StartGame_3x3.this , R.anim.bottom_to_up1));
        PlayerTurn.setAnimation(AnimationUtils.loadAnimation(StartGame_3x3.this , R.anim.bottom_to_up2));
        winStats.setAnimation(AnimationUtils.loadAnimation(StartGame_3x3.this , R.anim.bottom_to_up2));
        showWinnerStats() ;
    }

    private void showWinnerStats(){
        if(currPlayer.equals("circle")) {
            toWin.setBackgroundColor(getColor(R.color.red));
            toLose.setBackgroundColor(getColor(R.color.green));
        }
        else {
            toWin.setBackgroundColor(getColor(R.color.green));
            toLose.setBackgroundColor(getColor(R.color.red));
        }
    }

    /**
     * Game functions
     * @param view
     */
    public void GameAct(View view){
        // increase counter
        counter++ ;

        // show retry button after 2 moves
        if(counter == 1)
            ButtonRetry.setVisibility(View.VISIBLE);

        // disable click for previous tapped button's
        view.findViewById(view.getId()).setEnabled(false);

        // toggle buttons for 2 players accordingly
        if(counter % 2 == 0) { // circle ::
            view.findViewById(view.getId()).setBackgroundResource(R.drawable.circle_bg);
            PlayerTurn.setBackground(AppCompatResources.getDrawable(this , R.drawable.cross));
            val.put(view.findViewById(view.getId()) , raw.circle) ;
        }
        else { // cross ::
            view.findViewById(view.getId()).setBackgroundResource(R.drawable.cross_bg);
            PlayerTurn.setBackground(AppCompatResources.getDrawable(this , R.drawable.baseline_panorama_fish_eye_24));
            val.put(view.findViewById(view.getId()) , raw.cross) ;
        }

        // check for winner for any point of time
        String winner = CheckForWinner() ;
        if(counter < 8){
            if(!winner.equals("draw")){
                IsGameOver = true ;
                // show winner
                if(winner.equals("circle")){
                    CircleWin.setVisibility(View.VISIBLE);
                }
                if(winner.equals("cross")){
                    CrossWin.setVisibility(View.VISIBLE);
                }
                // ....
                if(!winner.equals(currPlayer)){
                    _vibrate_();
                    ChangeBackground();
                    showCustomSnack_Bar("you lose" , R.color.red);
                }
                else { // you win
                    showCustomSnack_Bar(winner+" have won !" , R.color.green);
                    // update score here
                    // update credit here
                    updateDatabase(winner);
                }
                DisableCLick() ;
            }
        }
        else{ // board is filled
            if(!winner.equals("draw")){
                IsGameOver = true ;
                // show winner
                if(winner.equals("circle")){
                    CircleWin.setVisibility(View.VISIBLE);
                }
                if(winner.equals("cross")){
                    CrossWin.setVisibility(View.VISIBLE);
                }
                // ....
                if(!winner.equals(currPlayer)){
                    _vibrate_();
                    ChangeBackground();
                    showCustomSnack_Bar("Game draw" , R.color.red);
                }
                else { // you win
                    showCustomSnack_Bar(winner+" have won !" , R.color.green);
                    // update score here
                    // update credit here
                    updateDatabase(winner);
                }
            }
            else{
                IsGameOver = true ;
                _vibrate_();
                ChangeBackground();
                showCustomSnack_Bar("Game draw" , R.color.red);
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

    /**
     * DB updates
     * @param param who wins
     */
    private void updateDatabase(String param){
        if(status) {
            if (param.equals("circle"))
                updateScoreForCircle();
            else
                updateScoreForCross();
        }
    }
    private void updateScoreForCircle(){
        Map<String, Object> dataMap = new HashMap<>() ;
        mDocReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    // total wins
                    int circleWin = Integer.parseInt(String.valueOf(documentSnapshot.getString(FireStoreDataFields.winAsCircle))) ;
                    dataMap.put(FireStoreDataFields.winAsCircle , String.valueOf((circleWin+1))) ;
                    // total wins on 3X3 board
                    int win3X3 = Integer.parseInt(String.valueOf(documentSnapshot.getString(FireStoreDataFields.score3X3))) ;
                    dataMap.put(FireStoreDataFields.score3X3 , String.valueOf(win3X3 + 1)) ;
                    // total credit
                    int prevCredit = Integer.parseInt(String.valueOf(documentSnapshot.getString(FireStoreDataFields.credit))) ;
                    dataMap.put(FireStoreDataFields.credit , String.valueOf(prevCredit+3)) ;

                    mDocReference.update(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                                showCustomSnack_Bar("progress saved" , R.color.green);
                        }
                    }) ;
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace() ;
            }
        }) ;
    }
    private void updateScoreForCross(){
        Map<String, Object> dataMap = new HashMap<>() ;
        mDocReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    // total wins
                    int crossWin = Integer.parseInt(String.valueOf(documentSnapshot.getString(FireStoreDataFields.winAsCross))) ;
                    dataMap.put(FireStoreDataFields.winAsCross , String.valueOf((crossWin+1))) ;
                    // total wins on 3X3 board
                    int win3X3 = Integer.parseInt(String.valueOf(documentSnapshot.getString(FireStoreDataFields.score3X3))) ;
                    dataMap.put(FireStoreDataFields.score3X3 , String.valueOf(win3X3 + 1)) ;
                    // total credit
                    int prevCredit = Integer.parseInt(String.valueOf(documentSnapshot.getString(FireStoreDataFields.credit))) ;
                    dataMap.put(FireStoreDataFields.credit , String.valueOf(prevCredit+3)) ;
                    mDocReference.update(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                                showCustomSnack_Bar("progress saved" , R.color.green);
                        }
                    }) ;
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace() ;
            }
        }) ;
    }

    /**
     * Utility Metods
     */
    private void ChangeBackground(){
        int startColor = getColor(R.color.ash);
        int endColor = getColor(R.color.red);

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
            Intent i = new Intent(this , StartGame_3x3.class) ;
            i.putExtra("selected_player", currPlayer) ;
            startActivity(i);
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
    private void showCustomSnack_Bar(String message , int color) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), " ", Snackbar.LENGTH_LONG);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        View customSnackbarView = getLayoutInflater().inflate(R.layout.custom_snackbar_layout, null);
        ImageView iconImageView = customSnackbarView.findViewById(R.id.iconImageView);
        iconImageView.setImageResource(R.drawable.baseline_cancel_24);
        TextView messageTextView = customSnackbarView.findViewById(R.id.messageTextView);
        messageTextView.setText(message);
        snackbarLayout.addView(customSnackbarView, 0);
        snackbarLayout.setBackgroundColor(getColor(color));
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