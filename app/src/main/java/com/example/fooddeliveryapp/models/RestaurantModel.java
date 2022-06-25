package com.example.fooddeliveryapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class RestaurantModel implements Parcelable {
    private String image, name, address;
    private float deliveryCharge;
    private HoursModel hours;
    private List<MenuModel> menus;

    public RestaurantModel(String image, String name, String address, float deliveryCharge, HoursModel hours, List<MenuModel> menus) {
        this.image = image;
        this.name = name;
        this.address = address;
        this.deliveryCharge = deliveryCharge;
        this.hours = hours;
        this.menus = menus;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(float deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public HoursModel getHours() {
        return hours;
    }

    public void setHours(HoursModel hours) {
        this.hours = hours;
    }

    public List<MenuModel> getMenus() {
        return menus;
    }

    public void setMenus(List<MenuModel> menus) {
        this.menus = menus;
    }

    protected RestaurantModel(Parcel in) {
        name = in.readString();
        address = in.readString();
        image = in.readString();
        deliveryCharge = in.readFloat();
        menus = in.createTypedArrayList(MenuModel.CREATOR); //get CREATOR object from menu parcelable
    }

    public static final Creator<RestaurantModel> CREATOR = new Creator<RestaurantModel>() {
        @Override
        public RestaurantModel createFromParcel(Parcel in) {
            return new RestaurantModel(in);
        }

        @Override
        public RestaurantModel[] newArray(int size) {
            return new RestaurantModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(image);
        dest.writeFloat(deliveryCharge);
        dest.writeTypedList(menus); //get func from menu parcelable
    }
}
