package com.collect.library.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetAddressUtil {
    Context context;

    public GetAddressUtil(Context context) {
        this.context = context;
    }

    public List<String> getAddress(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context);
        Log.e("thistt", "the falg is " + geocoder.isPresent());
        List<String> addresseList = new ArrayList<>();
        try {

            //根据经纬度获取地理位置信息---这里会获取最近的几组地址信息，具体几组由最后一个参数决定
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 5);

            for (Address address : addresses) {

//                    stringBuilder.append(address.getFeatureName()).append("_");//周边地址
//                    stringBuilder.append(address.getPostalCode()).append("_");
//                    stringBuilder.append(address.getCountryCode()).append("_");//国家编码
//                    stringBuilder.append(address.getSubAdminArea()).append("_");
//                    Log.e("thistt", "地址信息--->" + stringBuilder);

                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {

                    StringBuilder stringBuilder = new StringBuilder();
                    if (i != 0) {
                        stringBuilder.append(address.getCountryName()).append("_");//国家
                        stringBuilder.append(address.getAdminArea()).append("_");//省份
                        stringBuilder.append(address.getLocality()).append("_");//市
                        stringBuilder.append(address.getSubLocality()).append("_");//
                        stringBuilder.append(address.getThoroughfare()).append("_");//道路
//                        stringBuilder.append(address.getLatitude()).append("_");//经度
//                        stringBuilder.append(address.getLongitude());//维度
                    }
                    //每一组地址里面还会有许多地址。xxx街道-xxx位置
                    stringBuilder.append(address.getAddressLine(i));
                    Log.e("thistt", "地址信息--->" + stringBuilder);

                    addresseList.add(stringBuilder.toString());
                }
            }
        } catch (IOException e) {
            Toast.makeText(context, "报错", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        return addresseList;
    }
}
