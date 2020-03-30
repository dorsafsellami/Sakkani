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
import android.widget.RadioButton;

import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;

public class Ajouter_annonce extends AppCompatActivity {



    FirebaseAuth fAuth;

    FirebaseFirestore fStore;

    EditText t1,t2,t3,t4,t5,t6,t7,t8,t9,t10;
    Button save;
    RadioButton b1,b2,b3,b4;
    ProgressBar prog;
    DatabaseReference reff;

    public static final String TAG = "TAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_annonce);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        prog.setVisibility(View.VISIBLE);

        save.findViewById(R.id.menregistrer);

        t1 = findViewById(R.id.mnom);
        t2 = findViewById(R.id.memail);
        t3 = findViewById(R.id.mtel);
        t4 = findViewById(R.id.mregion);
        t5 = findViewById(R.id.madresse);
        t6 = findViewById(R.id.mchambre);
        t7 = findViewById(R.id.mnb_perso);
        t8 = findViewById(R.id.mdistance);
        t9 = findViewById(R.id.mprix);
        t10 = findViewById(R.id.mpropriete);
        b1 = findViewById(R.id.moui);
        b2 = findViewById(R.id.mnon);
        b3 = findViewById(R.id.mavec);
        b4 = findViewById(R.id.msans);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Nom = t1.getText().toString();
                String email = t2.getText().toString();
                String telephone = t3.getText().toString();
                String region = t4.getText().toString();
                String adresse = t5.getText().toString();
                String chambre = t6.getText().toString();
                String nembre_personne = t7.getText().toString();
                String distance = t8.getText().toString();
                String prix = t9.getText().toString();
                String propriete = t10.getText().toString();
                String RB1 = b1.getText().toString();
                String RB2 = b2.getText().toString();
                String RB3 = b3.getText().toString();
                String RB4 = b4.getText().toString();

                if (TextUtils.isEmpty(Nom)){
                    t1.setError("Nom est vide");
                    return;

                }
                if (TextUtils.isEmpty(email)){
                    t2.setError("Email est vide");
                    return;

                }
                if (TextUtils.isEmpty(telephone)){
                    t3.setError("Téléphone est vide");
                    return;

                }
                if (TextUtils.isEmpty(region)){
                    t4.setError("Région est vide");
                    return;

                }
                if (TextUtils.isEmpty(adresse)){
                    t5.setError("Adresse est vide");
                    return;

                }
                if (TextUtils.isEmpty(chambre)){
                    t6.setError("Chambre est vide");
                    return;

                }
                if (TextUtils.isEmpty(nembre_personne)){
                    t7.setError("Nembre de personne est vide");
                    return;

                } if (TextUtils.isEmpty(prix)){
                    t9.setError("Prix est vide");
                    return;

                }


            Map<String, Object> Maison = new HashMap<>();

            Maison.put("Nom", Nom);
            Maison.put("Mail", email);
            Maison.put("Phone", telephone);
            Maison.put("Région", region);
            Maison.put("Adresse", adresse);
            Maison.put("Chambre", chambre);
            Maison.put("Nbre personne", nembre_personne);
            Maison.put("Distance", distance);
            Maison.put("Prix", prix);
            Maison.put("Propriete", propriete);
            Maison.put("Oui", RB1);
            Maison.put("Non", RB2);
            Maison.put("Avec", RB3);
            Maison.put("Sans", RB4);

            fStore.collection("Annonce maison").document().set(Maison).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(Ajouter_annonce.this, "Maison est enregistré avec succès", Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Ajouter_annonce.this, "Erreur", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, e.toString());

                }
            });
                startActivity(new Intent(getApplicationContext(), profil.class));
                finish();
                prog.setVisibility(View.GONE);
            }
    });


    }
}

