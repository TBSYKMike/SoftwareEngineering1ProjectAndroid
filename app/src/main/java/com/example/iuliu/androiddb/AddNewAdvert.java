package com.example.iuliu.androiddb;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.apache.commons.codec.binary.Base64;

import java.nio.ByteBuffer;

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
    public  String encodeImage(Bitmap image) {
        Base64 base64=new Base64();
        byte[] data = new byte[0];
        int size = image.getRowBytes() * image.getHeight();
        ByteBuffer byteBuffer = ByteBuffer.allocate(size);
        image.copyPixelsToBuffer(byteBuffer);
        data = new byte[size];
        data = byteBuffer.array();
        String stringToStore;
        return stringToStore = new String(base64.encode(data));
    }

    class btnTakePhotoClicker implements Button.OnClickListener{

        @Override
        public void onClick(View v){
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            startActivityForResult(cameraIntent, CAM_REQUEST);
        }

    }



}