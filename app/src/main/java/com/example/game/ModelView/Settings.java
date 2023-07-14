package com.example.game.ModelView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.game.R;
import com.example.game.ModelView.Account.SignIn;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Settings extends AppCompatActivity {
    // Get a reference to the Firebase Storage instance
    private StorageReference storageRef1 = FirebaseStorage.getInstance().getReference();
    private ImageView imgview1 ;
    private TextView beta_message ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        InitializeWidgets() ;

        showbetamessage();

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
        // Fetch the download URL for the image
        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Use the download URL to load the image into the ImageView
                Picasso.get().load(uri).into(imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle any errors that occur while fetching the image
            }
        });
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(Settings.this , MainMenu.class));
        finish() ;
    }

    public void LogIn(View view) {
        startActivity(new Intent(Settings.this , SignIn.class));

    }
}