package com.example.fooddeliveryapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.models.RestaurantModel;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {
    private List<RestaurantModel> restaurantList;
    private RestaurantClickListener listener;
    Context context;

    public RestaurantAdapter(List<RestaurantModel> restaurantList, Context context, RestaurantClickListener listener) {
        this.restaurantList = restaurantList;
        this.context = context;
        this.listener = listener;
    }

    public void updateData(List<RestaurantModel> restaurantList, Context context) {
        this.restaurantList = restaurantList;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.restro_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(restaurantList.get(position).getName());
        holder.address.setText("Location: "+restaurantList.get(position).getAddress());
        holder.hours.setText("Timing: "+restaurantList.get(position).getHours().getTodaysHours());
        Glide.with(holder.image)
                .load(restaurantList.get(position).getImage())
                .into(holder.image);

        /** added listener to the items **/
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(restaurantList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, address, hours;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.restaurantName);
            address = itemView.findViewById(R.id.restaurantAddress);
            hours = itemView.findViewById(R.id.restaurantHours);

        }
    }

    public interface RestaurantClickListener{
        public void onItemClick(RestaurantModel restaurantModel);
    }

}
