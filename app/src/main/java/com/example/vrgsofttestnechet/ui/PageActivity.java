package com.example.vrgsofttestnechet.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.example.vrgsofttestnechet.R;

public class PageActivity extends AppCompatActivity {

    private WebView wwPage;
    private String pageURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);

        wwPage = findViewById(R.id.wwPage);

        Bundle extras = getIntent().getExtras();
        pageURL = extras.getString("pageURL");
        wwPage.loadUrl(pageURL);
    }
}
