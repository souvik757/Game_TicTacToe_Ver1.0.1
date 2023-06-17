package com.example.game.ModelView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.example.game.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class MainMenu extends AppCompatActivity {
    // Get a reference to the Firebase Storage instance
    private StorageReference storageRef1 = FirebaseStorage.getInstance().getReference();
    private LinearLayoutCompat mainBackGround ;
    private ImageView img1 ;
    private Button profile ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        InitializeWidgets() ;
        InitializeViews() ;
    }
    private void InitializeWidgets(){
        img1    = findViewById(R.id.Button_Play)    ;
        profile = findViewById(R.id.Button_Profile) ;
    }
    private void InitializeViews(){
        storageRef1 = storageRef1.child("BackGrounds/play.png") ;
        SetResourcesForImageView(storageRef1, img1) ;
    }
    public void InitializeAnimations(View view){
        scaleImage(1.7f);
        new Handler().postDelayed(()->{
            scaleImage(1.0f);
        } , 100) ;
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

    public void GoToProfile(View view){
        rotateImage(180);
        new Handler().postDelayed(()->{
            Snackbar.make(view , "coming soon !" , Snackbar.LENGTH_LONG).show();
        } , 100) ;
    }

    public void GoToNextMenu(View view) {
        startActivity(new Intent(MainMenu.this , StartMenu.class));
        finish() ;
    }

    @Override
    public void onBackPressed() {
        AlertBoxExit(MainMenu.this , "Do ypu really want to exit ?");
    }
    public void AlertBoxExit(Context context , String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setTitle("Alert !");
        builder.setCancelable(true);
        builder.setPositiveButton("confirm", (DialogInterface.OnClickListener) (dialog, which) -> {
            // do things. . .
            finish() ;
        });
        builder.setNegativeButton("cancel", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void scaleImage(float scale) {
        ObjectAnimator scaleAnimatorX = ObjectAnimator.ofFloat(img1, "scaleX", scale);
        ObjectAnimator scaleAnimatorY = ObjectAnimator.ofFloat(img1, "scaleY", scale);

        scaleAnimatorX.setDuration(500);
        scaleAnimatorY.setDuration(500);

        scaleAnimatorX.start();
        scaleAnimatorY.start();
    }
    private void rotateImage(float rotation) {
        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(profile, "rotation", rotation);
        rotationAnimator.setDuration(500);
        rotationAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        rotationAnimator.start();
    }
}
/*  showCustomSnackbar("");

    private void showCustomSnackbar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        View customSnackbarView = getLayoutInflater().inflate(R.layout.custom_snackbar_layout, null);
        ImageView iconImageView = customSnackbarView.findViewById(R.id.iconImageView);
        iconImageView.setImageResource(R.drawable.ic_alert);
        TextView messageTextView = customSnackbarView.findViewById(R.id.messageTextView);
        messageTextView.setText("Custom Snackbar");
        snackbarLayout.addView(customSnackbarView, 0);
        snackbarLayout.setBackgroundColor(Color.RED);
        snackbar.show();
    }
}
 */