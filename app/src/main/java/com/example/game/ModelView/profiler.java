package com.example.game.ModelView;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.game.R;
import com.example.game.UtilsClasses.UtilMethods;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class profiler extends AppCompatActivity {
    private FirebaseUser currentUser ;
    private TextView userName ;
    private Button signOut ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiler);
        initializeWidgets() ;
        initializeDatabase();
        fillData();
        initializeOnCLickListeners() ;
    }
    private void initializeWidgets(){
        userName = findViewById(R.id.name) ;
        signOut = findViewById(R.id.signOut) ;
    }
    private void initializeDatabase(){
        currentUser = FirebaseAuth.getInstance().getCurrentUser() ;
    }
    private void fillData(){
        String Email , Name ;
        Email = currentUser.getEmail() ;
        Name = UtilMethods.extractNameFromEmail(String.valueOf(Email).trim()) ;
        userName.setText(Name) ;
    }
    private void initializeOnCLickListeners(){
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut() ;
                finish() ;
            }
        });
    }
}