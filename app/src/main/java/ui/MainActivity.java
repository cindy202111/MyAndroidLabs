package ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import algonquin.cst2335.dai00047.databinding.ActivityMainBinding;


/**Description: Password validation for login
 * @author Yongli Dai
 * @version 1.0
 * @since  Nov.29,2022
 */

public class MainActivity extends AppCompatActivity {
//    /**
//     * This holds the text at the centre of the screen
//     */
//    private TextView tv = null;
//    /**
//     * This holds the editText reference for login
//     */
//    private EditText et = null;
//    /**
//     * This holds the login button reference
//     */
//    private Button btn = null;



//    boolean foundUpperCase = false;
//    boolean foundLowerCase = false;
//    boolean foundNumber = false;
//    boolean foundSpecial = false;

    protected String cityName;
    protected RequestQueue queue = null;
    Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //This part goes at the top of the onCreate function
        queue = Volley.newRequestQueue(this);
        ActivityMainBinding binding = ActivityMainBinding.inflate( getLayoutInflater() );
        setContentView(binding.getRoot());

        binding.forecastButton.setOnClickListener(click -> {
            cityName = binding.cityTextField.getText().toString();
            String stringURL = null;
            try{
                stringURL = "https://api.openweathermap.org/data/2.5/weather?q="
                        + URLEncoder.encode(cityName, "UTF-8")
                        + "&appid=7e943c97096a9784391a981c4d878b22&units=metric";

                //this goes in the button click handler
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, stringURL, null,
                        (response) -> {
                            try {
                                JSONObject mainObject = response.getJSONObject("main");
                                double current = mainObject.getDouble("temp");
                                double min = mainObject.getDouble("temp_min");
                                double max = mainObject.getDouble("temp_max");
                                int humidity = mainObject.getInt("humidity");

                                JSONArray weather = response.getJSONArray("weather");
                                JSONObject position = weather.getJSONObject(0);
                                String description = position.getString("description");
                                String icon = position.getString("icon");

                                String pathname = getFilesDir() + "/" + icon + ".png";
                                File file = new File(pathname);
                                if(file.exists()){
                                    image = BitmapFactory.decodeFile(pathname);

                                }else {
                                    ImageRequest imgReq = new ImageRequest("https://openweathermap.org/img/w/" + icon + ".png", new Response.Listener<Bitmap>() {

                                        @Override
                                        public void onResponse(Bitmap bitmap) {
                                            // Do something with loaded bitmap
                                            try {

                                                image = bitmap;
                                                image.compress(Bitmap.CompressFormat.PNG, 100, MainActivity.this.openFileOutput(icon + ".png", Activity.MODE_PRIVATE));

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, 1024, 1024, ImageView.ScaleType.CENTER, null, (error) -> {

                                    });
                                    queue.add(imgReq);
                                }

                                runOnUiThread( (  )  -> {
                                    binding.temp.setText("The current temperature is " + current);
                                    binding.temp.setVisibility(View.VISIBLE);

                                    binding.minTemp.setText("The min temperature is " + min);
                                    binding.minTemp.setVisibility(View.VISIBLE);

                                    binding.maxTemp.setText("The max temperature is " + max);
                                    binding.maxTemp.setVisibility(View.VISIBLE);

                                    binding.humidity.setText("The humidity is " + humidity);
                                    binding.humidity.setVisibility(View.VISIBLE);

                                    binding.description.setText("The description is " + description);
                                    binding.description.setVisibility(View.VISIBLE);});

                                binding.icon.setImageBitmap(image);
                                binding.icon.setVisibility(View.VISIBLE);



                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        },//success call back function
                        // error call back function
                        (error) -> {});

                queue.add(request);



            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        });

    }

}