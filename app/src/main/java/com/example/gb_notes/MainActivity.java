package com.example.gb_notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.gb_notes.observer.Publisher;
import com.example.gb_notes.ui.AppInfoFragment;
import com.example.gb_notes.ui.NotesListFragment;
import com.example.gb_notes.ui.SettingsFragment;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {

    private Publisher publisher = new Publisher();
    private Navigation navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();

        NotesListFragment fragmentList = NotesListFragment.newInstance();
        getNavigation().addFragment(fragmentList, false);
    }

    public Navigation getNavigation() {
        return navigation;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    private void initview() {
        navigation = new Navigation(getSupportFragmentManager());
        Toolbar toolbar = initToolbar();
        initDrawer(toolbar);
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    private void initDrawer(Toolbar toolbar) {
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (navigateFragment(id)) {
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                }
                return false;
            }
        });

    }

    private boolean navigateFragment(int id) {
        switch (id) {
            case R.id.action_settings:
                getNavigation().addFragment(new SettingsFragment(), true);
                return true;
            case R.id.action_appInfo:
                getNavigation().addFragment(new AppInfoFragment(), true);
                return true;
            case R.id.action_notesList:
                getNavigation().addFragment(new NotesListFragment(), true);
                return true;
        }
        return false;

    }


}
