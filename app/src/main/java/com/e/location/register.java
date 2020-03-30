package com.e.location;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText user, email, password, tel;
    Button cxn;
    FirebaseAuth fAuth;
    ProgressBar prog;
    FirebaseFirestore fStore;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        user =findViewById(R.id.name);
        email= findViewById(R.id.email);
        password =findViewById(R.id.password);
        tel= findViewById(R.id.tel);
        cxn= findViewById(R.id.cxn);

        fStore =FirebaseFirestore.getInstance();
        fAuth=FirebaseAuth.getInstance();
        prog=findViewById(R.id.progr);

        cxn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String mail= email.getText().toString().trim();
                String pasword = password.getText().toString().trim();
                final String fullname = user.getText().toString();
                final String phone =tel.getText().toString();

                if(TextUtils.isEmpty(mail)){
                    email.setError("Email est vide");
                    return;
                }
                if(TextUtils.isEmpty(pasword)){
                    password.setError("Mot de passe est vide");
                    return;
                }
                if(pasword.length()<6){
                    password.setError("Mot de passe est inférieur à 6 caractère");
                    return;
                }
                prog.setVisibility(View.VISIBLE);


                fAuth.createUserWithEmailAndPassword(mail, pasword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(register.this, "acces avec succes", Toast.LENGTH_SHORT).show();
                            userID =fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("user").document(userID);
                            Map<String, Object> user = new HashMap<>();
                            user.put("name", fullname);
                            user.put("email", mail);
                            user.put("phone", phone);

                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess : User profil is created for "+ userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure :"+ e.toString());
                                }
                            });


                            startActivity(new Intent(getApplicationContext(), profil.class));
                        } else {
                            Toast.makeText(register.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            prog.setVisibility(View.GONE);
                        }
                    }


                });


            }
        });


    }

    public void connection(View view) {
        startActivity(new Intent(getApplicationContext(), Login.class));

    }
}
