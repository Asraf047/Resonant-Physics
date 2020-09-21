package com.amanullah.myapplication98;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.amanullah.myapplication98.ui.Earn.EarnFragment;
import com.amanullah.myapplication98.ui.Learn.LearnFragment;
import com.amanullah.myapplication98.ui.Profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.auth.User;

public class MainActivity extends AppCompatActivity  implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    private String label = null;
    private boolean isClient = false;
    private User mCurrentUser;

    final Fragment fragment1 = new LearnFragment();
    final Fragment fragment2 = new EarnFragment();
    final Fragment fragment3 = new ProfileFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(MainActivity.this);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                FragmentTransaction ft = fm.beginTransaction();
                ft.add(R.id.nav_host_fragment, fragment1, "1");
                ft.add(R.id.nav_host_fragment, fragment2, "2").hide(fragment2);
                ft.add(R.id.nav_host_fragment, fragment3, "3").hide(fragment3);
                ft.commit();
                active = fragment1;
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_learn: {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        fm.beginTransaction().hide(active).show(fragment1).commit();
                        active = fragment1;
//                        TodayFragment fragment = (TodayFragment)fm.findFragmentByTag("1");
//                        fragment.updateView();
                    }
                });
                return true;
            }
            case R.id.navigation_earn: {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        fm.beginTransaction().hide(active).show(fragment2).commit();
                        active = fragment2;
                    }
                });
                return true;
            }
            case R.id.navigation_profile: {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        fm.beginTransaction().hide(active).show(fragment3).commit();
                        active = fragment3;
                    }
                });
                return true;
            }
        }
        return false;
    }


    @Override
    public void onBackPressed() {
        Fragment currentFragment = fm.findFragmentById(R.id.nav_host_fragment);

//        if(currentFragment instanceof testFragment){
//            fm.beginTransaction().remove(currentFragment).commit();
//            Log.d(TAG, "onBackPressed: appointmentFragment");
//        } else
            if (currentFragment instanceof LearnFragment ||
                currentFragment instanceof EarnFragment ||
                currentFragment instanceof ProfileFragment ) {
            if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
                if(Build.VERSION.SDK_INT>=16){
                    finishAffinity();
                } else {
                    finish();
                    System.exit(0);
                }
                return;
            } else {
                Toast.makeText(getBaseContext(), "Tap back again to exit", Toast.LENGTH_SHORT).show();
            }
            mBackPressed = System.currentTimeMillis();
        }  else {
            super.onBackPressed();
        }


    }
}
