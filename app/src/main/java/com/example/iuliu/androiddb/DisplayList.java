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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DisplayList extends AppCompatActivity {
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    UserAdapter userAdapter;
    ListView listView;
    ArrayList<Users> arrayUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_list);
        arrayUsers=new ArrayList<Users>();
        listView=(ListView)findViewById(R.id.listView);
        ImageView pictureView=(ImageView)findViewById(R.id.picture_random);
        userAdapter=new UserAdapter(this,R.layout.row_layout,arrayUsers);
        listView.setAdapter(userAdapter);
        json_string=getIntent().getExtras().getString("json_data");
        try {
            ArrayList<Users> listData = new ArrayList<>();


            jsonObject=new JSONObject(json_string);
            jsonArray=jsonObject.getJSONArray("server_response");
            int count=0;
            String name, password,random;
            while (count<jsonArray.length())
            {
                JSONObject JO=jsonArray.getJSONObject(count);
                name=JO.getString("name");
                password=JO.getString("password");
                random=JO.getString("random");
                Users user=new Users(name,password,random);
                userAdapter.add(user);
                count++;

            }

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                    Users newsData = (Users) listView.getItemAtPosition(position);
                    Toast.makeText(DisplayList.this, "Selected :" + " " + newsData.getName(), Toast.LENGTH_LONG).show();

                    Intent intent=new Intent (getApplicationContext(),CheckItem.class);

                    startActivity(intent);

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
