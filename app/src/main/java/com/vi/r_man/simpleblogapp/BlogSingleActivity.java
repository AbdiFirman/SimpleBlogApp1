package com.vi.r_man.simpleblogapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class BlogSingleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_single);

        String post_key = getIntent().getExtras().getString("blog_id");
        Toast.makeText(BlogSingleActivity.this, post_key, Toast.LENGTH_LONG).show();
    }
}
