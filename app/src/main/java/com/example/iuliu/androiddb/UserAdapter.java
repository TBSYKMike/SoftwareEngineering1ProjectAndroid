package com.example.iuliu.androiddb;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Iuliu on 2016-05-09.
 */
public class UserAdapter extends ArrayAdapter {
    List list =new ArrayList();
    String temp1="bbb";
    String temp;
    ArrayList<Users> usersArrayList;
    AddInfo object=new AddInfo();
    public UserAdapter(Context context, int resource,ArrayList<Users> usersArrayList) {
        super(context, resource,usersArrayList);
        this.usersArrayList=usersArrayList;
    }



    public void add(Users object)
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


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        UserHolder userHolder;
        row = convertView;

        if(row == null){

            LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout, parent, false);
            userHolder= new UserHolder();
            userHolder.txt_id=(TextView)row.findViewById(R.id.txt_id);
            userHolder.txt_name=(TextView)row.findViewById(R.id.txt_name);
            userHolder.txt_password=(TextView)row.findViewById(R.id.txt_password);
            userHolder.txt_random=(TextView)row.findViewById(R.id.txt_random);
            userHolder.img_view=(ImageView) row.findViewById(R.id.picture_random);
            row.setTag(userHolder);
        }
        else{
            userHolder=(UserHolder)row.getTag();
        }
        try {
            Users users=(Users)this.getItem(position);

            temp1=users.getRandom();
            // temp = Encrypt.decryptPassword(temp1);
            temp=Kripto.decrypt(temp1);
            userHolder.txt_id.setText(users.getId());
            userHolder.txt_name.setText(users.getName());
            userHolder.txt_password.setText(users.getPassword());
            userHolder.txt_random.setText(temp);


           // byte[] d=object.decodeImage(users.getPassword());

          //  Bitmap bitmap=new BitmapFactory().decodeByteArray(d,0,d.length);
           // userHolder.img_view.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(temp1);

        }


        return row;
    }


    static class UserHolder
    {
        TextView txt_id,txt_name,txt_password,txt_random;
        ImageView img_view;
    }
}
