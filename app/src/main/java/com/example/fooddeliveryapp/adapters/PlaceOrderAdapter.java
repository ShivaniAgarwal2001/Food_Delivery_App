package com.example.fooddeliveryapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.models.MenuModel;

import java.util.List;

public class PlaceOrderAdapter extends RecyclerView.Adapter<PlaceOrderAdapter.ViewHolder> {
    private List<MenuModel> menuList;
    Context context;

    public PlaceOrderAdapter(List<MenuModel> menuList, Context context) {
        this.menuList = menuList;
        this.context = context;
    }

    public void updateData(List<MenuModel> menuList, Context context) {
        this.menuList = menuList;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuModel menu_pos = menuList.get(position);
        holder.itemName.setText(menuList.get(position).getName());
//        holder.itemPrice.setText("Price- $"+menuList.get(position).getPrice());
        holder.itemPrice.setText("Price- $"+String.format("%.2f", menuList.get(position).getPrice() * menuList.get(position).getTotalInCart()));
        holder.quantity.setText("Quantity- " + menuList.get(position).getTotalInCart());
        Glide.with(holder.image)
                .load(menuList.get(position).getUrl())
                .into(holder.image);

        Log.d("TAG", "onBindViewHolder: "+menu_pos.getName()+"\n"+menu_pos.getPrice()+"\n"+menu_pos.getTotalInCart());

    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView itemName, itemPrice, quantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            itemName = itemView.findViewById(R.id.itemName);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            quantity = itemView.findViewById(R.id.quantity);

        }
    }


}
