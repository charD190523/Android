package com.example.cinemaapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

<<<<<<< HEAD
=======
import androidx.activity.EdgeToEdge;
>>>>>>> 1fc599b4c6a13815f8f8f670a21c555a5c9061a7
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
<<<<<<< HEAD
import androidx.core.view.GravityCompat;
=======
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
>>>>>>> 1fc599b4c6a13815f8f8f670a21c555a5c9061a7
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.cinemaapp.fragment.HomeFragment;
import com.example.cinemaapp.fragment.ScheduleFragment;
<<<<<<< HEAD
//import com.example.cinemaapp.fragment.StoreFragment;
=======
>>>>>>> 1fc599b4c6a13815f8f8f670a21c555a5c9061a7
import com.example.cinemaapp.fragment.UserInfoActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

<<<<<<< HEAD
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;
=======
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;
>>>>>>> 1fc599b4c6a13815f8f8f670a21c555a5c9061a7
    private long backPressedTime = 0;

    public static Context appContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

<<<<<<< HEAD
        // Gán context cho biến toàn cục
=======
        // Gán context cho biến toàn cuc
>>>>>>> 1fc599b4c6a13815f8f8f670a21c555a5c9061a7
        appContext = getApplicationContext();

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        bottomNavigationView = findViewById(R.id.bottom_nav);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
<<<<<<< HEAD
                replaceFragment(new HomeFragment());
                return true;
            } else if (itemId == R.id.nav_schedule) {
                replaceFragment(new ScheduleFragment());
=======
                Fragment selectedFragment = new HomeFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
                return true;
            } else if( itemId == R.id.nav_schedule){
                Fragment selectedFragment = new ScheduleFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
>>>>>>> 1fc599b4c6a13815f8f8f670a21c555a5c9061a7
                return true;
            } else if (itemId == R.id.nav_profile) {
                Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
                startActivity(intent);
                return true;
            }

            return false;
        });

        // Mặc định vào HomeFragment
        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.nav_home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
<<<<<<< HEAD
        int id = item.getItemId();

        if (id == R.id.drawer_home) {
            replaceFragment(new HomeFragment());
        } else if (id == R.id.drawer_schedule) {
            replaceFragment(new ScheduleFragment());
        } else if (id == R.id.drawer_store) {
            replaceFragment(new ScheduleFragment());
        } else if (id == R.id.drawer_profile) {
            Intent intent = new Intent(this, UserInfoActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Tính năng chưa được hỗ trợ", Toast.LENGTH_SHORT).show();
        }

        drawerLayout.closeDrawer(GravityCompat.START); // Đóng Navigation Drawer
        return true;
    }


    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
=======
        // Xử lý chuyển fragment khi chọn trong menu bên trái nếu cần
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.
                START)) {
>>>>>>> 1fc599b4c6a13815f8f8f670a21c555a5c9061a7
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if (currentFragment instanceof HomeFragment) {
                if (backPressedTime + 2000 > System.currentTimeMillis()) {
                    finishAffinity(); // Đóng tất cả Activity và thoát ứng dụng
<<<<<<< HEAD
=======
                    System.exit(0); // Đảm bảo tiến trình dừng hoàn toàn
>>>>>>> 1fc599b4c6a13815f8f8f670a21c555a5c9061a7
                } else {
                    Toast.makeText(this, "Nhấn back lần nữa để thoát", Toast.LENGTH_SHORT).show();
                }
                backPressedTime = System.currentTimeMillis();
            } else {
                super.onBackPressed();
            }
        }
    }
<<<<<<< HEAD
}
=======


}
>>>>>>> 1fc599b4c6a13815f8f8f670a21c555a5c9061a7
