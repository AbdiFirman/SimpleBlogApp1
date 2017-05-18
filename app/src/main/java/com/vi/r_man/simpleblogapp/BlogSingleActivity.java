package com.vi.r_man.simpleblogapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class BlogSingleActivity extends AppCompatActivity {
    private String post_key = null;
    private DatabaseReference databaseReference;

    private ImageView imageView;
    private TextView textViewTitle;
    private TextView textViewDesc;

    private Button removePost;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_single);

        imageView = (ImageView) findViewById(R.id.post_image1);
        textViewTitle = (TextView) findViewById(R.id.post_title1);
        textViewDesc = (TextView) findViewById(R.id.post_text1);
        removePost = (Button) findViewById(R.id.removePost);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Blog");

        final String post_key = getIntent().getExtras().getString("blog_id");
//        Toast.makeText(BlogSingleActivity.this, post_key, Toast.LENGTH_LONG).show();

        databaseReference.child(post_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String post_title = (String) dataSnapshot.child("title").getValue();
                String post_desc = (String) dataSnapshot.child("desc").getValue();
                String post_image = (String) dataSnapshot.child("image").getValue();
                String post_uid = (String) dataSnapshot.child("uid").getValue();

                textViewTitle.setText(post_title);
                textViewDesc.setText(post_desc);

                Picasso.with(BlogSingleActivity.this).load(post_image).into(imageView);

                if (firebaseAuth.getCurrentUser().getUid().equals(post_uid)){
                    removePost.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        removePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(post_key).removeValue();

                Intent mainIntent = new Intent(BlogSingleActivity.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });
    }
}
