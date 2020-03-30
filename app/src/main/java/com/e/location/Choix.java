package com.e.location;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Choix extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix);
    }

    public void Chercher(View view) {
        startActivity(new Intent(getApplicationContext(), Annonce.class));
    }

    public void Annoncer(View view) {
        startActivity(new Intent(getApplicationContext(), Login.class));
    }
}
