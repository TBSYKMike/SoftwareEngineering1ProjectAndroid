package com.example.iuliu.androiddb;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Iuliu on 2016-05-09.
 */
public class AddsAdapter extends ArrayAdapter {
   ImageLoader imageLoader;


    List list =new ArrayList();
    String temp1="bbb";
    String temp;
    ArrayList<Adds> addsArrayList;
    AddInfo object=new AddInfo();
    public AddsAdapter(Context context, int resource, ArrayList<Adds> addsArrayList) {
        super(context, resource, addsArrayList);
        this.addsArrayList = addsArrayList;
    }
    public void remove(int pos){
       // super.remove(int pos);
        list.remove(pos);
    }
    public void reset(){
        list.clear();
    }
    public void add(Adds object)
    {
        super.add(object);
        list.add(object);
    }
    @Override
    public  int getCount()
    {
        return  list.size();
    }
    @Override
    public Object getItem(int position)
    {
        return list.get(position);
    }

    ImageView imageView;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        UserHolder userHolder;
        row = convertView;

        if(row == null){
            LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout, parent, false);
            userHolder= new UserHolder();
            userHolder.txt_item_name=(TextView)row.findViewById(R.id.txt_item_name);
            userHolder.txt_item_condition=(TextView)row.findViewById(R.id.txt_item_condition);
            userHolder.txt_item_date=(TextView)row.findViewById(R.id.txt_item_date);
            userHolder.txt_accountName=(TextView)row.findViewById(R.id.txt_user_name);
            userHolder.img_view=(ImageView) row.findViewById(R.id.picture_random);
            row.setTag(userHolder);
        }
        else{
            userHolder=(UserHolder)row.getTag();
        }
        try {
            Adds adds =(Adds)this.getItem(position);
            userHolder.txt_item_name.setText(adds.getItem_date());
            userHolder.txt_item_condition.setText(adds.getItem_condition());
            userHolder.txt_item_date.setText(adds.getItem_name());
            userHolder.txt_accountName.setText(adds.getUser_name());

            String imgLittleUrl = adds.getItem_picture_small();

            Singleton.getInstance().setStringLittlePictureUrl(imgLittleUrl);
            Glide.with(this.getContext()).load(imgLittleUrl)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(userHolder.img_view);



        } catch (Exception e) {


            e.printStackTrace();
            System.out.println(temp1);

        }


        return row;
    }

    public void remove(Adds object) {
        addsArrayList.remove(object);
        notifyDataSetChanged();
    }

    static class UserHolder
    {
        TextView txt_item_id, txt_item_name, txt_item_info, txt_item_picture_small, txt_item_picture_large, txt_item_condition,
                txt_item_date, txt_item_status, txt_item_visit_count, txt_item_winner_userID, txt_item_user_userID,txt_accountName;
        ImageView img_view;
    }


}
