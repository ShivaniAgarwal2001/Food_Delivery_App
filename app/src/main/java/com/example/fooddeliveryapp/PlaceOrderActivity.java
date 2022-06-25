package com.example.fooddeliveryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputBinding;
import android.widget.CompoundButton;

import com.example.fooddeliveryapp.adapters.PlaceOrderAdapter;
import com.example.fooddeliveryapp.databinding.ActivityPlaceOrderBinding;
import com.example.fooddeliveryapp.models.MenuModel;
import com.example.fooddeliveryapp.models.RestaurantModel;

import java.util.List;

public class PlaceOrderActivity extends AppCompatActivity {
    // Initialize variables by calling binding class for xml which is generated automatically.
    ActivityPlaceOrderBinding binding;
    private boolean isDelivered = false;
    private RestaurantModel restaurantList;   //TODO something wrong with list maybe empty -- fixed
    private List<MenuModel> orderedMenuList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//      setContentView(R.layout.activity_place_order);

        // inflating our xml layout in our activity main binding
        binding = ActivityPlaceOrderBinding.inflate(getLayoutInflater());
        // getting our root layout in our view.
        View view = binding.getRoot();
        setContentView(view);

        restaurantList = getIntent().getParcelableExtra("RestaurantModel");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Place Order");
//        actionBar.setSubtitle(restaurantList.getName());

        binding.buttonToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validationOfInputDetails();
                //on successful order start success activity..
                Intent i = new Intent(PlaceOrderActivity.this, SuccessOrderedPlaced.class);
                i.putExtra("RestaurantModel", restaurantList);
                startActivityForResult(i, 101);
                Log.d("TAG", "order list n delivery charge: "+orderedMenuList+"\n"+restaurantList.getDeliveryCharge());
            }
        });

        binding.switchToDelivery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){  //on
                    // do delivery
                    binding.addressLayout.setVisibility(View.VISIBLE);
                    binding.cityLayout.setVisibility(View.VISIBLE);
                    binding.stateLayout.setVisibility(View.VISIBLE);
                    binding.pincodeLayout.setVisibility(View.VISIBLE);
                    binding.tvdelivery.setVisibility(View.VISIBLE);
                    binding.deliveryCharge.setVisibility(View.VISIBLE);
                    //now set delivery boolean true n calculate total with delivery charge
                    isDelivered = true;
                    calculateTotalAmount(restaurantList);
                }else{
                    // pickup itself
                    binding.addressLayout.setVisibility(View.GONE);
                    binding.cityLayout.setVisibility(View.GONE);
                    binding.stateLayout.setVisibility(View.GONE);
                    binding.pincodeLayout.setVisibility(View.GONE);
                    binding.tvdelivery.setVisibility(View.GONE);
                    binding.deliveryCharge.setVisibility(View.GONE);
                    isDelivered = false;
                    calculateTotalAmount(restaurantList);
                }
            }
        });

//        Log.d("TAG", "onCreate: "+restaurantList.getMenus());
        initOrderedItemsRecycler();
        calculateTotalAmount(restaurantList);
    }

    private void validationOfInputDetails() {
        if(TextUtils.isEmpty(binding.inputName.getText().toString())) {
            binding.inputName.setError("Please enter name ");
            return;
        } else if(isDelivered && TextUtils.isEmpty(binding.inputAddress.getText().toString())) {
            binding.inputAddress.setError("Please enter address ");
            return;
        }else if(isDelivered && TextUtils.isEmpty(binding.inputCity.getText().toString())) {
            binding.inputCity.setError("Please enter city ");
            return;
        }else if(isDelivered && TextUtils.isEmpty(binding.inputState.getText().toString())) {
            binding.inputState.setError("Please enter zip ");
            return;
        }else if( TextUtils.isEmpty(binding.inputCardNo.getText().toString())) {
            binding.inputCardNo.setError("Please enter card number ");
            return;
        }else if( TextUtils.isEmpty(binding.inputCardExpiry.getText().toString())) {
            binding.inputCardExpiry.setError("Please enter card expiry ");
            return;
        }else if( TextUtils.isEmpty(binding.inputCardPin.getText().toString())) {
            binding.inputCardPin.setError("Please enter card pin/cvv ");
            return;
        }
    }

//    private void initOrderedItemsRecycler(RestaurantModel restaurantList) {
//        binding.orderedItemsRecycler.setLayoutManager(new LinearLayoutManager(this));
//        PlaceOrderAdapter adapter = new PlaceOrderAdapter(restaurantList.getMenus(),this);
//        binding.orderedItemsRecycler.setAdapter(adapter);
//    }

    private void initOrderedItemsRecycler(){
        orderedMenuList = restaurantList.getMenus();
                binding.orderedItemsRecycler.setLayoutManager(new LinearLayoutManager(this));
        PlaceOrderAdapter adapter = new PlaceOrderAdapter(orderedMenuList,this);
        binding.orderedItemsRecycler.setAdapter(adapter);

    }

    private void calculateTotalAmount(RestaurantModel restaurantList) {
        float subTotalAmount = 0f;

        for(MenuModel i : restaurantList.getMenus()) {
            subTotalAmount += i.getPrice() * i.getTotalInCart();
            Log.d("TAG", "calculateTotalAmount: "+i);
        }

        binding.subTotalMrp.setText("$"+String.format("%.2f", subTotalAmount));
        if(isDelivered) {
            binding.deliveryCharge.setText("$"+String.format("%.2f", restaurantList.getDeliveryCharge()));
            subTotalAmount += restaurantList.getDeliveryCharge();
        }
        binding.totalPrice.setText("$"+String.format("%.2f", subTotalAmount));
    }

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
}