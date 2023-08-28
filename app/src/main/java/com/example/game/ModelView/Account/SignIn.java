package com.example.game.ModelView.Account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.game.UtilsClasses.RealtimeDataFields;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignIn extends AppCompatActivity {
    // firebase auth var :
    private FirebaseAuth mAuth ;
    private DatabaseReference mReference ;
    // widgets
    private EditText email ;
    private EditText password ;
    private Button signIn ;
    private ProgressBar progressBar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initializeWidgets() ;
        initializeDatabase();
        initializeOnClickEvents();
    }
    private void initializeWidgets(){
        email = findViewById(R.id._email_) ;
        password = findViewById(R.id._password_) ;
        signIn = findViewById(R.id._signin_) ;
        progressBar = findViewById(R.id.progress_circular) ;
    }
    private void initializeDatabase(){
        mAuth = FirebaseAuth.getInstance() ;
        mReference = FirebaseDatabase.getInstance().getReference() ;
    }
    private void initializeOnClickEvents(){
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email , Password ;
                progressBar.setVisibility(View.VISIBLE);
                Email = email.getText().toString().trim() ;
                Password = password.getText().toString().trim() ;
                if(TextUtils.isEmpty(Email)) {
                    showCustomToast("enter email");
                    return ;
                }
                if(TextUtils.isEmpty(Password)) {
                    showCustomToast("enter password");
                    return ;
                }
                mAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            showCustomToast("signed in");
                            progressBar.setVisibility(View.GONE);
                            // go to profile
                            // update status on realtime
                            updateRealtime(Email);
                        }
                        else{
                            showCustomToast("unexpected error occurred");
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace() ;
                        showCustomToast("email or password may be wrong");
                        progressBar.setVisibility(View.GONE);
                    }
                }) ;
            }
        });
    }
    private void updateRealtime(String mail){
        String UID = mAuth.getCurrentUser().getUid() ;
        mReference.child(RealtimeDataFields.users).child(UID).child(RealtimeDataFields._users_.status).setValue(true) ;
        mReference.child(RealtimeDataFields.users).child(UID).child(RealtimeDataFields._users_.mail).setValue(mail) ;
    }

    public void GoToSignUp(View view) {
        startActivity(new Intent(SignIn.this , SignUp.class));
    }
    @Override
    public void onBackPressed() {
        finish() ;
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
}