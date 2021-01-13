package com.example.legendsbunkv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.example.legendsbunkv2.Intros.HomeFragmentIntro;
import com.google.android.material.navigation.NavigationView;
import com.pixplicity.easyprefs.library.Prefs;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        drawer=findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        toggle.syncState();
        //to make sure when app starts there is some activity and to make the same activity visible when the app is rotated.
        if (savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
        //        EasyPrefs Library Initialization code
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
//        EasyPrefs Library Initialization code

//setting status bar color android

        Window window = this.getWindow();
// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,android.R.color.black));
//setting status bar color android

        if(Prefs.getString("HomeFragmentIntro","enabled").equals("enabled") ){
            Log.d("abcde", "onCreate:before intro "+Prefs.getString("HomeFragmentIntro","enabled"));
            Intent intent=new Intent(this, HomeFragmentIntro.class);
            intent.putExtra("Intro","HomeFragment");
            startActivityForResult(intent,1000);
            Log.d("abcde", "onCreate:before intro "+Prefs.getString("HomeFragmentIntro","enabled"));
        }

    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_custom_menu_items,menu);

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
                if(drawer.isDrawerOpen(GravityCompat.START))
                    drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_postTimeTable:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new PostTimeTableFragment()).commit();
                if(drawer.isDrawerOpen(GravityCompat.START))
                    drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_log_past_attendance:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new LogPastAttendanceFragment()).commit();
                if(drawer.isDrawerOpen(GravityCompat.START))
                    drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_history:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HistoryFragment()).commit();
                if(drawer.isDrawerOpen(GravityCompat.START))
                    drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_create_TT:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new TimeTableFragment()).commit();
                if(drawer.isDrawerOpen(GravityCompat.START))
                    drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_futureAttendence:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new FutureAttendanceFragment()).commit();
                if(drawer.isDrawerOpen(GravityCompat.START))
                    drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_classBunkCalc:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ClassBunkCalcFragment()).commit();
                if(drawer.isDrawerOpen(GravityCompat.START))
                    drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_thresholdAttendance:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ThresholdAttendanceFragment()).commit();
                if(drawer.isDrawerOpen(GravityCompat.START))
                    drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_backupRestore:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new BackupRestoreFragment()).commit();
                if(drawer.isDrawerOpen(GravityCompat.START))
                    drawer.closeDrawer(GravityCompat.START);
                break;


        }

        return true;
    }
}

