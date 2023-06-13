package com.example.game.ModelView;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import com.example.game.GameActivity.StartGame_3x3;
import com.example.game.GameActivity.StartGame_4x4;
import com.example.game.GameActivity.StartGame_6x6;
import com.example.game.R;
import com.google.android.material.snackbar.Snackbar;

public class StartMenu extends AppCompatActivity {
    private ImageView playerX ;
    private ImageView playerO ;
    private Spinner spinner ;
    private ArrayAdapter adapter ;
    private boolean isClicked = false ;
    private boolean is_circle_ = false ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);
        // * -- initialize widgets --
        InitializeWidgets();
        // * -- set spinner item --
        SetSpinnerItem();
    }
    private void InitializeWidgets(){
        playerX = findViewById(R.id.player_X) ;
        playerO = findViewById(R.id.player_O) ;
        spinner = findViewById(R.id.board_choice) ;
    }
    private void SetSpinnerItem(){
        adapter = new ArrayAdapter(StartMenu.this , android.R.layout.simple_spinner_dropdown_item ,
                getResources().getStringArray(R.array.BoardChoices)) ;
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter) ;
    }
    public void Show_O(View view){
        isClicked = true ;
        is_circle_ = true ;
        playerO.setVisibility(View.VISIBLE);
        playerO.setAnimation(AnimationUtils.loadAnimation(StartMenu.this , R.anim.right_to_left1));
        playerX.setVisibility(View.INVISIBLE);
    }
    public void Show_X(View view){
        isClicked = true ;
        is_circle_ = false ;
        playerX.setVisibility(View.VISIBLE);
        playerX.setAnimation(AnimationUtils.loadAnimation(StartMenu.this , R.anim.left_to_right1));
        playerO.setVisibility(View.INVISIBLE);
    }
    public void start_game(View view){
        if(!isClicked){
            Snackbar.make(view , "select a player !" , Snackbar.LENGTH_LONG).show();
        }
        else {
        /*
        - set player      -- ' selected_player '
        - set board size  -- ' selected_board  '
         */
            Intent i;
            String setBoardSize = spinner.getSelectedItem().toString();
            if (setBoardSize.equals(getString(R.string.ThreeCrossThree)))
                i = new Intent(StartMenu.this, StartGame_3x3.class);
            else if (setBoardSize.equals(getString(R.string.FOurCrossFour)))
                i = new Intent(StartMenu.this, StartGame_4x4.class);
            else
                i = new Intent(StartMenu.this, StartGame_6x6.class);
            i.putExtra("selected_player", (is_circle_) ? "circle" : "cross");
            i.putExtra("selected_board", setBoardSize);
            startActivity(i);
            finish();
        }
    }
}