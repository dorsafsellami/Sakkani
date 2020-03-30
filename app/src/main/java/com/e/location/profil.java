package com.e.location;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class profil extends AppCompatActivity {



    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    TextView phone, name, email;
    String userID;

    ImageButton mImageView;
    StorageReference mStorageRef;
    ImageButton ajouter ;

    String[] listItem ;



    private static  final int IMAGE_PICK_CODE =1000;
    private static  final int PERMISSION_CODE =1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        // fetch data

        phone = findViewById(R.id.tel);
        name = findViewById(R.id.name);
        email = findViewById(R.id.adr);

        fAuth= FirebaseAuth.getInstance();
        fStore= FirebaseFirestore.getInstance();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        mStorageRef = storage.getReference();




        userID=fAuth.getCurrentUser().getUid();

        DocumentReference documentReference =fStore.collection("user").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                phone.setText(documentSnapshot.getString("phone"));
                name.setText(documentSnapshot.getString("name"));
                email.setText(documentSnapshot.getString("email"));

            }
        });

        // upload image

        mImageView = findViewById(R.id.imageView);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                        String[] permissions ={Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_CODE);
                    }else{
                        pickImageFromGallery();
                    }
                }else {
                     pickImageFromGallery();
                }
            }


        });

       // Add article

        ajouter= findViewById(R.id.ajouter);
        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listItem = new String[]{"Maison", "Studio", "Foyer public", "Foyer priver"};
                AlertDialog.Builder builder =new AlertDialog.Builder(profil.this);
                builder.setTitle("Type de foyer");
                builder.setSingleChoiceItems(listItem, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i){
                            case 0:
                                startActivity(new Intent(getApplicationContext(),Ajouter_annonce.class));
                                break;
                            case 1:
                                startActivity(new Intent(getApplicationContext(),Ajouter_maison.class));
                                break;
                            case 2:
                                startActivity(new Intent(getApplicationContext(),Ajouter_fpublic.class));
                                break;
                            case 3:
                                startActivity(new Intent(getApplicationContext(),Ajouter_fprive.class));
                                break;

                        }

                    }
                });
                builder.setPositiveButton("Enregistrer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //extract the mail and send reset link

                    }
                });

                builder.create().show();

            }
        });


    }
    private void pickImageFromGallery() {
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i, IMAGE_PICK_CODE);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:{
                if (grantResults.length>=0 && grantResults[0]== PackageManager.PERMISSION_GRANTED ){
                    pickImageFromGallery();
                }
                else {
                    Toast.makeText(this,"permission denied ",Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            mImageView.setImageURI(data.getData());
        }

    }


    //bouton déconnection

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }

}
