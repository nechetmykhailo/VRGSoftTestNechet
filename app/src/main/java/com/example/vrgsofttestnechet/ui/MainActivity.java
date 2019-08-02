package com.example.vrgsofttestnechet.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Window;

import com.example.vrgsofttestnechet.R;
import com.example.vrgsofttestnechet.adapter.AdapterFragment;
import com.example.vrgsofttestnechet.fragment.EmailedFragment;
import com.example.vrgsofttestnechet.fragment.FavoritesFragment;
import com.example.vrgsofttestnechet.fragment.SharedFragment;
import com.example.vrgsofttestnechet.fragment.ViewedFragment;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        viewPager.setOffscreenPageLimit(4);
    }

    private void setupViewPager(ViewPager viewPager) {
        AdapterFragment adapter = new AdapterFragment(getSupportFragmentManager());
        adapter.addFragment(new EmailedFragment(), "Emailed");
        adapter.addFragment(new SharedFragment(), "Shared");
        adapter.addFragment(new ViewedFragment(), "Viewed");
        adapter.addFragment(new FavoritesFragment(), "Favorites");
        viewPager.setAdapter(adapter);
    }
}
