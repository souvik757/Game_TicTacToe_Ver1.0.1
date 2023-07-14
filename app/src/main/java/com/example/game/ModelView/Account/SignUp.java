package com.example.game.ModelView.Account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.game.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignUp extends AppCompatActivity {
    // firebase connection :
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance() ;
    private CollectionReference collectionReference ;
    // firebase auth var :
    private FirebaseAuth firebaseAuth ;
    private FirebaseUser firebaseUser ;
    // widgets :
    private EditText emailET ;
    private EditText passwordET ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        InitializeWidgets() ;
    }
    private void InitializeWidgets(){
        emailET = findViewById(R.id._email_) ;
        passwordET = findViewById(R.id._password_) ;
    }

    private void ShowCustomSnackBar(String message) {
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
    private String ExtractName(String email){
        int index = 0 ;
        char[] name = new char[email.length()] ;
        for (int i = 0 ; i < email.length() ; i ++){
            if(email.charAt(i) == '@')
                break ;
            name[index++] = email.charAt(i) ;
        }

        return new String(name).trim() ;
    }


    @Override
    public void onBackPressed() {
        finish() ;
    }
}