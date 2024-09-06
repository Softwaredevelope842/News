package com.example.newsapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.NewsAdapter;
import com.example.newsapp.NewsApi;
import com.example.newsapp.R;
import com.example.newsapp.datamodelclass;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Spinner countrySpinner,categorySpinner;
    public static ArrayList<datamodelclass.Article> list = new ArrayList<>();
    private static final String BASE_URL = "https://newsapi.org/";
    private static final String API_KEY = "23489f95ed4e44389d7b3952a5ea84f8";
    private Retrofit retrofit;
    private NewsApi newsApi;
    ProgressDialog progressDialog;
    public static String selectedCountry=" ";
    public static String selectedCategory=" ";
    String country[]={"Select Country","ae","ar","at","au","be","bg","br","ca","ch",
            "cn","co","cu","cz","de","eg","fr","gb","gr",
            "hk","hu","id","ie","il","in","it","jp",};

    String language[]={"ar","de","en","es","fr","he","it","nl","no","pt","ru","sv","ud","zh", };
    String category[]={"Select Category","business","entertainment","general","health","science","sports","technology"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Please Wait!");
        progressDialog.setMessage("Loading...");


        recyclerView = findViewById(R.id.recyclerview);
        countrySpinner = findViewById(R.id.spinner);
        categorySpinner = findViewById(R.id.spinner2);

        ArrayAdapter spinnerCountryAdapter=new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1,country);
        ArrayAdapter spinnerCategoryAdapter=new ArrayAdapter(MainActivity.this,
                android.R.layout.simple_dropdown_item_1line,category);
        countrySpinner.setAdapter(spinnerCountryAdapter);
        categorySpinner.setAdapter(spinnerCategoryAdapter);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        newsApi = retrofit.create(NewsApi.class);


        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    selectedCountry=" ";
                }else {
                    selectedCountry=country[position];
                }
                fetchTopHeadLines(country[position],selectedCategory);

            }

            @Override
            public void onNothingSelected(AdapterView< ?> parent) {
            }
        });


        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    selectedCategory=" ";
                }else {
                    selectedCategory=category[position];
                }
                fetchTopHeadLines(selectedCountry,selectedCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fetchTopHeadLines(selectedCountry,selectedCategory);

    }
    private void fetchTopHeadLines(String country,String category) {
        progressDialog.show();
        Call<datamodelclass.Root>  call ;
        list.clear();
        if (country==" "&&category==" "){
            call = newsApi.getTopHeadLines("in","general", API_KEY);
        }else {
            call = newsApi.getTopHeadLines(country,category, API_KEY);
        }
        call.enqueue(new Callback<datamodelclass.Root>() {
            @Override
            public void onResponse(Call<datamodelclass.Root> call, retrofit2.Response<datamodelclass.Root> response) {
                if (response.isSuccessful() && response.body() != null) {
                    NewsAdapter newsAdapter=new NewsAdapter(list);
                    list.addAll(response.body().getArticles());
                    recyclerView.setAdapter(newsAdapter);
                    newsAdapter.notifyDataSetChanged();

                    Toast.makeText(MainActivity.this, "News fetched", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                    Log.e("ERROR", "Response failed: " + response);
                    Toast.makeText(MainActivity.this, "News fetching error", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<datamodelclass.Root> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("ERROR", "Request failed: " + t.getMessage());
                Toast.makeText(MainActivity.this, "on Failure mathod", Toast.LENGTH_SHORT).show();

            }
  });
}

}
