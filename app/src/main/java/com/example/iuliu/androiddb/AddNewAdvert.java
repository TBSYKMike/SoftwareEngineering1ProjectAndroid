package com.example.iuliu.androiddb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

//import org.apache.commons.codec.binary.Base64;

import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Mike on 2016-05-19.
 */
public class AddNewAdvert extends AppCompatActivity {



    String imgLarge="";
    String imgSmall="";






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_advert);


        btnTakePhoto = (Button) findViewById(R.id.buttonTakePhoto);
        mImageView = (ImageView) findViewById(R.id.imageViewNewlyTakenPhoto);
        btnTakePhoto.setOnClickListener( new btnTakePhotoClicker());

        buttonCreateNewAdvert = (Button) findViewById(R.id.buttonCreateNewAdvert);
        buttonCreateNewAdvert.setOnClickListener( new btnCreateNewAdvertClicker());

        editTextItemName = (EditText) findViewById(R.id.editTextInputItemName);
        editTextItemInfo = (EditText) findViewById(R.id.editTextInputItemInfo);


        setupSpinnerCondition();








    }




//-------------------------------------- Camera codes Start---------------------------------------------------------------------------------------------
    Button btnTakePhoto;
    ImageView mImageView;
    String mCurrentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            galleryAddPic();
            setPic();
        }
    }

    class btnTakePhotoClicker implements Button.OnClickListener{
        @Override
        public void onClick(View v){
            dispatchTakePictureIntent();
        }
    }


// run camera app
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        /*bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);
*/
        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
  //      bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;


        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        if (bitmap.getWidth() >= bitmap.getHeight()){

            bitmap = Bitmap.createBitmap(
                    bitmap,
                    bitmap.getWidth()/2 - bitmap.getHeight()/2,
                    0,
                    bitmap.getHeight(),
                    bitmap.getHeight()
            );

        }else{

            bitmap = Bitmap.createBitmap(
                    bitmap,
                    0,
                    bitmap.getHeight()/2 - bitmap.getWidth()/2,
                    bitmap.getWidth(),
                    bitmap.getWidth()
            );
        }
        bitmap = bitmap.createScaledBitmap(bitmap,400,400,false);

        mImageView.setImageBitmap(bitmap);
        createToast( Integer.toString(encodeImage(bitmap).getBytes().length) );

        editTextItemName.setText(Integer.toString(encodeImage(bitmap).getBytes().length));

        imgLarge = encodeImage(bitmap);
        bitmap = bitmap.createScaledBitmap(bitmap,100,100,false);
        editTextItemName.setText(editTextItemName.getText().toString()+" "+Integer.toString(encodeImage(bitmap).getBytes().length));
        imgSmall = encodeImage(bitmap);
    }

    // saving image to phone storage
    private File createImageFile() throws IOException {

        System.out.println(mCurrentPhotoPath);System.out.println(mCurrentPhotoPath);
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

//-------------------------------------- Camera codes end---------------------------------------------------------------------------------------------




//-------------------------------------- PHP mySQL codes start---------------------------------------------------------------------------------------------




    EditText editTextItemName;
    EditText editTextItemInfo;

    Button buttonCreateNewAdvert;

    String itemCondition;

    class btnCreateNewAdvertClicker implements Button.OnClickListener{
        @Override
        public void onClick(View v){
            registerUser();
        }
    }


    private static final String REGISTER_URL = "http://mybarter.net16.net/miketest.php";

    private void registerUser() {
        //   String name = editTextName.getText().toString().trim().toLowerCase();
        //   String username = editTextUsername.getText().toString().trim().toLowerCase();
        //   String password = editTextPassword.getText().toString().trim().toLowerCase();
        //   String email = editTextEmail.getText().toString().trim().toLowerCase();

        String itemName = editTextItemName.getText().toString().trim();
        String itemInfo = editTextItemInfo.getText().toString().trim();
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date());
        String UserID = "9";

        if(imgLarge.isEmpty() || imgSmall.isEmpty()){
            createToast("Picture Missing!\nPlease take a picture!");
        }
        else if(itemCondition.length() <1 || itemCondition.length() >4){
            createToast("Please set item condition");
        } else if( itemName.length() > 0 && itemInfo.length() >0 ) {
            register(itemName, itemInfo,imgSmall, imgLarge, itemCondition, timeStamp, "active", UserID);
        }else{
            createToast("Name or Info is Empty!\n Please fill both fields!");
        }


    }

    private void register(String name, String info, String picture_small, String picture_large,String condition,String date,String status,String user_userID ) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AddNewAdvert.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("name",params[0]);
                data.put("info",params[1]);
                data.put("picture_small",params[2]);
                data.put("picture_large",params[3]);
                data.put("condition",params[4]);
                data.put("date",params[5]);
                data.put("status",params[6]);
                data.put("user_userID",params[7]);



                String result = ruc.sendPostRequest(REGISTER_URL,data);

                return  result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(name, info, picture_small, picture_large,condition,date,status,user_userID );
    }



//-------------------------------------- PHP mySQL codes end---------------------------------------------------------------------------------------------

//-------------------------------------- Spinner codes start ---------------------------------------------------------------------------------------------

    Spinner spinnerCondition;


    private void setupSpinnerCondition(){
        spinnerCondition = (Spinner) findViewById(R.id.spinnerItemCondition);
        spinnerCondition.setOnItemSelectedListener(new spinnerConditionClicker());

        // Spinner Drop down elements
        ArrayList<String> categories = new ArrayList<String>();
        categories.add("Select Condition");
        categories.add("NEW");
        categories.add("USED");
        categories.add("OLD");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerCondition.setAdapter(dataAdapter);

    }
    class spinnerConditionClicker implements Spinner.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            // On selecting a spinner item
            String item = parent.getItemAtPosition(position).toString();
            itemCondition = item;


            // Showing selected spinner item
            //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
//-------------------------------------- Spinner codes end---------------------------------------------------------------------------------------------



//-------------------------------------- Encode Image codes Start---------------------------------------------------------------------------------------------
public String encodeImage(Bitmap image) {
    Base64 base64=new Base64();
    byte[] data = new byte[0];
    int size = image.getRowBytes() * image.getHeight();
    ByteBuffer byteBuffer = ByteBuffer.allocate(size);
    image.copyPixelsToBuffer(byteBuffer);
    data = new byte[size];
    data = byteBuffer.array();
    String stringToStore= new String(base64.encode(data));
    return stringToStore ;
}

//-------------------------------------- Encode Image codes End---------------------------------------------------------------------------------------------


    public void createToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }


}