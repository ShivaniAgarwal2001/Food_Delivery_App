package com.example.fooddeliveryapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.fooddeliveryapp.adapters.RestaurantAdapter;
import com.example.fooddeliveryapp.models.RestaurantModel;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RestaurantAdapter.RestaurantClickListener {
    List<RestaurantModel> restaurantModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.drawable.ic_launcher_foreground);
        actionBar.setTitle("Restaurants List");


        initRestaurantsRecycler();

    }



    private List<RestaurantModel> getRestaurantData(){
        InputStream is = getResources().openRawResource(R.raw.restaurent);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        String jsonString = writer.toString();
        Gson gson = new Gson();
        RestaurantModel[] restaurantModels =  gson.fromJson(jsonString, RestaurantModel[].class);
        List<RestaurantModel> restList = Arrays.asList(restaurantModels);

        return  restList;
    }

    private void initRestaurantsRecycler() {
        restaurantModels = getRestaurantData();
        RecyclerView restaurantRecycler = findViewById(R.id.restaurantRecycler);
        restaurantRecycler.setLayoutManager(new LinearLayoutManager(this));
        RestaurantAdapter restaurantAdapter = new RestaurantAdapter(restaurantModels, this, this);
        restaurantRecycler.setAdapter(restaurantAdapter);
    }

    @Override
    public void onItemClick(RestaurantModel restaurantModel) {
//        Toast.makeText(this, "shivani", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("RestaurantModel", restaurantModel);
        startActivity(intent);
    }
}