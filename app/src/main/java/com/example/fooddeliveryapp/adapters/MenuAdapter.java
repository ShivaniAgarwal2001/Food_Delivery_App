package com.example.fooddeliveryapp.adapters;

import android.content.Context;
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

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    private List<MenuModel> menuList;
    private MenuClickListener listener;
    Context context;

    public MenuAdapter(List<MenuModel> menuList, Context context, MenuClickListener listener) {
        this.menuList = menuList;
        this.context = context;
        this.listener = listener;
    }

    public void updateData(List<MenuModel> menuList, Context context) {
        this.menuList = menuList;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuModel menu_pos = menuList.get(position);
        holder.menuName.setText(menuList.get(position).getName());
        holder.menuPrice.setText("Price- $"+menuList.get(position).getPrice());

        holder.addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //IMP
                menu_pos.setTotalInCart(1);   //initially 1 value is given to model
                listener.onAddToCartClick(menu_pos);
                holder.addToCartButton.setVisibility(View.GONE);
                holder.addMoreLayout.setVisibility(View.VISIBLE);
                //now set the 1 value to textview on ui screen
                holder.textCount.setText(menu_pos.getTotalInCart()+"");
            }
        });

        holder.imageMinusOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuModel menu  = menuList.get(position);
                int total = menu.getTotalInCart();
                total--;
                if(total > 0 ) {
                    menu.setTotalInCart(total);
                    listener.onUpdateCartClick(menu);
                    holder.textCount.setText(total +"");
                } else {
                    holder.addMoreLayout.setVisibility(View.GONE);
                    holder.addToCartButton.setVisibility(View.VISIBLE);
                    menu.setTotalInCart(total);
                    listener.onRemoveFromCartClick(menu);
                }
            }
        });

        holder.imageAddOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuModel menu  = menuList.get(position);
                int total = menu.getTotalInCart();
                total++;
                if(total <= 100 ) {
                    menu.setTotalInCart(total);
                    listener.onUpdateCartClick(menu);
                    holder.textCount.setText(total +"");
                }
            }
        });

        Glide.with(holder.image)
                .load(menuList.get(position).getUrl())
                .into(holder.image);



    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image, imageAddOne, imageMinusOne;
        TextView menuName, menuPrice, addToCartButton, textCount;
        LinearLayout addMoreLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            menuName = itemView.findViewById(R.id.menuName);
            menuPrice = itemView.findViewById(R.id.menuPrice);
            addToCartButton = itemView.findViewById(R.id.addToCartButton);
            textCount = itemView.findViewById(R.id.textCount);
            imageAddOne = itemView.findViewById(R.id.imageAddOne);
            imageMinusOne = itemView.findViewById(R.id.imageMinusOne);
            addMoreLayout = itemView.findViewById(R.id.addMoreLayout);

        }
    }

    public interface MenuClickListener{
        public void onAddToCartClick(MenuModel menu);
        public void onUpdateCartClick(MenuModel menu);
        public void onRemoveFromCartClick(MenuModel menu);
    }

}
