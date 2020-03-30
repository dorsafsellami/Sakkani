package com.e.location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText mail, MP;
    Button cxn;
    TextView forg;
    FirebaseAuth fAuth;
    ProgressBar progr;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mail = findViewById(R.id.email);
        MP = findViewById(R.id.password);
        cxn = findViewById(R.id.cxn);
        forg = findViewById(R.id.forg);
        progr = findViewById(R.id.progr);
        fAuth = FirebaseAuth.getInstance();


        // verification des données
        cxn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mail.getText().toString().trim();
                String password = MP.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mail.setError("Email est vide");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    MP.setError("Mot de passe est vide");
                    return;
                }
                progr.setVisibility(View.VISIBLE);

                //acceder au profile

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this, "acces avec succes", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), profil.class));
                            finish();
                        } else {
                            Toast.makeText(Login.this, "email ou mot de passe est incorrecte.", Toast.LENGTH_SHORT).show();
                            progr.setVisibility(View.GONE);
                        }
                    }


                });
            }

        });

    }
    // passer à l'interface s'inscrire

    public void inscrire(View view) {
        startActivity(new Intent(getApplicationContext(), register.class));
        finish();
    }

    // mot de passe oublier


    public void forget(View view) {
        final EditText resetMail = new EditText(view.getContext());
        AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
        passwordResetDialog.setTitle("Reset password ?");
        passwordResetDialog.setMessage("Enter your Email to received reset link.");
        passwordResetDialog.setView(resetMail);


        passwordResetDialog.setPositiveButton("oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //extract the mail and send reset link

                String mail =resetMail.getText().toString();
                fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Login.this,"Reset link send to your mail", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this, " Error ! Reset link ins not send"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        passwordResetDialog.setNegativeButton("non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // close the dialog

            }
        });
        passwordResetDialog.create().show();
    }
}

