package com.example.iuliu.androiddb;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by Mike on 2016-05-19.
 */
public class AddNewAdvert extends AppCompatActivity {

    Button btnTakePhoto;
    ImageView imgTakenPhoto;

    private static final int CAM_REQUEST = 1313;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_advert);

        btnTakePhoto = (Button) findViewById(R.id.buttonTakePhoto);
        imgTakenPhoto = (ImageView) findViewById(R.id.imageViewNewlyTakenPhoto);

        btnTakePhoto.setOnClickListener( new btnTakePhotoClicker());


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAM_REQUEST){
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imgTakenPhoto.setImageBitmap(thumbnail);
        }
    }

    class btnTakePhotoClicker implements Button.OnClickListener{

        @Override
        public void onClick(View v){
            Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            startActivityForResult(cameraintent, CAM_REQUEST);
        }

    }



}