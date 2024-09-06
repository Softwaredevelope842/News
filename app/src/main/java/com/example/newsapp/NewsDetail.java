package com.example.newsapp;


import static com.example.newsapp.MainActivity.list;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;


import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class NewsDetail extends AppCompatActivity {

    int itemPosition;
    ImageView imageView;
    private TextView tvtime,tvTitle,tvDesc,tvcontent,tvauthor;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        itemPosition=getIntent().getIntExtra("key",0);
        DateFormat formater = new SimpleDateFormat("dd-MM-yy");


        tvtime = findViewById(R.id.textView12);
        tvTitle = findViewById(R.id.textView4);
        tvDesc = findViewById(R.id.textView7);
        tvcontent = findViewById(R.id.textView5);
        tvauthor=findViewById(R.id.textView10);


        tvtime.setText(formater.format(list.get(itemPosition).publishedAt));
        tvTitle.setText(list.get(itemPosition).getTitle());
        tvDesc.setText(list.get(itemPosition).getDescription());
        tvcontent.setText(list.get(itemPosition).getContent());
        tvauthor.setText(list.get(itemPosition).getAuthor());

        imageView = findViewById(R.id.imageView2);
        Picasso.get().load(list.get(itemPosition).getUrlToImage()).placeholder(R.mipmap.ic_newslogo).into(imageView);



}
}
