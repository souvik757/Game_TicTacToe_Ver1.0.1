package com.example.game.ModelView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.game.R;
import com.example.game.ModelView.Account.SignIn;
import com.example.game.UtilsClasses.DataFields;
import com.example.game.UtilsClasses.UtilMethods;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ktx.Firebase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.checkerframework.checker.guieffect.qual.UI;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Settings extends AppCompatActivity {
    // Get a reference to the Firebase Storage instance
    private StorageReference storageRef1 = FirebaseStorage.getInstance().getReference();
    private FirebaseUser currentUser ;
    private FirebaseFirestore db ;
    private ImageView imgview1 ;
    private TextView beta_message ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        FirebaseApp.initializeApp(this) ;
        InitializeWidgets() ;
        showbetamessage() ;
        InitializeViews() ;
    }
    private void showbetamessage(){
        beta_message = findViewById(R.id._build_notice_) ;
        new Handler().postDelayed(()->{
            beta_message.setVisibility(View.VISIBLE);
        } , 1000) ;
    }
    private void InitializeWidgets(){
        imgview1 = findViewById(R.id._profile_pic_) ;
    }
    private void InitializeViews(){
        storageRef1 = storageRef1.child("setting/demo_profile.png") ;
        SetResourcesForImageView(storageRef1 , imgview1) ;
    }
    private void SetResourcesForImageView(StorageReference reference , ImageView imageView){
        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(Settings.this , MainMenu.class));
        finish() ;
    }

    public void LogIn(View view) {
        currentUser = FirebaseAuth.getInstance().getCurrentUser() ;
        if(currentUser == null)
            startActivity(new Intent(Settings.this , SignIn.class));
        else{
            startActivity(new Intent(Settings.this , profiler.class)) ;
        }
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