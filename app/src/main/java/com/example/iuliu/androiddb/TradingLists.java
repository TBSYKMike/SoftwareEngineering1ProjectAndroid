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

public class TradingLists extends AppCompatActivity {
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    AddsAdapter addsAdapter;
    ListView listView;
    ArrayList<Adds> arrayUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trading_lists);
        arrayUsers=new ArrayList<Adds>();
        listView=(ListView)findViewById(R.id.listView3);
        ImageView pictureView=(ImageView)findViewById(R.id.picture_random);
        addsAdapter =new AddsAdapter(this,R.layout.row_layout,arrayUsers);
        listView.setAdapter(addsAdapter);
        json_string=getIntent().getExtras().getString("json_data");
        try {
            ArrayList<Adds> listData = new ArrayList<>();


            jsonObject=new JSONObject(json_string);
            jsonArray=jsonObject.getJSONArray("server_response");
            int count=0;
            String item_id,item_name,item_info,item_picture_small,item_picture_large,item_condition,item_date,item_status,item_visit_count,item_winner_userID,item_user_userID;
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

                Adds user=new Adds(item_id,item_name,item_info,item_picture_small,item_picture_large,item_condition,item_date,item_status,item_visit_count,item_winner_userID,item_user_userID);
                addsAdapter.add(user);
                count++;

            }

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                    Adds newsData = (Adds) listView.getItemAtPosition(position);
                    Toast.makeText(TradingLists.this, "Selected :" + " " + newsData.getItem_name(), Toast.LENGTH_LONG).show();

                    Intent intent=new Intent (getApplicationContext(),CheckItem.class);

                    startActivity(intent);

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void addItems(View view)
    {
        startActivity(new Intent(this, AddInfo.class));
    }
    public void viewItems(View view)
    {
        Intent intent=new Intent (this,DisplayList.class);
        intent.putExtra("json_data",json_string);
        startActivity(intent);
    }
}
