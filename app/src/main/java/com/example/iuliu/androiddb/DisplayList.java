package com.example.iuliu.androiddb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

public class DisplayList extends AppCompatActivity {
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    UserAdapter userAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_list);
        listView=(ListView)findViewById(R.id.listView);

        userAdapter=new UserAdapter(this, R.layout.row_layout);
        listView.setAdapter(userAdapter);
        json_string=getIntent().getExtras().getString("json_data");
        try {
            jsonObject=new JSONObject(json_string);
            jsonArray=jsonObject.getJSONArray("server_response");
            int count=0;
            String name, password,random;
            while (count<jsonObject.length())
            {
                JSONObject JO=jsonArray.getJSONObject(count);
                name=JO.getString("name");
                password=JO.getString("password");
                random=Kripto.decrypt(JO.getString("random"));
                Users user=new Users(name,password,random);
                userAdapter.add(user);
                count++;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
