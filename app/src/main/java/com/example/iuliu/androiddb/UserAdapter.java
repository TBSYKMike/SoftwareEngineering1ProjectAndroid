package com.example.iuliu.androiddb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
    public UserAdapter(Context context, int resource) {
        super(context, resource);
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
        row = convertView;

        if(row == null){

            LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout, parent, false);
        }
        final Users listItem = (Users)list.get(position);
        if(listItem != null){

            TextView txv_name = (TextView)row.findViewById(R.id.txt_name);
            TextView txv_password = (TextView)row.findViewById(R.id.txt_password);
            TextView txv_random = (TextView)row.findViewById(R.id.txt_random);


              try {
                temp1=listItem.getRandom();
               // temp = Encrypt.decryptPassword(temp1);
                  temp=Kripto.decrypt(temp1);
                System.out.println(temp1+"2222222222222222222222222222222222222222");
                System.out.println(temp+"1111111111111111111111111111111111111111111111111111111111111");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(temp1);
                int i=0;
                i=temp1.length();
                System.out.println(i);
                System.out.println("NUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUMergeWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
            }

                txv_name.setText(listItem.getName());
                txv_password.setText(listItem.getPassword());
                txv_random.setText(temp);





        }

        return row;
    }

    static class UserHolder
    {
        TextView txt_name,txt_password,txt_random;
    }
}
