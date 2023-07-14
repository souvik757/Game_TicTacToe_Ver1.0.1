package com.example.game.ModelView.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.game.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignIn extends AppCompatActivity {

    // firebase connection :
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance() ;
    // firebase auth var :
    private FirebaseAuth firebaseAuth ;
    private FirebaseAuth.AuthStateListener authStateListener ;
    private FirebaseUser firebaseUser ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }

    public void GoToSignUp(View view) {
        startActivity(new Intent(SignIn.this , SignUp.class));
    }

    @Override
    public void onBackPressed() {
        finish() ;
    }
}