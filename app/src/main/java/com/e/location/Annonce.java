package com.e.location;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;

public class Annonce extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annonce);


        drawerLayout =findViewById(R.id.drawer);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView =findViewById(R.id.navigateur);
        navigationView.setNavigationItemSelectedListener(this);




        actionBarDrawerToggle = new ActionBarDrawerToggle(Annonce.this, drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        // actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new tout()).commit();
            navigationView.setCheckedItem(R.id.nav_hom);
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new tout()).commit();
                break;
            case R.id.nav_maison:
                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new maison()).commit();
                break;
            case R.id.nav_studio:
                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new studio()).commit();
                break;
            case R.id.nav_Fpublic:
                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new foyer_public()).commit();
                break;
            case R.id.nav_Fpriver:
                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new foyer_privé()).commit();
                break;
            case R.id.nav_transport:
                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new transport()).commit();
                break;
            case R.id.nav_patrimoine:
                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new patrimoine()).commit();
                break;



        }


        drawerLayout.closeDrawer(GravityCompat.START);



        return true;
    }
    
}
