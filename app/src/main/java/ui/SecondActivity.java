package ui;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import algonquin.cst2335.dai00047.R;
import algonquin.cst2335.dai00047.databinding.ActivitySecondBinding;


public class SecondActivity extends AppCompatActivity {
    ActivitySecondBinding binding;
    SharedPreferences prefs ;
   // String emailAddress = prefs.getString("editText", "");
    ActivityResultLauncher<Intent> cameraResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {


                    if (result.getResultCode() == Activity.RESULT_OK) {


                        Intent data = result.getData();
                        Bitmap thumbnail = data.getParcelableExtra("data");
                        binding.imageView.setImageBitmap( thumbnail);


                       FileOutputStream fOut = null;
                        try{

                            fOut = openFileOutput("Picture.png", Context.MODE_PRIVATE);
                            thumbnail.compress(Bitmap.CompressFormat.PNG,100, fOut);
                            fOut.flush();
                            fOut.close();
                        }
                        catch (IOException e)


                        { e.printStackTrace();


                        }



                    }

                }

            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        File file = new File( getFilesDir(), "Picture.png");
        prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String phone = prefs.getString("editTextPhone", "");
        binding.editTextPhone.setText(phone);


        if(file.exists())


        {
            Bitmap theImage = BitmapFactory.decodeFile(getFilesDir()+"/"+"Picture.png");
            binding.imageView.setImageBitmap( theImage );

        }




        Intent fromPrevious = getIntent();
        String emailAddress = fromPrevious.getStringExtra("EmailAddress");
        binding.textView.setText("Welcome "+emailAddress);

        binding.button2.setOnClickListener( click -> {
            String phoneNumber = binding.editTextPhone.getText().toString();
            Intent call = new Intent(Intent.ACTION_DIAL);
            call.setData(Uri.parse("tel:" + phoneNumber));
            prefs.edit().putString("editTextPhone", binding.editTextPhone.getText().toString()).commit();
            prefs.edit().apply();
            startActivity(call);
        });




        binding.button3.setOnClickListener( click -> {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraResult.launch(cameraIntent);
        });










    }
}

