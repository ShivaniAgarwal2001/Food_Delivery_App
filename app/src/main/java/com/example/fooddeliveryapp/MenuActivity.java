package com.example.fooddeliveryapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fooddeliveryapp.adapters.MenuAdapter;
import com.example.fooddeliveryapp.models.MenuModel;
import com.example.fooddeliveryapp.models.RestaurantModel;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity implements MenuAdapter.MenuClickListener {
    private List<MenuModel> menuList ;
    private RestaurantModel restaurantList;
    private TextView buttonCheckout;
    // for click listener to retrive data
    private List<MenuModel> itemsInCartList;
    private int totalItemInCart = 0;
    MenuModel m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        restaurantList = getIntent().getParcelableExtra("RestaurantModel");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(restaurantList.getName()+" Menu");
        actionBar.setSubtitle("Address: "+restaurantList.getAddress());
        actionBar.setDisplayHomeAsUpEnabled(true);

        initMenuRecycler();

        buttonCheckout = findViewById(R.id.buttonCheckout);
        buttonCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemsInCartList != null && itemsInCartList.size() <= 0) {
                    Toast.makeText(MenuActivity.this, "Please add items in cart.", Toast.LENGTH_SHORT).show();
                    return;
                }
                restaurantList.setMenus(itemsInCartList);
                Intent i = new Intent(MenuActivity.this, PlaceOrderActivity.class);
                i.putExtra("RestaurantModel", restaurantList);  //passing for the list of ordered menu items
                startActivityForResult(i, 101);

                Log.d("TAG", "onClick: "+restaurantList.getName()+"\n"+itemsInCartList+"\n"+totalItemInCart);  //todo
            }
        });
    }

    private void initMenuRecycler() {
        menuList = restaurantList.getMenus();
        RecyclerView menuRecycler = findViewById(R.id.menuRecycler);
        menuRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        MenuAdapter adapter = new MenuAdapter(menuList, this, this);
        menuRecycler.setAdapter(adapter);
    }

    /** these 3 are listener func which r use to handle item count in menu [- addToCart +]**/
    @Override
    public void onAddToCartClick(MenuModel menu) {   // [addToCart]
        if(itemsInCartList == null) {
            itemsInCartList = new ArrayList<>();
        }
        itemsInCartList.add(menu);
        totalItemInCart = 0;  // since, not selected than 0 cost of food

        for(MenuModel i : itemsInCartList) {
            totalItemInCart = totalItemInCart + i.getTotalInCart();  //get price of each item using loop from model n add to variable
        }
        buttonCheckout.setText("Checkout (" +totalItemInCart +") items");
    }

    @Override
    public void onUpdateCartClick(MenuModel menu) {   // [+]
        if(itemsInCartList.contains(menu)) {
            int index = itemsInCartList.indexOf(menu);  // menu 1 or 2 or 3 so on
            Log.d("TAG", "onUpdateCartClick: "+index);
            itemsInCartList.remove(index);
            itemsInCartList.add(index, menu);

            totalItemInCart = 0;

            for(MenuModel i : itemsInCartList) {
                totalItemInCart = totalItemInCart + i.getTotalInCart();
            }
            buttonCheckout.setText("Checkout (" +totalItemInCart +") items");
        }
    }

    @Override
    public void onRemoveFromCartClick(MenuModel menu) {  // [-]
        if(itemsInCartList.contains(menu)) {
            itemsInCartList.remove(menu);

            totalItemInCart = 0;

            for(MenuModel i : itemsInCartList) {
                totalItemInCart = totalItemInCart + i.getTotalInCart();
            }
            buttonCheckout.setText("Checkout (" +totalItemInCart +") items");
        }
    }

    /** for back button of action bar **/
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                finish();
//            default:
                //do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    /** if user went to order page fill details n still wants to add more item can come back using **/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101 && resultCode == Activity.RESULT_OK) {
            finish();
        }
    }
}