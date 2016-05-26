package com.example.iuliu.androiddb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by Mike on 2016-05-23.
 */
public class DownloadImageWithURLTask extends AsyncTask<String, Void, Bitmap> {
   Bitmap bmImage;
    int imgWidth;
    public DownloadImageWithURLTask( int imgWidth) {
       // this.bmImage = bmImage;
        this.imgWidth = imgWidth;

    }

    protected Bitmap doInBackground(String... urls) {



        String pathToFile = urls[0];
        Bitmap bitmap = null;




        try {
            InputStream in = new java.net.URL(pathToFile).openStream();
            bitmap = BitmapFactory.decodeStream(in);
           // in.close();
            int scale;
            if(imgWidth>0) {
                scale = imgWidth / bitmap.getWidth();

                bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() * scale, bitmap.getHeight() * scale, true);

           }
              in.close();
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return bitmap;
    }
    protected void onPostExecute(Bitmap result) {

        bmImage=result;
    }
    public Bitmap getImg(){
        return bmImage;
    }
}