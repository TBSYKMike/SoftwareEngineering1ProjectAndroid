package com.example.iuliu.androiddb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Mike on 2016-05-27.
 */
public class ShowLargePicture extends AppCompatActivity {

    ImageView imageViewLargePic;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_large_picture);

        imageViewLargePic = (ImageView) findViewById(R.id.imageViewShowLargePic);
        System.out.println("-----------fawfaffafa");



    }


    @Override
    protected void onResume() {
        super.onResume();
        try {
            Picasso.with(ShowLargePicture.this.getBaseContext()).load(Singleton.getInstance().getStringBigPictureUrl()  ).into(imageViewLargePic);
        }catch (Exception e){
            System.out.println("-----------fel");
        }
        System.out.println("-----------fawfaasfafaffasaffaafffafa");
    }

    public void onClickClosePicure(View view){
        finish();
    }

}
