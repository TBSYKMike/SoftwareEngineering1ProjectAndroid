package com.example.iuliu.androiddb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DisplayList extends AppCompatActivity {
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    AddsAdapter addsAdapter;
    ListView listView;
    ArrayList<Adds> arrayUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_list);
        arrayUsers=new ArrayList<Adds>();
        listView=(ListView)findViewById(R.id.listView);
        ImageView pictureView=(ImageView)findViewById(R.id.picture_random);
        addsAdapter =new AddsAdapter(this,R.layout.row_layout,arrayUsers);
        listView.setAdapter(addsAdapter);
        json_string=getIntent().getExtras().getString("json_data");
        try {
            ArrayList<Adds> listData = new ArrayList<>();


            jsonObject=new JSONObject(json_string);
            jsonArray=jsonObject.getJSONArray("server_response");
            int count=0;
            String item_id,item_name,item_info,item_picture_small,item_picture_large,item_condition,item_date,item_status,item_visit_count,item_winner_userID,item_user_userID,accountName;
            while (count<jsonArray.length())
            {
                JSONObject JO=jsonArray.getJSONObject(count);
                item_id=JO.getString("item_id");
                item_name=JO.getString("item_name");
                item_info=JO.getString("item_info");
                item_picture_small=JO.getString("item_picture_small");
               item_picture_large=JO.getString("item_picture_large");
                item_condition=JO.getString("item_condition");
                item_date=JO.getString("item_date");
                item_status=JO.getString("item_status");
                item_visit_count=JO.getString("item_visit_count");
                item_winner_userID=JO.getString("item_winner_userID");
                item_user_userID=JO.getString("item_user_userID");
                accountName=JO.getString("userName");

                Adds user=new Adds(item_id,item_name,item_info,item_picture_small,item_picture_large,item_condition,item_date,item_status,item_visit_count,item_winner_userID,item_user_userID,accountName);
                addsAdapter.add(user);
                count++;

            }

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                    Adds newsData = (Adds) listView.getItemAtPosition(position);
                    Toast.makeText(DisplayList.this, "Selected :" + " " + newsData.getItem_name(), Toast.LENGTH_LONG).show();
                    Singleton.getInstance().setItemId(newsData.getItem_id());
                    Singleton.getInstance().setNameItem(newsData.getItem_name());
                    Intent intent=new Intent (getApplicationContext(),New_Transaction.class);

                    startActivity(intent);

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
