package com.example.iuliu.androiddb;

import android.graphics.Path;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import org.apache.commons.codec.binary.Base64;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class AddInfo extends AppCompatActivity {
    EditText Name,Password,Random;
    String name,password,random;

    String test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info_layout);
        Name=(EditText)findViewById(R.id.et_name);
        Password=(EditText)findViewById(R.id.et_password);
        Random=(EditText)findViewById(R.id.et_random);
    }
    public void saveInfo(View view)
    {
        name=Name.getText().toString();
     //   password=this.encodeImage();
        password=Password.getText().toString();
        random=Random.getText().toString();
        BackgroundTask backgroundTask = new BackgroundTask();
        backgroundTask.execute(name,password,random);
      //  this.transferPhoto();

    }
    class BackgroundTask extends AsyncTask<String,Void,String>
    {
        String add_info_url;
        @Override
        protected void onPreExecute() {
            add_info_url= "http://mybarter.net16.net/add_info.php";
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            String name,password,random;
            name=args[0];
            password=args[1];
           random=args[2];

            try {

                URL url =new URL(add_info_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                try {

                    test=Kripto.encrypt(random);

                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data_string = URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                            URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&"+
                            URLEncoder.encode("random","UTF-8")+"="+URLEncoder.encode(test,"UTF-8");
                    bufferedWriter.write(data_string);

                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream=httpURLConnection.getInputStream();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return "Data insert";
                }catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
        }
    }
/*   public void transferPhoto()
    {



        //File path = Environment.getExternalStoragePublicDirectory(
           //     Environment.DIRECTORY_PICTURES);
      //  File file = new File(path, "DemoPicture.jpg");

        try {
       //     path.mkdirs();

            String extr = Environment.getExternalStorageDirectory().toString();
            File mFolder = new File(extr + "/Picture1");
            if (!mFolder.exists()) {
                mFolder.mkdirs();
            }

            String s = "tmp.png";

            File f = new File(mFolder.getAbsolutePath(), s);


            InputStream is = getResources().openRawResource(+ R.drawable.bug);
            OutputStream os = new FileOutputStream(f);
            byte[] data = new byte[is.available()];
            is.read(data);
            os.write(data);
            is.close();
            os.close();


            System.out.println("Image Successfully Manipulated!");
        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        }

    }*/
    public  String encodeImage() {
        Base64 base64=new Base64();
        InputStream is = getResources().openRawResource(+ R.drawable.star);

        byte[] data = new byte[0];
        try {
            data = new byte[is.available()];
            is.read(data);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String stringToStore;
        return stringToStore = new String(base64.encode(data));
    }


    public  byte[] decodeImage(String imageDataString) {
        Base64 base64=new Base64();

        return base64.decode(imageDataString.getBytes());
    }

}
