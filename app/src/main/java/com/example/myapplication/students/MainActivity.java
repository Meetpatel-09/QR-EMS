package com.example.myapplication.students;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.myapplication.FirstActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.R;
import com.example.myapplication.students.attendance.ScanActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private NavController navController;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        navController = Navigation.findNavController(this, R.id.fragment_l);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.start, R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(this);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        toggle.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.logout_menu) {
            auth.signOut();
            startActivity(new Intent(MainActivity.this, FirstActivity.class));
            finish();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.navigation_about:
//                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.navigation_developer:
//                Toast.makeText(this, "Developer", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.navigation_ebook:
//                Toast.makeText(this, "EBook", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.navigation_theme:
//                Toast.makeText(this, "Themes", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.navigation_rate:
//                Toast.makeText(this, "Rate", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.navigation_video:
//                Toast.makeText(this, "Video Lectures", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.navigation_website:
//                Toast.makeText(this, "Web-Site", Toast.LENGTH_SHORT).show();
//                break;
            case R.id.navigation_scan:
                startActivity(new Intent(MainActivity.this, ScanActivity.class));
                break;
        }
        return true;
    }
}