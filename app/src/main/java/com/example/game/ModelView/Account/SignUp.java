package com.example.game.ModelView.Account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.game.R;
import com.example.game.UtilsClasses.FireStoreDataFields;
import com.example.game.UtilsClasses.UtilMethods;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    // firebase auth var :
    private FirebaseAuth mAuth ;
    private FirebaseUser currentUser ;
    // firebase fire store
    private FirebaseFirestore db ;
    // widgets :
    private EditText emailET ;
    private EditText passwordET ;
    private Button signUp ;
    private ProgressBar progressBar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        InitializeWidgets() ;
        InitializeDatabase();
        InitializeOnCLickEvents() ;
    }
    private void InitializeWidgets(){
        emailET = findViewById(R.id._email_) ;
        passwordET = findViewById(R.id._password_) ;
        signUp = findViewById(R.id._signup_) ;
        progressBar = findViewById(R.id.progressBar) ;
    }
    private void InitializeDatabase(){
        mAuth = FirebaseAuth.getInstance() ;
        db = FirebaseFirestore.getInstance();
    }
    private void InitializeOnCLickEvents(){
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email , password ;
                email = emailET.getText().toString().trim() ;
                password = passwordET.getText().toString().trim() ;
                progressBar.setVisibility(View.VISIBLE);
                if(TextUtils.isEmpty(email)){
                    showCustomToast("enter email !");
                    return ;
                }
                if(TextUtils.isEmpty(password)){
                    showCustomToast("enter password !");
                    return ;
                }
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            showCustomToast("account created successfully");
                            progressBar.setVisibility(View.GONE);
                            // send a verification email
                            currentUser = mAuth.getCurrentUser() ;
                            createFireStore(email, currentUser.getUid());
                            currentUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                        showCustomToast("verification email sent !");
                                }
                            }) ;
                            Log.d("ALERT", "acc created by sign up") ;
                            finish() ;
                        }
                        else {
                            showCustomToast("unexpected error occurred");
                            progressBar.setVisibility(View.GONE);
                            Log.d("ALERT" , "error in sign up") ;
                        }
                    }
                }) ;

            }
        });
    }
    @Override
    public void onBackPressed() {
        finish() ;
    }
    private void showCustomSnackBar(String message) {
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
    private void showCustomToast(String message){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id._custom_toast_container_));

        ImageView alert = layout.findViewById(R.id._icon_) ;
        alert.setImageResource(R.drawable.baseline_circle_notifications_24);
        TextView text = layout.findViewById(R.id._message_);
        text.setText(message);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();

    }
    private void createFireStore(String email , String UID){
        String collectionPath , uid , date ;
        String credit , score3 , score4 , score5 , winCircle , winCross , activeTime ;
        collectionPath = UtilMethods.extractNameFromEmail(email) ;
        uid = UID ;
        date = String.valueOf(new Timestamp(new Date())).trim() ;
        credit = "0" ;
        score3 = "0" ;
        score4 = "0" ;
        score5 = "0" ;
        winCircle = "0" ;
        winCross = "0" ;
        activeTime = "0" ;
        Map<String , Object> info = new HashMap<>() ;
        info.put(FireStoreDataFields.credit , credit) ;
        info.put(FireStoreDataFields.userID , uid) ;
        info.put(FireStoreDataFields.dateOfCreation , date) ;
        info.put(FireStoreDataFields.score3X3 , score3) ;
        info.put(FireStoreDataFields.score4X4 , score4) ;
        info.put(FireStoreDataFields.score5X5 , score5) ;
        info.put(FireStoreDataFields.winAsCircle , winCircle) ;
        info.put(FireStoreDataFields.winAsCross , winCross) ;
        info.put(FireStoreDataFields.totalActiveHour , activeTime) ;
        db = FirebaseFirestore.getInstance() ;
        db.collection(collectionPath).document(FireStoreDataFields.playerInfo)
                .set(info)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        showCustomToast("successfully snapshots written");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showCustomToast("snapshot writing rejected");
                    }
                }) ;
    }
}