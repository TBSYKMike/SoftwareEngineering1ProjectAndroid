package com.example.iuliu.androiddb;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class commentsSection extends AppCompatActivity {

    JSONObject jsonObject;
    JSONArray jsonArray;
    private ArrayAdapter<String> adapter;
    ArrayList<String> Comments;
    ArrayList<String> Poster;
    ArrayList<String> date;
    ListView userComments;

    private EditText comment;
    private static final String REGISTER_URL = "http://mybarter.net16.net/comment.php";
    private static final String REGISTER_URL_POST ="http://mybarter.net16.net/commentPoster.php";
    private static final String json_string = "response";
    private static Context contextClass;
    private static int layout;

    public static final String SPARAD_DATA = "sparadData";
    String ItemId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment);
        Poster = new ArrayList<>();
        Comments = new ArrayList<>();
        date = new ArrayList<>();

        ItemId = Singleton.getInstance().getItemId();


        connectionReadComments(ItemId);

        Comments = new ArrayList<String>();
        userComments = (ListView) findViewById(R.id.comments);

        contextClass = commentsSection.this;
        layout = R.layout.comment_layout;

    }
    public void addComment(View view) {

        comment = (EditText) findViewById(R.id.commentBox);
        connectionPostComment(comment.getText().toString(), ItemId, Singleton.getInstance().getItemOwn_id());


    }



    private void connectionReadComments(String itemId) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            Activity_SMS2_DBConnectionClass ruc = new Activity_SMS2_DBConnectionClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(commentsSection.this, "Please Wait",null, true, true);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();
               try{
                    jsonObject = new JSONObject(s);
                    jsonArray = jsonObject.getJSONArray(json_string);

                   Poster = new ArrayList<>();
                   Comments = new ArrayList<>();
                   date = new ArrayList<>();

                    for(int i = 0; i< jsonArray.length(); i++){
                        Poster.add(jsonArray.getJSONObject(i).getString("accountName"));
                        Comments.add(jsonArray.getJSONObject(i).getString("message_text"));
                        date.add(jsonArray.getJSONObject(i).getString("message_datetime"));

                    }

                    commentAdapter adapter = new commentAdapter(contextClass,R.layout.comment_layout, Comments, Poster, date);
                    userComments.setAdapter(null);
                    userComments.setAdapter(adapter);

                }catch(JSONException ex){


                }
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("itemId",params[0]);

                String result = ruc.sendPostRequest(REGISTER_URL,data);

                return  result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(itemId);
    }
    private void connectionPostComment(String comments, String itemId, String posterId) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            Activity_SMS2_DBConnectionClass ruc = new Activity_SMS2_DBConnectionClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(commentsSection.this, "Please Wait",null, true, true);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();


                connectionReadComments(ItemId);

            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("comments",params[0]);
                data.put("itemId",params[1]);
                data.put("posterId",params[2]);

                String result = ruc.sendPostRequest(REGISTER_URL_POST,data);

                return  result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(comments, itemId, posterId);
    }


}
