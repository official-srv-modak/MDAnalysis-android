package com.souravmodak.mdanalysis;

import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Menu;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;


import com.souravmodak.mdanalysis.databinding.ActivityMainBinding;
import com.souravmodak.mdanalysis.misc.ApiService;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private ApiService apiService;
    private Menu settingsMenu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize view binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set up toolbar
        setSupportActionBar(binding.appBarMain.toolbar);

        // Set up FloatingActionButton click listener to navigate to GalleryFragment
        binding.appBarMain.addProduct.setOnClickListener(view -> openGalleryFragment());

        // Set up drawer and navigation
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Configure navigation with drawer
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();

        // Setup NavController and NavigationUI
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Handle navigation drawer item clicks
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_gallery) {
                openGalleryFragment();
            } else if (id == R.id.nav_home) {
                // Navigate to Home Fragment
                navController.navigate(R.id.nav_home);
            } else if (id == R.id.nav_slideshow) {
                // Navigate to Slideshow Fragment
                navController.navigate(R.id.nav_slideshow);
            }
            drawer.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void openGalleryFragment() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        navController.navigate(R.id.nav_gallery);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        // Access the MenuItem after inflating the menu
        MenuItem item = menu.findItem(R.id.action_settings);
        item.setTitle("Logout");
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                // Perform logout operations here
                handleLogout();
                return true; // Return true to indicate that the click event was handled
            }
        });

        return true;
    }

    private void handleLogout() {
        // Implement your logout logic here
        // For example, clearing user session, redirecting to login activity, etc.

        // Example: Redirect to LoginActivity
        finish(); // Optionally finish the current activity
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }



}